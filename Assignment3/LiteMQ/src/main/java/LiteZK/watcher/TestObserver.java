package LiteZK.watcher;

public class TestObserver extends Observer {

    public TestObserver(TimeCounter s){
        this.subject = s;
    }

    @Override
    public void update() {
        System.out.println(this.subject.getState());
    }
}
