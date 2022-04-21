package dat.cupcake.control;

import dat.cupcake.model.config.ApplicationStart;
import dat.cupcake.model.entities.CompleteOrder;
import dat.cupcake.model.entities.Order;
import dat.cupcake.model.entities.User;
import dat.cupcake.model.exceptions.DatabaseException;
import dat.cupcake.model.persistence.ConnectionPool;
import dat.cupcake.model.persistence.OrderMapper;
import dat.cupcake.model.persistence.UserMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "pay", urlPatterns = {"/pay"} )
public class Pay extends HttpServlet {
    private ConnectionPool connectionPool;

    @Override
    public void init() throws ServletException
    {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        // You shouldn't end up here with a GET-request, thus you get sent back to frontpage
        doPost(request, response);
        response.sendRedirect("index.jsp");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        response.setContentType("text/html");
        HttpSession session = request.getSession();
        String pay;

        session.setAttribute("pay", null);

        UserMapper userMapper = new UserMapper(connectionPool);
        OrderMapper orderMapper = new OrderMapper(connectionPool);
        session = request.getSession();

        User user = (User) session.getAttribute("user");
        int cost = (int) session.getAttribute("ialt");

        if(user.getMoney() >= cost) {
            try {
                user.setMoney(user.getMoney() - cost);
                userMapper.pay(user.getMoney(), user.getIdUser());

                orderMapper.updatePayment(user.getIdUser());
                ArrayList<Order> orders = orderMapper.getOrders(user.getIdUser());
                session.setAttribute("orders", orders);
                ArrayList<CompleteOrder> customerOrder = orderMapper.customerOrders(user.getIdUser());
                session.setAttribute("completeOrders", customerOrder); // adding orders object to session scope
                session.setAttribute("user", user);
                pay = "paid";
                session.setAttribute("pay", pay);
            }
            catch (DatabaseException e)
            {
                Logger.getLogger("web").log(Level.SEVERE, e.getMessage());
                request.setAttribute("errormessage", e.getMessage());
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        }
        else{
            pay = null;
            session.setAttribute("pay", pay);
        }
        request.getRequestDispatcher("betalt.jsp").forward(request, response);
    }

    public void destroy()
    {

    }
}
