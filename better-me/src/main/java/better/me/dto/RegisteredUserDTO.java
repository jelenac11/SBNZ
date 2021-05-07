package better.me.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisteredUserDTO extends UserDTO {
	
	public RegisteredUserDTO(String username, String email, String password, String firstName, String lastName) {
		super(username, email, password, firstName, lastName);
	}
}
