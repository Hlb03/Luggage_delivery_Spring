package com.luggage_delivery.config;
/*
  User: admin
  Cur_date: 21.11.2022
  Cur_time: 18:08
*/

import com.luggage_delivery.entity.Status;
import com.luggage_delivery.entity.User;
import com.luggage_delivery.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final static Logger LOG = LoggerFactory.getLogger(UserDetailServiceImpl.class);

    private final UserRepository userRepository;

    @Autowired
    public UserDetailServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.getUserByLogin(login);
        if (user == null) {
            LOG.debug("USER WITH LOGIN " + login + " WAS NOT FOUND. REDIRECT TO LOGIN PAGE");
            throw new UsernameNotFoundException("User with login " + login + " is not registered");
        }
        boolean active = user.getStatusName().equals(Status.ACTIVE);

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getLogin())
                .password(user.getPassword())
                .disabled(!active)
                .authorities(user.getRoleName().getAuthority())
                .build();
    }
}
