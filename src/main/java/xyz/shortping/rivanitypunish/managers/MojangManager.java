/**
 *
 * Copyright (c) Michael Steinmötzger 2020
 *
 * All rights reserved
 *
 * */
package xyz.shortping.rivanitypunish.managers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.UUID;
import org.json.JSONObject;

public class MojangManager {

    private final String API_URL = "https://api.mojang.com/users/profiles/minecraft/";

    public UUID loadUUID(String playerName) {
        try {
            final URL url = new URL(API_URL + playerName);
            return getUniqueIdFromString((new JSONObject(new Scanner(url.openConnection().getInputStream()).nextLine())).getString("id"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private UUID getUniqueIdFromString(String uuid) {
        return UUID.fromString(uuid.replaceFirst("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5"));
    }

}
