package sg.edu.nus.iss.paf_day27_tvshows_comment.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sg.edu.nus.iss.paf_day27_tvshows_comment.models.Comment;
import sg.edu.nus.iss.paf_day27_tvshows_comment.models.Tvshow;
import sg.edu.nus.iss.paf_day27_tvshows_comment.services.CommentService;

@Controller
@RequestMapping("/review")
public class CommentController {
    
    @Autowired
    CommentService commentSvc;

    @GetMapping
    public String getSearchPage(){

        return "search";

    }

    @GetMapping("/search")
    public String searchShow(@RequestParam("id") String id, Model model){

        Tvshow tvshow = commentSvc.getTvshowById(Integer.parseInt(id));
        model.addAttribute("tvshow", tvshow);
        model.addAttribute("comment", new Comment());
        return "comment";

    }

    @PostMapping("/submitreview/{id}")
    public String processReview(Comment comment, @PathVariable("id") String id){

        commentSvc.insertComment(comment, Integer.parseInt(id));
        return "redirect:/review";

    }



    
}
