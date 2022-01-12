package proxy.example.main;

import proxy.example.ProxyFactory;
import proxy.example.Subject;

public class Client {
    public static void main(String[] args) {
//        Subject real = new RealSubject();
//        Subject proxy = new Proxy(real);
        int ACT1 = 1;
        int ACT2 = 2;

        int permit = ACT1;

        ProxyFactory factory = new ProxyFactory();
        Subject proxy = factory.getObject();

        System.out.println(proxy.action1());
        System.out.println(proxy.action2());
    }
}
