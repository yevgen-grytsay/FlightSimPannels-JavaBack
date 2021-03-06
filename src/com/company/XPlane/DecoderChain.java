package com.company.XPlane;

import com.company.Decoder;
import com.company.XPlane.Decoder.*;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DecoderChain {
    private List<Decoder> decoderList;

    public static DecoderChain defaultChain() {
        return new DecoderChain()
                .add(new AttitudeDecoder())
                .add(new SpeedsDecoder())
                .add(new VerticalSpeedDecoder())
                .add(new AltitudeDecoder())
                .add(new DgDecoder())
        ;
    }

    public DecoderChain() {
        decoderList = new ArrayList<>();
    }

    public DecoderChain add(Decoder decoder) {
        decoderList.add(decoder);

        return this;
    }

    public JSONObject decode(List<Packet> chunks) {
        decoderList.stream().forEach(Decoder::reset);

        for (Decoder d: decoderList) {
            for (Packet packet: chunks) {
                d.decode(packet);
            }
        }

        JSONObject root = new JSONObject();
        for (Decoder d: decoderList) {
            root.put(d.getName(), d.getResult());
        }

        return root;
    }
}
