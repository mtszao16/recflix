package com.recflix.app;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.regex;

/**
 * Manages UserInteraction persistence
 */
public class UserInteractionRepository {

    private final MongoCollection<Document> userInteractions;

    UserInteractionRepository(MongoCollection<Document> userInteractions) {
        this.userInteractions = userInteractions;
    }

    public UserInteraction findById(String id) {
        Document doc = userInteractions.find(eq("_id", new ObjectId(id))).first();
        return userInteraction(doc);
    }

    public List<UserInteraction> getAllInteractions() {
        List<UserInteraction> allInteractions = new ArrayList<>();
        for (Document doc : userInteractions.find()) {
            allInteractions.add(userInteraction(doc));
        }
        return allInteractions;
    }

    public UserInteraction saveUserInteraction(UserInteraction userInteraction) {
        Document doc = new Document();
        doc.append("interationTime", userInteraction.getInterationTime());
        doc.append("interactionType", userInteraction.getInteractionType());
        userInteractions.insertOne(doc);
        return new UserInteraction(userInteraction.getInterationTime(), userInteraction.getInteractionType());
    }

    private UserInteraction userInteraction(Document doc) {
        return new UserInteraction(doc.getString("interationTime"), doc.getString("interactionType"));
    }
}
