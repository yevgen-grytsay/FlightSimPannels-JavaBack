package com.company;

import org.json.simple.JSONObject;

import java.net.DatagramPacket;
import java.util.Map;

public interface Decoder {
    public void decode(DatagramPacket packet);

    public JSONObject getResult();
}
