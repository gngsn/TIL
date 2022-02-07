package command.Exec;

// ConcreteCommand
public class Exec1 implements Command {
    public Exec1() {
        System.out.println("Exec1 객체를 구현합니다.");
    }

    @Override
    public void execute() {
        System.out.println("Exec1 실행합니다.");
        // additional code here
    }

    @Override
    public void undo() {
        System.out.println("Exec1 명령을 취소합니다.");
    }
}
