package com.parimal.journalApp.service;

import com.parimal.journalApp.entity.User;
import com.parimal.journalApp.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user=userRepo.findByUserName(username);

        if(user!=null){
            return org.springframework.security.core.userdetails.User.builder().username(user.getUserName()).password(user.getPassword()).roles(user.getRoles().toArray(new String[0])).build();
        }

        throw new UsernameNotFoundException("User not found with username: "+username);
    }
}
