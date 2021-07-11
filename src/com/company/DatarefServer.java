package com.company;

import com.company.XPlane.Decoder.SpeedsDecoder;
import com.company.XPlane.Decoder.AttitudeDecoder;
import com.company.XPlane.DecoderChain;
import com.company.XPlane.Packet;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.List;

public class DatarefServer extends Thread {

    private DatagramSocket socket;
    private boolean running;
    private byte[] buf = new byte[1024];

    public DatarefServer() {
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
            System.out.println(data.toJSONString());

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
