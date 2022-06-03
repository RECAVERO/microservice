package com.spring.demo.feignclients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.spring.demo.models.Car;

@FeignClient(value="car-service")
public interface CarFeigClient {
	@PostMapping("/car")
	Car saveCar(@RequestBody Car car);
	@GetMapping("/car/{id}")
	List<Car> userById(@PathVariable("id") int id);
}
