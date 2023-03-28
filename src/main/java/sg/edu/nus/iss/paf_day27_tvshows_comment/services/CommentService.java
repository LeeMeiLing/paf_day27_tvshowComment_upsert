package sg.edu.nus.iss.paf_day27_tvshows_comment.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.paf_day27_tvshows_comment.models.Comment;
import sg.edu.nus.iss.paf_day27_tvshows_comment.models.Tvshow;
import sg.edu.nus.iss.paf_day27_tvshows_comment.repositories.CommentRepository;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepo;
    
    public Tvshow getTvshowById(Integer id){

       return commentRepo.getTvshowById(id);
    }


   
    public void insertComment(Comment comment, Integer id){

        commentRepo.insertComment(comment, id);

    }

}
