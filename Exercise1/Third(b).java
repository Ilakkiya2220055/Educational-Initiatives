class Lights {
    public void on() { System.out.println("Lights ON"); }
    public void off() { System.out.println("Lights OFF"); }
}
class AC {
    public void on() { System.out.println("AC ON"); }
    public void off() { System.out.println("AC OFF"); }
}
class Security {
    public void activate() { System.out.println("Security activated"); }
    public void deactivate() { System.out.println("Security deactivated"); }
}
class HomeFacade {
    private Lights lights = new Lights();
    private AC ac = new AC();
    private Security security = new Security();
    public void leaveHome() {
        lights.off();
        ac.off();
        security.activate();
        System.out.println("Leaving home setup done");
    }
    public void arriveHome() {
        lights.on();
        ac.on();
        security.deactivate();
        System.out.println("Arriving home setup done");
    }
}
class FacadeDemo {
    public static void main(String[] args) {
        HomeFacade home = new HomeFacade();
        home.arriveHome();
        home.leaveHome();
    }
}
