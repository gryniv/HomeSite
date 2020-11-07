package com.home.site.repos.def;

import com.home.site.domain.*;
import com.home.site.domain.def.*;
import org.springframework.data.jpa.repository.*;

public interface DefaultUserRepo extends JpaRepository<DefaultUser, Long> {
    DefaultUser findByUsername(String username);

    DefaultUser findByActivationCode(String code);
}