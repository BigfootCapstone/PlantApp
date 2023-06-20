package com.codeup.plantapp.repositories;

import com.codeup.plantapp.models.Friend;
import com.codeup.plantapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long>{

    List<Friend> findAllByUserID2(User user);

//    List<Friend> findAllByUser(User user);

    Friend findFriendByUser(User user);

    Friend findFriendByUserAndUserID2(User user, User user2);

}