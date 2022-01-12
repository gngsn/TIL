package proxy.image;

public class Client {
    public static void main(String[] args) {
        final Graphic img1 = new ImageProxy("Image***1");
        final Graphic img2 = new ImageProxy("Image***2");

        img1.showImage();
        img2.showImage();
    }
}