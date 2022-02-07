package command.Exec;

// ConcreteCommand
public class Exec2 implements Command {
    private Concrete receiver;

    public Exec2(Concrete receiver) {
        System.out.println("Exec2 객체를 구현합니다.");
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        System.out.println("Exec2를 실행합니다.");

        this.receiver.action1();
        this.receiver.action2();
    }

    @Override
    public void undo() {
        System.out.println("Exec2 명령을 취소합니다.");
    }
}
