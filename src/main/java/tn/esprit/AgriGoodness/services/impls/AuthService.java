package tn.esprit.AgriGoodness.services.impls;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.esprit.AgriGoodness.entities.Role;
import tn.esprit.AgriGoodness.entities.User;
import tn.esprit.AgriGoodness.enums.ERole;
import tn.esprit.AgriGoodness.repositories.RoleRepository;
import tn.esprit.AgriGoodness.repositories.UserRepository;
import tn.esprit.AgriGoodness.services.inters.IAuthService;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class AuthService implements IAuthService {
    UserRepository userRepo;
    RoleRepository roleRepo;
    PasswordEncoder encoder;
    JavaMailSender emailSender;
    @Override
    public Boolean existsByUsername(String username) {
        return userRepo.existsByUsername(username);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepo.existsByEmail(email);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepo.findByUsername(username);
    }
    @Override
    public Optional<Role> findByName(ERole name) {
        return roleRepo.findByName(name);
    }

    @Override
    public List<Role> findAllRoles() { return roleRepo.findAll();}
    @Override
    public User saveUser(User user) {
        user.setCreationDate(new Date(System.currentTimeMillis()));
        user.setPassword(encoder.encode(user.getPassword()));

        return userRepo.save(user); }

    @Override
    public String forgetPassword(String email) {

        Optional<User> userOptional = userRepo.findByEmail(email);

        if (userOptional.isEmpty()) {
            return "Invalid email.";
        }

        User user = userOptional.get();
        String token = generateToken();
        user.setToken(token);
        user.setTokenCreationDate(LocalDateTime.now());

        String subject = "Forget Password Request";
        String body = "Your verification code is: " + token;

        sendEmail(email, subject, body);
        userRepo.save(user);

        return user.getToken();
    }

    @Override
    public String resetPassword(String token, String password) {

        Optional<User> userOptional = userRepo.findByToken(token);

        if (userOptional.isEmpty()) {
            return "Invalid token.";
        }

        LocalDateTime tokenCreationDate = userOptional.get().getTokenCreationDate();

        if (isTokenExpired(tokenCreationDate)) {
            return "Token expired.";

        }

        User user = userOptional.get();
        user.setPassword(encoder.encode(password));
        user.setToken(null);
        user.setTokenCreationDate(null);

        userRepo.save(user);

        return "Your password successfully updated.";
    }
    private String generateToken() {
        return String.valueOf(UUID.randomUUID()) +
                UUID.randomUUID();
    }
    private boolean isTokenExpired(LocalDateTime tokenCreationDate) {
        LocalDateTime now = LocalDateTime.now();
        Duration diff = Duration.between(tokenCreationDate, now);
        return diff.toMinutes() >= 30;
    }

    public void sendEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("AgriGoodness@gmail.com");
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);

        emailSender.send(message);
        System.out.println("Mail Sent successfully...");
    }
}
