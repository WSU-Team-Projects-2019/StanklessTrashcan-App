package com.blastbeatsandcode.trashcan.model;

import com.blastbeatsandcode.trashcan.model.components.*;

// Class representing the TrashCAN itself
public class TrashCan {
    private StatusLED _led;
    private FanBlade _fan;
    private UVCBulb _bulb;
    private Lid _lid;

    public TrashCan() {
        // TODO: Fill in this constructor based on real-world application
        _led = new StatusLED();
        _fan = new FanBlade();
        _bulb = new UVCBulb();
        _lid = new Lid();
    }

    TrashCan (StatusLED led, FanBlade fan, UVCBulb bulb, Lid lid) {
        // TODO: Is this a constructor we would need?
        _led = led;
        _fan = fan;
        _bulb = bulb;
        _lid = lid;
    }

    public StatusLED getLED() {
        return _led;
    }

    public FanBlade getFanBlade() {
        return _fan;
    }

    public UVCBulb getUVCBulb() {
        return _bulb;
    }

    public Lid getLid() {
        return _lid;
    }
}
