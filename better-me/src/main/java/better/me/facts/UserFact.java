package better.me.facts;

import better.me.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserFact {

	protected Long id;
	protected String username;
	protected String email;
	protected String firstName;
	protected String lastName;
	
	public UserFact (User u) {
		this(u.getId(), u.getUsername(), u.getEmail(), u.getFirstName(), u.getLastName());
	}

}
