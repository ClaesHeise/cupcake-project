package dat.cupcake.model.entities;

import java.util.Objects;

public class User
{
    private int idUser;
    private String mail;
    private String password;
    private String name;
    private int phone;
    private int idCard;
    private String role;
    private int money;

    public User(int idUser, String mail, String password, String name, int phone, int idCard, String role, int money) {
        this.idUser = idUser;
        this.mail = mail;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.idCard = idCard;
        this.role = role;
        this.money = money;
    }

    public int getPhone() {
        return phone;
    }

    public int getIdCard() {
        return idCard;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String username)
    {
        this.name = username;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getRole()
    {
        return role;
    }

    public void setRole(String role)
    {
        this.role = role;
    }

    public int getIdUser() {
        return idUser;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "User{" +
                "idUser=" + idUser +
                ", mail='" + mail + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", phone=" + phone +
                ", idCard=" + idCard +
                ", role='" + role + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getMail().equals(user.getMail()) && getPassword().equals(user.getPassword()) &&
                 getIdUser() == user.getIdUser() && getName().equals(user.getName()) &&
                getPhone() == user.getPhone() && getIdCard() == user.getIdCard() &&
                getRole().equals(user.getRole()) && getMoney() == user.getMoney();
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getName(), getPassword(), getRole());
    }
}
