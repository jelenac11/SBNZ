package better.me.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import better.me.dto.AgeBoundariesDTO;
import better.me.services.AgeService;

@RestController
@RequestMapping(value = "/api/age")
@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600, allowedHeaders = "*")
public class AgeController {
	
	@Autowired
	private AgeService ageService;
	
	@PostMapping("/change-age-boundaries")
	public ResponseEntity<?> changeBoundaries(@RequestBody @Valid AgeBoundariesDTO boundaries){
		try {
			return new ResponseEntity<>(ageService.changeBoundaries(boundaries), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

}
