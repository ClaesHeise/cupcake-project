package dat.cupcake.persistence;

import dat.cupcake.model.entities.User;
import dat.cupcake.model.exceptions.DatabaseException;
import dat.cupcake.model.persistence.ConnectionPool;
import dat.cupcake.model.persistence.UserMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest
{
    private final static String USER = "root";
    private final static String PASSWORD = "Dreng127";
    private final static String URL = "jdbc:mysql://localhost:3306/cupcake_test?serverTimezone=CET&allowPublicKeyRetrieval=true&useSSL=false";

    private static ConnectionPool connectionPool;
    private static UserMapper userMapper;

    @BeforeAll
    public static void setUpClass() {
            connectionPool = new ConnectionPool(USER, PASSWORD, URL);
            userMapper = new UserMapper(connectionPool);
    }

    @BeforeEach
    void setUp()
    {
        try (Connection testConnection = connectionPool.getConnection()) {
            try (Statement stmt = testConnection.createStatement() ) {
                // Remove all rows from all tables
                stmt.execute("delete from user");
                // Resets the AUTO_INCREMENT of iduser
                //stmt.execute("ALTER TABLE user AUTO_INCREMENT=1");
                // Indsæt et par brugere
                stmt.execute("insert into user (iduser, mail, password, name, phone, idcard, role) " +
                        "values (1, 'user@live.dk','1234','user',12345678,0,'user'),(0,'admin@live.dk','4321','admin',87654321,0,'admin'), " +
                        "(2,'Ben@gmail.com','1234','Ben',36795665,1,'user');");
            }
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
            fail("Database connection failed");
        }
    }

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

    @Test
    void login() throws DatabaseException
    {
        User expectedUser = new User(2, "Ben@gmail.com","1234", "Ben",36795665,1, "user", 0);
        User actualUser = userMapper.login("Ben@gmail.com","1234");
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void invalidPasswordLogin() throws DatabaseException
    {
        assertThrows(DatabaseException.class, () -> userMapper.login("user@live.dk","123"));
    }

    @Test
    void invalidUserNameLogin() throws DatabaseException
    {
        assertThrows(DatabaseException.class, () -> userMapper.login("bob@gmail.com","1234"));
    }

    @Test
    void createUser() throws DatabaseException
    {
        userMapper.createUser("Jill@gmail.com", "1234", "Jill", 34895662,  "user", 4571, 5, 20, 876);
        User logInUser = userMapper.login("Jill@gmail.com","1234");
        User expectedUser = new User(3, "Jill@gmail.com", "1234", "Jill", 34895662, 2, "user", 0);
        //assertEquals(expectedUser, newUser);
        assertEquals(expectedUser, logInUser);

    }
}