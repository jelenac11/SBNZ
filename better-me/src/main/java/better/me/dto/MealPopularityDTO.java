package better.me.dto;

import better.me.modelDB.MealPopularityDB;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MealPopularityDTO {
	
	private String meal;
	private double rate;
	private int eaten;

	public MealPopularityDTO(MealPopularityDB m) {
		this(m.getMeal(), m.getRate(), m.getEaten());
	}
	
}
