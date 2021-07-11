package com.company.XPlane.Decoder;

import com.company.Decoder;
import com.company.XPlane.Packet;
import org.json.simple.JSONObject;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

public class AltitudeDecoder implements Decoder {
    private Map<String, String> data;

    @Override
    public String getName() {
        return "altitude";
    }

    @Override
    public void reset() {
        data = new HashMap<>();
    }

    @Override
    public void decode(Packet packet) {
        if (packet.xtype == 20) {
            FloatBuffer floatBuffer = packet.toFloatBuffer();

            put("altitude", floatBuffer.get(5));
        }

        if (packet.xtype == 7) {
            FloatBuffer floatBuffer = packet.toFloatBuffer();
//            put("baro", floatBuffer.get(0));
//            put("mbar", floatBuffer.get(0) * 33.863886f);
            JSONObject pressures = new JSONObject();
            pressures.put("baro", String.format("%.2f", floatBuffer.get(0)));
            pressures.put("mbar", String.format("%.2f", floatBuffer.get(0) * 33.863886f));
            data.put("pressures", pressures.toJSONString());
        }
    }

    @Override
    public JSONObject getResult() {
        JSONObject attitude = new JSONObject();
        for (Map.Entry<String, String> entry : data.entrySet()) {
            attitude.put(entry.getKey(), entry.getValue());
        }

        return attitude;
    }

    private void put(String name, float value) {
        data.put(name, String.format("%.2f", value));
    }
}
