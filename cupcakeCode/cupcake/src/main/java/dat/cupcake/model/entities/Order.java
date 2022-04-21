package dat.cupcake.model.entities;

public class Order {
    private int idOrder;
    private int idCustomer;
    private int idCakeBot;
    private int idCakeTop;
    private int amount;
    private String paid;


    public Order(int idOrder, int idCustomer, int idCakeBot, int idCakeTop, int amount, String paid) {
        this.idOrder = idOrder;
        this.idCustomer = idCustomer;
        this.idCakeBot = idCakeBot;
        this.idCakeTop = idCakeTop;
        this.amount = amount;
        this.paid = paid;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public int getIdCakeBot() {
        return idCakeBot;
    }

    public int getIdCakeTop() {
        return idCakeTop;
    }

    public int getAmount() {
        return amount;
    }

    public String stringAmount() {
        return ""+this.amount;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return getIdOrder() == order.getIdOrder() && getIdCustomer() == order.getIdCustomer() &&
                getIdCakeBot() == order.getIdCakeBot() && getIdCakeTop() == order.getIdCakeTop() &&
                getAmount() == order.getAmount() && getPaid().equals(order.getPaid());
    }
}
