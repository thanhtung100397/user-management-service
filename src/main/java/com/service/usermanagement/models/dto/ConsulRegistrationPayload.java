package com.service.usermanagement.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ConsulRegistrationPayload implements Serializable {
    @JsonProperty("ID")
    private String ID;
    @JsonProperty("Name")
    private String Name;
    @JsonProperty("Address")
    private String Address;
    @JsonProperty("Port")
    private int Port;
    @JsonProperty("Check")
    private ConsulHealCheckPayload Check;

    public ConsulRegistrationPayload(String ID,
                                     String name,
                                     String address,
                                     int port,
                                     String deregisterCriticalServiceAfter,
                                     String http,
                                     String interval) {
        this.ID = ID;
        this.Name = name;
        this.Address = address;
        this.Port = port;
        this.Check = new ConsulHealCheckPayload(deregisterCriticalServiceAfter, http, interval);
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public int getPort() {
        return Port;
    }

    public void setPort(int port) {
        Port = port;
    }

    public ConsulHealCheckPayload getCheck() {
        return Check;
    }

    public void setCheck(ConsulHealCheckPayload check) {
        Check = check;
    }
}
