package better.me.controller;

import java.util.List;
import javax.validation.Valid;

import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import better.me.dto.AdminDTO;
import better.me.dto.RegisteredUserDTO;
import better.me.dto.ResponseLoginDTO;
import better.me.dto.ResponseUserDTO;
import better.me.dto.UserDTO;
import better.me.dto.UserLoginDTO;
import better.me.events.LoginEvent;
import better.me.helper.RegisteredUserMapper;
import better.me.model.User;
import better.me.modelDB.AuthorityDB;
import better.me.modelDB.RegisteredUserDB;
import better.me.modelDB.UserDB;
import better.me.security.TokenUtils;
import better.me.services.AdminService;
import better.me.services.RegisteredUserService;
import better.me.services.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600, allowedHeaders = "*")
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

	@Autowired
	private TokenUtils tokenUtils;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userDetailsService;

	@Autowired
	private RegisteredUserService registeredUserService;
	
	@Autowired
	private AdminService adminService;

	@Autowired
	private RegisteredUserMapper registeredUserMapper;


	@Autowired
	@Qualifier(value = "cepLoginSession")
	private KieSession cepLoginSession;
	
	public AuthenticationController() {
		
	}

	@SuppressWarnings("null")
	@PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createAuthenticationToken(@Valid @RequestBody UserLoginDTO authenticationRequest) {
		Authentication authentication = null;
		UserDB user = null;
		try {
			authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getEmail(), authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			user = userDetailsService.findByEmail(authenticationRequest.getEmail());
			if (user != null) {
				LoginEvent loginEvent = new LoginEvent(new User(user), false);
				cepLoginSession.insert(loginEvent);
				cepLoginSession.fireAllRules();
				user.setAllowedToLogin(loginEvent.getUser().isAllowedToLogin());
				userDetailsService.save(user);
				if(!user.isAllowedToLogin()) {
					return new ResponseEntity<>("You can not login after three failed attempts. Try again after 5 minutes.", HttpStatus.FORBIDDEN);
				}
			}
			return new ResponseEntity<>("Incorrect email or password.", HttpStatus.UNAUTHORIZED);
		} catch (DisabledException e) {
			return new ResponseEntity<>("Account is not verified. Check your email.", HttpStatus.FORBIDDEN);
		}
		
		user = userDetailsService.findByEmail(authenticationRequest.getEmail());
		LoginEvent loginEvent = new LoginEvent(new User(user), false);
		cepLoginSession.insert(loginEvent);
		cepLoginSession.fireAllRules();	
		user.setAllowedToLogin(loginEvent.getUser().isAllowedToLogin());
		userDetailsService.save(user);
		if (user.isAllowedToLogin()) {
			@SuppressWarnings("unchecked")
			List<AuthorityDB> auth = (List<AuthorityDB>) user.getAuthorities();

			SecurityContextHolder.getContext().setAuthentication(authentication);

			String jwt = tokenUtils.generateToken(user.getEmail(), auth.get(0).getName());

			ResponseUserDTO responseUser = null;
			if (auth.get(0).getName().equals("ROLE_REGISTERED_USER")) {
				responseUser = new RegisteredUserDTO(this.registeredUserService.findByEmail(user.getEmail()));
			} else {
				responseUser = new AdminDTO(this.adminService.findByEmail(user.getEmail()));
			}
			ResponseLoginDTO loginResponse = new ResponseLoginDTO(responseUser, jwt);
			return new ResponseEntity<>(loginResponse, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("You can not login after three failed attempts. Try again after 5 minutes.", HttpStatus.FORBIDDEN);
		}
	}

	@PostMapping(value = "/sign-up", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addUser(@Valid @RequestBody UserDTO userRequest) throws Exception {
		UserDB existEmail = this.userDetailsService.findByEmail(userRequest.getEmail());
		if (existEmail != null) {
			return new ResponseEntity<>("Email already exists.", HttpStatus.CONFLICT);
		}
		UserDB existUser = this.userDetailsService.findByUsername(userRequest.getUsername());
		if (existUser != null) {
			return new ResponseEntity<>("Username already exists.", HttpStatus.CONFLICT);
		}
		RegisteredUserDB newUser = null;
		try {
			newUser = registeredUserService.create(registeredUserMapper.toEntity(userRequest));
		} catch (Exception e) {
			return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(registeredUserMapper.toResDTO(newUser), HttpStatus.CREATED);
	}

	@GetMapping(value = "/current-user")
	public ResponseEntity<?> currentUser() {
		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
			return new ResponseEntity<>("Session expired.", HttpStatus.UNAUTHORIZED);
		}
		UserDB current = (UserDB) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		@SuppressWarnings("unchecked")
		List<AuthorityDB> auth = (List<AuthorityDB>) current.getAuthorities();
		ResponseUserDTO responseUser = null;
		if (auth.get(0).getName().equals("ROLE_REGISTERED_USER")) {
			responseUser = new RegisteredUserDTO(this.registeredUserService.findByEmail(current.getEmail()));
		} else {
			responseUser = new AdminDTO(this.adminService.findByEmail(current.getEmail()));
		}
		return new ResponseEntity<>(responseUser, HttpStatus.OK);
	}

}
