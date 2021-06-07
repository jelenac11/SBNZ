package better.me.dto;

import better.me.modelDB.AdminDB;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AdminDTO extends ResponseUserDTO {
	
	private String username;
	private String email;
	private String firstName;
	private String lastName;

	public AdminDTO(AdminDB a) {
		this(a.getRealUsername(), a.getEmail(), a.getFirstName(), a.getLastName());
		this.role = "ROLE_ADMIN";
	}
	
	public AdminDTO() {
		this.role = "ROLE_ADMIN";
	}
	
}
