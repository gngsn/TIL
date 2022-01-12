package proxy.image;

public class ImageProxy implements Graphic {
    private Image image;
    private String fileName;

    public ImageProxy(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void showImage() {
        if(this.image == null){
            this.image = new Image(this.fileName);
        }
        this.image.showImage();
    }
}
