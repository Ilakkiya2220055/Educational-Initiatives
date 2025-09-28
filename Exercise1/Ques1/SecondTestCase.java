interface RouteStrategy {
    void buildRoute(String from, String to);
}
class WalkingRoute implements RouteStrategy {
    public void buildRoute(String from, String to) {
        System.out.println("Walking route from " + from + " to " + to);
    }
}
class DrivingRoute implements RouteStrategy {
    public void buildRoute(String from, String to) {
        System.out.println("Driving route from " + from + " to " + to);
    }
}
class Navigator {
    private RouteStrategy strategy;
    public void setStrategy(RouteStrategy strategy) { this.strategy = strategy; }
    public void buildRoute(String from, String to) { strategy.buildRoute(from, to); }
}
class StrategyDemo {
    public static void main(String[] args) {
        Navigator nav = new Navigator();

        nav.setStrategy(new WalkingRoute());
        nav.buildRoute("Home", "Office");

        nav.setStrategy(new DrivingRoute());
        nav.buildRoute("Home", "Airport");
    }
}
