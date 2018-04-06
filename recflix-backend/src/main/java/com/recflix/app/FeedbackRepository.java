package com.recflix.app;

import com.mongodb.client.MongoCollection;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

/**
 * Manages Feedback persistence
 */
public class FeedbackRepository {

    private final MongoCollection<Document> feedbacks;

    FeedbackRepository(MongoCollection<Document> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public Feedback findById(String id) {
        Document doc = feedbacks.find(eq("_id", new ObjectId(id))).first();
        return feedback(doc);
    }

    public List<Feedback> getAllFeedbacks() {
        List<Feedback> allFeedbacks = new ArrayList<>();
        for (Document doc : feedbacks.find()) {
            allFeedbacks.add(feedback(doc));
        }
        return allFeedbacks;
    }

    public Feedback saveFeedback(Feedback feedback) {
        Document doc = new Document();
        doc.append("rating", feedback.getRating());
        doc.append("type", feedback.getType());
        doc.append("userId", feedback.getUserId());
        doc.append("movieId", feedback.getMovieId());
        doc.append("createdAt", Scalars.dateTime.getCoercing().serialize(feedback.getCreatedAt()));
        feedbacks.insertOne(doc);

        return new Feedback(doc.get("_id").toString(), doc.getInteger("rating"), doc.getString("type"),
                ZonedDateTime.parse(doc.getString("createdAt")), doc.getString("watchedBy"),
                doc.getString("watchedMovie"));
    }

    private Feedback feedback(Document doc) {
        return new Feedback(doc.get("_id").toString(), doc.getInteger("rating"), doc.getString("type"),
                ZonedDateTime.parse(doc.getString("createdAt")), doc.getString("watchedBy"),
                doc.getString("watchedMovie"));
    }
}
