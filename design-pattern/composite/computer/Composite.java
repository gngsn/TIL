package composite.computer;

import java.util.HashMap;

public class Composite extends Component {
    private HashMap<String, Component> children = new HashMap<String, Component>();

    public Composite(String name) {
        this.setName(name);
    }

    public void addNode(Component folder) {
        String name = folder.getName();
        this.children.put(name, folder);
    }

    public void removeNode(Component component) {
        String name = component.getName();
        this.children.remove(name);
    }

    public boolean isNode(Component component) {
        return this.children.size() > 1;
    }

}
