package com.codeup.plantapp.repositories;

import com.codeup.plantapp.models.Friend;
import com.codeup.plantapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long>{

    List<Friend> findFriendsByUser(User user);

    Friend findFriendByUserID2(User user);

}