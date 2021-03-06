package better.me.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import better.me.dto.GroceryDTO;
import better.me.services.NutritionService;

@RestController
@RequestMapping(value = "/api/nutrition", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600, allowedHeaders = "*")
public class NutritionController {

	@Autowired
	private NutritionService nutritionService;
	
	@GetMapping(value = "/submit-day")
	public ResponseEntity<?> submitDay() {
		try {
			return new ResponseEntity<>(nutritionService.submitDay(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/user-report")
	public ResponseEntity<?> getReport() {
		try {
			return new ResponseEntity<>(nutritionService.getReport(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/all-allergens")
	public ResponseEntity<?> getAllAllergens() {
		try {
			return new ResponseEntity<>(nutritionService.getAllAllergens(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/all-groceries")
	public ResponseEntity<?> getAllGroceries() {
		try {
			return new ResponseEntity<>(nutritionService.getAllGroceries(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(value = "/new-grocery")
	public ResponseEntity<?> createGrocery(@RequestBody GroceryDTO dto) {
		try {
			return new ResponseEntity<>(nutritionService.createGrocery(dto), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/recommended")
	public ResponseEntity<?> getRecommended() {
		try {
			return new ResponseEntity<>(nutritionService.getRecommended(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
}
