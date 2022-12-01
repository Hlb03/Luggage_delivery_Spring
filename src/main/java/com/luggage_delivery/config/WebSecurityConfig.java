package com.luggage_delivery.config;
/*
  User: admin
  Cur_date: 16.11.2022
  Cur_time: 17:09
*/

import com.luggage_delivery.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailServiceImpl userDetailService;

    @Autowired
    public WebSecurityConfig(PasswordEncoder passwordEncoder, UserDetailServiceImpl userDetailService) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailService = userDetailService;
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests(request -> {
                    request.antMatchers("/make-order/order-process").hasRole(Role.USER.name());
                    request.antMatchers("/", "/make-order", "/make-order/price-calculate", "/registration")
                            .permitAll().anyRequest().authenticated();
                })
                .formLogin(login -> login.loginPage("/login").usernameParameter("email")
                        .permitAll().defaultSuccessUrl("/"))
                .logout(logout -> {
                    new AntPathRequestMatcher("logout", "POST");
                    logout.invalidateHttpSession(true);
                    logout.clearAuthentication(true);
                    logout.deleteCookies("JSESSIONID");
                    logout.logoutSuccessUrl("/");
                })
                .csrf()
                .disable()
                .build();
    }

//    @Bean
//    protected void config(AuthenticationManagerBuilder auth) {
//        auth.authenticationProvider(daoAuthenticationProvider());
//    }

    @Bean
    protected DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder.bCryptPasswordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailService);
        return daoAuthenticationProvider;
    }
}
