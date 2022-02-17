package memento.editor;

// Memento
public class Snapshot {
    private Editor editor;
    private String text;
    private int posX, posY;

    public Snapshot(Editor editor, String text, int posX, int posY) {
        this.editor = editor;
        this.text = text;
        this.posX = posX;
        this.posY = posY;
    }

    public String getText() {
        return text;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }
}
