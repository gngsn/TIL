package command.Exec;

        import java.util.HashMap;
        import java.util.Map;

public class Invoker {
    Map<String, Command> cmd = new HashMap<>();

    public void setCommand(String key, Command command) {
        this.cmd.put(key, command);
    }

    public void remove(String key) {
        this.cmd.remove(key);
    }

    public void execute(String key) {
        if (this.cmd.containsKey(key)) {
            this.cmd.get(key).execute();
        }
    }
}
