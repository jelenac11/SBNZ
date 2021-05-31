package better.me.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Report {

	private List<Boolean> successfulWeeks;
	
	public Report() {
		this.successfulWeeks = new ArrayList<Boolean>();
	}
}
