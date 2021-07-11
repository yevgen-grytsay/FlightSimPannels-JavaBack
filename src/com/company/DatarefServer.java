package com.company;

import com.company.XPlane.Decoder.SpeedsDecoder;
import com.company.XPlane.Decoder.AttitudeDecoder;
import com.company.XPlane.DecoderChain;
import com.company.XPlane.Packet;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.List;
import java.util.Queue;

public class DatarefServer extends Thread {

    private final Queue<String> bus;
    private DatagramSocket socket;
    private boolean running;
    private byte[] buf = new byte[1024];

    public DatarefServer(Queue<String> bus) {
        this.bus = bus;
        try {
            socket = new DatagramSocket(49100);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    public void run() {
        running = true;

        DecoderChain chain = new DecoderChain();
        chain
                .add(new AttitudeDecoder())
                .add(new SpeedsDecoder())
        ;

        while (running) {
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            List<Packet> chunks = Packet.decode(packet);
            JSONObject data = chain.decode(chunks);

            JSONObject dataObj = new JSONObject();
            dataObj.put("data", data);

            JSONArray args = new JSONArray();
            args.add(dataObj);

            JSONObject root = new JSONObject();
            root.put("name", "data:measures");
            root.put("args", args);

            System.out.println(root.toJSONString());
            try {
                this.bus.add(root.toJSONString());
            } catch (IllegalStateException e) {
                System.out.println("Illegal queue state");
            }

//            this.bus.add(root.toJSONString());
//            InetAddress address = packet.getAddress();
//            int port = packet.getPort();
//            packet = new DatagramPacket(buf, buf.length, address, port);
//            MessageDecoder.decode(packet);
//            String received
//                    = new String(packet.getData(), 0, packet.getLength());
//
//            if (received.equals("end")) {
//                running = false;
//                continue;
//            }

//            System.out.println(received);
//            try {
//                socket.send(packet);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
        }
        socket.close();
    }


}
