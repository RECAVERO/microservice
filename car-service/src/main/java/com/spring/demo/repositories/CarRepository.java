package com.spring.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.spring.demo.entity.Car;
@Repository
public interface CarRepository extends JpaRepository<Car, Integer>{
    @Query(value = "{call sp_car_lista()}", nativeQuery = true)
    List<Car> getListaCar();
    @Query(value = "{call sp_car_save(:_brand,:_model,:_userid)}", nativeQuery = true)
    Car saveCar(@Param("_brand") String _brand,@Param("_model") String _model,@Param("_userid") int _userid);
    @Query(value = "{call sp_car_id(:_id)}", nativeQuery = true)
    List<Car> carById(@Param("_id") int _id);
}
