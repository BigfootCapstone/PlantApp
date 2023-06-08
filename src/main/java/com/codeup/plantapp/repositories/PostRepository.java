package com.codeup.plantapp.repositories;


import com.codeup.plantapp.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Post findById(long id);

}