package better.me.dto;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SortedMealsDTO {

	private ArrayList<ResponseMealDTO> sorted;
	
	public SortedMealsDTO() {
		this.sorted = new ArrayList<ResponseMealDTO>();
	}
}
