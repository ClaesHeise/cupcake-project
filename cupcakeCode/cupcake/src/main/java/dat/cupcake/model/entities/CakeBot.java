package dat.cupcake.model.entities;

public class CakeBot {
    int idCakeBot;
    String nameBot;
    int priceBot;

    public CakeBot(int idCakeBot, String nameBot, int priceBot) {
        this.idCakeBot = idCakeBot;
        this.nameBot = nameBot;
        this.priceBot = priceBot;
    }

    public int getIdCakeBot() {
        return idCakeBot;
    }

    public String getNameBot() {
        return nameBot;
    }

    public int getPriceBot() {
        return priceBot;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof CakeBot)) return false;
        CakeBot cakeBot = (CakeBot) o;
        return getIdCakeBot() == cakeBot.getIdCakeBot() && getNameBot().equals(cakeBot.getNameBot()) &&
                getPriceBot() == cakeBot.getPriceBot();
    }
}
