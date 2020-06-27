package LiteMQ.client.producer;

public class ProducerRecord<K,V> {
    private String topic;
    private Integer partition;
    private Long timestamp;
    K key;
    V value;
    // Header

    public ProducerRecord(String topic,
                          Integer partition,
                          Long timestamp,
                          K key,
                          V value){
        this.topic = topic;
        this.partition = partition;
        this.timestamp = timestamp;
        this.key = key;
        this.value = value;
    }

    public ProducerRecord(String topic,
                          Integer partition,
                          K key,
                          V value){
        this.topic = topic;
        this.partition = partition;
        this.key = key;
        this.value = value;
    }

    public ProducerRecord(String topic,
                          K key,
                          V value){
        this.topic = topic;
        this.key = key;
        this.value = value;
    }

    public ProducerRecord(String topic,
                          V value){
        this.topic = topic;
        this.value = value;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Integer getPartition() {
        return partition;
    }

    public void setPartition(Integer partition) {
        this.partition = partition;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }
}
