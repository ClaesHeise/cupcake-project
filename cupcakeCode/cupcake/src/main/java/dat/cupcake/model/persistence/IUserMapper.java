package dat.cupcake.model.persistence;

import dat.cupcake.model.entities.User;
import dat.cupcake.model.exceptions.DatabaseException;

import java.util.ArrayList;

public interface IUserMapper
{
    public User login(String email, String kodeord) throws DatabaseException;
    public void createUser(String mail, String password, String name, int phone, String role,
                           int cardNr, int expMonth, int expYear, int cvc) throws DatabaseException;
    public ArrayList<User> createCustomerList(String role) throws DatabaseException;
}
