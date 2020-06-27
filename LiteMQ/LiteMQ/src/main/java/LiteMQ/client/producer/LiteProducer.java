package LiteMQ.client.producer;

import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.Future;

public class LiteProducer<K , V> implements Producer<K,V> {
    private String host;
    private int port;
    private InetAddress address;
    private String acks;
    private Class keyClass;
    private Class valClass;


    @Override
    public Future<RecordMetaData> send(ProducerRecord<K, V> record) {
        return null;
    }

    public void close() throws IOException {

    }
}
