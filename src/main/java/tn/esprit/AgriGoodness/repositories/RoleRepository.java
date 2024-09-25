package tn.esprit.AgriGoodness.repositories;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.AgriGoodness.entities.Role;
import tn.esprit.AgriGoodness.enums.ERole;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
}
