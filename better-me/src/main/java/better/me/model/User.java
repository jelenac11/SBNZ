package better.me.model;

import better.me.modelDB.UserDB;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

	protected Long id;
	protected String username;
	protected String email;
	protected String firstName;
	protected String lastName;
	
	public User (UserDB u) {
		this(u.getId(), u.getUsername(), u.getEmail(), u.getFirstName(), u.getLastName());
	}

}
