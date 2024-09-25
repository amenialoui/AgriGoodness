package tn.esprit.AgriGoodness.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level= AccessLevel.PRIVATE)
public class ForgetPassword {
    @NotBlank
    String email;
}
