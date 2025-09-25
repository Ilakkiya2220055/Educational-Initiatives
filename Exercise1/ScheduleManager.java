import java.util.*;

interface Observer {
    void update(String message);
}

interface Subject {
    void register(Observer o);
    void unregister(Observer o);
    void notifyObservers(String message);
}

class ScheduleManager implements Subject {
    private final List<Observer> observers = new ArrayList<>();
    private final List<Task> tasks = new ArrayList<>();


    public void register(Observer o) { observers.add(o); }


    public void unregister(Observer o) { observers.remove(o); }


    public void notifyObservers(String message) {
        for (Observer o : observers) o.update(message);
    }

    public boolean addTask(Task t) {
        for (Task existing : tasks) {
            if (existing.overlapsWith(t)) {
                notifyObservers("Conflict: " + t.name + " overlaps with " + existing.name);
                return false;
            }
        }
        tasks.add(t);
        notifyObservers("Task added: " + t.name);
        return true;
    }

    public List<Task> listTasks() {
        tasks.sort(Comparator.comparing(Task::getStart));
        return Collections.unmodifiableList(tasks);
    }
}

class Task {
    String name;
    int start;
    int end;

    Task(String name, int start, int end) {
        this.name = name; this.start = start; this.end = end;
        if (start >= end) throw new IllegalArgumentException("start < end required");
    }

    boolean overlapsWith(Task other) {
        return !(this.end <= other.start || this.start >= other.end);
    }

    int getStart() { return start; }

    @Override
    public String toString() {
        return String.format("%02d:%02d-%02d:%02d %s",
                start/60, start%60, end/60, end%60, name);
    }
}

class ConsoleSubscriber implements Observer {
    private final String id;
    ConsoleSubscriber(String id) { this.id = id; }
    @Override
    public void update(String message) {
        System.out.println("[" + id + "] " + message);
    }
}

public class ObserverExample {
    public static void main(String[] args) {
        ScheduleManager manager = new ScheduleManager();
        manager.register(new ConsoleSubscriber("UserA"));
        manager.register(new ConsoleSubscriber("Logger"));

        Task t1 = new Task("Morning Exercise", 7*60, 8*60);
        Task t2 = new Task("Team Meeting", 9*60, 10*60);
        Task t3 = new Task("Training Session", 9*60 + 30, 10*60 + 30);

        manager.addTask(t1);
        manager.addTask(t2);
        manager.addTask(t3);

        System.out.println("Current tasks:");
        for (Task t : manager.listTasks()) System.out.println("  " + t);
    }
}
