package com.recflix.app;

import com.recflix.utils.HashString;

import com.mongodb.client.MongoCollection;

import org.bson.Document;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.eq;

import java.util.List;
import java.util.ArrayList;

import java.security.MessageDigest;

/**
 * Manages user persistence
 */
public class UserRepository {

    private final MongoCollection<Document> users;

    public UserRepository(MongoCollection<Document> users) {
        this.users = users;
    }

    public User findByEmail(String email) {
        Document doc = users.find(eq("email", email)).first();
        return user(doc);
    }

    public User findById(String id) {
        Document doc = users.find(eq("_id", new ObjectId(id))).first();
        return user(doc);
    }

    public User saveUser(User user) {
        String hashedPassword = HashString.hashTheString(user.getPassword());
        Document doc = new Document();
        doc.append("name", user.getName());
        doc.append("email", user.getEmail());
        doc.append("password", hashedPassword);
        users.insertOne(doc);
        return new User(doc.get("_id").toString(), user.getName(), user.getEmail(), hashedPassword);
    }

    private User user(Document doc) {
        return new User(doc.get("_id").toString(), doc.getString("name"), doc.getString("email"),
                doc.getString("password"));
    }

    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        for (Document doc : users.find()) {
            allUsers.add(user(doc));
        }
        return allUsers;
    }
}
