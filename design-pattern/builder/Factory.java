package builder;

import java.util.ArrayList;
import java.util.Arrays;

class Factory extends Builder {

    public Factory() {
        System.out.println("객체를 생성하였습니다.");
    }

    public Factory(Algorithm algorithm) {
        System.out.println("객체를 생성하였습니다.");
        this.algorithm = algorithm;
    }

    public Factory build() {
        System.out.println("-- 빌드 --");
        this.algorithm.setCpu("i7");
        this.algorithm.setRam(new ArrayList<>(Arrays.asList(8, 8)));
        this.algorithm.setStorage(new ArrayList<>(Arrays.asList(256, 512)));

        return this;
    }
}