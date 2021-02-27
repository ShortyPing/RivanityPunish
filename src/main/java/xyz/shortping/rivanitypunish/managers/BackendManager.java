/**
 *
 * Copyright (c) Michael Steinmötzger 2020
 *
 * All rights reserved
 *
 * */
package xyz.shortping.rivanitypunish.managers;

import com.mongodb.async.SingleResultCallback;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import de.dytanic.cloudnet.api.CloudAPI;
import java.util.ArrayList;
import java.util.UUID;
import java.util.function.Consumer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.bson.Document;
import xyz.shortping.rivanitypunish.RivanityPunish;
import xyz.shortping.rivanitypunish.entities.RivanityBan;
import xyz.shortping.rivanitypunish.entities.RivanityMute;

public class BackendManager {

    private final RivanityPunish plugin;

    public BackendManager(RivanityPunish plugin) {
        this.plugin = plugin;
    }

    //<editor-fold defaultstate="collapsed" desc="-- BANS --">
    //<editor-fold defaultstate="collapsed" desc="createBan">
    public void createBan(String bannedPlayer, ProxiedPlayer responsiblePlayer, String reason, boolean permanent, long time, Consumer<RivanityBan> consumer) {
        UUID uuid = CloudAPI.getInstance().getPlayerUniqueId(bannedPlayer);
        String uuidString = uuid.toString();
        plugin.getMongoManager().getBans().find(Filters.eq("bannedPlayer", uuidString)).first((Document t, Throwable thrwbl) -> {

            if (t == null) {

                RivanityBan ban = new RivanityBan();
                String banId = UUID.randomUUID().toString().replace("-", "").substring(0, 7);
                ban.setBanID(banId);
                ban.setBannedPlayer(uuidString);
                ban.setPermanent(permanent);
                ban.setResponsiblePlayer(responsiblePlayer.getUniqueId().toString());
                ban.setReason(reason);
                ban.setTimestamp(time);
                ban.setEvidences(new ArrayList<>());

                t = plugin.getGson().fromJson(plugin.getGson().toJson(ban), Document.class);

                plugin.getMongoManager().getBans().insertOne(t, (Void t1, Throwable thrwbl1) -> {
                    consumer.accept(ban);
                });
            }

        });

    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="createBan">
    public void createBan(String bannedPlayer, String reason, boolean permanent, long time, Consumer<RivanityBan> consumer) {
        UUID uuid = CloudAPI.getInstance().getPlayerUniqueId(bannedPlayer);
        String uuidString = uuid.toString();
        plugin.getMongoManager().getBans().find(Filters.eq("bannedPlayer", uuidString)).first((Document t, Throwable thrwbl) -> {

            if (t == null) {

                RivanityBan ban = new RivanityBan();
                String banId = UUID.randomUUID().toString().replace("-", "").substring(0, 7);
                ban.setBanID(banId);
                ban.setBannedPlayer(uuidString);
                ban.setPermanent(permanent);
                ban.setResponsiblePlayer("System");
                ban.setReason(reason);
                ban.setTimestamp(time);

                t = plugin.getGson().fromJson(plugin.getGson().toJson(ban), Document.class);

                plugin.getMongoManager().getBans().insertOne(t, (Void t1, Throwable thrwbl1) -> {
                    consumer.accept(ban);
                });
            }

        });

    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getBan">
    public void getBan(String banId, Consumer<RivanityBan> consumer) {

        plugin.getMongoManager().getBans().find(Filters.eq("banID", banId)).first((Document t, Throwable thrwbl) -> {

            if (t != null) {
                RivanityBan ban = plugin.getGson().fromJson(t.toJson(), RivanityBan.class);

                consumer.accept(ban);
            }

        });
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getBan">
    public void getBan(UUID bannedPlayer, Consumer<RivanityBan> consumer) {
        String uuid = bannedPlayer.toString();

        plugin.getMongoManager().getBans().find(Filters.eq("bannedPlayer", uuid)).first((Document t, Throwable thrwbl) -> {

            if (t != null) {
                consumer.accept(plugin.getGson().fromJson(t.toJson(), RivanityBan.class));
            }
        });
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="isBanExist">
    public void isBanExist(String banId, Consumer<Boolean> consumer) {

        plugin.getMongoManager().getBans().find(Filters.eq("banID", banId)).first((Document t, Throwable thrwbl) -> {

            if (t == null) {
                consumer.accept(Boolean.FALSE);
            } else {
                consumer.accept(Boolean.TRUE);
            }

        });
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="isBanExist">
    public void isBanExist(UUID bannedPlayer, Consumer<Boolean> consumer) {
        String uuid = bannedPlayer.toString();
        plugin.getMongoManager().getBans().find(Filters.eq("bannedPlayer", uuid)).first((Document t, Throwable thrwbl) -> {

            if (t == null) {
                consumer.accept(Boolean.FALSE);
            } else {
                consumer.accept(Boolean.TRUE);
            }

        });
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="deleteBan">
    public void deleteBan(String banID) {

        plugin.getMongoManager().getBans().deleteOne(Filters.eq("banID", banID), (DeleteResult t, Throwable thrwbl) -> {
        });

    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="deleteBan">
    public void deleteBan(UUID bannedPlayer) {
        String uuid = bannedPlayer.toString();

        plugin.getMongoManager().getBans().deleteOne(Filters.eq("bannedPlayer", uuid), (DeleteResult t, Throwable thrwbl) -> {
        });

    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="updateBan">
    public void updateBan(RivanityBan rivanityBan, Consumer<RivanityBan> consumer) {
        
        Document t = plugin.getGson().fromJson(plugin.getGson().toJson(rivanityBan), Document.class);
        
        plugin.getMongoManager().getBans().replaceOne(Filters.eq("banID", rivanityBan.getBanID()), t, (UpdateResult t1, Throwable thrwbl) -> {
            
            consumer.accept(rivanityBan);
        });
        
    }
    //</editor-fold>

//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="-- MUTES --">
    //<editor-fold defaultstate="collapsed" desc="createMute">
    public void createMute(String mutedPlayer, ProxiedPlayer responsiblePlayer, String reason, boolean permanent, long time, Consumer<RivanityMute> consumer) {
        UUID uuid = CloudAPI.getInstance().getPlayerUniqueId(mutedPlayer);
        String uuidString = uuid.toString();
        plugin.getMongoManager().getMutes().find(Filters.eq("bannedPlayer", uuidString)).first((Document t, Throwable thrwbl) -> {

            if (t == null) {

                RivanityMute mute = new RivanityMute();
                String banId = UUID.randomUUID().toString().replace("-", "").substring(0, 7);
                mute.setBanID(banId);
                mute.setBannedPlayer(uuidString);
                mute.setPermanent(permanent);
                mute.setResponsiblePlayer(responsiblePlayer.getUniqueId().toString());
                mute.setReason(reason);
                mute.setTimestamp(time);
                mute.setEvidences(new ArrayList<>());

                t = plugin.getGson().fromJson(plugin.getGson().toJson(mute), Document.class);

                plugin.getMongoManager().getMutes().insertOne(t, (Void t1, Throwable thrwbl1) -> {
                    consumer.accept(mute);
                });
            }

        });

    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="createMute">
    public void createMute(String mutedPlayer, String reason, boolean permanent, long time, Consumer<RivanityMute> consumer) {
        UUID uuid = CloudAPI.getInstance().getPlayerUniqueId(mutedPlayer);
        String uuidString = uuid.toString();
        plugin.getMongoManager().getMutes().find(Filters.eq("bannedPlayer", uuidString)).first((Document t, Throwable thrwbl) -> {

            if (t == null) {

                RivanityMute mute = new RivanityMute();
                String banId = UUID.randomUUID().toString().replace("-", "").substring(0, 7);
                mute.setBanID(banId);
                mute.setBannedPlayer(uuidString);
                mute.setPermanent(permanent);
                mute.setResponsiblePlayer("System");
                mute.setReason(reason);
                mute.setTimestamp(time);

                t = plugin.getGson().fromJson(plugin.getGson().toJson(mute), Document.class);

                plugin.getMongoManager().getMutes().insertOne(t, (Void t1, Throwable thrwbl1) -> {
                    consumer.accept(mute);
                });
            }

        });

    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getMute">
    public void getMute(String banId, Consumer<RivanityMute> consumer) {

        plugin.getMongoManager().getMutes().find(Filters.eq("banID", banId)).first((Document t, Throwable thrwbl) -> {

            if (t != null) {
                RivanityMute mute = plugin.getGson().fromJson(t.toJson(), RivanityMute.class);

                consumer.accept(mute);
            }

        });
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getMute">
    public void getMute(UUID mutedPlayer, Consumer<RivanityMute> consumer) {
        String uuid = mutedPlayer.toString();

        plugin.getMongoManager().getMutes().find(Filters.eq("bannedPlayer", uuid)).first((Document t, Throwable thrwbl) -> {

            if (t != null) {
                consumer.accept(plugin.getGson().fromJson(t.toJson(), RivanityMute.class));
            }
        });
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="isMuteExist">
    public void isMuteExist(String banId, Consumer<Boolean> consumer) {

        plugin.getMongoManager().getMutes().find(Filters.eq("banID", banId)).first((Document t, Throwable thrwbl) -> {

            if (t == null) {
                consumer.accept(Boolean.FALSE);
            } else {
                consumer.accept(Boolean.TRUE);
            }

        });
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="isMuteExist">
    public void isMuteExist(UUID mutedPlayer, Consumer<Boolean> consumer) {
        String uuid = mutedPlayer.toString();
        plugin.getMongoManager().getMutes().find(Filters.eq("bannedPlayer", uuid)).first((Document t, Throwable thrwbl) -> {

            if (t == null) {
                consumer.accept(Boolean.FALSE);
            } else {
                consumer.accept(Boolean.TRUE);
            }

        });
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="deleteMute">
    public void deleteMute(String banID) {

        plugin.getMongoManager().getMutes().deleteOne(Filters.eq("banID", banID), (DeleteResult t, Throwable thrwbl) -> {
        });

    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="deleteMute">
    public void deleteMute(UUID mutedPlayer) {
        String uuid = mutedPlayer.toString();

        plugin.getMongoManager().getMutes().deleteOne(Filters.eq("bannedPlayer", uuid), (DeleteResult t, Throwable thrwbl) -> {
        });

    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="updateMute">
    public void updateMute(RivanityMute rivanityMute, Consumer<RivanityMute> consumer) {
        
        Document t = plugin.getGson().fromJson(plugin.getGson().toJson(rivanityMute), Document.class);
        
        plugin.getMongoManager().getMutes().replaceOne(Filters.eq("banID", rivanityMute.getBanID()), t, (UpdateResult t1, Throwable thrwbl) -> {
            
            consumer.accept(rivanityMute);
        });
    }
    //</editor-fold>

//</editor-fold>
}
