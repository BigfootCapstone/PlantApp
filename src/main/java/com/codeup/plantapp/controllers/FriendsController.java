package com.codeup.plantapp.controllers;

import com.codeup.plantapp.models.Friend;
import com.codeup.plantapp.models.GardenPlant;
import com.codeup.plantapp.models.User;
import com.codeup.plantapp.repositories.FriendRepository;
import com.codeup.plantapp.repositories.UserRepository;
import com.codeup.plantapp.services.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/friends")
public class FriendsController {

        private final UserRepository usersDao;
        private final FriendRepository friendDao;

        public FriendsController(UserRepository usersDao, FriendRepository friendDao){
            this.usersDao = usersDao;
            this.friendDao = friendDao;
        }

        @Autowired
        private Keys keys;
/*
|><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><|
|><<>><<>><<>><<>><<>><<>>SEE UNASIGNED FRIENDS <<>><<>><<>><<>><<>><<>><|
|><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><|
*/
    @GetMapping("/")
    public String allUsers(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<User> botaniUsers  = usersDao.findAllByIdIsNot(user.getId()); // all users except current user
        List<Friend> friendsAssoc = friendDao.findAllByUser(user); // all friends associations

        List<User> unassociatedFriends = new ArrayList<>();

        for (User unknownUser : botaniUsers) {
            boolean isAssociated = false; // Flag to track if the user is associated with any friend

            for (Friend friend : friendsAssoc) {
                if (friend.getUserID2().equals(unknownUser)) {
                    isAssociated = true;
                    break;
                }
            }

            if (!isAssociated) {
                unassociatedFriends.add(unknownUser);
            }
        }

        model.addAttribute("users", unassociatedFriends);
        return "friends";
    }

/*
|><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><|
*/
/*
|><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><|
|><<>><<>><<>><<>><<>><<>> USER ADD FRIEND   ><<>><<>><<>><<>><<>><<>><<>><<>><|
|><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><|
*/
    @GetMapping("/{id}")
    public String addFriend(@PathVariable long id,
                            RedirectAttributes redirectAttributes){
        User user1 = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = usersDao.findUserById(id);

        Friend newRequest = new Friend(user1, user, false);
        friendDao.save(newRequest);

        redirectAttributes.addFlashAttribute("successMessage", "Comment submitted successfully!");

        return "redirect:/friends/";
    }
/*
|><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><|
*/
/*
|><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><|
|><<>><<>><<>><<>><<>><<>>USER ACC/IGN FRIEND REQUEST <<>><<>><<>><<>><<>><<>><|
|><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><|
*/
    @GetMapping("/accept/{id}")
    public String acceptFriend(@PathVariable long id){
        User user = usersDao.findUserById(id);
        Friend friend = friendDao.findFriendByUserID2(user);

        friend.setConfirmed(true);
        friendDao.save(friend);

        return "redirect:/users/profile";
    }

    @GetMapping("/ignore/{id}")
    public String ignoreFriend(@PathVariable long id){
        User user = usersDao.findUserById(id);
        Friend friend = friendDao.findFriendByUserID2(user);

        friendDao.deleteById(friend.getId());

        return "redirect:/users/profile";
    }
/*
|><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><|
*/
}