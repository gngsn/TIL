package TemplateMethod.Beverage;

public abstract class CaffeineBeverage {
    final void prepareRecipe() {
        boilWater();
        brew();
        pourInCup();
        addCondiments();
    }

    // handle in different ways.
    // there going to have to be declared as abstract.
    abstract void brew();
    abstract void addCondiments();


    void boilWater() {
        System.out.println("Boiling Water");
    }

    void pourInCup() {
        System.out.println("Pouring into Cup");
    }
}
