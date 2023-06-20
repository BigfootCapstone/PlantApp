package com.codeup.plantapp.models;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;


public class BotaniUserDetails extends User implements UserDetails {
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return null;
        return AuthorityUtils.commaSeparatedStringToAuthorityList("");
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public BotaniUserDetails(){}

    //  At construction USER is created first, then this class is created
//    public BotaniUserDetails(long id, String username, String email, String password) {
//        super(id, username, email, password);
//    }

    public BotaniUserDetails(User user){
        super(user);
    }

}
