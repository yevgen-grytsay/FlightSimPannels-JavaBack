package com.company.XPlane.Decoder;

import com.company.Decoder;
import com.company.XPlane.Packet;
import org.json.simple.JSONObject;

import java.nio.FloatBuffer;

public class AltitudeDecoder implements Decoder {
    JSONObject root;

    @Override
    public String getName() {
        return "altitude";
    }

    @Override
    public void reset() {
        root = new JSONObject();
    }

    @Override
    public void decode(Packet packet) {
        if (packet.xtype == 20) {
            FloatBuffer floatBuffer = packet.toFloatBuffer();

            root.put("altitude", String.format("%.2f", floatBuffer.get(5)));
        }

        if (packet.xtype == 7) {
            FloatBuffer floatBuffer = packet.toFloatBuffer();
            float baro = floatBuffer.get(0);

            JSONObject pressures = new JSONObject();
            pressures.put("baro", String.format("%.2f", baro));
            pressures.put("mbar", String.format("%.2f", baro * 33.863886f));

            root.put("pressures", pressures);
        }
    }

    @Override
    public JSONObject getResult() {
        return root;
    }

    private void put(String name, float value) {
        root.put(name, String.format("%.2f", value));
    }
}
