import java.util.ArrayList;
import java.util.List;
class Stock {
    private String name;
    private double price;
    private List<Trader> traders = new ArrayList<>();
    public Stock(String name, double price) {
        this.name = name;
        this.price = price;
    }
    public void attach(Trader trader) {
        traders.add(trader);
    }
    public void detach(Trader trader) {
        traders.remove(trader);
    }
    public void setPrice(double price) {
        this.price = price;
        notifyTraders();
    }
    private void notifyTraders() {
        for (Trader t : traders) {
            t.update(this);
        }
    }
    public double getPrice() {
        return price;
    }
    public String getName() {
        return name;
    }
}
interface Trader {
    void update(Stock stock);
}
class Investor implements Trader {
    private String name;
    public Investor(String name) { this.name = name; }

    public void update(Stock stock) {
        System.out.println(name + " notified: " + stock.getName() + " price changed to " + stock.getPrice());
    }
}
class ObserverDemo {
    public static void main(String[] args) {
        Stock apple = new Stock("Apple", 150);
        Investor john = new Investor("John");
        Investor emily = new Investor("Emily");
        apple.attach(john);
        apple.attach(emily);
        apple.setPrice(155); 
        apple.setPrice(160); 
    }
}
