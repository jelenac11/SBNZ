package better.me.events;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

import better.me.model.RegisteredUser;
import better.me.modelDB.AlarmDB;
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
public class Alarm {

	private RegisteredUser user;
	private String message;
	
	public Alarm(AlarmDB a) {
		this.user = new RegisteredUser();
		this.message = a.getMessage();
	}
}
