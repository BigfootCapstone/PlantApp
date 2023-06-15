package com.codeup.plantapp.repositories;

import com.codeup.plantapp.models.GardenPlant;
import com.codeup.plantapp.models.Post;
import com.codeup.plantapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PostRepository extends JpaRepository<Post, Long> {
   Post findById(long id);

   List<Post> findPostByUser(User user);
}
