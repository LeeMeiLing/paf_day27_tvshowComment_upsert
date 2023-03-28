package sg.edu.nus.iss.paf_day27_tvshows_comment.models;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Tvshow {
    
    private Integer id;
    private String name;
    private String language;
    private String status;
    private List<Comment> comments = new ArrayList<>();

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public List<Comment> getComments() {
        return comments;
    }
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Comment toComment(JsonObject json) {

        Comment comment = new Comment();
        comment.setName(json.getString("name"));
        comment.setRating((float) json.getJsonNumber("rating").doubleValue());
        comment.setComment(json.getString("comment"));
        return comment;
    }
    
    
    
    @Override
    public String toString() {
        return "Tvshow [id=" + id + ", name=" + name + ", language=" + language + ", status=" + status + "]";
    }
    
    public static Tvshow toTvshow (String jsonStr){

        Tvshow tvshow = new Tvshow();
        JsonReader reader = Json.createReader(new StringReader(jsonStr));
		JsonObject o = reader.readObject();
        tvshow.setId(o.getInt("id"));
        tvshow.setName(o.getString("name"));
        tvshow.setLanguage(o.getString("language"));
        tvshow.setStatus(o.getString("status"));
        try{

            List<Comment> comments = o.getJsonArray("comments").stream()
            .map(json -> json.asJsonObject())
            .map(json -> tvshow.toComment(json))
            .collect(Collectors.toList());
            tvshow.setComments(comments);

            System.out.println(comments); // debug
        
        }catch(Exception ex){
            return tvshow;
        }
        
        return tvshow;
        
    }
   

    /*
     * {
    "_id" : ObjectId("642111bea963308b5e1c58cf"),
    "id" : NumberInt(2),
    "url" : "http://www.tvmaze.com/shows/2/person-of-interest",
    "name" : "Person of Interest",
    "type" : "Scripted",
    "language" : "English",
    "genres" : [
        "Drama",
        "Action",
        "Crime"
    ],
    "status" : "Ended",
    "runtime" : NumberInt(60),
    "premiered" : "2011-09-22",
    "officialSite" : "http://www.cbs.com/shows/person_of_interest/",
    "schedule" : {
        "time" : "22:00",
        "days" : [
            "Tuesday"
        ]
    },
    "rating" : {
        "average" : NumberInt(9)
    },
    "weight" : NumberInt(96),
    "network" : {
        "id" : NumberInt(2),
        "name" : "CBS",
        "country" : {
            "name" : "United States",
            "code" : "US",
            "timezone" : "America/New_York"
        }
    },
    "webChannel" : null,
    "externals" : {
        "tvrage" : NumberInt(28376),
        "thetvdb" : NumberInt(248742),
        "imdb" : "tt1839578"
    },
    "image" : {
        "medium" : "http://static.tvmaze.com/uploads/images/medium_portrait/163/407679.jpg",
        "original" : "http://static.tvmaze.com/uploads/images/original_untouched/163/407679.jpg"
    },
    "summary" : "<p>You are being watched. The government has a secret system, a machine that spies on you every hour of every day. I know because I built it. I designed the Machine to detect acts of terror but it sees everything. Violent crimes involving ordinary people. People like you. Crimes the government considered \"irrelevant\". They wouldn't act so I decided I would. But I needed a partner. Someone with the skills to intervene. Hunted by the authorities, we work in secret. You'll never find us. But victim or perpetrator, if your number is up, we'll find you.</p>",
    "updated" : NumberInt(1535507028),
    "_links" : {
        "self" : {
            "href" : "http://api.tvmaze.com/shows/2"
        },
        "previousepisode" : {
            "href" : "http://api.tvmaze.com/episodes/659372"
        }
    }
}
     */
}
