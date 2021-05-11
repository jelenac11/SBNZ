package better.me.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EatenMealDTO {

	@NotBlank(message="Meal name must not be empty!")
	private String name;
	
	@NotNull
	@Min(1)
	private int grams; 
}
