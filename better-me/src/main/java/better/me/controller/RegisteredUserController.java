package better.me.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import better.me.dto.UserResDTO;
import better.me.helper.RegisteredUserMapper;
import better.me.model.RegisteredUser;
import better.me.services.RegisteredUserService;

@RestController
@RequestMapping(value = "/api/registered-users", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600, allowedHeaders = "*")
public class RegisteredUserController {

	@Autowired
	private RegisteredUserService registeredUserService;
	private RegisteredUserMapper registeredUserMapper;

	public RegisteredUserController() {
		registeredUserMapper = new RegisteredUserMapper();
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<UserResDTO> getRegisteredUser(@PathVariable Long id) {
		RegisteredUser registeredUser = registeredUserService.getById(id);
		if (registeredUser == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(registeredUserMapper.toResDTO(registeredUser), HttpStatus.OK);
	}
	
	@GetMapping(value = "/determine-bmi")
	public ResponseEntity<?> determineBmi() {
		try {
			return new ResponseEntity<>(registeredUserService.determineBmi(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

}
