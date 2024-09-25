package tn.esprit.AgriGoodness.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import tn.esprit.AgriGoodness.enums.ERole;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "roles")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	ERole name;

	public Role(ERole name) {
		this.name=name;
	}
}
