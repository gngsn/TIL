package me.gngsn.java8to11;

public class Foo {
    public static void main(String args[]) {

        /*  아래의 lambda 표현식과 동일
        RunSomething runSomething = new RunSomething() {
            @Override
            public void doIt() {
                System.out.println("Hello");
            }
        };
           */

        RunSomething runSomething = () -> System.out.println("Hello");
        runSomething.doIt();
    }
}
