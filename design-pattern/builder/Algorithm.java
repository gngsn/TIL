package builder;

import java.util.ArrayList;

public abstract class Algorithm {
    protected Computer composite;

    public abstract Algorithm setCpu(String cpu);
    public abstract Algorithm setRam(ArrayList<Integer> ram);
    public abstract Algorithm setStorage(ArrayList<Integer> storage);


    public Computer getInstance() {
        return this.composite;
    }
}
