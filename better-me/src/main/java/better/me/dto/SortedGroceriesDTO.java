package better.me.dto;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SortedGroceriesDTO {

	private ArrayList<ResponseGroceryDTO> sorted;
	
	public SortedGroceriesDTO() {
		this.sorted = new ArrayList<ResponseGroceryDTO>();
	}
}
