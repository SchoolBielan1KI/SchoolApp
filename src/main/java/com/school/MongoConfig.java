package com.school;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoConfig {
    public static MongoDatabase getDatabase() {
        // Берет строку из GitHub Secrets (в CI) или из переменной среды (локально)
        String uri = System.getenv("MONGO_URI");
        if (uri == null || uri.isEmpty()) {
            throw new RuntimeException("MONGO_URI не задан!");
        }
        MongoClient client = MongoClients.create(uri);
        return client.getDatabase("school_db");
    }
}