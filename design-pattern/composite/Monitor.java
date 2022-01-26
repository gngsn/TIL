package composite;

import java.util.ArrayList;

public class Monitor {
    public String name = "모니터";
    public ArrayList<Monitor> screen;

    public void addMonitor(Monitor monitor) {
        screen.add(monitor);
    }

    public void show() {
        screen.forEach(s -> System.out.println(s.getName()));
    }

    public String getName() {
        return name;
    }
}