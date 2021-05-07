package better.me.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

	@NotEmpty(message = "Username cannot be null or empty.")
	private String username;

	@NotEmpty(message = "Email cannot be null or empty.")
	@Email(message = "Email format is not valid.")
	private String email;

	@NotEmpty(message = "Password cannot be null or empty.")
	@Length(min = 6)
	private String password;

	@NotEmpty(message = "First name cannot be null or empty.")
	private String firstName;

	@NotEmpty(message = "Last name cannot be null or empty.")
	private String lastName;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
