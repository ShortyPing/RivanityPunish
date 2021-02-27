/**
 *
 * Copyright (c) Michael Steinmötzger 2020
 *
 * All rights reserved
 *
 * */
package xyz.shortping.rivanitypunish.managers;

import de.dytanic.cloudnet.api.CloudAPI;
import de.dytanic.cloudnet.bridge.CloudProxy;
import de.dytanic.cloudnet.bridge.CloudServer;
import java.awt.Color;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.md_5.bungee.api.ChatColor;
import xyz.shortping.rivanitypunish.RivanityPunish;

public class BanManager {

    private final RivanityPunish plugin;

    public BanManager(RivanityPunish plugin) {
        this.plugin = plugin;
    }

    public int normalBanReason = 9;
    public int permBanReason = 10;

    public String getReason(int id) {
        switch (id) {
            case 1:
                return "Unerlaubte Clientmodifikationen";
            case 2:
                return "Unerlaubter Skin";
            case 3:
                return "Unerlaubtes Cape";
            case 4:
                return "Bugusing";
            case 5:
                return "Statspushing";
            case 6:
                return "Schwere Beleidigung";
            case 7:
                return "Trolling";
            case 8:
                return "Teaming";
            case 9:
                return "Hausverbot";
            default:
                return "ERROR";
        }

    }

    public int getReason(String reason) {
        switch (reason.toLowerCase()) {
            case "unerlaubte clientmodifikationen":
                return 1;
            case "unerlaubter skin":
                return 2;
            case "unerlaubtes cape":
                return 3;
            case "bugusing":
                return 4;
            case "statspushing":
                return 5;
            case "schwere beleidigung":
                return 6;
            case "trolling":
                return 7;
            case "teaming":
                return 8;
            case "hausverbot":
                return 9;
            default:
                return -1;
        }
    }

    public long getBanTime(String reason) {
        switch (reason.toLowerCase()) {
            case "unerlaubte clientmodifikationen":
                return 60 * 60 * 24 * 30;
            case "unerlaubter skin":
                return 60 * 60 * 24 * 12;
            case "unerlaubtes cape":
                return 60 * 60 * 24 * 6;
            case "bugusing":
                return 60 * 60 * 24 * 20;
            case "statspushing":
                return 60 * 60 * 24 * 60;
            case "schwere beleidigung":
                return 60 * 60 * 24 * 30;
            case "trolling":
                return 60 * 60 * 24 * 15;
            case "teaming":
                return 60 * 60 * 24 * 15;
            case "hausverbot":
                return -1;
            default:
                return -2;
        }
    }

    public long getBanTime(int id) {
        switch (id) {
            case 1:
                return 60 * 60 * 24 * 30;
            case 2:
                return 60 * 60 * 24 * 12;
            case 3:
                return 60 * 60 * 24 * 6;
            case 4:
                return 60 * 60 * 24 * 20;
            case 5:
                return 60 * 60 * 24 * 60;
            case 6:
                return 60 * 60 * 24 * 30;
            case 7:
                return 60 * 60 * 24 * 15;
            case 8:
                return 60 * 60 * 24 * 15;
            case 9:
                return -1;
            default:
                return -2;
        }

    }

    public boolean isBanPermanent(int id) {
        switch (id) {
            case 1:
                return false;
            case 2:
                return false;
            case 3:
                return false;
            case 4:
                return false;
            case 5:
                return false;
            case 6:
                return false;
            case 7:
                return false;
            case 8:
                return false;
            case 9:
                return true;
            default:
                return true;
        }
    }

    public boolean isBanPermanent(String reason) {
        switch (reason.toLowerCase()) {
            case "unerlaubte clientmodifikationen":
                return false;
            case "unerlaubter skin":
                return false;
            case "unerlaubtes cape":
                return false;
            case "bugusing":
                return false;
            case "statspushing":
                return false;
            case "schwere beleidigung":
                return false;
            case "trolling":
                return false;
            case "teaming":
                return false;
            case "hausverbot":
                return true;
            default:
                return true;
        }
    }

    public int getNormalBanReason() {
        return normalBanReason;
    }

    public int getPermBanReason() {
        return permBanReason;
    }

    public void broadcastBanMessage(String bannedPlayer, String banner, String reason, String banID, boolean perm) {
        if (perm) {
            CloudAPI.getInstance().getOnlinePlayers().forEach((all) -> {
                if (all.getPermissionEntity().hasPermission(CloudAPI.getInstance().getPermissionPool(), "rivanity.bansystem.use", "Bungee")) {
                    all.getPlayerExecutor().sendMessage(all, plugin.getPrefix() + "§7§m-------------------------" + "\n" + plugin.getPrefix() + "" + "\n" + plugin.getPrefix() + "§7Der Spieler §c" + bannedPlayer + " §7wurde §4Permanent §7gebannt." + "\n"
                            + plugin.getPrefix() + "" + "\n"
                            + plugin.getPrefix() + "§7Gebannt von: §c" + banner + "\n"
                            + plugin.getPrefix() + "§7Grund: §c" + reason + "\n"
                            + plugin.getPrefix() + "§7BanID: §c" + banID + "\n"
                            + plugin.getPrefix() + "" + "\n"
                            + plugin.getPrefix() + "§7§m-------------------------" + "\n");
                }
            });
            return;
        }
        CloudAPI.getInstance().getOnlinePlayers().forEach((all) -> {
            if (all.getPermissionEntity().hasPermission(CloudAPI.getInstance().getPermissionPool(), "rivanity.bansystem.use", "Bungee")) {
                all.getPlayerExecutor().sendMessage(all, plugin.getPrefix() + "§7§m-------------------------" + "\n" + plugin.getPrefix() + "" + "\n" + plugin.getPrefix() + "§7Der Spieler §c" + bannedPlayer + " §7wurde gebannt." + "\n"
                        + plugin.getPrefix() + "" + "\n"
                        + plugin.getPrefix() + "§7Gebannt von: §c" + banner + "\n"
                        + plugin.getPrefix() + "§7Grund: §c" + reason + "\n"
                        + plugin.getPrefix() + "§7BanID: §c" + banID + "\n"
                        + plugin.getPrefix() + "" + "\n"
                        + plugin.getPrefix() + "§7§m-------------------------" + "\n");
            }
        });
    }

    public String getBanKickMessage(String reason, String banner, long time, String banid, boolean perm) {
        if (!perm) {
            return "§7Du wurdest von dem §cRivanity.net §7Netzwerk gebannt.\n\n"
                    + "§7Gebannt von: §c" + banner + "\n"
                    + "§7Grund: §c" + reason + "\n\n"
                    + "§7Verbleibende Zeit: §c" + getRemainingTime(time) + "\n\n"
                    + "§7Bei unklarheiten kannst du dich mit der BanID §c" + banid + " §7im Support melden.";
        } else {
            return "§7Du wurdest von dem §cRivanity.net §7Netzwerk gebannt.\n\n\n"
                    + "§7Gebannt von: §c" + banner + "\n\n"
                    + "§7Grund: §c" + reason + "\n\n"
                    + "§7Verbleibende Zeit: §4Permanent\n\n"
                    + "§7Bei unklarheiten kannst du dich mit der BanID §c" + banid + " §7im Support melden.";
        }
    }

    public String getRemainingTime(long time) {
        long seconds = time - (System.currentTimeMillis() / 1000);

        long minutes;
        for (minutes = 0L; seconds > 60L; ++minutes) {
            seconds -= 60L;
        }

        long hours;
        for (hours = 0L; minutes > 60L; ++hours) {
            minutes -= 60L;
        }

        long days;
        for (days = 0L; hours > 24L; ++days) {
            hours -= 24L;
        }

        return "§c" + days + (days == 1L ? " §7Tag" : " §7Tage") + " §c" + hours + (hours == 1L ? " §7Stunde" : " §7Stunden") + " §c" + minutes + (minutes == 1L ? " §7Minute" : " §7Minuten") + " §c" + seconds + (seconds == 1L ? " §7Sekunde" : " §7Sekunden");

    }

    public void kickPlayer(String username, String reason) {
        CloudAPI.getInstance().getOnlinePlayers().forEach((all) -> {
            if (all.getName().equalsIgnoreCase(username)) {
                all.getPlayerExecutor().kickPlayer(all, reason);
            }
        });
    }

    public void sendBanWebhook(String username, String reason, String banner, long timestamp, boolean permanent) {
        String uuid = CloudAPI.getInstance().getPlayerUniqueId(username).toString();
        if (plugin.isWebhookEnabled()) {
            if (!permanent) {
                DiscordWebhook webhook = new DiscordWebhook(plugin.getWebhookUrl());
                webhook.setAvatarUrl("https://crafatar.com/avatars/" + uuid);
                webhook.setUsername(banner + " ➡ " + username);
                webhook.addEmbed(new DiscordWebhook.EmbedObject()
                        .setTitle("Ban")
                        .setColor(Color.RED)
                        .addField("Spieler", username, true)
                        .addField("Gebannt von", banner, true)
                        .addField("Grund", reason, true)
                        .addField("Zeit", ChatColor.stripColor(getRemainingTime(timestamp)), true));

                try {
                    webhook.execute();
                } catch (IOException ex) {
                    Logger.getLogger(BanManager.class.getName()).log(Level.SEVERE, null, ex);
                }
                return;
            }
            DiscordWebhook webhook = new DiscordWebhook(plugin.getWebhookUrl());
            webhook.setAvatarUrl("https://crafatar.com/avatars/" + uuid);
            webhook.setUsername(banner + " ➡ " + username);
            webhook.addEmbed(new DiscordWebhook.EmbedObject()
                    .setTitle("Ban")
                    .setColor(Color.RED)
                    .addField("Spieler", username, true)
                    .addField("Gebannt von", banner, true)
                    .addField("Grund", reason, true)
                    .addField("Zeit", "Permanent", true));

            try {
                webhook.execute();
            } catch (IOException ex) {
                Logger.getLogger(BanManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
