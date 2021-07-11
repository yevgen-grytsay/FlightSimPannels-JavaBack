package com.company.XPlane;

import java.net.DatagramPacket;
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

    public static List<Packet> decode(DatagramPacket packet) {
        var data = packet.getData();
        var length = data.length - 1;
        var sentences = length / 36;

        var result = new ArrayList<Packet>();
        for (int i = 0; i < sentences; ++i) {
//            var startIndex =
            var sentence = Arrays.copyOfRange(data, i * 36 + 5, i * 36 + 5 + 36);

            var xtype = Integer.valueOf(sentence[0]);
            var dataref = Arrays.copyOfRange(sentence, 4, 36);

            result.add(new Packet(xtype, dataref));
        }

        return result;
    }
}
