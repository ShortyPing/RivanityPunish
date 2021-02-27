

/**

Copyright (c) Michael Steinmötzger 2020

All rights reserved

**/

package xyz.shortping.rivanitypunish.managers;

import java.util.HashMap;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import xyz.shortping.rivanitypunish.RivanityPunish;


public class CacheManager {
    
    private final RivanityPunish plugin;
    private final HashMap<Object, Object> cacheMap;

    public CacheManager(RivanityPunish plugin) {
        this.plugin = plugin;
        this.cacheMap = new HashMap<>();
    }
    
    public void cacheObject(ProxiedPlayer player, Object object) {
        cacheMap.put(player, object);
    }

}
