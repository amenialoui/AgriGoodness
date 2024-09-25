package tn.esprit.AgriGoodness.entities;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import tn.esprit.AgriGoodness.enums.ECompany;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users",
		uniqueConstraints = {
				@UniqueConstraint(columnNames = "username"),
				@UniqueConstraint(columnNames = "email")
		})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@NotBlank
	@Size(min = 4, max = 20)
	String username;

	@NotBlank
	@Size(max = 50)
	@Email
	String email;

	@NotBlank
	@Size(min = 8)
	String password;

	@Enumerated(EnumType.STRING)
	ECompany company;

	@Temporal(TemporalType.DATE)
	Date creationDate;

	String token;

	@Column(columnDefinition = "TIMESTAMP")
	LocalDateTime tokenCreationDate;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id"))
	Set<Role> roles = new HashSet<>();

	public User(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}
}
