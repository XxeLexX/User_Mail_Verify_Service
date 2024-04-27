package com.xiaoxili.userservice.service.impl;

import org.springframework.stereotype.Service;

import com.xiaoxili.userservice.model.Confirmation;
import com.xiaoxili.userservice.model.User;
import com.xiaoxili.userservice.repository.ConfirmationRepository;
import com.xiaoxili.userservice.repository.UserRepository;
import com.xiaoxili.userservice.service.EmailService;
import com.xiaoxili.userservice.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final ConfirmationRepository confirmationRepository;
    private final EmailService emailService;

    @Override
    public User saveUser(User user) {
        // check if the user already exists
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // set enable to false, after verify, the status can be true, cause only verified user can have access
        user.setEnabled(false);
        // save the user to database
        userRepository.save(user);

        // create a confirmation for user and save the confirmation to database,
        // the confirmation table has a foreign key id which is the primary key of the table user
        Confirmation confirmation = new Confirmation(user);
        confirmationRepository.save(confirmation);

        // send a text mail with verify token
        emailService.sendSimpleMailMessage(user.getName(), user.getEmail(), confirmation.getToken());
        
        return user;
    }

    @Override
    public Boolean verifyToken(String token) {
        Confirmation confirmation = confirmationRepository.findByToken(token);
        User user = userRepository.findByEmailIgnoreCase(confirmation.getUser().getEmail());
        user.setEnabled(true);
        userRepository.save(user);
        //confirmationRepository.delete(confirmation);
        return Boolean.TRUE;
    }
}
