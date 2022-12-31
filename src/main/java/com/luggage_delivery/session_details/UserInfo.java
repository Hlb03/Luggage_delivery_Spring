package com.luggage_delivery.session_details;
/*
  User: admin
  Cur_date: 28.12.2022
  Cur_time: 9:24
*/

import com.luggage_delivery.entity.Role;
import com.luggage_delivery.entity.User;
import com.luggage_delivery.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserInfo {

    private final static Logger LOG = LoggerFactory.getLogger(UserInfo.class);

    private User user;
    private final UserService userService;

    public UserInfo(UserService userService) {
        this.userService = userService;
    }

    public Role getUserInfo() {
        if (user == null)
            user = userService.getUserByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
        if (user != null) {
            LOG.debug("USER WITH ROLE " + user.getRoleName() + " WAS AUTHORIZED");
            return user.getRoleName();
        }

        return null;
    }
}
