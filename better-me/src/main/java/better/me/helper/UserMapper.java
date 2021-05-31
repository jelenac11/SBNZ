package better.me.helper;

import org.springframework.stereotype.Component;

import better.me.dto.UserDTO;
import better.me.dto.UserResDTO;
import better.me.modelDB.UserDB;

@Component
public class UserMapper {

	public UserDB toEntity(UserDTO dto) {
		return new UserDB(dto.getUsername(), dto.getEmail(), dto.getPassword(), dto.getFirstName(), dto.getLastName(), true);
	}

	public UserDTO toDto(UserDB entity) {
		return new UserDTO(entity.getUsername(), entity.getEmail(), entity.getPassword(), entity.getFirstName(),
				entity.getLastName());
	}

	public UserResDTO toResDTO(UserDB entity) {
		return new UserResDTO(entity.getId(), entity.getUsername(), entity.getEmail(), entity.getPassword(),
				entity.getFirstName(), entity.getLastName());
	}
}
