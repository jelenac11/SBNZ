package better.me.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import better.me.dto.CategoryBoundariesDTO;
import better.me.services.CategoryService;

@RestController
@RequestMapping(value = "/api/categories")
@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600, allowedHeaders = "*")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/change-boundaries")
	public ResponseEntity<?> changeBoundaries(@RequestBody CategoryBoundariesDTO boundaries){
		try {
			return new ResponseEntity<>(categoryService.changeBoundaries(boundaries), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

}
