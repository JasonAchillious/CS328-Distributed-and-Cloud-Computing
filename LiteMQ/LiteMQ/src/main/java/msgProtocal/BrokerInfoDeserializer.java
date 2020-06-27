package msgProtocal;

import com.google.gson.*;

import java.lang.reflect.Type;

public class BrokerInfoDeserializer implements JsonDeserializer<BrokerInfo> {

    @Override
    public BrokerInfo deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        String strBs = jsonObject.get("brokerState").getAsString();
        BrokerState bs = null;
        switch (strBs){
            case "valid": bs = BrokerState.valid; break;
            case "timedOut": bs = BrokerState.timedOut; break;
            case "notStarted": bs = BrokerState.notStarted; break;
            case "disallowed": bs = BrokerState.disallowed; break;
            default:
                try {
                    throw new Exception("Deserializing error: not such broker state.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }

        BrokerInfo brokerInfo = new BrokerInfo(
                jsonObject.get("brokerId").getAsInt(),
                bs,
                jsonObject.get("brokerHostName").getAsString(),
                jsonObject.get("exportPort").getAsInt(),
                jsonObject.get("registryHostName").getAsString(),
                jsonObject.get("registryPort").getAsInt()
                );

        //System.out.println(brokerInfo.getBrokerHostName());
        return brokerInfo;
    }
}
