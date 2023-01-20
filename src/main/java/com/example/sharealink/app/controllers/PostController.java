package com.example.sharealink.app.controllers;

import com.example.sharealink.app.models.entities.Post;
import com.example.sharealink.app.services.CookieService;
import com.example.sharealink.app.services.PostService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PostController {
    private final PostService postService;
    private final CookieService cookieService;

    @Autowired
    public PostController(PostService postService, CookieService cookieService) {
        this.postService = postService;
        this.cookieService = cookieService;
    }

    @GetMapping("/feed/{pageNo}")
    public String mainPage(@PathVariable(value = "pageNo") int pageNo,HttpServletRequest httpServletRequest, Model model) {
        String username = cookieService.getNameFromCookie(httpServletRequest);
        Page<Post> page = postService.findAllPostPaginated(pageNo, 10);
        List<Post> listOfPosts = page.getContent();

        model.addAttribute("username", username);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listOfPosts", listOfPosts);
        return "main-feed";
    }

    @GetMapping("/topPosts/{pageNo}")
    public String findPaginatedDescVotes(@PathVariable(value = "pageNo") int pageNo, HttpServletRequest httpServletRequest,
                                         Model model) {
        String username = cookieService.getNameFromCookie(httpServletRequest);
        Page<Post> page = postService.findPostsPaginatedByDescVotes(pageNo, 10);
        List<Post> listOfPosts = page.getContent();

        model.addAttribute("username", username);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listOfPosts", listOfPosts);
        return "highest-rated-desc";
    }

    @GetMapping("/newPost")
    public String newPostPage() {
        return "new-post";
    }

    @PostMapping("/newPost")
    public String createNewPost(@RequestParam(name = "title") String title,
                                @RequestParam(name = "url") String url,
                                HttpServletRequest httpRequest) {
        String token = cookieService.getCookieValue(httpRequest);
        postService.createPost(title, url, token);
        return "redirect:/feed/1";
    }

    @GetMapping("/upVote/{id}")
    public String voteUpPost(@PathVariable(value = "id") Long id) {
        postService.voteUp(id);
        return "redirect:/feed/1";
    }

    @GetMapping("/downVote/{id}")
    public String voteDownPost(@PathVariable(value = "id") Long id) {
        postService.voteDown(id);
        return "redirect:/feed/1";
    }
}
