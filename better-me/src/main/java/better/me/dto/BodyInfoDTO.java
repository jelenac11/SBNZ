package better.me.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BodyInfoDTO {

	@NotBlank(message="Description of shoulders must not be empty!")
	private String shoulders;
	
	@NotBlank(message="Description of fitting jeans must not be empty!")
	private String jeans;
	
	@NotBlank(message="Description of forearms must not be empty!")
	private String forearms;
	
	@NotBlank(message="Body tendation must not be empty!")
	private String bodyTendation;
	
	@NotBlank(message="Body look must not be empty!")
	private String bodyLook;
	
	@NotBlank(message="Weight tendation must not be empty!")
	private String weightTendation;
	
	@NotBlank(message="Description of encircle hand wrist must not be empty!")
	private String encircleHandWrist;
}
