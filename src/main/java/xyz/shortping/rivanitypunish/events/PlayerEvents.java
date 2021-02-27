/**
 *
 * Copyright (c) Michael Steinmötzger 2020
 *
 * All rights reserved
 *
 * */
package xyz.shortping.rivanitypunish.events;

import de.dytanic.cloudnet.api.CloudAPI;
import java.util.UUID;
import java.util.function.Consumer;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import xyz.shortping.rivanitypunish.RivanityPunish;
import xyz.shortping.rivanitypunish.entities.RivanityBan;
import xyz.shortping.rivanitypunish.entities.RivanityMute;

public class PlayerEvents implements Listener {

    private final RivanityPunish plugin;

    public PlayerEvents(RivanityPunish plugin) {
        this.plugin = plugin;
        BungeeCord.getInstance().getPluginManager().registerListener(plugin, this);
    }

    @EventHandler
    public void on(PostLoginEvent event) {

        ProxiedPlayer player = event.getPlayer();

        plugin.getBackendManager().isBanExist(player.getUniqueId(), (Boolean t) -> {
            if (t) {
                plugin.getBackendManager().getBan(player.getUniqueId(), (RivanityBan ban) -> {

                    if (!ban.isPermanent()) {
                        long curr = System.currentTimeMillis() / 1000;

                        if (ban.getTimestamp() < curr) {
                            plugin.getBackendManager().deleteBan(player.getUniqueId());
                        } else {
                            String name = CloudAPI.getInstance().getPlayerName(UUID.fromString(ban.getResponsiblePlayer()));
                            player.disconnect(message(plugin.getBanManager().getBanKickMessage(ban.getReason(), name, ban.getTimestamp(), ban.getBanID(), ban.isPermanent())));
                        }
                    } else {
                        String name = CloudAPI.getInstance().getPlayerName(UUID.fromString(ban.getResponsiblePlayer()));
                        player.disconnect(message(plugin.getBanManager().getBanKickMessage(ban.getReason(), name, ban.getTimestamp(), ban.getBanID(), ban.isPermanent())));

                    }
                });
            }
        });

    }

    private BaseComponent[] message(String message) {
        return new ComponentBuilder(message).create();
    }

}
