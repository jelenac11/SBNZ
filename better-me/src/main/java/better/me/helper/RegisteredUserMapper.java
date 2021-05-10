package better.me.helper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import better.me.dto.RegisteredUserDTO;
import better.me.dto.UserDTO;
import better.me.dto.UserResDTO;
import better.me.model.RegisteredUser;

@Component
public class RegisteredUserMapper {

	public RegisteredUser toEntity(UserDTO user) {
		return new RegisteredUser(user.getUsername(), user.getEmail(), user.getPassword(), user.getFirstName(),
				user.getLastName());
	}

	public RegisteredUserDTO toDto(RegisteredUser entity) {
		return new RegisteredUserDTO(entity);
	}

	public UserResDTO toResDTO(RegisteredUser entity) {
		return new UserResDTO(entity.getId(), entity.getUsername(), entity.getEmail(), entity.getPassword(),
				entity.getFirstName(), entity.getLastName());
	}

	public List<UserResDTO> toResDTOList(Iterable<RegisteredUser> entities) {
		List<UserResDTO> dtos = new ArrayList<>();
		for (RegisteredUser entity : entities) {
			dtos.add(toResDTO(entity));
		}
		return dtos;
	}

	public List<UserResDTO> toDTOResList(List<RegisteredUser> entities) {
		List<UserResDTO> dtos = new ArrayList<>();
		for (RegisteredUser entity : entities) {
			dtos.add(toResDTO(entity));
		}
		return dtos;
	}

}
