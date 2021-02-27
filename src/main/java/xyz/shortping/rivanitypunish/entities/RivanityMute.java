

/**

Copyright (c) Michael Steinmötzger 2020

All rights reserved

**/

package xyz.shortping.rivanitypunish.entities;

import java.util.ArrayList;


public class RivanityMute {
    
    private String banID;
    private String bannedPlayer;
    private String responsiblePlayer;
    private String reason;
    private boolean permanent;
    private long timestamp;
    private ArrayList<String> evidences;

    public String getBanID() {
        return banID;
    }

    public void setBanID(String banID) {
        this.banID = banID;
    }

    public String getBannedPlayer() {
        return bannedPlayer;
    }

    public void setBannedPlayer(String bannedPlayer) {
        this.bannedPlayer = bannedPlayer;
    }

    public String getResponsiblePlayer() {
        return responsiblePlayer;
    }

    public void setResponsiblePlayer(String responsiblePlayer) {
        this.responsiblePlayer = responsiblePlayer;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean isPermanent() {
        return permanent;
    }

    public void setPermanent(boolean permanent) {
        this.permanent = permanent;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public ArrayList<String> getEvidences() {
        return evidences;
    }

    public void setEvidences(ArrayList<String> evidences) {
        this.evidences = evidences;
    }
    
    
}
