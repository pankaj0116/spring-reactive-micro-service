package com.test.reactive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.reactive.model.CricketerResponse;
import com.test.reactive.repository.Cricketer;
import com.test.reactive.service.CricketerService;

@RestController
@RequestMapping("/api")
public class CricketerController {
	
	@Autowired
	CricketerService cricketerService;

	@GetMapping("/fetch/cricketers")
	public CricketerResponse getAllCricketers() {
		return cricketerService.getAllCricketers();
	}

	@GetMapping("/fetch/cricketer/{id}")
	public CricketerResponse getCricketer(@PathVariable("id") String id) {
		return cricketerService.getCricketer(id);
	}

	@PostMapping("/create/cricketer")
	public CricketerResponse addCricketer(@RequestBody Cricketer cricketer) {
		return cricketerService.addCricketer(cricketer);
	}

	@PostMapping("/update/cricketer/{id}")
	public CricketerResponse updateCricketer(@PathVariable("id") String id,
			@RequestBody Cricketer cricketer) {
		return cricketerService.updateCricketer(id, cricketer);
	}

	@PostMapping("/delete/cricketer/{id}")
	public CricketerResponse deleteCricketer(@PathVariable("id") String id) {
		return cricketerService.deleteCricketer(id);
	}

}
