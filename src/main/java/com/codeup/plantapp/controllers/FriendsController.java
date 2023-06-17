package com.codeup.plantapp.controllers;

import com.codeup.plantapp.models.Friend;
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

import java.util.List;

import static com.codeup.plantapp.util.FriendsManager.showUnknownFriends;

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

//  ALL BUT CURRENT USER
        List<User> botaniUsers  = usersDao.findAllByIdIsNot(user.getId());

//  ALL BUT CURRENT FRIEND ASSOCIATIONS (TRUE OR FALSE)
        List<Friend> friendsAssoc = friendDao.findAllByUserID2(user);

        model.addAttribute("users", showUnknownFriends(botaniUsers, friendsAssoc));
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
//  USER
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//  USER2
        User friend = usersDao.findUserById(id);

//  USER to USER2
//        Friend userToFriend = new Friend(user, friend, false);
//  USER2 to USER
        Friend friendToUser = new Friend(friend, user, false);

//  USER REQUESTS FRIENDSHIP
//        friendDao.save(userToFriend);

//  USER2 MAY ACCEPT OR IGNORE
        friendDao.save(friendToUser);

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

//  USER2
        User userDecider = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//  USER
        User userRequestor = usersDao.findUserById(id);

//  USER2 to USER
        Friend decider = friendDao.findFriendByUser(userDecider);

//  USER2 FRIENDS W/ USER
        decider.setConfirmed(true);

//  USER2 ACCEPTS FRIEND REQUEST
        friendDao.save(decider);

        Friend reqToDec = new Friend(userRequestor, userDecider, true);
        friendDao.save(reqToDec);

        return "redirect:/users/profile";
    }

    @GetMapping("/ignore/{id}")
    public String ignoreFriend(@PathVariable long id){

//  USER2
        User user2 = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//  USER
        User user = usersDao.findUserById(id);

//  USER2 to USER
        Friend decider = friendDao.findFriendByUser(user2);
//  USER to USER2
        Friend sender = friendDao.findFriendByUser(user);

//  USER2 IGNORES FRIEND USER
        friendDao.deleteById(decider.getId());
//  USER LOSES ASSOC WITH USER2
        friendDao.deleteById(sender.getId());

        return "redirect:/users/profile";
    }
/*
|><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><|
*/
}