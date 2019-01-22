package com.blastbeatsandcode.trashcan.model.components;

import com.blastbeatsandcode.trashcan.model.states.FanStatus;
import com.blastbeatsandcode.trashcan.model.states.StatusEnum;

// Represents the FanBlade component of the trash can
public class FanBlade implements Component {
    private FanStatus _currentStatus;

    public FanBlade() {
        _currentStatus = FanStatus.FAN_OFF;
    }

    @Override
    public StatusEnum getStatus() {
        return _currentStatus;
    }

    @Override
    public boolean turnOn() {
        // TODO: Add logic to interface with hardware and return boolean based on if it actually happened or not
        _currentStatus = FanStatus.FAN_ON;
        return false;
    }

    @Override
    public boolean turnOff() {
        // TODO: Add logic to interface with hardware and return boolean based on if it actually happened or not
        _currentStatus = FanStatus.FAN_OFF;
        return false;
    }
}
