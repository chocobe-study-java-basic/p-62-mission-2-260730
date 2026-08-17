package com.github.chocobe.sbb_mission2.domain.user.repository;

import com.github.chocobe.sbb_mission2.domain.user.entity.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<SiteUser, Long> {

    Optional<SiteUser> findByUsername(String username);

}
