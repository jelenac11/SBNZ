package better.me.dto;

import better.me.model.RecommendedMeal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecommendedMealDTO {

	private String meal;
	private double points;
	
	public RecommendedMealDTO(RecommendedMeal m) {
		this.meal = m.getMeal().getName();
		this.points = m.getPoints();
	}
	
}
