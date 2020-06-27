package LiteMQ.client.producer;

import java.io.Closeable;
import java.util.concurrent.Future;

public interface Producer<K, V> extends Closeable {

    public Future<RecordMetaData> send(ProducerRecord<K,V> record);

}
