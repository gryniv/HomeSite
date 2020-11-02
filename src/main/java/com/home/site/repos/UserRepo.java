package com.home.site.repos;

import com.home.site.domain.*;
import org.springframework.data.jpa.repository.*;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByActivationCode(String code);
}