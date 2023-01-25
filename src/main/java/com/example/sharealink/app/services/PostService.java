package com.example.sharealink.app.services;

import com.example.sharealink.app.models.entities.Post;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostService {
    void createPost(String title, String url, String token);
    boolean urlStartsWith(String url);
    List<Post> loadAllPost();
    void vote(Long id, String username, boolean isUpvote);
    Page<Post> findPostsPaginatedByDescVotes(int pageNo, int pageSize);
    Page<Post> findAllPostPaginated(int pageNo, int pageSize);
}
