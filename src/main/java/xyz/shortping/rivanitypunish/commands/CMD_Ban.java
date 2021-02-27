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

public class CMD_Ban extends Command {

    private final RivanityPunish plugin;

    public CMD_Ban(String name, RivanityPunish plugin) {
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
                    player.sendMessage(message(plugin.getPrefix() + "§4Verfügbare Bangründe"));
                    player.sendMessage(message(" "));

                    if (player.hasPermission("rivanity.bansystem.permanent")) {
                        int j = plugin.getBanManager().permBanReason;
                        for (int i = 1; i != j; i++) {
                            player.sendMessage(message(plugin.getPrefix() + "§e" + plugin.getBanManager().getReason(i) + " §7(§c§l" + i + "§7)"));
                        }
                        player.sendMessage(message(" "));
                        player.sendMessage(message(plugin.getPrefix() + "§cVerwende: /ban <Spieler> <Grund>"));
                    } else {
                        int j = plugin.getBanManager().normalBanReason;
                        for (int i = 1; i != j; i++) {
                            player.sendMessage(message(plugin.getPrefix() + "§e" + plugin.getBanManager().getReason(i) + " §7(§c§l" + i + "§7)"));
                        }
                        player.sendMessage(message(" "));
                        player.sendMessage(message(plugin.getPrefix() + "§cVerwende: /ban <Spieler> <Grund>"));
                    }
                } else if (args.length == 2) {

                    try {
                        String tstring = args[0];
                        int id = Integer.parseInt(args[1]);

                        if (id > 0 && id < plugin.getMuteManager().permMuteReason) {
                            UUID uuid = CloudAPI.getInstance().getPlayerUniqueId(tstring);

                            if (id >= plugin.getMuteManager().normalMuteReason) {
                                if (player.hasPermission("rivanity.bansystem.permanent")) {
                                    plugin.getBackendManager().isBanExist(uuid, (Boolean t) -> {
                                        if (!t) {
                                            long bantime = System.currentTimeMillis() / 1000;
                                            bantime = bantime + plugin.getBanManager().getBanTime(id);
                                            plugin.getBackendManager().createBan(tstring, (ProxiedPlayer) player, plugin.getBanManager().getReason(id), plugin.getBanManager().isBanPermanent(id), bantime, (RivanityBan ban) -> {
                                                plugin.getBanManager().broadcastBanMessage(tstring, player.getName(), ban.getReason(), ban.getBanID(), ban.isPermanent());
                                                plugin.getBanManager().kickPlayer(tstring, plugin.getBanManager().getBanKickMessage(ban.getReason(), player.getName(), ban.getTimestamp(), ban.getBanID(), ban.isPermanent()));
                                                plugin.getBanManager().sendBanWebhook(tstring, ban.getReason(), player.getName(), ban.getTimestamp(), ban.isPermanent());

                                            });
                                        } else {
                                            player.sendMessage(message(plugin.getPrefix() + "§cDieser Spieler ist bereits gebannt."));
                                        }
                                    });
                                } else {
                                    player.sendMessage(message(plugin.getPrefix() + "§cDiese ID darfst du nicht benutzen"));
                                }
                            } else {
                                plugin.getBackendManager().isBanExist(uuid, (Boolean t) -> {
                                    if (!t) {
                                        long bantime = System.currentTimeMillis() / 1000;
                                        bantime = bantime + plugin.getBanManager().getBanTime(id);
                                        plugin.getBackendManager().createBan(tstring, (ProxiedPlayer) player, plugin.getBanManager().getReason(id), plugin.getBanManager().isBanPermanent(id), bantime, (RivanityBan ban) -> {
                                            plugin.getBanManager().broadcastBanMessage(tstring, player.getName(), ban.getReason(), ban.getBanID(), ban.isPermanent());
                                            plugin.getBanManager().kickPlayer(tstring, plugin.getBanManager().getBanKickMessage(ban.getReason(), player.getName(), ban.getTimestamp(), ban.getBanID(), ban.isPermanent()));
                                            plugin.getBanManager().sendBanWebhook(tstring, ban.getReason(), player.getName(), ban.getTimestamp(), ban.isPermanent());

                                        });
                                    } else {
                                        player.sendMessage(message(plugin.getPrefix() + "§cDieser Spieler ist bereits gebannt."));
                                    }
                                });
                            }

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

                    plugin.getBackendManager().isBanExist(uuid, (Boolean t) -> {
                        if (!t) {
                            long bantime = System.currentTimeMillis() / 1000;
                            player.sendMessage(bantime + "");
                            bantime = bantime + plugin.getBanManager().getBanTime(id);
                            player.sendMessage(bantime + "");
                            plugin.getBackendManager().createBan(tstring, plugin.getBanManager().getReason(id), plugin.getBanManager().isBanPermanent(id), bantime, (RivanityBan ban) -> {
                                plugin.getBanManager().broadcastBanMessage(tstring, "System", ban.getReason(), ban.getBanID(), ban.isPermanent());
                                plugin.getBanManager().kickPlayer(tstring, plugin.getBanManager().getBanKickMessage(ban.getReason(), "System", ban.getTimestamp(), ban.getBanID(), ban.isPermanent()));
                                plugin.getBanManager().sendBanWebhook(tstring, ban.getReason(), "System", ban.getTimestamp(), ban.isPermanent());

                            });
                        } else {
                            player.sendMessage(message(plugin.getPrefix() + "§cDieser Spieler ist bereits gebannt."));
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
