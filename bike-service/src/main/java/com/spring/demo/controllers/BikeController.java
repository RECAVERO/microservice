package com.spring.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.demo.entity.Bike;

import com.spring.demo.services.BikeService;


@RestController
@RequestMapping("/bike")
public class BikeController {
	@Autowired
	BikeService bikeservice;
	@GetMapping
	public ResponseEntity<List<Bike>> getAll(){
		return new ResponseEntity<List<Bike>>(bikeservice.getAll(), HttpStatus.OK);
	}
	@PostMapping
	public Bike saveUser(@RequestBody Bike car){
		return bikeservice.saveBike(car);
	}
	
	@GetMapping("/{id}")
	public List<Bike> bikeById(@PathVariable("id") int id){
		List<Bike> bikes=bikeservice.bikeById(id);
		return bikes;
	}
}
