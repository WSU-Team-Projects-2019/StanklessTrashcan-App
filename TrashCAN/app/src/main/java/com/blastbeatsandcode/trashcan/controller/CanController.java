package com.blastbeatsandcode.trashcan.controller;

import com.blastbeatsandcode.trashcan.model.TrashCan;
import com.blastbeatsandcode.trashcan.view.TrashCanView;

import java.util.ArrayList;

// Singleton controller
public class CanController {
    private ArrayList<TrashCanView> _views;
    private static CanController instance;
    private TrashCan _m;
    private PiConn _conn;

    // Private constructor for singleton
    private CanController() {
        _views = new ArrayList<TrashCanView>();
        _m = new TrashCan();
        _conn = new PiConn();
    }

    // Creates or returns the instance of the singleton controller
    public static CanController getInstance() {
        if (instance == null) {
            instance = new CanController();
        }
        return instance;
    }

    // Registers the trashcan that will be used throughout the app
    public void registerTrashCan(TrashCan can) {
        _m = can;
    }

    // Registers views to add them to the views list so they may be updated at once
    public void registerView(TrashCanView view) {
        _views.add(view);
    }

    // Initiate cleaning cycle
    public boolean initiateClean() {
        // TODO: Implement this, initiate cleaning cycle
        return false;
    }

    // Opens or closes lid
    public boolean controlLid(boolean set) {
        // TODO: Implement this, open or close lid
        return false;
    }

    // Update all of the views
    public void update() {
        for (TrashCanView v : _views) {
            v.update();
        }
    }
}
