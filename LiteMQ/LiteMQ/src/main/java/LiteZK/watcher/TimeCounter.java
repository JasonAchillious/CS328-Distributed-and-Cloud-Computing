package LiteZK.watcher;

public class TimeCounter extends Subject<Integer> implements Runnable{
    private int brokerId;
    private int maxLimit;

    public TimeCounter(int brokerId, int maxLimit){
        this.maxLimit = maxLimit;
        this.state = 0;
        this.brokerId = brokerId;
    }

    public int getBrokerId() {
        return brokerId;
    }

    @Override
    public Integer getState() {
        return state;
    }

    @Override
    public synchronized void setState(Integer state) {
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
            //System.out.println("updated");
        }
    }

    @Override
    public void run() {
        try {
            while (true) {

                Thread.sleep(1000);
                this.setState(this.state+1);
                System.out.println(this.state);
                if (this.state > this.maxLimit) {
                    notifyAllObservers();
                    break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
