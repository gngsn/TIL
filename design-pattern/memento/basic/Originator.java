package memento.basic;

public class Originator {
    protected String state;

    public Memento create() {
        System.out.println("메멘토 객체를 생성합니다.");
        return new Memento(this.state);
    }

    public void restore(Memento memento) {
        System.out.println("메멘토 객체로 복원합니다.");
        this.state = memento.getObj();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
