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

import com.spring.demo.entity.Car;
import com.spring.demo.services.CarService;

@RestController
@RequestMapping("/car")
public class CarController {
	@Autowired
	CarService carservice;
	@GetMapping
	public ResponseEntity<List<Car>> getAll(){
		return new ResponseEntity<List<Car>>(carservice.getAll(), HttpStatus.OK);
	}
	@PostMapping
	public Car saveUser(@RequestBody Car car){
		return carservice.saveCar(car);
	}
	
	@GetMapping("/{id}")
	public List<Car> userById(@PathVariable("id") int id){
		List<Car> cars=carservice.carById(id);
		return cars;
	}
}
