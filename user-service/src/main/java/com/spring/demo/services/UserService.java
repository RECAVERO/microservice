package com.spring.demo.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.spring.demo.entity.User;
import com.spring.demo.feignclients.BikeFeigClient;
import com.spring.demo.feignclients.CarFeigClient;
import com.spring.demo.models.Bike;
import com.spring.demo.models.Car;
import com.spring.demo.repositories.UserRepository;

@Service
public class UserService {
	@Autowired
	UserRepository userrepository;
	@Autowired
	RestTemplate restTemplate;
	@Autowired
	CarFeigClient carFeigClient;
	
	private final BikeFeigClient bikeFeigClient;
	public  UserService(BikeFeigClient _bikeFeigClient) {
		this.bikeFeigClient=_bikeFeigClient;
	}
	
	public List<User> getAll(){
		return userrepository.getListaUser();
	}
	
	public User saveUser(User user) {
		return userrepository.saveUser(user.getName(),user.getEmail());
	}
	
	public User userById(int id){
		return userrepository.userById(id);
	}
	
	public List<Car> getCars(int userid){
		List<Car> cars=restTemplate.getForObject("http://car-service/car/"+userid, List.class);
		return cars;
	}
	
	public List<Bike> getBikes(int userid){
		List<Bike> bikes=restTemplate.getForObject("http://bike-service/bike/"+userid, List.class);
		return bikes;
	}
	
	public Car saveCar(int userid,Car car) {
		car.setUserid(userid);
		Car carNew=carFeigClient.saveCar(car);
		return carNew;
	}
	
	public Bike saveBike(int userid,Bike bike) {
		bike.setUserid(userid);
		Bike bikeNew=bikeFeigClient.saveBike(bike);
		return bikeNew;
	}
	
	public Map<String,Object> getUserAndVehicles(int userid){
		Map<String,Object> result=new HashMap<>();
		User user=userrepository.userById(userid);
		if(user==null) {
			 result.put("Mensaje", "No se encontro users");
			 return result;
		}else {
			result.put("User", user);
		}
		
		List<Car> cars=carFeigClient.userById(userid);
		if(cars.isEmpty()) {
			result.put("Cars", "No se encontro Cars");
		}else {
			result.put("Car", cars);
		}
		
		List<Bike> bikes=bikeFeigClient.userById(userid);
		if(bikes.isEmpty()) {
			result.put("Bikes", "No se encontro Bikes");
		}else {
			result.put("Bikes", bikes);
		}
		
		return result;
	}
	
}
