package com.company.XPlane.Decoder;

import com.company.Decoder;
import org.json.simple.JSONObject;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Map;

public class Attitude implements Decoder {
    @Override
    public void decode(DatagramPacket packet) {
//        if (xtype == 17) {
//            FloatBuffer floatBuffer = ByteBuffer.wrap(dataref)
//                    .order(ByteOrder.LITTLE_ENDIAN)
//                    .asFloatBuffer();
//            var pitch2 = floatBuffer.get(0);
//            var roll2 = floatBuffer.get(1);
//            var trueheading = floatBuffer.get(2);
//            var magheading = floatBuffer.get(3);
//        }
    }

    @Override
    public JSONObject getResult() {
        return null;
    }
}
