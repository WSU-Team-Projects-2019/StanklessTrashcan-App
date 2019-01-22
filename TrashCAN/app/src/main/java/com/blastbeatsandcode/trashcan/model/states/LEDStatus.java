package com.blastbeatsandcode.trashcan.model.states;

// Status for LED
public enum LEDStatus implements StatusEnum {
    LED_ON,
    LED_OFF;
    private LEDStatus _currentStatus;

    @Override
    public StatusEnum getStatus() {
        return _currentStatus;
    }

    @Override
    public StatusEnum turnOn() {
        _currentStatus = LED_ON;
        return _currentStatus;
    }

    @Override
    public StatusEnum turnOff() {
        _currentStatus = LED_OFF;
        return _currentStatus;
    }
}
