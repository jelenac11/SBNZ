package better.me.dto;

import java.util.List;

import better.me.model.Ingredient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewMealDTO {

	private String name;
	private int time;
	private String description;
	private List<Ingredient> ingredients;
}
