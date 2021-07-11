package com.company.XPlane.Decoder;

import com.company.Decoder;
import com.company.XPlane.Packet;
import org.json.simple.JSONObject;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

public class SpeedsDecoder implements Decoder {
    private Map<String, String> data;

    @Override
    public String getName() {
        return "speed";
    }

    @Override
    public void reset() {
        data = new HashMap<>();
    }

    @Override
    public void decode(Packet packet) {
        if (packet.isSpeeds()) {
            FloatBuffer floatBuffer = packet.toFloatBuffer();

            put("indicated", floatBuffer.get(0));
            put("equivalent", floatBuffer.get(1));
            put("trueair", floatBuffer.get(2));
            put("truegnd", floatBuffer.get(3));
            put("mph", floatBuffer.get(5));
            put("mphair", floatBuffer.get(6));
            put("mphgnd", floatBuffer.get(7));
        }
    }

    @Override
    public JSONObject getResult() {
        JSONObject root = new JSONObject();
        for (Map.Entry<String, String> entry : data.entrySet()) {
            root.put(entry.getKey(), entry.getValue());
        }

        return root;
    }

    private void put(String name, float value) {
        data.put(name, String.format("%.2f", value));
    }
}
