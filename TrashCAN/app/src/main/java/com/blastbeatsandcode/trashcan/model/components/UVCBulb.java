package com.blastbeatsandcode.trashcan.model.components;

import com.blastbeatsandcode.trashcan.model.states.BulbStatus;
import com.blastbeatsandcode.trashcan.model.states.StatusEnum;

// Represents the UVC Bulb component of the trash can
public class UVCBulb implements Component {
    private BulbStatus _currentStatus;

    public UVCBulb() {
        _currentStatus = BulbStatus.BULB_OFF;
    }

    @Override
    public StatusEnum getStatus() {
        return _currentStatus;
    }

    @Override
    public boolean turnOn() {
        // TODO: Add logic to interface with hardware and return boolean based on if it actually happened or not
        _currentStatus = BulbStatus.BULB_ON;
        return false;
    }

    @Override
    public boolean turnOff() {
        // TODO: Add logic to interface with hardware and return boolean based on if it actually happened or not
        _currentStatus = BulbStatus.BULB_OFF;
        return false;
    }
}
