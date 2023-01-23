package com.example.sharealink.app.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "You must provide a title!")
    private String title;
    @NotBlank(message = "You must provide a Url!")
    private String url;
    private int score;

    @ManyToOne
    //@JoinColumn(name = "id")
    private User user;
    @OneToMany
    private List<User> upVoters;
    @OneToMany
    private List<User> downVoters;
}