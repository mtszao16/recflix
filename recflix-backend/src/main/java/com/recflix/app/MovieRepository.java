package com.recflix.app;

import com.mongodb.client.MongoCollection;

import org.bson.Document;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.eq;

import java.util.List;
import java.util.ArrayList;

/**
 * Manages movie persistence
 */
public class MovieRepository {

    private final MongoCollection<Document> movies;

    public MovieRepository(MongoCollection<Document> movies) {
        this.movies = movies;
    }

    public Movie findById(String id) {
        Document doc = movies.find(eq("_id", new ObjectId(id))).first();
        return movie(doc);
    }

    public Movie saveMovie(Movie movie) {
        Document doc = new Document();
        doc.append("name", movie.getName());
        doc.append("url", movie.getUrl());
        movies.insertOne(doc);
        return new Movie(doc.get("_id").toString(), movie.getName(), movie.getUrl());
    }

    private Movie movie(Document doc) {
        return new Movie(doc.get("_id").toString(), doc.getString("name"), doc.getString("url"));
    }

    public List<Movie> getAllMovies() {
        List<Movie> allMovies = new ArrayList<>();
        for (Document doc : movies.find()) {
            allMovies.add(movie(doc));
        }
        return allMovies;
    }
}
