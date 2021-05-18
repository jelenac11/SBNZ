package better.me.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import better.me.dto.FilterDTO;
import better.me.services.FilterService;

@RestController
@RequestMapping(value = "/api/filter")
@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600, allowedHeaders = "*")
public class FilterController {

	@Autowired
	private FilterService filterService;
	
	@PostMapping("/meals")
	public ResponseEntity<?> filterMeals(@RequestBody FilterDTO filter){
		try {
			return new ResponseEntity<>(filterService.filterMeals(filter), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
}
