package com.company;

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

        DecoderChain chain = DecoderChain.defaultChain();

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

//            System.out.println(root.toJSONString());

            this.bus.add(root.toJSONString());
            System.out.printf("Queue size: %d%n", bus.size());

        }
        socket.close();
    }


}
