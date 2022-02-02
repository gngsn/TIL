package Decorator.message;

import java.util.Base64;

public class Encript extends Decorator {
    public Encript(Message message) {
        super(message);
    }

    public String B64Encode(String messageToEncrypt) {
        byte[] bytesEncoded = Base64.getEncoder().encode(messageToEncrypt.getBytes());
        return new String(bytesEncoded);
    }

    @Override
    public void sendMessage(String message) {
        System.out.println("encript : " + message);
        String encodedMessage = B64Encode(message);
        this.getDecoratedMessage().sendMessage(encodedMessage);
    }
}
