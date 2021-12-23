package observer;

public class UserA implements Observer {
    private String name;

    public UserA(String name) {
        this.name = name;
    }

    @Override
    public void update() {
        System.out.println(name + " 갱신됩니다.");
    }
}