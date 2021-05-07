package better.me.helper;

import org.springframework.stereotype.Component;

import better.me.dto.UserDTO;
import better.me.dto.UserResDTO;
import better.me.model.User;

@Component
public class UserMapper {

	public User toEntity(UserDTO dto) {
		return new User(dto.getUsername(), dto.getEmail(), dto.getPassword(), dto.getFirstName(), dto.getLastName());
	}

	public UserDTO toDto(User entity) {
		return new UserDTO(entity.getUsername(), entity.getEmail(), entity.getPassword(), entity.getFirstName(),
				entity.getLastName());
	}

	public UserResDTO toResDTO(User entity) {
		return new UserResDTO(entity.getId(), entity.getUsername(), entity.getEmail(), entity.getPassword(),
				entity.getFirstName(), entity.getLastName());
	}
}
