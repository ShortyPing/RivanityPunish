/**
 *
 * Copyright (c) Michael Steinmötzger 2020
 *
 * All rights reserved
 *
 * */
package xyz.shortping.rivanitypunish;

import com.google.gson.Gson;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.plugin.Plugin;
import xyz.shortping.rivanitypunish.commands.CMD_Ban;
import xyz.shortping.rivanitypunish.commands.CMD_Check;
import xyz.shortping.rivanitypunish.commands.CMD_Mute;
import xyz.shortping.rivanitypunish.commands.CMD_Proof;
import xyz.shortping.rivanitypunish.commands.CMD_Unban;
import xyz.shortping.rivanitypunish.commands.CMD_Unmute;
import xyz.shortping.rivanitypunish.events.PlayerEvents;
import xyz.shortping.rivanitypunish.managers.BackendManager;
import xyz.shortping.rivanitypunish.managers.BanManager;
import xyz.shortping.rivanitypunish.managers.CacheManager;
import xyz.shortping.rivanitypunish.managers.MojangManager;
import xyz.shortping.rivanitypunish.managers.MongoManager;
import xyz.shortping.rivanitypunish.managers.MuteManager;

public class RivanityPunish extends Plugin {

    public String Prefix = "§cRivanity §8┃ ",
            noPerm = Prefix + "§cDu hast keine Rechte auf diesen Befehl",
            consolePrefix = "[RivanityPunish] ";

    private String webhookUrl = "https://discordapp.com/api/webhooks/731227480656183307/8EtL4B_Im3n70xwfAAvA_2Uz1xoAascxzeMyHyxjXmDsA5P19FiaPzZaJojnfs75IjXr";
    private boolean webhookEnabled = true;

    private CacheManager cacheManager;
    private MongoManager mongoManager;
    private MojangManager mojangManager;
    private Gson gson;
    private BackendManager backendManager;
    private PlayerEvents playerEvents;
    private BanManager banManager;
    private MuteManager muteManager;

    @Override
    public void onEnable() {
        System.out.println(consolePrefix + "Started System...");
        this.gson = new Gson();
        this.mongoManager = new MongoManager("dev1.shortping.xyz");
        this.mongoManager.connect("RivanityPunish", "W6yYqF!Gh28g", "RivanityPunish");

        init();
    }

    private void init() {
        this.cacheManager = new CacheManager(this);

        this.mojangManager = new MojangManager();
        this.backendManager = new BackendManager(this);
        this.banManager = new BanManager(this);
        this.playerEvents = new PlayerEvents(this);
        this.muteManager = new MuteManager(this);
        BungeeCord.getInstance().getPluginManager().registerCommand(this, new CMD_Ban("ban", this));
        BungeeCord.getInstance().getPluginManager().registerCommand(this, new CMD_Mute("mute", this));
        BungeeCord.getInstance().getPluginManager().registerCommand(this, new CMD_Unban(this, "unban"));
        BungeeCord.getInstance().getPluginManager().registerCommand(this, new CMD_Unmute(this, "unmute"));
        BungeeCord.getInstance().getPluginManager().registerCommand(this, new CMD_Check(this, "check"));
        BungeeCord.getInstance().getPluginManager().registerCommand(this, new CMD_Proof("proof", this));
        BungeeCord.getInstance().getPluginManager().registerCommand(this, new CMD_Proof("evidence", this));
    }

    public String getPrefix() {
        return Prefix;
    }

    public String getNoPerm() {
        return noPerm;
    }

    public String getNoConsole() {
        return consolePrefix;
    }

    public MojangManager getMojangManager() {
        return mojangManager;
    }

    public MongoManager getMongoManager() {
        return mongoManager;
    }

    public Gson getGson() {
        return gson;
    }

    public BackendManager getBackendManager() {
        return backendManager;
    }

    public BanManager getBanManager() {
        return banManager;
    }

    public String getWebhookUrl() {
        return webhookUrl;
    }

    public boolean isWebhookEnabled() {
        return webhookEnabled;
    }

    public MuteManager getMuteManager() {
        return muteManager;
    }

}
