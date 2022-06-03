package com.spring.demo.feignclients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.spring.demo.models.Bike;
import com.spring.demo.models.Car;

@FeignClient(value="bike-service")
public interface BikeFeigClient {
	@PostMapping("/bike")
	Bike saveBike(@RequestBody Bike bike);
	@GetMapping("/bike/{id}")
	List<Bike> userById(@PathVariable("id") int id);
}
