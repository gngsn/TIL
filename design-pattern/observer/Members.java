package observer;

import java.util.ArrayList;

public class Members implements Subject {
    private ArrayList<Observer> objs;

    public Members() {
        objs = new ArrayList<>();
    }

    @Override
    public void addObserver(Observer observer) {
        objs.add(observer);
    }

    @Override
    public void deleteObserver(Observer observer) {
        int index = objs.indexOf(observer);
        objs.remove(index);
    }

    @Override
    public void notiObserver() {
        for(Observer obj : objs) {
            obj.update();
        }
    }
}