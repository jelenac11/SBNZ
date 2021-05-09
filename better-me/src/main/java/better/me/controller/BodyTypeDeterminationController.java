package better.me.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import better.me.dto.BodyInfoDTO;
import better.me.services.BodyTypeDeterminationService;

@RestController
@RequestMapping(value = "/api/body-type")
@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600, allowedHeaders = "*")
public class BodyTypeDeterminationController {
	
	@Autowired
	private BodyTypeDeterminationService btdService;
	
	@PostMapping("/determine")
	public ResponseEntity<?> determine(@RequestBody BodyInfoDTO dto){
		try {
			return new ResponseEntity<>(btdService.determine(dto), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

}
