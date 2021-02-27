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
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import xyz.shortping.rivanitypunish.RivanityPunish;
import xyz.shortping.rivanitypunish.entities.RivanityBan;
import xyz.shortping.rivanitypunish.entities.RivanityMute;

public class CMD_Check extends Command {

    private final RivanityPunish plugin;

    public CMD_Check(RivanityPunish plugin, String name) {
        super(name);
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender player, String[] args) {

        if (player.hasPermission("rivanity.bansystem.use")) {
            if (args.length == 0) {
                player.sendMessage(message(plugin.getPrefix() + "§cVerwende /check <BanId>"));
            } else {
                String banid = args[0];
                player.sendMessage(message(plugin.getPrefix() + "§7Suche nach Ban..."));
                plugin.getBackendManager().isBanExist(banid, (Boolean t) -> {
                    if (t) {
                        plugin.getBackendManager().getBan(banid, (RivanityBan ban) -> {
                            player.sendMessage(" ");
                            player.sendMessage(" ");
                            player.sendMessage(message(plugin.getPrefix() + "§7Gebannter Spieler: §c" + CloudAPI.getInstance().getPlayerName(UUID.fromString(ban.getBannedPlayer()))));
                            player.sendMessage(message(plugin.getPrefix() + "§7Verantwortliches Teammitglied: §c" + CloudAPI.getInstance().getPlayerName(UUID.fromString(ban.getResponsiblePlayer()))));
                            player.sendMessage(message(plugin.getPrefix() + "§7Grund: §c" + ban.getReason()));
                            player.sendMessage(" ");
                            player.sendMessage(message(plugin.getPrefix() + "§7Verbleibende Zeit: §c" + plugin.getBanManager().getRemainingTime(ban.getTimestamp())));
                            player.sendMessage(" ");
                            player.sendMessage(message(plugin.getPrefix() + "§7BanID: §c" + ban.getBanID()));
                            player.sendMessage(" ");
                            player.sendMessage(" ");
                            ban.getEvidences().forEach(new Consumer<String>() {
                                @Override
                                public void accept(String evidence) {
                                    TextComponent t = new TextComponent(plugin.getPrefix() + "§c" + evidence);
                                    t.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, evidence));
                                    t.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§cBeweis Öffnen").create()));
                                    player.sendMessage(t);
                                }
                            });
                        });
                    } else {
                        plugin.getBackendManager().isMuteExist(banid, (Boolean t1) -> {

                            if (t1) {
                                plugin.getBackendManager().getMute(banid, (RivanityMute ban) -> {
                                    player.sendMessage(" ");
                                    player.sendMessage(" ");
                                    player.sendMessage(message(plugin.getPrefix() + "§7Gemuteter Spieler: §c" + CloudAPI.getInstance().getPlayerName(UUID.fromString(ban.getBannedPlayer()))));
                                    player.sendMessage(message(plugin.getPrefix() + "§7Verantwortliches Teammitglied: §c" + CloudAPI.getInstance().getPlayerName(UUID.fromString(ban.getResponsiblePlayer()))));
                                    player.sendMessage(message(plugin.getPrefix() + "§7Grund: §c" + ban.getReason()));
                                    player.sendMessage(" ");
                                    player.sendMessage(message(plugin.getPrefix() + "§7Verbleibende Zeit: §c" + plugin.getMuteManager().getRemainingTime(ban.getTimestamp())));
                                    player.sendMessage(" ");
                                    player.sendMessage(message(plugin.getPrefix() + "§7BanID: §c" + ban.getBanID()));
                                    player.sendMessage(" ");
                                    player.sendMessage(" ");
                                    ban.getEvidences().forEach((String evidence) -> {
                                        TextComponent t2 = new TextComponent(plugin.getPrefix() + "§c" + evidence);
                                        t2.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, evidence));
                                        t2.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§cBeweis Öffnen").create()));
                                        player.sendMessage(t2);
                                    });
                                });
                            } else {
                                player.sendMessage(message(plugin.getPrefix() + "§cDiese BanID existiert nicht."));
                            }
                        });
                    }
                });
            }

        } else {
            player.sendMessage(message(plugin.getNoPerm()));
        }

    }

    private BaseComponent[] message(String msg) {
        return new ComponentBuilder(msg).create();
    }

}
