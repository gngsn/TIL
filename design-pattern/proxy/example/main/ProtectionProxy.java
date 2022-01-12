package proxy.example.main;

import proxy.example.Proxy;
import proxy.example.RealSubject;
import proxy.example.Subject;

public class ProtectionProxy {
    public static void main(String[] args) {
        int ACT1 = 1;
        int ACT2 = 2;

        int permit = ACT1;

        Subject real = new RealSubject();
        Subject proxy = new Proxy(real, permit);

        System.out.println(proxy.action4());
    }
}
