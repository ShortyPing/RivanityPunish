/**
 *
 * Copyright (c) Michael Steinmötzger 2020
 *
 * All rights reserved
 *
 * */
package xyz.shortping.rivanitypunish.commands;

import de.dytanic.cloudnet.api.CloudAPI;
import java.util.UUID;
import java.util.function.Consumer;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import xyz.shortping.rivanitypunish.RivanityPunish;

public class CMD_Unban extends Command {

    private final RivanityPunish plugin;

    public CMD_Unban(RivanityPunish plugin, String name) {
        super(name);
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender player, String[] args) {

        if (player instanceof ProxiedPlayer) {
            if (player.hasPermission("rivanity.bansystem.unban")) {
                if (args.length == 0) {
                    player.sendMessage(message(plugin.getPrefix() + "§cVerwende /unban <Spieler>"));
                } else if (args.length == 1) {
                    String user = args[0];

                    try {
                        UUID uuid = CloudAPI.getInstance().getPlayerUniqueId(user);
                        
                        plugin.getBackendManager().isBanExist(uuid, (Boolean t) -> {
                            if(t) {
                                plugin.getBackendManager().deleteBan(uuid);
                                player.sendMessage(message(plugin.getPrefix() + "§7Der Spieler §a" + user + " §7wurde entbannt."));
                            } else {
                                player.sendMessage(message(plugin.getPrefix() + "§7Der Spieler §a" + user + " §7ist nicht gebannt."));
                            }
                        });
                    } catch (NullPointerException e) {
                        player.sendMessage(message(plugin.getPrefix() + "§cDieser Spieler war noch nie Online."));
                    }
                }
            }
        }

    }

    private BaseComponent[] message(String message) {
        return new ComponentBuilder(message).create();
    }
}
