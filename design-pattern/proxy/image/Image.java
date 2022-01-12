package proxy.image;

public class Image implements Graphic {
    private String fileName;

    public Image(String fileName){
        this.fileName = fileName;
    }

    @Override
    public void showImage() {
        System.out.println("Show Image : " + this.fileName);
    }

}