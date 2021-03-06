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
public class CategoryBoundariesDTO {

	@Min(1)
	private int intermediateFrom;
	@Min(1)
	private int advancedFrom;
	@Min(1)
	private int proFrom;
	
}
