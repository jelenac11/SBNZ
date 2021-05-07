package better.me.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("AD")
@Getter
@Setter
@NoArgsConstructor
public class Admin extends User {

	private static final long serialVersionUID = 1L;
	
	public Admin(Long id) {
		super(id);
	}

	public Admin(String username, String email, String firstName, String lastName) {
		super(username, email, firstName, lastName);
	}
	
	public Admin(String username, String email, String password, String firstName, String lastName) {
		super(username, email, password, firstName, lastName);
	}

	public Admin(Long id, String username, String email, String password, String firstName, String lastName) {
		super(id, username, email, password, firstName, lastName);
	}

}
