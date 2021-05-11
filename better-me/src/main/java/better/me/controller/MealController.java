package better.me.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import better.me.dto.EatenMealDTO;
import better.me.services.MealService;

@RestController
@RequestMapping(value = "/api/meals")
@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600, allowedHeaders = "*")
public class MealController {

	@Autowired
	private MealService mealService;
	
	@PostMapping("/eaten-meal")
	public ResponseEntity<?> determine(@RequestBody EatenMealDTO dto){
		try {
			return new ResponseEntity<>(mealService.calculateNutritions(dto), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
