package com.blastbeatsandcode.trashcan.model.components;

import com.blastbeatsandcode.trashcan.model.states.LidStatus;
import com.blastbeatsandcode.trashcan.model.states.StatusEnum;

// Represents the Lid component of the trash can
public class Lid implements Component {
    LidStatus _currentStatus;

    public Lid() {
        _currentStatus = LidStatus.LID_CLOSED;
    }

    @Override
    public StatusEnum getStatus() {
        return _currentStatus;
    }

    @Override
    public boolean turnOn() {
        // TODO: Add logic to interface with hardware and return boolean based on if it actually happened or not
        _currentStatus = LidStatus.LID_OPEN;
        return false;
    }

    @Override
    public boolean turnOff() {
        // TODO: Add logic to interface with hardware and return boolean based on if it actually happened or not
        _currentStatus = LidStatus.LID_CLOSED;
        return false;
    }
}
