package com.codeup.plantapp.controllers;

import org.springframework.stereotype.Controller;
import com.codeup.plantapp.repositories.*;
import com.codeup.plantapp.models.*;
//import com.codeup.plantapp.services.EmailService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostController {
    private PostRepository postsDao;
    private UserRepository usersDao;
    private CommentRepository commentsDao;
//    private final EmailService emailService;

    public PostController(PostRepository postsDao, UserRepository usersDao, CommentRepository commentsDao){
//        this.emailService = emailService;
        this.postsDao = postsDao;
        this.usersDao = usersDao;
        this.commentsDao = commentsDao;
    }

    /*
    |><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><|
    |><<>><<>><<>><<>><<>><<>><<>><<>><<SHOWPOST>><<>><<>><<>><<>><<>><<>><<>><<>><|
    |><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><|
    */
    @GetMapping("/all")
    public String allPosts(Model model) {
        List<Post> allPosts = postsDao.findAll();
        model.addAttribute("allPosts", allPosts);
        return "posts/index";
    }

    @GetMapping("/{id}")
    public String onePost(@PathVariable long id, Model model) {
        Post post = postsDao.findById(id);
        User user = post.getUser();
        model.addAttribute("user", user);
        model.addAttribute("post", post);
        return "posts/show";
    }

    @PostMapping("/comment")
    public String submitComment(@RequestParam(name="content") String content,
                                @RequestParam(name="postId") long postId){
        Post post = postsDao.findById(postId);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Comment comment = new Comment(content, user, post);
        commentsDao.save(comment);
        return "redirect:/post/all";
    }

    /*
    |><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><|
    |><<>><<>><<>><<>><<>><<>><<>><<>CREATE A POST <>><<>><<>><<>><<>><<>><<>><<>><|
    |><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><|
    */
    @GetMapping("/create")
    public String createPost(HttpSession session, Model model) {
        session.getAttribute("user");
        model.addAttribute("post", new Post());
        return "posts/create";
    }

    @PostMapping("/create")
    public String submitPost(HttpSession session,
                             @ModelAttribute Post post,
                             @RequestParam(name = "title") String title,
                             @RequestParam(name = "body") String body) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        post.setUser(user);
        postsDao.save(post);
//        emailService.prepareAndSend(posts, title, body);
        return "redirect:/posts/all";
    }

    /*
    |><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><|
    |><<>><<>><<>><<>><<>><<>><<>><<>><EDIT POST ><<>><<>><<>><<>><<>><<>><<>><<>><|
    |><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><|
    */
    @GetMapping("/{id}/edit")
    public String editPost(HttpSession session,
                           @PathVariable long id,
                           Model model) {
        Post post = postsDao.findById(id);
        model.addAttribute("thingpost", post);
        return "posts/edit";
    }

    @PostMapping("/{id}/edit")
    public String submitEditPost(HttpSession session,
                                 @ModelAttribute Post thingpost,
                                 @RequestParam(name = "title") String title,
                                 @RequestParam(name = "body") String body) {
        User user = (User) session.getAttribute("user");
        thingpost.setUser(user);
        thingpost.setTitle(title);
        thingpost.setBody(body);
        postsDao.save(thingpost);
        return "redirect:/";
    }

}
