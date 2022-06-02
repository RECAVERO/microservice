package com.spring.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.demo.entity.Car;
import com.spring.demo.repositories.CarRepository;

@Service
public class CarService {
	@Autowired
	CarRepository carrepository;
	public List<Car> getAll(){
		return carrepository.getListaCar();
	}
	
	public Car saveCar(Car car) {
		return carrepository.saveCar(car.getBrand(),car.getModel(),car.getUserid());
	}
	
	public List<Car> carById(int id){
		return carrepository.carById(id);
	}
}
