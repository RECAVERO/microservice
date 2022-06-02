package com.spring.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.demo.entity.Bike;
import com.spring.demo.repositories.BikeRepository;

@Service
public class BikeService {
	@Autowired
	BikeRepository bikerepository;
	public List<Bike> getAll(){
		return bikerepository.getListaBike();
	}
	
	public Bike saveBike(Bike car) {
		return bikerepository.saveBike(car.getBrand(),car.getModel(),car.getUserid());
	}
	
	public List<Bike> bikeById(int id){
		return bikerepository.bikeById(id);
	}
}
