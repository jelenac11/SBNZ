package better.me.dto;

import javax.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AgeBoundariesDTO {

	@Min(1)
	private int teenFrom;
	@Min(1)
	private int youngAdultFrom;
	@Min(1)
	private int adultFrom;
	@Min(1)
	private int elderFrom;
	
}
