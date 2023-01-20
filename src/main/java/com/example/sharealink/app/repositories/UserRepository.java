package com.example.sharealink.app.repositories;

import com.example.sharealink.app.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User getUserByUsername(String username);
}