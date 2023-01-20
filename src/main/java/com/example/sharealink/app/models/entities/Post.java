package com.example.sharealink.app.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "You must provide a title!")
    private String title;
    @NotBlank(message = "You must provide a Url!")
    private String url;
    private int votes;

    @ManyToOne
    //@JoinColumn(name = "id")
    private User user;
}