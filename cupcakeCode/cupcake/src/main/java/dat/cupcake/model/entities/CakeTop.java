package dat.cupcake.model.entities;

public class CakeTop {
    int idCakeTop;
    String nameTop;
    int priceTop;

    public CakeTop(int idCakeTop, String nameTop, int priceTop) {
        this.idCakeTop = idCakeTop;
        this.nameTop = nameTop;
        this.priceTop = priceTop;
    }

    public int getIdCakeTop() {
        return idCakeTop;
    }

    public String getNameTop() {
        return nameTop;
    }

    public int getPriceTop() {
        return priceTop;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof CakeTop)) return false;
        CakeTop cakeTop = (CakeTop) o;
        return getIdCakeTop() == cakeTop.getIdCakeTop() && getNameTop().equals(cakeTop.getNameTop()) &&
                getPriceTop() == cakeTop.getPriceTop();
    }
}
