package com.spring.demo.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.demo.entity.User;
import com.spring.demo.models.Bike;
import com.spring.demo.models.Car;
import com.spring.demo.services.UserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserService userservice;

	@GetMapping
	public ResponseEntity<List<User>> getAll() {
		return new ResponseEntity<List<User>>(userservice.getAll(), HttpStatus.OK);
	}

	@PostMapping
	public User saveUser(@RequestBody User user) {
		return userservice.saveUser(user);
	}

	@GetMapping("/{id}")
	public User userById(@PathVariable("id") int id) {
		return userservice.userById(id);
	}

	@CircuitBreaker(name = "carsCB", fallbackMethod = "fallBackGetCars")
	@GetMapping("/cars/{userid}")
	public ResponseEntity<List<Car>> userCar(@PathVariable("userid") int userid) {
		List<Car> cars = userservice.getCars(userid);
		return ResponseEntity.ok(cars);
	}

	@CircuitBreaker(name = "bikesCB", fallbackMethod = "fallBackGetBikes")
	@GetMapping("/bikes/{userid}")
	public ResponseEntity<List<Bike>> useBike(@PathVariable("userid") int userid) {
		List<Bike> bikes = userservice.getBikes(userid);
		return ResponseEntity.ok(bikes);
	}

	// feign client
	@CircuitBreaker(name = "carsCB", fallbackMethod = "fallBackSaveCar")
	@PostMapping("/savecar/{userid}")
	public ResponseEntity<Car> saveCar(@PathVariable("userid") int userid, @RequestBody Car car) {
		if (userservice.userById(userid) == null)
			ResponseEntity.notFound().build();
		Car cars = userservice.saveCar(userid, car);
		return ResponseEntity.ok(cars);
	}

	@CircuitBreaker(name = "bikesCB", fallbackMethod = "fallBackSaveBike")
	@PostMapping("/savebike/{userid}")
	public ResponseEntity<Bike> saveBike(@PathVariable("userid") int userid, @RequestBody Bike bike) {
		if (userservice.userById(userid) == null)
			ResponseEntity.notFound().build();
		Bike bikes = userservice.saveBike(userid, bike);
		return ResponseEntity.ok(bikes);
	}

	@CircuitBreaker(name = "allCB", fallbackMethod = "fallBackGetAll")
	@GetMapping("/getAll/{userid}")
	public ResponseEntity<Map<String, Object>> getAllVehicles(@PathVariable("userid") int userid) {
		Map<String, Object> result = userservice.getUserAndVehicles(userid);
		return ResponseEntity.ok(result);
	}

	private ResponseEntity<List<Car>> fallBackGetCars(@PathVariable("userid") int userid, RuntimeException e) {
		return new ResponseEntity("El usuario " + userid + " tiene los coches en el taller", HttpStatus.OK);
	}

	private ResponseEntity<Car> fallBackSaveCar(@PathVariable("userid") int userid, @RequestBody Car car,RuntimeException e) {
		return new ResponseEntity("El usuario " + userid + " no tiene dinero para coches", HttpStatus.OK);
	}

	private ResponseEntity<List<Bike>> fallBackGetBikes(@PathVariable("userid") int userid, RuntimeException e) {
		return new ResponseEntity("El usuario " + userid + " tiene las motos en el taller", HttpStatus.OK);
	}

	private ResponseEntity<Car> fallBackSaveBike(@PathVariable("userid") int userid, @RequestBody Bike bike, RuntimeException e) {
		return new ResponseEntity("El usuario " + userid + "  no tiene dinero para motos", HttpStatus.OK);
	}

	public ResponseEntity<Map<String, Object>> fallBackGetAll(@PathVariable("userid") int userid, RuntimeException e) {
		return new ResponseEntity("El usuario " + userid + " tiene los vehículos en el taller", HttpStatus.OK);
	}
}
