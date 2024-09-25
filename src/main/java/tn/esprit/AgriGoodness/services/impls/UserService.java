package tn.esprit.AgriGoodness.services.impls;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import tn.esprit.AgriGoodness.entities.User;
import tn.esprit.AgriGoodness.repositories.RoleRepository;
import tn.esprit.AgriGoodness.repositories.UserRepository;
import tn.esprit.AgriGoodness.services.inters.IUserService;

import java.util.List;

@Service
@AllArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class UserService implements IUserService {

    UserRepository userRepo;

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public User getUser(Long id) {
        return userRepo.findById(id).orElseThrow(() -> new RuntimeException("User with id " + id + " not found"));
    }

    @Override
    public void updateUser(Long id, User _user) {
        User user = getUser(id);
        user.setUsername(_user.getUsername());
        user.setEmail(_user.getEmail());
        user.setCompany(_user.getCompany());

        userRepo.save(user);
    }

    @Override
    public void deleteUser(Long id)
    {
        userRepo.deleteById(id);
    }
}
