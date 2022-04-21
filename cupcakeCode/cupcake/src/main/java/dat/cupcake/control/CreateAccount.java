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

@WebServlet(name = "createaccount", urlPatterns = {"/createaccount"} )
public class CreateAccount extends HttpServlet {
    private ConnectionPool connectionPool;

    @Override
    public void init() throws ServletException
    {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    private static String USER = "root";
    private static String PASSWORD = "Dreng127";
    private static String URL = "jdbc:mysql://localhost:3306/cupcake";

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
        session.setAttribute("user", null); // adding empty user object to session scope
        UserMapper userMapper = new UserMapper(connectionPool);
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
        OrderMapper orderMapper = new OrderMapper(this.connectionPool = new ConnectionPool(USER, PASSWORD, URL));
        String email = request.getParameter("createEmail");
        String password = request.getParameter("createPassword");
        String name = request.getParameter("createName");
        int phone = Integer.parseInt(request.getParameter("createPhone"));
        int cardNr = Integer.parseInt(request.getParameter("createCardNr"));
        int expMonth = Integer.parseInt(request.getParameter("createExpMonth"));
        int expYear = Integer.parseInt(request.getParameter("createExpYear"));
        int cvc = Integer.parseInt(request.getParameter("createCvc"));

        try
        {
            userMapper.createUser(email, password, name, phone, "user", cardNr, expMonth, expYear, cvc);
            User user = userMapper.login(email, password);
            session = request.getSession();
            session.setAttribute("user", user); // adding user object to session scope
            if(user.getRole().equalsIgnoreCase("user")) {
                session = request.getSession();
                for(Order o : orders){
                    orderMapper.updateOrders(user.getIdUser(), o.getIdOrder());
                }
                ArrayList<CompleteOrder> previousCustomerOrder = orderMapper.customerOrders(user.getIdUser());
                previousCustomerOrder.addAll(customerOrders);
                session.setAttribute("completeOrders", previousCustomerOrder); // adding orders object to session scope
                int ialt = 0;
                for(CompleteOrder co : previousCustomerOrder){
                    ialt += co.price();
                }
                session.setAttribute("ialt", ialt);
                ArrayList<Order> previousOrders = orderMapper.getOrders(user.getIdUser());
                previousOrders.addAll(orders);
                session.setAttribute("orders", previousOrders);
            }
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
        catch (DatabaseException e)
        {
            Logger.getLogger("web").log(Level.SEVERE, e.getMessage());
            request.setAttribute("errormessage", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    public void destroy()
    {

    }
}
