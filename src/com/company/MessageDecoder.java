package com.company;

import com.company.XPlane.Packet;

import java.io.ByteArrayInputStream;
import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Arrays;
import java.util.Scanner;

public class MessageDecoder {

    public static String decode(DatagramPacket packet) {
        var data = packet.getData();
        var length = data.length - 1;
        var sentences = length / 36;

        for (int i = 0; i < sentences; ++i) {
//            var startIndex =
            var sentence = Arrays.copyOfRange(data, i*36 + 5, i*36 + 5 + 36);

            var xtype = Integer.valueOf(sentence[0]);
            var dataref = Arrays.copyOfRange(sentence, 4, 36);

            if (xtype == 17) {
//                Scanner sc = new Scanner(new ByteArrayInputStream(dataref));
//                var pitch = sc.nextFloat();
//                var roll = sc.nextFloat();
//                var trueheading = sc.nextFloat();
//                var magheading = sc.nextFloat();

//                ByteBuffer.wrap(Arrays.copyOfRange(dataref, 0, 4)).order(ByteOrder.LITTLE_ENDIAN).getFloat();

//                var pitch = ByteBuffer.wrap(Arrays.copyOfRange(dataref, 0, 4)).order(ByteOrder.LITTLE_ENDIAN).getFloat();
//                var roll = ByteBuffer.wrap(Arrays.copyOfRange(dataref, 4, 8)).order(ByteOrder.LITTLE_ENDIAN).getFloat();


                FloatBuffer floatBuffer = ByteBuffer.wrap(dataref)
                        .order(ByteOrder.LITTLE_ENDIAN)
                        .asFloatBuffer();
                var pitch2 = floatBuffer.get(0);
                var roll2 = floatBuffer.get(1);
                var trueheading = floatBuffer.get(2);
                var magheading = floatBuffer.get(3);

//                System.out.printf("Pitch: %.2f, Roll: %.2f%n", pitch, roll);
                System.out.printf("Pitch2: %.2f, Roll2: %.2f%n", pitch2, roll2);
            }
        }

        return "";
    }
}

/*
17
var data = {
	    pitch: Helper.fix(dataView.getFloat32(0,true)),
	    roll: Helper.fix(dataView.getFloat32(4,true)),
	    trueheading: Helper.fix(dataView.getFloat32(8,true)),
	    magheading: Helper.fix(dataView.getFloat32(12,true))
    };
 */