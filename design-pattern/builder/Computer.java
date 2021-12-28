package builder;

import java.util.ArrayList;

public class Computer {
    public String cpu;
    public ArrayList<Integer> ram;
    public ArrayList<Integer> storage;

    public Computer() {
        System.out.println("Computer 객체 생성");
    }

    public String toString() {
        return "이 컴퓨터의 사양은 CPU="+ this.cpu +
                ", RAM=" + this.ram.toString() +
                ", Storage=" + this.storage.toString() +
                " 입니다.\n";
    }

    public int memory() {
        int size = 0;

        for (Integer mem: ram) {
            size += mem;
        }

        return size;
    }

    public int storage() {
        int size = 0;

        for (Integer disk: this.storage) {
            size += disk;
        }

        return size;
    }

}
