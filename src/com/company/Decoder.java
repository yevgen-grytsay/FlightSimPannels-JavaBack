package com.company;

import com.company.XPlane.Packet;
import org.json.simple.JSONObject;

import java.net.DatagramPacket;
import java.util.Map;

public interface Decoder {
    public String getName();

    public void reset();

    public void decode(Packet packet);

    public JSONObject getResult();
}
