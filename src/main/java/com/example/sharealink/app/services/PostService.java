package com.example.sharealink.app.services;

import com.example.sharealink.app.models.entities.Post;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostService {
    void createPost(String title, String url, String token);
    List<Post> loadAllPost();
    void voteDown(Long id);
    void voteUp(Long id);
    Page<Post> findPostsPaginatedByDescVotes(int pageNo, int pageSize);
    Page<Post> findAllPostPaginated(int pageNo, int pageSize);
}
