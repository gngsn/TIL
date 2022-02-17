package memento.editor;

// Originator
public class Editor {
    protected String text;
    protected int posX, posY;

    public Snapshot create() {
        System.out.println("Create Snapshot");
        return new Snapshot(this, text, posX, posY);
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

}
