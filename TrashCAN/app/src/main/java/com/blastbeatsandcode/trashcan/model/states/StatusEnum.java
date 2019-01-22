package com.blastbeatsandcode.trashcan.model.states;

public interface StatusEnum {
    StatusEnum getStatus();
    StatusEnum turnOn();
    StatusEnum turnOff();
}