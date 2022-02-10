package iterator.fruit;

public class Client {
    public static void main(String[] args) {
        Collection menu = new Collection(4);
        menu.append(new Fruit("Apple"));
        menu.append(new Fruit("Banana"));
        menu.append(new Fruit("Berry"));
        menu.append(new Fruit("Grape"));

        FruitIterator loop = menu.iterator();

        while(loop.hasNext()) {
            Fruit item = loop.next();
            System.out.println(item.getName());
        }
    }
}
