package com.blastbeatsandcode.trashcan.controller;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

// Connection between the app and the Raspberry Pi
// TODO: Implement this. Is there a better way to handle it?
public class PiConn {
    private String _response;
    private String _request;
    private String _connectionInfo;
    private CloseableHttpClient _client;

    public PiConn() {
        _response = "";
        _request = "";
        _connectionInfo = "";
        _client = HttpClients.createDefault();
    }

    public boolean sendRequest() {
        // TODO: Implement this
        return false;
    }

    public void setRequest(String req) {
        // TODO: Implement this
    }

    private boolean connectToServer() {
        // TODO: Implement this
        return false;
    }
}
