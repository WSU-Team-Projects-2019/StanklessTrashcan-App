package com.blastbeatsandcode.trashcan.model.states;

// Status for the UVC bulb
public enum BulbStatus implements StatusEnum {
    BULB_ON,
    BULB_OFF;
    private BulbStatus _currentStatus;

    @Override
    public StatusEnum getStatus() {
        return _currentStatus;
    }

    @Override
    public StatusEnum turnOn() {
        _currentStatus = BULB_ON;
        return _currentStatus;
    }

    @Override
    public StatusEnum turnOff() {
        _currentStatus = BULB_OFF;
        return _currentStatus;
    }
}
