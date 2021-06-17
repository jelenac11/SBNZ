package better.me.events;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

import better.me.model.RegisteredUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Role(Role.Type.EVENT)
@Expires("6h")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotAllowedToEatEvent {

	private RegisteredUser user;
}
