interface Notification {
    void notifyUser();
}
class EmailNotification implements Notification {
    public void notifyUser() { System.out.println("Sending Email Notification"); }
}
class SMSNotification implements Notification {
    public void notifyUser() { System.out.println("Sending SMS Notification"); }
}
class NotificationFactory {
    public static Notification createNotification(String type) {
        if (type.equalsIgnoreCase("EMAIL")) return new EmailNotification();
        else if (type.equalsIgnoreCase("SMS")) return new SMSNotification();
        else throw new IllegalArgumentException("Unknown type");
    }
}
class FactoryDemo {
    public static void main(String[] args) {
        Notification n1 = NotificationFactory.createNotification("EMAIL");
        n1.notifyUser();
        Notification n2 = NotificationFactory.createNotification("SMS");
        n2.notifyUser();
    }
}
