package com.blastbeatsandcode.trashcan.model.states;

// Status for the trash can lid
public enum LidStatus implements StatusEnum {
    LID_OPEN,
    LID_CLOSED;
    private LidStatus _currentStatus;

    @Override
    public StatusEnum getStatus() {
        return _currentStatus;
    }

    @Override
    public StatusEnum turnOn() {
        _currentStatus = LID_OPEN;
        return _currentStatus;
    }

    @Override
    public StatusEnum turnOff() {
        _currentStatus = LID_CLOSED;
        return _currentStatus;
    }
}
