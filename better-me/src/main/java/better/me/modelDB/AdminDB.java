package better.me.modelDB;

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
public class AdminDB extends UserDB {

	private static final long serialVersionUID = 1L;
	
	public AdminDB(Long id) {
		super(id);
	}

	public AdminDB(String username, String email, String firstName, String lastName, boolean allowedToLogin) {
		super(username, email, firstName, lastName, allowedToLogin);
	}
	
	public AdminDB(String username, String email, String password, String firstName, String lastName, boolean allowedToLogin) {
		super(username, email, password, firstName, lastName, allowedToLogin);
	}

	public AdminDB(Long id, String username, String email, String password, String firstName, String lastName, boolean allowedToLogin) {
		super(id, username, email, password, firstName, lastName, allowedToLogin);
	}

}
