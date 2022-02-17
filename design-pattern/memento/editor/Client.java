package memento.editor;

public class Client {
    public static void main(String[] args) {
        Editor editor = new Editor();
        Command care = new Command();

        editor.setText("design pattern 1 - Memento");
        editor.setPos(10, 24);
        System.out.println(editor.getstate());
        care.makeBackup(editor);


        editor.setText("design pattern 2 - Iterator");
        editor.setPos(22, 24);
        System.out.println(editor.getstate());
        care.makeBackup(editor);

        editor.setText("design pattern 3 - Prototype");
        editor.setPos(44, 24);
        System.out.println(editor.getstate());
        care.makeBackup(editor);

        System.out.println("\n=== 복구 ===\n");

        Editor obj = care.undo(editor);
        System.out.println(obj.getstate());

        obj = care.undo(editor);
        System.out.println(obj.getstate());

        obj = care.undo(editor);
        System.out.println(obj.getstate());
    }
}
