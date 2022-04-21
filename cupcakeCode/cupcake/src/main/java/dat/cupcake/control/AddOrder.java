package dat.cupcake.control;

import dat.cupcake.model.config.ApplicationStart;
import dat.cupcake.model.entities.CompleteOrder;
import dat.cupcake.model.entities.Order;
import dat.cupcake.model.entities.User;
import dat.cupcake.model.exceptions.DatabaseException;
import dat.cupcake.model.persistence.OrderMapper;
import dat.cupcake.model.persistence.ConnectionPool;

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

@WebServlet(name = "addorder", urlPatterns = {"/addorder"} )
public class AddOrder extends HttpServlet
{
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
        ArrayList<CompleteOrder> customerOrders = new ArrayList<>();
        ArrayList<Order> orders = new ArrayList<>();

        // If any orders has been made, in the time the user has been on the website, this will get those orders and the complete orders
        // Gets complete orders
        if (session.getAttribute("completeOrders") != null) {
            customerOrders = (ArrayList<CompleteOrder>) session.getAttribute("completeOrders");
        }
        else {
            session.setAttribute("completeOrders", null);
        }
        // Gets orders
        if (session.getAttribute("orders") != null) {
            orders = (ArrayList<Order>) session.getAttribute("orders");
        }
        else {
            session.setAttribute("orders", null);
        }

        OrderMapper orderMapper = new OrderMapper(connectionPool);
        session = request.getSession();
        int cakeBot = Integer.parseInt(request.getParameter("bottom"));
        int cakeTop = Integer.parseInt(request.getParameter("topping"));
        int amount = Integer.parseInt(request.getParameter("amount"));
        if(session.getAttribute("user") != null){
            User user = (User) session.getAttribute("user");
            try {
                Order order = orderMapper.createOrder(user.getIdUser(), cakeBot, cakeTop, amount);
                orders.add(order);
                session.setAttribute("orders", orders);
                customerOrders = orderMapper.customerOrders(user.getIdUser());
                session.setAttribute("completeOrders", customerOrders);
            }
            catch (DatabaseException e)
            {
                Logger.getLogger("web").log(Level.SEVERE, e.getMessage());
                request.setAttribute("errormessage", e.getMessage());
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        }
        else{
            try {
                Order order = orderMapper.createOrder(0, cakeBot, cakeTop, amount);
                orders.add(order);
                session.setAttribute("orders", orders);
                customerOrders.add(orderMapper.anonymousOrders(order, 0));
                session.setAttribute("completeOrders", customerOrders);
                int ialt = 0;
                for(CompleteOrder co : customerOrders){
                    ialt += co.price();
                }
                session.setAttribute("ialt", ialt);
            }
            catch (DatabaseException e)
            {
                Logger.getLogger("web").log(Level.SEVERE, e.getMessage());
                request.setAttribute("errormessage", e.getMessage());
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        }
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    public void destroy()
    {

    }
}
