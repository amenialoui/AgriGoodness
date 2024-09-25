package tn.esprit.AgriGoodness.services.inters;

import tn.esprit.AgriGoodness.entities.Role;
import tn.esprit.AgriGoodness.entities.User;
import tn.esprit.AgriGoodness.enums.ERole;

import java.util.List;
import java.util.Optional;
public interface IAuthService {
    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Optional<User> findByUsername(String username);

    Optional<Role> findByName(ERole name);

    List<Role> findAllRoles();

    User saveUser(User user);

    String forgetPassword(String email);

    String resetPassword(String token, String password);
}
