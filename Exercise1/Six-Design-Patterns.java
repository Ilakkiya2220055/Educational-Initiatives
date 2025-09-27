public class AppLogger {
    private static AppLogger instance;
    private AppLogger() {}
    public static synchronized AppLogger getInstance() {
        if (instance == null) instance = new AppLogger();
        return instance;
    }
    public void info(String msg) { System.out.println("[INFO] " + msg); }
    public void error(String msg) { System.err.println("[ERROR] " + msg); }
}
public interface Subscriber {
    void update(String news);
}
import java.util.ArrayList;
import java.util.List;
public class NewsPublisher {
    private final List<Subscriber> subscribers = new ArrayList<>();
    public void subscribe(Subscriber s) { subscribers.add(s); }
    public void unsubscribe(Subscriber s) { subscribers.remove(s); }
    public void publish(String news) {
        AppLogger.getInstance().info("Publishing: " + news);
        for (Subscriber s : new ArrayList<>(subscribers)) {
            try { s.update(news); } catch (Exception ex) { AppLogger.getInstance().error("Subscriber failed: " + ex.getMessage()); }
        }
    }
}
public class ConsoleSubscriber implements Subscriber {
    private final String name;
    public ConsoleSubscriber(String name) { this.name = name; }
    public void update(String news) { System.out.println(name + " received -> " + news); }
}
public interface SortStrategy {
    void sort(int[] arr);
}
public class BubbleSortStrategy implements SortStrategy {
    public void sort(int[] arr) {
        int n = arr.length;
        for (int i=0;i<n-1;i++){
            for (int j=0;j<n-1-i;j++){
                if (arr[j] > arr[j+1]) {
                    int t = arr[j]; arr[j]=arr[j+1]; arr[j+1]=t;
                }
            }
        }
    }
}
public class QuickSortStubStrategy implements SortStrategy {
    public void sort(int[] arr) {
        quick(arr,0,arr.length-1);
    }
    private void quick(int[] a,int lo,int hi){
        if(lo>=hi) return;
        int p = partition(a,lo,hi);
        quick(a,lo,p-1);
        quick(a,p+1,hi);
    }
    private int partition(int[] a,int lo,int hi){
        int pivot = a[hi];
        int i=lo;
        for(int j=lo;j<hi;j++){
            if(a[j] < pivot){
                int t=a[i]; a[i]=a[j]; a[j]=t; i++;
            }
        }
        int t=a[i]; a[i]=a[hi]; a[hi]=t;
        return i;
    }
}

public class Sorter {
    private SortStrategy strategy;
    public Sorter(SortStrategy s) { this.strategy = s; }
    public void setStrategy(SortStrategy s) { this.strategy = s; }
    public void sort(int[] arr) { strategy.sort(arr); }
}
public interface Animal {
    String speak();
}
public class Dog implements Animal {
    public String speak() { return "Woof"; }
}
public class Cat implements Animal {
    public String speak() { return "Meow"; }
}
public class AnimalFactory {
    public static Animal create(String type) {
        if ("dog".equalsIgnoreCase(type)) return new Dog();
        if ("cat".equalsIgnoreCase(type)) return new Cat();
        throw new IllegalArgumentException("Unknown animal: " + type);
    }
}
public class RoundHole {
    private final int radius;
    public RoundHole(int r) { this.radius = r; }
    public boolean fits(RoundPeg p) { return p.getRadius() <= radius; }
}
public class RoundPeg {
    private final int radius;
    public RoundPeg(int r){ this.radius = r; }
    public int getRadius() { return radius; }
}
public class SquarePeg {
    private final int width;
    public SquarePeg(int width) { this.width = width; }
    public int getWidth() { return width; }
}
public class SquarePegAdapter extends RoundPeg {
    private final SquarePeg peg;
    public SquarePegAdapter(SquarePeg peg) {
        super( (int) Math.ceil( (peg.getWidth() * Math.sqrt(2)) / 2.0) ); 
        this.peg = peg;
    }
}
public interface Image {
    void display();
}
public class RealImage implements Image {
    private final String fileName;
    public RealImage(String fileName) { this.fileName = fileName; loadFromDisk(); }
    private void loadFromDisk() { AppLogger.getInstance().info("Loading image from disk: " + fileName); }
    public void display() { System.out.println("Displaying " + fileName); }
}
public class ImageProxy implements Image {
    private final String fileName;
    private RealImage real;
    public ImageProxy(String fileName) { this.fileName = fileName; }
    public void display() {
        if (real == null) real = new RealImage(fileName);
        real.display();
    }
}
public class DemoMain {
    public static void main(String[] args) {
        AppLogger.getInstance().info("=== Exercise 1 Demo ===");

        NewsPublisher publisher = new NewsPublisher();
        Subscriber a = new ConsoleSubscriber("Alice");
        Subscriber b = new ConsoleSubscriber("Bob");
        publisher.subscribe(a); publisher.subscribe(b);
        publisher.publish("New design pattern tutorial released!");
        int[] arr = {5,3,8,1,2};
        Sorter sorter = new Sorter(new BubbleSortStrategy());
        sorter.sort(arr);
        System.out.print("Bubble sorted: ");
        for(int v:arr) System.out.print(v + " ");
        System.out.println();
        int[] arr2 = {9,4,7,2};
        sorter.setStrategy(new QuickSortStubStrategy());
        sorter.sort(arr2);
        System.out.print("Quick sorted: ");
        for(int v:arr2) System.out.print(v + " ");
        System.out.println();

        
        Animal dog = AnimalFactory.create("dog");
        Animal cat = AnimalFactory.create("cat");
        System.out.println("Dog says: " + dog.speak() + " | Cat says: " + cat.speak());

        
        RoundHole hole = new RoundHole(5);
        SquarePeg smallPeg = new SquarePeg(7);
        SquarePegAdapter adapted = new SquarePegAdapter(smallPeg);
        System.out.println("Does square peg fit? " + hole.fits(adapted));

        
        Image image = new ImageProxy("photo.png");
        image.display(); 
        image.display(); 

        AppLogger.getInstance().info("=== End Demo ===");
    }
}
