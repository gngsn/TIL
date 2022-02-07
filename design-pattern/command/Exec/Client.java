package command.Exec;

public class Client {
    public static void main(String[] args) {
        Command exec1 = new Exec1();

        Concrete concrete = new Concrete();
        Command exec2 = new Exec2(concrete);

//        exec1.execute();
//        exec2.execute();

        Invoker invoker = new Invoker();

        invoker.setCommand("cmd1", exec1);
        invoker.setCommand("cmd2", exec2);
        invoker.setCommand("cmd3", new Command() {
            @Override
            public void execute() {
                System.out.println("Exec3를 실행합니다.");
            }
            @Override
            public void undo() {
                System.out.println("Exec3 명령을 취소합니다.");
            }
        });

        invoker.execute("cmd1");
        invoker.execute("cmd2");
        invoker.execute("cmd3");

        exec1.undo();
    }
}
