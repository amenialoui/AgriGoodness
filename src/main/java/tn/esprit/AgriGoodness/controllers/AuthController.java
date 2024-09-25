package tn.esprit.AgriGoodness.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tn.esprit.AgriGoodness.dtos.requests.ForgetPassword;
import tn.esprit.AgriGoodness.dtos.requests.LoginRequest;
import tn.esprit.AgriGoodness.dtos.requests.ResetPasswordRequest;
import tn.esprit.AgriGoodness.dtos.requests.SignupRequest;
import tn.esprit.AgriGoodness.dtos.responses.JwtResponse;
import tn.esprit.AgriGoodness.dtos.responses.MessageResponse;
import tn.esprit.AgriGoodness.entities.Role;
import tn.esprit.AgriGoodness.entities.User;
import tn.esprit.AgriGoodness.enums.ERole;
import tn.esprit.AgriGoodness.security.jwt.JwtUtils;
import tn.esprit.AgriGoodness.security.services.UserDetailsImpl;
import tn.esprit.AgriGoodness.services.inters.IAuthService;
import tn.esprit.AgriGoodness.services.inters.IUserService;


@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
  AuthenticationManager authenticationManager;
  IAuthService authService;
  IUserService userService;
  PasswordEncoder encoder;
  JwtUtils jwtUtils;
  @GetMapping("/getAllRoles")
  public ResponseEntity<List<Role>> getAllRoles() {
    List<Role> roles = authService.findAllRoles();
    return new ResponseEntity<>(roles, HttpStatus.OK);
  }
  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    String jwt = jwtUtils.generateJwtToken(userDetails);
    List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

      return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(),
              userDetails.getUsername(), userDetails.getEmail(), roles));

  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    if (authService.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
    }

    if (authService.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
    }

    User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
        signUpRequest.getPassword());

    String role = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();

    switch (role) {
      case "ResponsablePaie" -> {
        Role roleToAdd = authService.findByName(ERole.ResponsablePaie)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(roleToAdd);
      }
      case "DirecteurRH" -> {
        Role roleToAdd = authService.findByName(ERole.DirecteurRH)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(roleToAdd);
      }
      case "ResponsableGestionCarrieres" -> {
        Role roleToAdd = authService.findByName(ERole.ResponsableGestionCarrieres)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(roleToAdd);
      }

    }

    user.setRoles(roles);
    authService.saveUser(user);

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }

  @PostMapping("/signout")
  public ResponseEntity<?> logoutUser() {
    return ResponseEntity.ok(new MessageResponse("Log out successful!"));
  }

  @PostMapping("/forgetpassword")
  public ResponseEntity<?> forgetPassword(@RequestBody ForgetPassword fp) {
    return ResponseEntity.ok(new MessageResponse(authService.forgetPassword(fp.getEmail())));
  }

  @PutMapping("/resetpassword")
  public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest rpr) {
    return ResponseEntity.ok(new MessageResponse(authService.resetPassword(rpr.getToken(), rpr.getPassword())));
  }

}
