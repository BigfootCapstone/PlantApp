package com.codeup.plantapp.repositories;

import com.codeup.plantapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByIdIsNot(long id);

    User findUserById(long id);

    User findByUsername(String username);

}