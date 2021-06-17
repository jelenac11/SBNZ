package better.me.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import better.me.dto.EatenMealDTO;
import better.me.dto.NewMealDTO;
import better.me.model.ConcreteMeal;
import better.me.services.MealService;

@RestController
@RequestMapping(value = "/api/meals")
@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600, allowedHeaders = "*")
public class MealController {

	@Autowired
	private MealService mealService;

	@PostMapping("/eaten-meal")
	public ResponseEntity<?> determine(@RequestBody EatenMealDTO dto) {
		try {
			return new ResponseEntity<>(mealService.calculateNutritions(dto), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/eaten-custom-meal")
	public ResponseEntity<?> customMeal(@RequestBody ConcreteMeal dto) {
		try {
			return new ResponseEntity<>(mealService.eatCustomMeal(dto), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping
	public ResponseEntity<?> create(@RequestBody NewMealDTO dto) {
		try {
			return new ResponseEntity<>(mealService.create(dto), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(value = "/{name}/{value}")
	public ResponseEntity<?> rateMeal(@PathVariable(value = "name") String name, @PathVariable(value = "value") Integer value) {
		try {
			return new ResponseEntity<>(mealService.rate(name, value), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/{name}")
	public ResponseEntity<?> getRate(@PathVariable(value = "name") String name) {
		try {
			return new ResponseEntity<>(mealService.getRate(name), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/all-meals")
	public ResponseEntity<?> getAllMeals() {
		try {
			return new ResponseEntity<>(mealService.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

}
