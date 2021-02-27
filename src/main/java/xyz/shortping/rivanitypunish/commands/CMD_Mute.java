/**
 *
 * Copyright (c) Michael Steinmötzger 2020
 *
 * All rights reserved
 *
 * */
package xyz.shortping.rivanitypunish.commands;

import de.dytanic.cloudnet.api.CloudAPI;
import de.dytanic.cloudnet.bridge.CloudProxy;
import de.dytanic.cloudnet.lib.player.CloudPlayer;
import java.util.UUID;
import java.util.function.Consumer;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import xyz.shortping.rivanitypunish.RivanityPunish;
import xyz.shortping.rivanitypunish.entities.RivanityBan;
import xyz.shortping.rivanitypunish.entities.RivanityMute;

public class CMD_Mute extends Command {

    private final RivanityPunish plugin;

    public CMD_Mute(String name, RivanityPunish plugin) {
        super(name);
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender player, String[] args) {

        if (player instanceof ProxiedPlayer) {

            if (player.hasPermission("rivanity.bansystem.use")) {
                if (args.length == 0) {
                    player.sendMessage(message(" "));
                    player.sendMessage(message(" "));
                    player.sendMessage(message(plugin.getPrefix() + "§4Verfügbare Mutegründe"));
                    player.sendMessage(message(" "));

                    if (player.hasPermission("rivanity.bansystem.permanent")) {
                        int j = plugin.getMuteManager().permMuteReason;
                        for (int i = 1; i != j; i++) {
                            player.sendMessage(message(plugin.getPrefix() + "§e" + plugin.getMuteManager().getReason(i) + " §7(§c§l" + i + "§7)"));
                        }
                        player.sendMessage(message(" "));
                        player.sendMessage(message(plugin.getPrefix() + "§cVerwende: /mute <Spieler> <Grund>"));
                    } else {
                        int j = plugin.getMuteManager().normalMuteReason;
                        for (int i = 1; i != j; i++) {
                            player.sendMessage(message(plugin.getPrefix() + "§e" + plugin.getMuteManager().getReason(i) + " §7(§c§l" + i + "§7)"));
                        }
                        player.sendMessage(message(" "));
                        player.sendMessage(message(plugin.getPrefix() + "§cVerwende: /mute <Spieler> <Grund>"));
                    }
                } else if (args.length == 2) {

                    try {
                        String tstring = args[0];
                        int id = Integer.parseInt(args[1]);

                        if (id > 0 && id < plugin.getMuteManager().permMuteReason) {
                            UUID uuid = CloudAPI.getInstance().getPlayerUniqueId(tstring);

                            if (id >= plugin.getMuteManager().normalMuteReason) {
                                if (player.hasPermission("rivanity.bansystem.permanent")) {
                                    plugin.getBackendManager().isMuteExist(uuid, (Boolean t) -> {
                                        if (!t) {
                                            long bantime = System.currentTimeMillis() / 1000;
                                            bantime = bantime + plugin.getMuteManager().getBanTime(id);
                                            plugin.getBackendManager().createMute(tstring, (ProxiedPlayer) player, plugin.getMuteManager().getReason(id), plugin.getMuteManager().isBanPermanent(id), bantime, (RivanityMute ban) -> {
                                                plugin.getMuteManager().broadcastMuteMessage(tstring, player.getName(), ban.getReason(), ban.getBanID(), ban.isPermanent());
                                                plugin.getMuteManager().sendMuteWebhook(tstring, player.getName(), ban.getReason(), ban.getTimestamp(), ban.isPermanent());

                                            });
                                        } else {
                                            player.sendMessage(message(plugin.getPrefix() + "§cDieser Spieler ist bereits gemuted."));
                                        }
                                    });
                                } else {
                                    player.sendMessage(message(plugin.getPrefix() + "§cDiese ID darfst du nicht benutzen"));
                                }

                            } else {
                                plugin.getBackendManager().isMuteExist(uuid, (Boolean t) -> {
                                    if (!t) {
                                        long bantime = System.currentTimeMillis() / 1000;
                                        bantime = bantime + plugin.getMuteManager().getBanTime(id);
                                        plugin.getBackendManager().createMute(tstring, (ProxiedPlayer) player, plugin.getMuteManager().getReason(id), plugin.getMuteManager().isBanPermanent(id), bantime, (RivanityMute ban) -> {
                                            plugin.getMuteManager().broadcastMuteMessage(tstring, player.getName(), ban.getReason(), ban.getBanID(), ban.isPermanent());
                                            plugin.getMuteManager().sendMuteWebhook(tstring, player.getName(), ban.getReason(), ban.getTimestamp(), ban.isPermanent());

                                        });
                                    } else {
                                        player.sendMessage(message(plugin.getPrefix() + "§cDieser Spieler ist bereits gemuted."));
                                    }
                                });
                            }
                        } else {
                            player.sendMessage(message(plugin.getPrefix() + "§cDiese ID existiert nicht."));
                        }
                    } catch (NumberFormatException numberFormatException) {
                        player.sendMessage(message(plugin.getPrefix() + "§cBitte gebe eine Gültige Zahl ein."));
                    } catch (NullPointerException exception) {
                        player.sendMessage(message(plugin.getPrefix() + "§cDieser Spieler war noch nie Online.."));
                    }
                }
            } else {
                player.sendMessage(message(plugin.getPrefix()));
            }

        } else {
            if (args.length == 2) {

                try {
                    String tstring = args[0];
                    int id = Integer.parseInt(args[1]);

                    UUID uuid = CloudAPI.getInstance().getPlayerUniqueId(tstring);

                    plugin.getBackendManager().isMuteExist(uuid, (Boolean t) -> {
                        if (!t) {
                            long bantime = System.currentTimeMillis() / 1000;
                            bantime = bantime + plugin.getMuteManager().getBanTime(id);
                            plugin.getBackendManager().createMute(tstring, plugin.getMuteManager().getReason(id), plugin.getMuteManager().isBanPermanent(id), bantime, (RivanityMute ban) -> {
                                plugin.getMuteManager().broadcastMuteMessage(tstring, "System", ban.getReason(), ban.getBanID(), ban.isPermanent());
                                plugin.getMuteManager().sendMuteWebhook(tstring, "System", ban.getReason(), ban.getTimestamp(), ban.isPermanent());

                            });
                        } else {
                            player.sendMessage(message(plugin.getPrefix() + "§cDieser Spieler ist bereits gemuted."));
                        }
                    });
                } catch (NumberFormatException numberFormatException) {
                    player.sendMessage(message(plugin.getPrefix() + "§cBitte gebe eine Gültige Zahl ein."));
                } catch (NullPointerException exception) {
                    player.sendMessage(message(plugin.getPrefix() + "§cDieser Spieler war noch nie Online.."));
                }
                
            }
        }

    }

    private BaseComponent[] message(String message) {
        return new ComponentBuilder(message).create();
    }

}
