package com.codeup.plantapp.repositories;

import com.codeup.plantapp.models.Friend;
import com.codeup.plantapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long>{

//  IOT Limit the USERS view of users they have not requests or already associated with
    List<Friend> findAllByUser(User user);


    Friend findFriendByUser(User user);

}