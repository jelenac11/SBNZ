package better.me.facts;

import java.util.Map;

import better.me.enums.BodyType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Answers {

	private BodyType bodyType;
	private Map<String, String> answers;
}
