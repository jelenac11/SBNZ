package better.me.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import better.me.services.ReportService;

@RestController
@RequestMapping(value = "/api/reports", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600, allowedHeaders = "*")
public class ReportController {

	@Autowired
	private ReportService reportService;

	@GetMapping(value = "/admin-report")
	public ResponseEntity<?> getAdminReport() {
		return new ResponseEntity<>(reportService.getAdminReport(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/user-report")
	public ResponseEntity<?> getUserReport() {
		try {
			return new ResponseEntity<>(reportService.getUserReport(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

}
