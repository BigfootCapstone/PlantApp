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
                if (friend.getUserID2().equals(unknownUser)) {
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
        for (User friend: friends){
            System.out.println(friend.getUsername());
        }
        return friends;
    }

    public static List<User> friendsRequests(User user) {
        List<Friend> friendsRequest = user.getFriends();
        List<User> friendReq = new ArrayList<>();
        for (Friend friend : friendsRequest) {
            if (!friend.isConfirmed() && !friend.getUser().equals(user)) {
                System.out.println("Request from other user: " + friend.getUser().getUsername());
                friendReq.add(friend.getUser());
            } else if (!friend.isConfirmed()) {
                System.out.println("Request to user: " + friend.getUserID2().getUsername());
                friendReq.add(friend.getUserID2());
            }
        }
        return friendReq;
    }

    }