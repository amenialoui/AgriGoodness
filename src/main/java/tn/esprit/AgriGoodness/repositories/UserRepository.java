package tn.esprit.AgriGoodness.repositories;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.AgriGoodness.entities.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findById(Long id);
  Optional<User> findByUsername(String username);
  Optional<User> findByToken(String token);

  Optional<User> findByEmail(String email);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);

}
