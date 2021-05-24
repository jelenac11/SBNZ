package better.me.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AgeBoundaries {

	private int startAge;
	private int endAge;
	private String ageCategory;
	
}
