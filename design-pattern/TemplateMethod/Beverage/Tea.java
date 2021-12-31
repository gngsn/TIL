package TemplateMethod.Beverage;

public class Tea extends CaffeineBeverage{
    public void brew() {
        System.out.println("Steeping the Tea");
    }

    public void addCondiments() {
        System.out.println("Adding Lemon");

    }
}
