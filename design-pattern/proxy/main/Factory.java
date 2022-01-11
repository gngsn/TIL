package proxy.main;

import proxy.ProxyFactory;
import proxy.Subject;

public class Factory {
    public static void main(String[] args) {
        ProxyFactory factory = new ProxyFactory();
        Subject proxy = factory.getObject();

        System.out.println(proxy.action1());
        System.out.println(proxy.action2());
    }
}
