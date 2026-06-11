package com.school;

import com.mongodb.client.MongoDatabase;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MongoTest {
    @Test
    void testConnection() {
        // Убедись, что переменная MONGO_URI задана в твоей ОС
        // В терминале: export MONGO_URI="mongodb+srv://..."
        MongoDatabase db = MongoConfig.getDatabase();
        assertNotNull(db);
    }
}