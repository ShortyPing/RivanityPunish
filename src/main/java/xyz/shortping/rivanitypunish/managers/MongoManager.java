/**
 *
 * Copyright (c) Michael Steinmötzger 2020
 *
 * All rights reserved
 *
 * */
package xyz.shortping.rivanitypunish.managers;


import com.mongodb.ConnectionString;
import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoClients;
import com.mongodb.async.client.MongoCollection;
import com.mongodb.async.client.MongoDatabase;
import java.text.MessageFormat;
import org.bson.Document;

public class MongoManager {

    private final String hostname;

    private MongoClient client;
    private MongoDatabase database;

    private MongoCollection<Document> bans, reports, mutes;

    public MongoManager(String hostname) {
        this.hostname = hostname;
    }

    public void connect(String username, String password, String database) {

        this.client = MongoClients.create(new ConnectionString(MessageFormat.format("mongodb://{0}:{1}@{2}/{3}?retryWrites=true&w=majority", username, password, hostname, database)));
        this.database = this.client.getDatabase(database);
        this.bans = this.database.getCollection("Bans");
        this.reports = this.database.getCollection("Reports");
        this.mutes = this.database.getCollection("Mutes");
    }

    public MongoDatabase getDatabase() {
        return database;
    }

    public MongoCollection<Document> getBans() {
        return bans;
    }

    public MongoCollection<Document> getReports() {
        return reports;
    }

    public MongoCollection<Document> getMutes() {
        return mutes;
    }
    
    
    
    
}
