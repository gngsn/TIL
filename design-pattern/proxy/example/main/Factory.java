package proxy.example.main;

import proxy.example.ProxyFactory;
import proxy.example.Subject;

public class Factory {
    public static void main(String[] args) {
        ProxyFactory factory = new ProxyFactory();
        Subject proxy = factory.getObject();

        System.out.println(proxy.action1());
        System.out.println(proxy.action2());
    }
}
