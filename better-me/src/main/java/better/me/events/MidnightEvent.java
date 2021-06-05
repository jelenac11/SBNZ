package better.me.events;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

import better.me.model.RegisteredUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Role(Role.Type.EVENT)
@Expires("8d")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MidnightEvent {

	private RegisteredUser user;
	private int numOfWeek;
}
