package com.company.XPlane.Decoder;

import com.company.Decoder;
import com.company.XPlane.Packet;
import org.json.simple.JSONObject;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

public class VerticalSpeedDecoder implements Decoder {
    private Map<String, String> data;

    @Override
    public String getName() {
        return "verticalspeed";
    }

    @Override
    public void reset() {
        data = new HashMap<>();
    }

    @Override
    public void decode(Packet packet) {
        if (packet.isVerticalSpeed()) {
            FloatBuffer floatBuffer = packet.toFloatBuffer();

            put("mach", floatBuffer.get(0));
            put("speed", floatBuffer.get(2));
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
