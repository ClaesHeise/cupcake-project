package dat.cupcake.model.entities;

public class CompleteOrder {
    int idCustomer;
    String cupBot;
    int cupBotPrice;
    String cupTop;
    int cupTopPrice;
    int amount;

    public CompleteOrder(int idCustomer, String cupBot, int cupBotPrice, String cupTop, int cupTopPrice, int amount) {
        this.idCustomer = idCustomer;
        this.cupBot = cupBot;
        this.cupBotPrice = cupBotPrice;
        this.cupTop = cupTop;
        this.cupTopPrice = cupTopPrice;
        this.amount = amount;
    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public String getCupBot() {
        return cupBot;
    }

    public int getCupBotPrice() {
        return cupBotPrice;
    }

    public String getCupTop() {
        return cupTop;
    }

    public int getCupTopPrice() {
        return cupTopPrice;
    }

    public int getAmount() {
        return amount;
    }

    public String stringAmount() {
        return ""+this.amount;
    }

    public int price(){
        return (this.cupBotPrice+this.cupTopPrice)*this.amount;
    }

    public String stringPrice(){
        return ""+(this.cupBotPrice+this.cupTopPrice)*this.amount;
    }

    @Override
    public String toString() {
        return amount + " stks. | Bund: " + cupBot + ", Top: " + cupTop + "| Pris: " + price() + " kr.";
    }


    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof CompleteOrder)) return false;
        CompleteOrder completeOrder = (CompleteOrder) o;
        return getIdCustomer() == completeOrder.getIdCustomer() && getCupBot().equals(completeOrder.getCupBot()) &&
                getCupBotPrice() == completeOrder.getCupBotPrice() && getCupTop().equals(completeOrder.getCupTop()) &&
                getCupTopPrice() == completeOrder.getCupTopPrice() && getAmount() == completeOrder.getAmount() &&
                price() == completeOrder.price();
    }
}
