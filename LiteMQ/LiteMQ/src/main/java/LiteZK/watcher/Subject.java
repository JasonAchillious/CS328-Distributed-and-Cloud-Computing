package LiteZK.watcher;

import java.util.ArrayList;
import java.util.List;

public abstract class Subject <V> {
    protected List<Observer> observers = new ArrayList<Observer>();
    protected volatile V state;


    public V getState() {
        return state;
    }

    public synchronized void setState(V state) {
        this.state = state;
        notifyAllObservers();
    }

    public synchronized void attach(Observer observer){
        observers.add(observer);
    }

    public void notifyAllObservers(){
        for (Observer observer : observers) {
            observer.update();
        }
    }
}

