package com.example.sharealink.app.repositories;

import com.example.sharealink.app.models.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAll();
    Post getById(Long id);
    Page<Post> findAllByOrderByScoreDesc(Pageable pageable);
}