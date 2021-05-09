package better.me.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "groceries")
public class Grocery {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "grocery_id")
	private Long id;
	
	@Column
	private String name;
	
	@Column
	private double calories;
	
	@Column
	private double carbs;
	
	@Column
	private double proteins;
	
	@Column
	private double fats;
	
	@OneToMany(mappedBy = "grocery")
	private Set<Ingredient> ingredients;

}
