public class PerishableItem extends Product {
    private String expirationDate;

    public PerishableItem(int code, String name, double price, int stock, String expirationDate) {
        super(code, name, price, stock);
        this.expirationDate = expirationDate;
    }

    public String getExpirationDate() { return expirationDate; }
    public void setExpirationDate(String expirationDate) { this.expirationDate = expirationDate; }

    @Override
    public String getDescription() {
        return expirationDate;
    }
}
