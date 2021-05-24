package better.me.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryBoundaries {

	private String ruleName;
	private String newCategory;
	private int from;
	private int to;
	
}
