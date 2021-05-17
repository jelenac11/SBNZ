package better.me.helper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import better.me.dto.RegisteredUserDTO;
import better.me.dto.UserDTO;
import better.me.dto.UserResDTO;
import better.me.modelDB.RegisteredUserDB;

@Component
public class RegisteredUserMapper {

	public RegisteredUserDB toEntity(UserDTO user) {
		return new RegisteredUserDB(user.getUsername(), user.getEmail(), user.getPassword(), user.getFirstName(),
				user.getLastName());
	}

	public RegisteredUserDTO toDto(RegisteredUserDB entity) {
		return new RegisteredUserDTO(entity);
	}

	public UserResDTO toResDTO(RegisteredUserDB entity) {
		return new UserResDTO(entity.getId(), entity.getUsername(), entity.getEmail(), entity.getPassword(),
				entity.getFirstName(), entity.getLastName());
	}

	public List<UserResDTO> toResDTOList(Iterable<RegisteredUserDB> entities) {
		List<UserResDTO> dtos = new ArrayList<>();
		for (RegisteredUserDB entity : entities) {
			dtos.add(toResDTO(entity));
		}
		return dtos;
	}

	public List<UserResDTO> toDTOResList(List<RegisteredUserDB> entities) {
		List<UserResDTO> dtos = new ArrayList<>();
		for (RegisteredUserDB entity : entities) {
			dtos.add(toResDTO(entity));
		}
		return dtos;
	}

}
