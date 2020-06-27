package msgProtocal;

public class HeartBeatInfo {
    private int version;
    private int brokerId;
    private long timestamp;
    private BrokerState state;

    public HeartBeatInfo(int version, int brokerId, long timestamp, BrokerState state) {
        this.version = version;
        this.brokerId = brokerId;
        this.timestamp = timestamp;
        this.state = state;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(int brokerId) {
        this.brokerId = brokerId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public BrokerState getState() {
        return state;
    }

    public void setState(BrokerState state) {
        this.state = state;
    }
}
