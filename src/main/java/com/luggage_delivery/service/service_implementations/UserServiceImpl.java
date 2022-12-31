package com.luggage_delivery.service.service_implementations;

import com.luggage_delivery.entity.Role;
import com.luggage_delivery.entity.Status;
import com.luggage_delivery.entity.User;
import com.luggage_delivery.exceptions.UserRegistrateException;
import com.luggage_delivery.repository.UserRepository;
import com.luggage_delivery.service.UserService;
import com.luggage_delivery.service.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ResourceBundle;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final MailService mailService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, MailService mailService) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.mailService = mailService;
    }

    @Override
    @Transactional
    public void addUser(User user, String secondPassword) throws UserRegistrateException {
        if (userRepository.getUserByLogin(user.getLogin()) != null)
            throw new UserRegistrateException(user.getLogin() + " is already registered. Please follow to the login page");

        if (!user.getPassword().equals(secondPassword))
            throw new UserRegistrateException("Passwords are not equal. Please try again");

        user.setBalance(new BigDecimal("0.00"));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoleName(Role.USER);
        user.setStatusName(Status.NOT_ACTIVATED);
        user.setActivationCode(UUID.randomUUID().toString());
        userRepository.save(user);
        System.out.println("USER DATA: " + user);
        ResourceBundle rb = ResourceBundle.getBundle("mail-info");

        String message = String.format(
                "Dear %s, \n\n\nYou have left a registration request on our website." +
                        "\nTo activate your account please click on the link below:\n\n\n" +
                        rb.getString("local-url") + "registration/submit/%s",
                user.getFirstName() + " " + user.getLastName(),
                user.getActivationCode()
        );

        //change it in real case. As for sender and receiver is the same person - (user.getEmail()) as first param
        String mailAuthor = rb.getString("username");
        mailService.sendEmail(mailAuthor,
                message, "Activate your account on 'Luggage delivery' website");
    }


    @Override
    public User getUserByLogin(String login) {
        return userRepository.getUserByLogin(login);
    }

    @Override
    public User activateUserByCode(String activationCode) {
        return userRepository.getUserByActivationCode(activationCode);
    }

    @Override
    public void updateUserBalance(BigDecimal newBalance, String userName) {
        User user = getUserByLogin(userName);
        user.setBalance(user.getBalance().add(newBalance));
        userRepository.save(user);
    }

    @Override
    public void updateUserStatus(User user) {
        userRepository.save(user);
    }
}
