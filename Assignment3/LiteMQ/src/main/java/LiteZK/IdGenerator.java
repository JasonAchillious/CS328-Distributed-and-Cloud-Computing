package LiteZK;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class IdGenerator {
    AtomicInteger var = new AtomicInteger(0);

    public int getNextId() {
        return var.incrementAndGet();
    }
}
