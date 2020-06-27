package LiteZK.watcher;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BrokerListSubject extends Subject<List<Integer>>{
    @Override
    public List<Integer> getState() {
        return state;
    }


    public synchronized void setState(CopyOnWriteArrayList<Integer> state) {
        this.state = state;
    }


    @Override
    public synchronized void attach(Observer observer){
        observers.add(observer);
    }

    @Override
    public void notifyAllObservers(){
        for (Observer observer : observers) {
            observer.update();
        }
    }
}
