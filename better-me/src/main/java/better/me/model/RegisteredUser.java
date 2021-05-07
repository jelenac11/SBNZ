package better.me.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("RU")
public class RegisteredUser extends User {

	private static final long serialVersionUID = 1L;

	public RegisteredUser(Long id) {
		super(id);
	}

	public RegisteredUser(Long id, String username, String email, String password, String firstName, String lastName) {
		super(id, username, email, password, firstName, lastName);
	}

	public RegisteredUser(String username, String email, String password, String firstName, String lastName) {
		super(username, email, password, firstName, lastName);
	}

}
