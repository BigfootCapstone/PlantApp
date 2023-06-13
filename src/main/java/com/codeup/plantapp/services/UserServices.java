package com.codeup.plantapp.services;

import com.codeup.plantapp.models.User;
import com.codeup.plantapp.models.UserRolls;
import com.codeup.plantapp.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


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
            return new UserRolls(user.getId(), user.getUsername(), user.getEmail(),user.getPassword());
        }
    }

    // maybe use
    public void updateUserProfile(Long id, String firstName, String lastName, String city, String email, String password) {
        User user = usersDao.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        user.setFirst_name(firstName);
        user.setLast_name(lastName);
        user.setCity(city);
        user.setEmail(email);
        user.setPassword(password);
        usersDao.save(user);
    }
}
