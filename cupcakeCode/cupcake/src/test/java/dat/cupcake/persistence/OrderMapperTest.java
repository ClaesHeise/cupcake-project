package dat.cupcake.persistence;

import dat.cupcake.model.entities.*;
import dat.cupcake.model.exceptions.DatabaseException;
import dat.cupcake.model.persistence.ConnectionPool;
import dat.cupcake.model.persistence.OrderMapper;
import dat.cupcake.model.persistence.UserMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderMapperTest {
    private final static String USER = "root";
    private final static String PASSWORD = "Dreng127";
    private final static String URL = "jdbc:mysql://localhost:3306/cupcake_test";

    private static ConnectionPool connectionPool;
    private static OrderMapper orderMapper;

    @BeforeAll
    public static void setUpClass() {
        connectionPool = new ConnectionPool(USER, PASSWORD, URL);
        orderMapper = new OrderMapper(connectionPool);
    }

    @BeforeEach
    void setUp()
    {
        try (Connection testConnection = connectionPool.getConnection()) {
            try (Statement stmt = testConnection.createStatement() ) {
                // Remove all rows from all tables
                stmt.execute("DELETE FROM orders");
                // Resets the AUTO_INCREMENT of iduser
                //stmt.execute("ALTER TABLE user AUTO_INCREMENT=1");
                // Indsæt et par brugere
                stmt.execute("INSERT INTO orders (idorder, idcustomer, idcakebot, idcaketop, amount) " +
                        "VALUES (1,1,2,5,20),(2,1,4,2,4)");
            }
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
            fail("Database connection failed");
        }
    }
    // create order - needed

    @Test
    void testConnection() throws SQLException
    {
        Connection connection = connectionPool.getConnection();
        assertNotNull(connection);
        if (connection != null)
        {
            connection.close();
        }
    }
//    public ArrayList<CakeBot> getBottom(ArrayList<Order> orders) throws DatabaseException {
//
//    }

    @Test
    void getOrder() throws  DatabaseException {
        Order expectedOrder = new Order(1, 1, 2, 5, 20, "Not paid");
        ArrayList<Order> expectedOrders = new ArrayList<>();
        expectedOrders.add(expectedOrder);
        ArrayList<Order> actualOrders = orderMapper.getOrders(1);
        for(int i = 0; i<expectedOrders.size(); i++){
            assertEquals(expectedOrders.get(i), actualOrders.get(i));
        }
    }

    @Test
    void getCakeBot() throws DatabaseException {
        CakeBot expectedCake = new CakeBot(2, "Vanilje", 5);
        Order order = new Order(1, 1, 2, 5, 20, "Not paid");
        CakeBot actualCake = orderMapper.getBottom(order);
        assertEquals(expectedCake, actualCake);

    }

    @Test
    void getCakeTop() throws DatabaseException {
        CakeTop expectedCake = new CakeTop(5, "Jordbær", 6);
        Order expectedOrder = new Order(1, 1, 2, 5, 20, "Not paid");
        CakeTop actualCake = orderMapper.getTop(expectedOrder);
        assertEquals(expectedCake, actualCake);
    }

    @Test
    void customerOrder() throws DatabaseException {
        CompleteOrder expectedOrder = new CompleteOrder(1, "Vanilje", 5, "Jordbær", 5, 20);
        ArrayList<CompleteOrder> expectedOrders = new ArrayList<>();
        expectedOrders.add(expectedOrder);
        ArrayList<CompleteOrder> actualOrders = orderMapper.customerOrders(1);
        for(int i = 0; i<expectedOrders.size(); i++){
            assertEquals(expectedOrders.get(i), actualOrders.get(i));
        }
    }
//    @Test
//    void login() throws DatabaseException
//    {
//        User expectedUser = new User(2, "Ben@gmail.com","1234", "Ben",36795665,1, "user");
//        User actualUser = userMapper.login("Ben@gmail.com","1234");
//        assertEquals(expectedUser, actualUser);
//    }
//
//    @Test
//    void invalidPasswordLogin() throws DatabaseException
//    {
//        assertThrows(DatabaseException.class, () -> userMapper.login("user@live.dk","123"));
//    }
//
//    @Test
//    void invalidUserNameLogin() throws DatabaseException
//    {
//        assertThrows(DatabaseException.class, () -> userMapper.login("bob@gmail.com","1234"));
//    }
//
//    @Test
//    void createUser() throws DatabaseException
//    {
//        User newUser = userMapper.createUser("Jill@gmail.com", "1234", "Jill", 34895662, 2, "user");
//        User logInUser = userMapper.login("Jill@gmail.com","1234");
//        User expectedUser = new User(3, "Jill@gmail.com", "1234", "Jill", 34895662, 2, "user");
//        assertEquals(expectedUser, newUser);
//        assertEquals(expectedUser, logInUser);
//
//    }
}
