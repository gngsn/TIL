package TemplateMethod.Beverage;

public class Main {
    public static void main(String[] args) {
        Tea tea = new Tea();
        Coffee coffee = new Coffee();

        System.out.println("-- Make a Tea --");
        tea.prepareRecipe();

        System.out.println("\n-- Make a coffee --");
        coffee.prepareRecipe();
    }
}
