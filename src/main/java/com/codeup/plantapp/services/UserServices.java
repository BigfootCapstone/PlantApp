package com.codeup.plantapp.services;

import com.codeup.plantapp.models.User;
import com.codeup.plantapp.models.BotaniUserDetails;
import com.codeup.plantapp.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Service
public class UserServices implements UserDetailsService {
    public final UserRepository usersDao;

    public UserServices(UserRepository usersDao) {
        this.usersDao = usersDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = usersDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("No user found for " + username);
        } else {
//          Returns SpringBlogUserDetails object
            return new BotaniUserDetails(user);
        }
    }

}