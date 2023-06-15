package com.codeup.plantapp.repositories;
import com.codeup.plantapp.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment findById(long id);
}