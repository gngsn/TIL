package flyweight.tree;

import java.awt.*;

public class Client {
    static int CANVAS_SIZE = 1000;
    static int TREES_TO_DRAW = 1000000;
    static int TREE_TYPES = 2;

    public static void main(String[] args) {
        Forest forest = new Forest();
        for (int i = 0; i < Math.floor(TREES_TO_DRAW / TREE_TYPES); i++) {
            forest.plantTree(random(), random(),"Summer Oak", Color.GREEN);
            forest.plantTree(random(), random(),"Autumn Oak", Color.ORANGE);
        }

        forest.setSize(CANVAS_SIZE, CANVAS_SIZE);
        forest.setVisible(true);

        System.out.println(TREES_TO_DRAW + " trees drawn");
        System.out.println("---------------------");
        System.out.println("Memory usage:");
        System.out.println("Tree size (8 bytes) * " + TREES_TO_DRAW);
        System.out.println("+ TreeTypes size (~22 bytes) * " + TREE_TYPES + "");
        System.out.println("---------------------");
        System.out.println("Total: " + ((TREES_TO_DRAW * 8 + TREE_TYPES * 22) / 1024 / 1024) +
                "MB (instead of " + ((TREES_TO_DRAW * 30) / 1024 / 1024) + "MB)");
    }

    private static int random() {
        return (int) (Math.random() * (CANVAS_SIZE + 1));
    }
}
