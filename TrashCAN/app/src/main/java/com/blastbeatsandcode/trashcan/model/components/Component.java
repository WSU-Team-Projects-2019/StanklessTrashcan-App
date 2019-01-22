package com.blastbeatsandcode.trashcan.model.components;

import com.blastbeatsandcode.trashcan.model.states.StatusEnum;

public interface Component {
    StatusEnum getStatus();
    boolean turnOn();
    boolean turnOff();
}
