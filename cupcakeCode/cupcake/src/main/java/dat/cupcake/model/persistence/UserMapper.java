package dat.cupcake.model.persistence;

import dat.cupcake.model.entities.User;
import dat.cupcake.model.exceptions.DatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserMapper implements IUserMapper
{
    ConnectionPool connectionPool;

    public UserMapper(ConnectionPool connectionPool)
    {
        this.connectionPool = connectionPool;
    }

    @Override
    public User login(String mail, String password) throws DatabaseException
    {
        Logger.getLogger("web").log(Level.INFO, "");

        User user = null;

        String sql = "SELECT * FROM user WHERE mail = ? AND password = ?";


        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ps.setString(1, mail);
                ps.setString(2, password);
                ResultSet rs = ps.executeQuery();
                if (rs.next())
                {
                    int idUser = rs.getInt("iduser");
                    String name = rs.getString("name");
                    int phone = rs.getInt("phone");
                    int idCard = rs.getInt("idcard");
                    String role = rs.getString("role");
                    int money = rs.getInt("money");
                    user = new User(idUser, mail, password, name, phone, idCard, role, money);
                } else
                {
                    throw new DatabaseException("Wrong username or password");
                }
            }
        } catch (SQLException ex)
        {
            throw new DatabaseException(ex, "Error logging in. Something went wrong with the database");
        }
        return user;
    }

    @Override
    public ArrayList<User> createCustomerList(String role) throws DatabaseException{
        ArrayList<User> customers = new ArrayList<>();

        Logger.getLogger("web").log(Level.INFO, "");

        User user = null;

        String sql = "SELECT * FROM user WHERE role = ?";


        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ps.setString(1, role);
                ResultSet rs = ps.executeQuery();
                while (rs.next())
                {
                    int idUser = rs.getInt("iduser");
                    String mail = rs.getString("mail");
                    String password = rs.getString("password");
                    String name = rs.getString("name");
                    int phone = rs.getInt("phone");
                    int idCard = rs.getInt("idcard");
                    int money = rs.getInt("money");
                    user = new User(idUser, mail, password, name, phone, idCard, role, money);
                    customers.add(user);
                }
            }
        } catch (SQLException ex)
        {
            throw new DatabaseException(ex, "Error logging in. Something went wrong with the database");
        }
        return customers;

    }

    private void createCard(int idCard, int cardNr, int expMonth, int expYear, int cvc) throws DatabaseException {

        String sql = "insert into paycard (idcard, cardnr, expmonth, expyear, cvc) values (?,?,?,?,?)";

        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ps.setInt(1, idCard);
                ps.setInt(2, cardNr);
                ps.setInt(3, expMonth);
                ps.setInt(4, expYear);
                ps.setInt(5, cvc);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected != 1)
                {
                    throw new DatabaseException("Dankortet kan ikke bruges");
                }
            }
        }
        catch (SQLException ex)
        {
            throw new DatabaseException(ex, "Could not insert username into database");
        }
    }

    @Override
    public void createUser(String mail, String password, String name, int phone, String role,
                           int cardNr, int expMonth, int expYear, int cvc) throws DatabaseException
    {

        int id = createCustomerList("user").size()+1;
        createCard(id, cardNr, expMonth, expYear, cvc);
        String sql = "insert into user (iduser, mail, password, name, phone, idcard, role) values (?,?,?,?,?,?,?)";

        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ps.setInt(1, id);
                ps.setString(2, mail);
                ps.setString(3, password);
                ps.setString(4, name);
                ps.setInt(5, phone);
                ps.setInt(6, id);
                ps.setString(7, role);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected != 1)
                {
                    throw new DatabaseException("The user with mail = " + mail + " could not be inserted into the database");
                }
            }
        }
        catch (SQLException ex)
        {
            throw new DatabaseException(ex, "Could not insert username into database");
        }
    }

    public void pay(int money, int idUser){

        String sql = "UPDATE user SET money = ? WHERE iduser = ?";
        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement stmt = connection.prepareStatement(sql))
            {
                stmt.setInt(1, money);
                stmt.setInt(2, idUser);
                stmt.executeUpdate();
            }
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }
}
