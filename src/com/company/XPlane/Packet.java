package com.company.XPlane;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Packet {
    public Integer xtype;
    public byte[] dataref;

    public Packet(int xcode, byte[] dataref) {
        this.xtype = xcode;
        this.dataref = dataref;
    }

    public FloatBuffer toFloatBuffer() {
        return ByteBuffer.wrap(dataref)
                .order(ByteOrder.LITTLE_ENDIAN)
                .asFloatBuffer();
    }

    public boolean isAttitude() {
        return xtype == 17;
    }

    public static List<Packet> decode(DatagramPacket packet) {
        var data = packet.getData();
        var length = packet.getLength() - 5;
        var sentences = length / 36;

        var result = new ArrayList<Packet>();
        for (int i = 0; i < sentences; ++i) {
            var sentence = Arrays.copyOfRange(data, i*36 + 5, i*36 + 36);

            var xtype = Byte.toUnsignedInt(sentence[0]);
            var dataref = Arrays.copyOfRange(sentence, 4, 36);

            result.add(new Packet(xtype, dataref));
        }

        return result;
    }

    public boolean isSpeeds() {
        return xtype == 3;
    }

    public boolean isVerticalSpeed() {
        return xtype == 4;
    }
}
