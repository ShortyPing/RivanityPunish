/**
 *
 * Copyright (c) Michael Steinmötzger 2020
 *
 * All rights reserved
 *
 * */
package xyz.shortping.rivanitypunish.commands;

import java.util.ArrayList;
import java.util.function.Consumer;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.plugin.Command;
import xyz.shortping.rivanitypunish.RivanityPunish;
import xyz.shortping.rivanitypunish.entities.RivanityBan;
import xyz.shortping.rivanitypunish.entities.RivanityMute;

public class CMD_Proof extends Command {

    private final RivanityPunish plugin;
    String proof = "";

    public CMD_Proof(String name, RivanityPunish plugin) {
        super(name);
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender player, String[] args) {

        if (player.hasPermission("rivanity.bansystem.use")) {
            if (args.length == 0) {
                player.sendMessage(message(plugin.getPrefix() + "§cVerwende /proof <add/remove> <BanID> <Beweis>"));
            } else if (args.length == 3) {
                String command = args[0];
                String banid = args[1];

                String proof = args[2];

                plugin.getBackendManager().isBanExist(banid, (Boolean t) -> {
                    if (t) {

                        plugin.getBackendManager().getBan(banid, (RivanityBan ban) -> {

                            switch (command.toLowerCase()) {
                                case "add":
                                    ArrayList<String> evidences = ban.getEvidences();

                                    evidences.add(proof);

                                    ban.setEvidences(evidences);

                                    plugin.getBackendManager().updateBan(ban, (RivanityBan t1) -> {
                                        player.sendMessage(message(plugin.getPrefix() + "§7Der Beweis §c" + proof + " §7wurde zu dem Ban §c" + banid + " §7hinzugefügt."));
                                    });
                                    break;
                                case "remove":
                                    ArrayList<String> evidences1 = ban.getEvidences();

                                    evidences1.remove(proof);

                                    ban.setEvidences(evidences1);

                                    plugin.getBackendManager().updateBan(ban, (RivanityBan t1) -> {
                                        player.sendMessage(message(plugin.getPrefix() + "§7Der Beweis §c" + proof + " §7wurde von dem Ban §c" + banid + " §7entfernt."));
                                    });
                            }
                        });

                    } else {
                        plugin.getBackendManager().isMuteExist(banid, (Boolean bbbb) -> {

                            if (bbbb) {
                                plugin.getBackendManager().getMute(banid, (RivanityMute mute) -> {

                                    switch (command.toLowerCase()) {
                                        case "add":
                                            ArrayList<String> evidences = mute.getEvidences();

                                            evidences.add(proof);

                                            mute.setEvidences(evidences);

                                            plugin.getBackendManager().updateMute(mute, (RivanityMute t1) -> {
                                                player.sendMessage(message(plugin.getPrefix() + "§7Der Beweis §c" + proof + " §7wurde zu dem Ban §c" + banid + " §7hinzugefügt."));
                                            });
                                            break;
                                        case "remove":
                                            ArrayList<String> evidences1 = mute.getEvidences();

                                            evidences1.remove(proof);

                                            mute.setEvidences(evidences1);

                                            plugin.getBackendManager().updateMute(mute, (RivanityMute t1) -> {
                                                player.sendMessage(message(plugin.getPrefix() + "§7Der Beweis §c" + proof + " §7wurde von dem Ban §c" + banid + " §7entfernt."));
                                            });
                                    }
                                });
                            } else {
                                player.sendMessage(message(plugin.getPrefix() + "§cDiese BanID existiert nicht."));
                            }
                        });
                    }
                });

            }
        }

    }

    private BaseComponent[] message(String message) {
        return new ComponentBuilder(message).create();
    }

}
