package com.company.XPlane.Decoder;

import com.company.Decoder;
import com.company.XPlane.Packet;
import org.json.simple.JSONObject;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

public class AttitudeDecoder implements Decoder {
    private Map<String, String> data;

    @Override
    public String getName() {
        return "attitude";
    }

    @Override
    public void reset() {
        data = new HashMap<>();
    }

    @Override
    public void decode(Packet packet) {
        if (packet.isAttitude()) {
            FloatBuffer floatBuffer = packet.toFloatBuffer();
            var pitch = floatBuffer.get(0);
            var roll = floatBuffer.get(1);
            var trueheading = floatBuffer.get(2);
            var magheading = floatBuffer.get(3);

            data.put("pitch", String.format("%.2f", pitch));
            data.put("roll", String.format("%.2f", roll));
            data.put("trueheading", String.format("%.2f", trueheading));
            data.put("magheading", String.format("%.2f", magheading));
        }

        if (packet.xtype == 18) {
            FloatBuffer floatBuffer = packet.toFloatBuffer();
            var slip = floatBuffer.get(7);
            data.put("slip", String.format("%.2f", slip));
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
}
