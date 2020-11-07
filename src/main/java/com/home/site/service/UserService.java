package com.home.site.service;

import com.home.site.domain.User;
import com.home.site.domain.*;
import com.home.site.repos.*;
import com.home.site.repos.def.*;
import com.home.site.service.def.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;

@Service
public class UserService extends DefaultUserService {
    private final UserRepo userRepo;

    private final MessageRepo messageRepo;

    public UserService(DefaultUserRepo defaultUserRepo, MailSender mailSender, PasswordEncoder passwordEncoder, UserRepo userRepo, MessageRepo messageRepo) {
        super(defaultUserRepo, mailSender, passwordEncoder, messageRepo);
        this.userRepo = userRepo;
        this.messageRepo = messageRepo;    }


    public void subscribe(User currentUser, User user) {
        user.getSubscribers().add(currentUser);

        userRepo.save(user);
    }

    public void unsubscribe(User currentUser, User user) {
        user.getSubscribers().remove(currentUser);

        userRepo.save(user);
    }
    public void like(User currentUser, Message message) {
        message.getLikes().add(currentUser);

        messageRepo.save(message);
    }

    public void unlike(User currentUser,  Message message) {
        message.getLikes().remove(currentUser);

        messageRepo.save(message);
    }
}