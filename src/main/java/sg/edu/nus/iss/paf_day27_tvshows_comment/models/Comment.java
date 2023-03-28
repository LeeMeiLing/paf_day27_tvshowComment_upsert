package sg.edu.nus.iss.paf_day27_tvshows_comment.models;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Comment {
    
    private String name;
    private Float rating;
    private String comment;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Float getRating() {
        return rating;
    }
    public void setRating(Float rating) {
        this.rating = rating;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Comment [name=" + name + ", rating=" + rating + ", comment=" + comment + "]";
    }
    public JsonObject toJson(){
        
        JsonObject json = Json.createObjectBuilder()
            .add("name",name)
            .add("rating",rating)
            .add("comment",comment)
            .build();
        return json;
    }

}
