package com.company.XPlane.Decoder;

import com.company.Decoder;
import com.company.XPlane.Packet;
import org.json.simple.JSONObject;

import java.nio.FloatBuffer;

public class DgDecoder implements Decoder {
    JSONObject root;

    @Override
    public String getName() {
        return "dg";
    }

    @Override
    public void reset() {
        root = new JSONObject();
    }

    @Override
    public void decode(Packet packet) {
        if (packet.isAttitude()) {
            FloatBuffer floatBuffer = packet.toFloatBuffer();

            root.put("pitch", String.format("%.2f", floatBuffer.get(0)));
            root.put("roll", String.format("%.2f", floatBuffer.get(1)));
            root.put("trueheading", String.format("%.2f", floatBuffer.get(2)));
            root.put("magheading", String.format("%.2f", floatBuffer.get(3)));
        }

        if (packet.xtype == 18) {
            FloatBuffer floatBuffer = packet.toFloatBuffer();
            var slip = floatBuffer.get(7);
            root.put("slip", String.format("%.2f", slip));
        }
    }

    @Override
    public JSONObject getResult() {
        return root;
    }
}
