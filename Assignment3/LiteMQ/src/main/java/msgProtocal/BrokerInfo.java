package msgProtocal;

public class BrokerInfo {
    private int brokerId;
    private BrokerState brokerState;
    private String brokerHostName;
    private int exportPort;
    private String registryHostName;
    private int registryPort;

    public BrokerInfo(int id, BrokerState bs,
                      String brokerHostName, int exportPort,
                      String registryHostName, int registryPort){
        this.brokerId = id;
        this.brokerState = bs;
        this.brokerHostName = brokerHostName;
        this.exportPort = exportPort;
        this.registryHostName = registryHostName;
        this.registryPort = registryPort;
    }

    public int getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(int brokerId) {
        this.brokerId = brokerId;
    }

    public BrokerState getBrokerState() {
        return brokerState;
    }

    public void setBrokerState(BrokerState brokerState) {
        this.brokerState = brokerState;
    }

    public String getBrokerHostName() {
        return brokerHostName;
    }

    public void setBrokerHostName(String brokerHostName) {
        this.brokerHostName = brokerHostName;
    }

    public int getExportPort() {
        return exportPort;
    }

    public void setExportPort(int exportPort) {
        this.exportPort = exportPort;
    }

    public String getRegistryHostName() {
        return registryHostName;
    }

    public void setRegistryHostName(String registryHostName) {
        this.registryHostName = registryHostName;
    }

    public int getRegistryPort() {
        return registryPort;
    }

    public void setRegistryPort(int registryPort) {
        this.registryPort = registryPort;
    }
}
