package com.example.sharealink.app.services;

import com.example.sharealink.app.models.entities.Post;
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
        post.setVotes(0);
        post.setUser(userService.findUserByUsername(tokenService.extractUsernameFromToken(token)));
        postRepository.save(post);
    }

    public List<Post> loadAllPost() {
        return postRepository.findAll();
    }

    public void voteUp(Long id) {
        postRepository.getById(id).setVotes(postRepository.getById(id).getVotes() + 1);
        postRepository.save(postRepository.getById(id));
    }


    public void voteDown(Long id) {
        postRepository.getById(id).setVotes(postRepository.getById(id).getVotes() - 1);
        postRepository.save(postRepository.getById(id));
    }

    public Page<Post> findPostsPaginatedByDescVotes(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo-1, pageSize);
        return postRepository.findAllByOrderByVotesDesc(pageable);
    }

    public Page<Post> findAllPostPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo-1, pageSize);
        return postRepository.findAll(pageable);
    }
}
