package better.me.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroceryFlag {

	private Grocery grocery;
	private double rate;
	private int rateNum;
	private int eaten;
	
}
