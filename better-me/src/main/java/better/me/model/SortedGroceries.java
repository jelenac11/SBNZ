package better.me.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SortedGroceries {

	private List<Grocery> sortedList;
	
	public SortedGroceries() {
		sortedList = new ArrayList<Grocery>();
	}
}
