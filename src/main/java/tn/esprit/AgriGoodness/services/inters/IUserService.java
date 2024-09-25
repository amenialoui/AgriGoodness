package tn.esprit.AgriGoodness.services.inters;

import tn.esprit.AgriGoodness.entities.User;

import java.util.List;public interface IUserService {
    List<User> getAllUsers();

    User getUser(Long id);

    void updateUser(Long id, User _user);

    void deleteUser(Long id);

}
