package observer;

public class UserB implements Observer {
    private String name;

    public UserB(String name) {
        this.name = name;
    }

    @Override
    public void update() {
        System.out.println(name + " 갱신됩니다.");
    }
}