package better.me.facts;

import better.me.model.Allergen;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AllergenFact {

	private Long id;
	private String name;

	public AllergenFact(Allergen a) {
		this(a.getId(), a.getName());
	}
	
}
