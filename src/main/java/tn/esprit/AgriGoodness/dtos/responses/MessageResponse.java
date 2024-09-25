package tn.esprit.AgriGoodness.dtos.responses;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level= AccessLevel.PRIVATE)
public class MessageResponse {
	String message;

}
