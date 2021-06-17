package better.me.dto;

import better.me.modelDB.GradeDB;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class GradeDTO {
	
	private int value;

	public GradeDTO(GradeDB g) {
		this(g.getValue());
	}
	
}
