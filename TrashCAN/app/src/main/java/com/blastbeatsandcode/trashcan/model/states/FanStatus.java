package com.blastbeatsandcode.trashcan.model.states;

// Status for the fan
public enum FanStatus implements StatusEnum {
    FAN_ON,
    FAN_OFF;
    private FanStatus _currentStatus;

    @Override
    public StatusEnum getStatus() {
        return _currentStatus;
    }

    @Override
    public StatusEnum turnOn() {
        _currentStatus = FAN_ON;
        return _currentStatus;
    }

    @Override
    public StatusEnum turnOff() {
        _currentStatus = FAN_OFF;
        return _currentStatus;
    }
}
