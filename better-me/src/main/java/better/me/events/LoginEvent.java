package better.me.events;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

import better.me.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Role(Role.Type.EVENT)
@Expires("5m")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginEvent {

	private User user;
	private boolean successfulLogin;
}
