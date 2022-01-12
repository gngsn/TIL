package proxy.image;

public class ImageProxy implements Graphic {
    private Image image;
    private String fileName;

    public ImageProxy(String fileName) {
        this.fileName = fileName;
    }

    public Image getImage() {
        if(this.image == null){
            this.image = new Image(this.fileName);
        }
        return image;
    }

    @Override
    public void showImage() {
        if(this.image == null) {
            this.image = getImage();
        }
        getImage().showImage();
    }
}
