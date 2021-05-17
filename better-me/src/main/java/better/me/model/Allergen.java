package better.me.model;

import better.me.modelDB.AllergenDB;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Allergen {

	private Long id;
	private String name;

	public Allergen(AllergenDB a) {
		this(a.getId(), a.getName());
	}
	
}
