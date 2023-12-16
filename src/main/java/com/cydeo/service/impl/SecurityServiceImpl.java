package com.cydeo.service.impl;

import com.cydeo.entity.User;
import com.cydeo.entity.common.UserPrincipal;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.SecurityService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {

    public SecurityServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // need to get our own user from DB
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("This user does not exist"));
        // throw an except if the user doesn't exist

        if(user == null){
            throw new UsernameNotFoundException("This user does not exists");

        }

        // return user information as a UserDetail -- in our case a UserPrincipal object
        UserPrincipal userPrincipal = new UserPrincipal(user);



        return new UserPrincipal(new User());
    }
}
