package dat.cupcake.model.persistence;

import dat.cupcake.model.entities.*;
import dat.cupcake.model.exceptions.DatabaseException;

import java.sql.*;
import java.util.ArrayList;

public class OrderMapper {
    ConnectionPool connectionPool;

    public OrderMapper(ConnectionPool connectionPool)
    {
        this.connectionPool = connectionPool;
    }

    // Comment: Lav en funktion med JOIN af orders, cuptop og cupbot istedet
    public ArrayList<Order> getOrders(int idCustomer) throws DatabaseException {
        ArrayList<Order> orders = new ArrayList<>();
        Order order = null;
        int idOrder;
        int idCakeBot;
        int idCakeTop;
        int amount;
        String paid;

        String sql = "SELECT * FROM orders WHERE idcustomer = ?";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, idCustomer);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    idOrder = rs.getInt("idorder");
                    idCakeBot = rs.getInt("idcakebot");
                    idCakeTop = rs.getInt("idcaketop");
                    amount = rs.getInt("amount");
                    paid = rs.getString("payment");
                    order = new Order(idOrder, idCustomer, idCakeBot, idCakeTop, amount, paid);
                    orders.add(order);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new DatabaseException(ex.getMessage());
        }
        return orders;
    }

    public CakeBot getBottom(Order order) throws DatabaseException {

        CakeBot cakeBot = null;
        String nameBot;
        int priceBot;
        int idCakeBot;

        idCakeBot = order.getIdCakeBot();
        String sql = "SELECT * FROM cakebot WHERE idcakebot = "+idCakeBot;

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    nameBot = rs.getString("type");
                    priceBot = rs.getInt("price");
                    cakeBot = new CakeBot(idCakeBot, nameBot, priceBot);
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Error logging in. Something went wrong with the database");
        }
        return cakeBot;
    }

    public CakeTop getTop(Order order) throws DatabaseException {

        CakeTop cakeTop = null;
        String nameTop;
        int priceTop;
        int idCakeTop;

        idCakeTop = order.getIdCakeTop();
        String sql = "SELECT * FROM caketop WHERE idcaketop = "+idCakeTop;

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    nameTop = rs.getString("type");
                    priceTop = rs.getInt("price");
                    cakeTop = new CakeTop(idCakeTop, nameTop, priceTop);
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Error logging in. Something went wrong with the database");
        }
        return cakeTop;
    }

    public ArrayList<CompleteOrder> customerOrders(int idCustomer) throws DatabaseException{
        ArrayList<CompleteOrder> completeOrders = new ArrayList<>();
        CompleteOrder completeOrder;

        ArrayList<Order> orders = getOrders(idCustomer);

        CakeBot bottoms;
        CakeTop tops;

        for(int i = 0; i < orders.size(); i++){
            bottoms = getBottom(orders.get(i));
            tops = getTop(orders.get(i));
            String name1 = bottoms.getNameBot();
            int price1 = bottoms.getPriceBot();
            String name2 = tops.getNameTop();
            int price2 = tops.getPriceTop();
            int amount = orders.get(i).getAmount();
            completeOrder = new CompleteOrder(idCustomer, name1, price1, name2, price2, amount);
            completeOrders.add(completeOrder);
        }
        return completeOrders;
    }

    public ArrayList<CompleteOrder> allCustomerOrders() throws DatabaseException{
        ArrayList<CompleteOrder> completeOrders = new ArrayList<>();
        CompleteOrder completeOrder;

        Order order;

        ArrayList<Order> orders = new ArrayList<>();

        String sql = "SELECT * FROM orders";

        try (Connection connection = connectionPool.getConnection()) {

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int idOrder = rs.getInt("idorder");
                    int idCustomer = rs.getInt("idcustomer");
                    int idCakeBot = rs.getInt("idcakebot");
                    int idCakeTop = rs.getInt("idcaketop");
                    int amount = rs.getInt("amount");
                    String paid = rs.getString("payment");
                    order = new Order(idOrder, idCustomer, idCakeBot, idCakeTop, amount, paid);
                    orders.add(order);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new DatabaseException(ex.getMessage());
        }

        CakeBot bottoms;
        CakeTop tops;

        for(int i = 0; i < orders.size(); i++){
            int idCustomer = orders.get(i).getIdCustomer();
            bottoms = getBottom(orders.get(i));
            tops = getTop(orders.get(i));
            String name1 = bottoms.getNameBot();
            int price1 = bottoms.getPriceBot();
            String name2 = tops.getNameTop();
            int price2 = tops.getPriceTop();
            int amount = orders.get(i).getAmount();
            completeOrder = new CompleteOrder(idCustomer, name1, price1, name2, price2, amount);
            completeOrders.add(completeOrder);
        }
        return completeOrders;
    }

    public CompleteOrder anonymousOrders(Order order, int idCustommer) throws DatabaseException{

        CompleteOrder completeOrder = null;

        CakeBot bottom = getBottom(order);
        CakeTop top = getTop(order);

        String name1 = bottom.getNameBot();
        int price1 = bottom.getPriceBot();
        String name2 = top.getNameTop();
        int price2 = top.getPriceTop();
        int amount = order.getAmount();
        completeOrder = new CompleteOrder(idCustommer, name1, price1, name2, price2, amount);

        return completeOrder;
    }

    public Order createOrder(int idcustomer, int idcakebot, int idcaketop, int amount) throws DatabaseException
    {
        Order order;
        String paid = "Not paid";

        String sql = "insert into orders (idcustomer, idcakebot, idcaketop, amount) values (?,?,?,?)";;
        try (Connection connection = connectionPool.getConnection())
        {

            try (Statement stmt = connection.createStatement()){
                String sql2 = "alter table orders AUTO_INCREMENT = 1";
                stmt.execute(sql2);
            }
            try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
            {
                ps.setInt(1, idcustomer);
                ps.setInt(2, idcakebot);
                ps.setInt(3, idcaketop);
                ps.setInt(4, amount);
                int rowsAffected = ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                rs.next();
                int idorder = rs.getInt(1);
                order = new Order(idorder, idcustomer, idcakebot, idcaketop, amount, paid);
            }
        }
        catch (SQLException ex)
        {
            throw new DatabaseException(ex, "Could not insert order into database");
        }
        return order;
    }

    public void updateOrders(int idCustommer, int idOrder){
        String sql = "UPDATE orders SET idcustomer = ? WHERE idorder = ?";
        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement stmt = connection.prepareStatement(sql))
            {
                stmt.setInt(1, idCustommer);
                stmt.setInt(2, idOrder);
                stmt.executeUpdate();
            }
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    public void updatePayment(int idCustommer){
        String sql = "UPDATE orders SET payment = 'paid' WHERE idcustomer = ?";
        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement stmt = connection.prepareStatement(sql))
            {
                stmt.setInt(1, idCustommer);
                stmt.executeUpdate();
            }
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

}