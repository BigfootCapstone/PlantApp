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
                friends.add(friend.getUserID2());
            }
        }
        return friends;
    }

    public static List<User> friendsRequests (User user) {
        List<Friend> friendsRequest = user.getFriends();
        List<User> friendReq = new ArrayList<>();
        for (Friend friend: friendsRequest) {
            if (!friend.isConfirmed()) {
                friendReq.add(friend.getUserID2());
            }
        }
        return friendReq;
    }

    }