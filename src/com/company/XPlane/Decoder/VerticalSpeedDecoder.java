package com.company.XPlane.Decoder;

import com.company.Decoder;
import com.company.XPlane.Packet;
import org.json.simple.JSONObject;

import java.nio.FloatBuffer;

public class VerticalSpeedDecoder implements Decoder {
    private JSONObject root;

    @Override
    public String getName() {
        return "verticalspeed";
    }

    @Override
    public void reset() {
        root = new JSONObject();
    }

    @Override
    public void decode(Packet packet) {
        if (packet.isVerticalSpeed()) {
            FloatBuffer floatBuffer = packet.toFloatBuffer();

            root.put("mach", String.format("%.2f", floatBuffer.get(0)));
            root.put("speed", String.format("%.2f", floatBuffer.get(2)));
        }
    }

    @Override
    public JSONObject getResult() {
        return root;
    }
}
