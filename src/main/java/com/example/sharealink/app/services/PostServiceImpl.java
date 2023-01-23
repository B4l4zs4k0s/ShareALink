package com.example.sharealink.app.services;

import com.example.sharealink.app.models.entities.Post;
import com.example.sharealink.app.models.entities.User;
import com.example.sharealink.app.repositories.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserService userService;
    private final TokenService tokenService;

    public PostServiceImpl(PostRepository postRepository, UserService userService, TokenService tokenService) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.tokenService = tokenService;
    }

    public void createPost(String title, String url, String token) {
        Post post = new Post();
        post.setTitle(title);
        post.setUrl(url);
        post.setScore(0);
        post.setUser(userService.findUserByUsername(tokenService.extractUsernameFromToken(token)));
        postRepository.save(post);
    }

    public List<Post> loadAllPost() {
        return postRepository.findAll();
    }


    public void vote(Long id, String username, boolean isUpvote) {
        User user = userService.findUserByUsername(username);
        Post post = postRepository.getById(id);
        removeOldVote(user, post);
        setVoter(isUpvote, user, post);
        post.setScore(post.getUpVoters().size() - post.getDownVoters().size());
        postRepository.save(post);
    }

    private static void setVoter(boolean isUpvote, User user, Post post) {
        if (isUpvote) {
            post.getUpVoters().add(user);
            post.setScore(post.getScore() + 1);
        } else {
            post.getDownVoters().add(user);
            post.setScore(post.getScore() - 1);
        }
    }

    public void removeOldVote(User user, Post post) {
        post.getUpVoters().remove(user);
        post.getDownVoters().remove(user);
    }

    public Page<Post> findPostsPaginatedByDescVotes(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return postRepository.findAllByOrderByScoreDesc(pageable);
    }

    public Page<Post> findAllPostPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return postRepository.findAll(pageable);
    }
}
