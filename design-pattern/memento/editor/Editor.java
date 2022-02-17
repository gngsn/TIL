package memento.editor;

// Originator
public class Editor {
    private String text;
    private int posX, posY;

    public Snapshot create() {
        System.out.println("Create Snapshot");
        return new Snapshot();
    }

    public void restore(Snapshot snapshot) {
        System.out.println("Restore to Snapshot");
        this.text = snapshot.getText();
        this.posX = snapshot.getPosX();
        this.posY = snapshot.getPosY();
    }

    public String getstate() {
        return "Text (" + posX + ", "+ posY + ") : " + this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setPos(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    // Memento
    class Snapshot {
        String text;
        int posX, posY;

        Snapshot() {
            this.text = Editor.this.text;
            this.posX = Editor.this.posX;
            this.posY = Editor.this.posY;
        }

        String getText() {
            return text;
        }

        int getPosX() {
            return posX;
        }

        int getPosY() {
            return posY;
        }
    }


}
