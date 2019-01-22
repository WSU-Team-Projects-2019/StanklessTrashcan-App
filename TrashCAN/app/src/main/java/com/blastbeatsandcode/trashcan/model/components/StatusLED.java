package com.blastbeatsandcode.trashcan.model.components;

import com.blastbeatsandcode.trashcan.model.states.LEDStatus;
import com.blastbeatsandcode.trashcan.model.states.StatusEnum;

// Represents the Status LED component of the trash can
public class StatusLED implements Component {
    private LEDStatus _currentStatus;

    public StatusLED() {
        _currentStatus = LEDStatus.LED_OFF;
    }

    @Override
    public StatusEnum getStatus() {
        return _currentStatus;
    }

    @Override
    public boolean turnOn() {
        // TODO: Add logic to interface with hardware and return boolean based on if it actually happened or not
        _currentStatus = LEDStatus.LED_ON;
        return false;
    }

    @Override
    public boolean turnOff() {
        // TODO: Add logic to interface with hardware and return boolean based on if it actually happened or not
        _currentStatus = LEDStatus.LED_OFF;
        return false;
    }
}
