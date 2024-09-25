package tn.esprit.AgriGoodness.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.AgriGoodness.entities.User;
import tn.esprit.AgriGoodness.services.inters.IUserService;

import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private IUserService userService;

    @GetMapping("/getAll")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/getUser/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @PutMapping("/updateUser/{id}")
    public void updateUser(@PathVariable Long id, @RequestBody User user) {
        userService.updateUser(id, user);
    }

    @DeleteMapping("/deleteUser/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

}
