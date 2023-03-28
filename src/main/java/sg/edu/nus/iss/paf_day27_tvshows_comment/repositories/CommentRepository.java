package sg.edu.nus.iss.paf_day27_tvshows_comment.repositories;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.client.result.UpdateResult;

import jakarta.json.JsonObject;
import sg.edu.nus.iss.paf_day27_tvshows_comment.models.Comment;
import sg.edu.nus.iss.paf_day27_tvshows_comment.models.Tvshow;

@Repository
public class CommentRepository {

    private static final String C_TVSHOWS = "tvshows";
    
    @Autowired
    MongoTemplate mongoTemplate;

    /*
     * db.tvshows.find({ id: 2 })
     */
    public Tvshow getTvshowById(Integer id){

        Criteria criteria =  Criteria.where("id").is(id);

        Query query = Query.query(criteria);

        Document doc = mongoTemplate.findOne(query,Document.class,C_TVSHOWS);

        String jsonStr = doc.toJson();

        Tvshow tvshow = Tvshow.toTvshow(jsonStr);
        
        return tvshow;
    }

    /*
     * db.tvshows.update(
            {id: 3},
            {
                $push: { comments: [
                {
                    name: "Bernard",
                    rating: 4.5,
                    comment: "This is so thrilling"
                }
                ]}
            },
            
            {upsert : true}

        )
     */
    public void upsertComment(Comment comment, Integer id){

        Query query = Query.query(Criteria.where("id").is(id));

        JsonObject json = comment.toJson();

        // Update updateOps= new Update().push("comments").each(List.of(json));
        // Update updateOps= new Update().push("comments").each(List.of(json).toArray());
        // Update updateOps= new Update().push("comments",json);
        Update updateOps= new Update().push("comments",comment); // if pass object directly,  _class field will be auto created
        
        UpdateResult updateResult = mongoTemplate.upsert(query, updateOps, C_TVSHOWS);

        System.out.printf("matched: %d\n", updateResult.getMatchedCount());
		System.out.printf("modified: %d\n", updateResult.getModifiedCount());

    }

    /*
     * db.tvshows.update(
            {id: 3},
            {
                $push: { comments: 
                    {
                        name: "Bernard",
                        rating: 4.5,
                        comment: "This is so thrilling"
                    }
                }
            }
        )
     */
    public void insertComment(Comment comment, Integer id){

        // convert comment to document
        Document doc = new Document();
        doc.put("name", comment.getName());
        doc.put("rating", comment.getRating());
        doc.put("comment", comment.getComment());

        Query query = Query.query(Criteria.where("id").is(id));

        Update updateOps= new Update().push("comments",doc);
        
        UpdateResult updateResult = mongoTemplate.updateFirst(query, updateOps, Document.class ,C_TVSHOWS);

        System.out.printf("matched: %d\n", updateResult.getMatchedCount());
		System.out.printf("modified: %d\n", updateResult.getModifiedCount());
        
    }

}
