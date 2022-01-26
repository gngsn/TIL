package composite;

public class Computer {
    private String name = "구성";

    private Monitor monitor;
    private Memory memory;
    private Disk disk;

    public void setMonitor(Monitor monitor) {
        this.monitor = monitor;
    }

    public void setMemory(Memory memory) {
        this.memory = memory;
    }

    public void setDisk(Disk disk) {
        this.disk = disk;
    }

    public String getName() {
        return name;
    }

    public Monitor getMonitor() {
        return monitor;
    }
}
