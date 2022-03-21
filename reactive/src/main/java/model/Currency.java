package model;

public enum Currency {
    USD(1), RUB(101), EUR(0.91);

    private final double coef;

    Currency(double coef) {
        this.coef = coef;
    }

    public double convert(double price, Currency to) {
        return price * to.coef / this.coef;
    }
}
