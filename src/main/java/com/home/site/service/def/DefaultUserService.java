package com.home.site.service.def;


import com.home.site.domain.*;
import com.home.site.domain.def.*;
import com.home.site.repos.*;
import com.home.site.repos.def.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;
import org.springframework.util.*;

import java.util.*;
import java.util.stream.*;

@Service
public abstract class DefaultUserService implements UserDetailsService {
    private final DefaultUserRepo defaultUserRepo;

    private final MailSender mailSender;

    private final PasswordEncoder passwordEncoder;

    final MessageRepo messageRepo;

    @Value("${hostname}")
    private String hostname;

    public DefaultUserService(DefaultUserRepo defaultUserRepo, MailSender mailSender, PasswordEncoder passwordEncoder, MessageRepo messageRepo) {
        this.defaultUserRepo = defaultUserRepo;
        this.mailSender = mailSender;
        this.passwordEncoder = passwordEncoder;
        this.messageRepo = messageRepo;
    }
    

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        DefaultUser defaultUser = defaultUserRepo.findByUsername(username);

        if (defaultUser == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return defaultUser;
    }

    public boolean addUser(DefaultUser defaultUser) {
        DefaultUser userFromDb = defaultUserRepo.findByUsername(defaultUser.getUsername());

        if (userFromDb != null) {
            return false;
        }

        defaultUser.setActive(true);
        defaultUser.setRoles(Collections.singleton(Role.USER));
        defaultUser.setActivationCode(UUID.randomUUID().toString());
        defaultUser.setPassword(passwordEncoder.encode(defaultUser.getPassword()));

        defaultUserRepo.save(defaultUser);

        sendMessage(defaultUser);

        return true;
    }

    private void sendMessage(DefaultUser defaultUser) {
        if (!StringUtils.isEmpty(defaultUser.getEmail())) {
            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome to Doctors Home Site. Please, visit next link: http://%s/activate/%s",
                    defaultUser.getUsername(),
                    hostname,
                    defaultUser.getActivationCode()
            );

            mailSender.send(defaultUser.getEmail(), "Activation code", message);
        }
    }

    public boolean activateUser(String code) {
        DefaultUser defaultUser = defaultUserRepo.findByActivationCode(code);

        if (defaultUser == null) {
            return false;
        }

        defaultUser.setActivationCode(null);

        defaultUserRepo.save(defaultUser);

        return true;
    }

    public List<DefaultUser> findAll() {
        return defaultUserRepo.findAll();
    }

    public void saveUser(DefaultUser defaultUser, String username, String email, String password, Map<String, String> form, Boolean active) {
        defaultUser.setUsername(username);
        defaultUser.setEmail(email);
        if (!password.isEmpty()) {
            defaultUser.setPassword(passwordEncoder.encode(password));
        }
        if (active == null) {
            active = false;
        }
        defaultUser.setActive(active);
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        defaultUser.getRoles().clear();

        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                defaultUser.getRoles().add(Role.valueOf(key));
            }
        }
        defaultUserRepo.save(defaultUser);
    }

    public void updateProfile(DefaultUser defaultUser, String password, String email) {
        String userEmail = defaultUser.getEmail();

        boolean isEmailChanged = (email != null && !email.equals(userEmail)) ||
                (userEmail != null && !userEmail.equals(email));

        if (isEmailChanged) {
            defaultUser.setEmail(email);

            if (!StringUtils.isEmpty(email)) {
                defaultUser.setActivationCode(UUID.randomUUID().toString());
            }
        }

        if (!StringUtils.isEmpty(password)) {
            defaultUser.setPassword(passwordEncoder.encode(defaultUser.getPassword()));
        }

        defaultUserRepo.save(defaultUser);

        if (isEmailChanged) {
            sendMessage(defaultUser);
        }
    }
    
}