package command.Editor;

public class Editor {
    private Application app;
    public String selection;
    public String text;

    public Editor(Application app) {
        this.app = app;
    }

    public void copy() {
        System.out.println("copy " + this.selection);
        app.clipboard = this.selection;
    }

    public void delete() {
        System.out.println("delete " + this.selection);
        this.selection = "";
    }

    public void paste() {
        this.selection = app.clipboard;
        System.out.println("paste " + this.selection);
    }
}