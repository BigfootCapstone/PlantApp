package com.codeup.plantapp.util;

import com.codeup.plantapp.models.Friend;
import com.codeup.plantapp.models.User;

import java.util.ArrayList;
import java.util.List;

public class FriendsManager {

    public static List<User> showUnknownFriends(List<User> allUsers, List<Friend> userFriends){
        List<User> unassociatedUsers = new ArrayList<>();

        for (User unknownUser : allUsers) {
            boolean isAssociated = false; // Flag to track if the user is associated with any friend

            for (Friend friend : userFriends) {
                if (friend.getUser().equals(unknownUser)) {
                    isAssociated = true;
                    break;
                }
            }

            if (!isAssociated) {
                unassociatedUsers.add(unknownUser);
            }
        }

        return unassociatedUsers;
    }

    public static List<User> knownFriends(User user) {
        List<Friend> friendsRequest = user.getFriends();

        List<User> friends = new ArrayList<>();
        for (Friend friend: friendsRequest) {
            if (friend.isConfirmed()) {
                if (friend.getUser().equals(user)) {
                    friends.add(friend.getUserID2());
                } else if (friend.getUserID2().equals(user)) {
                    friends.add(friend.getUser());
                }
            }
        }
        return friends;
    }

    public static List<User> friendsRequests(User user) {
//  GET ALL ASSOC WHERE BOB is USER
        List<Friend> friendsRequest = user.getFriends();

//  HOLD ALL USER2 WHERE BOB is USER
        List<User> friendReq = new ArrayList<>();

//      RUNS ASSOC WHERE BOB is USER
        for (Friend friend : friendsRequest) {

            if (!friend.isConfirmed()) {
//              SHOWS EACH REQUEST WHERE BOB is USER
//                friendReq.add(friend.getUser());
//              SHOWS EACH USER2 WHERE BOB is USER
                System.out.println(friend.getUser().getUsername());
                friendReq.add(friend.getUserID2());
            }
        }
        return friendReq;
    }

    }