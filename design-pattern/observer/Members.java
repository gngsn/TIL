package observer;

import java.util.ArrayList;

public class Members implements Subject {
    private ArrayList<Observer> objs;

    public Members() {
        objs = new ArrayList<>();
    }

    @Override
    public void attach(Observer observer) {
        objs.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        int index = objs.indexOf(observer);
        objs.remove(index);
    }

    @Override
    public void noti() {
        for(Observer obj : objs) {
            obj.update();
        }
    }
}