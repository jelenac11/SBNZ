package better.me.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SortedMeals {

	private List<Meal> sortedList;
	
	public SortedMeals() {
		sortedList = new ArrayList<Meal>();
	}
}
