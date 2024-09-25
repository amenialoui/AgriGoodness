package tn.esprit.AgriGoodness.dtos.requests;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level= AccessLevel.PRIVATE)
public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    String username;
 
    @NotBlank
    @Size(max = 50)
    @Email
    String email;
    @NotBlank
    String role;
    
    @NotBlank
    @Size(min = 6, max = 40)
    String password;

}
