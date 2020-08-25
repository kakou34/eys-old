package yte.intern.eys.usecases.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yte.intern.eys.usecases.user.entity.Authority;

import java.util.Optional;


public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    //Users findByUsername(String username);

    Optional<Authority> findByAuthority(String authority);

}
