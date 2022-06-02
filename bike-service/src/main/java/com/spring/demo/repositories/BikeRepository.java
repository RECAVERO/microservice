package com.spring.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.spring.demo.entity.Bike;
@Repository
public interface BikeRepository extends JpaRepository<Bike, Integer>{
    @Query(value = "{call sp_bike_lista()}", nativeQuery = true)
    List<Bike> getListaBike();
    @Query(value = "{call sp_bike_save(:_brand,:_model,:_userid)}", nativeQuery = true)
    Bike saveBike(@Param("_brand") String _brand,@Param("_model") String _model,@Param("_userid") int _userid);
    @Query(value = "{call sp_bike_id(:_id)}", nativeQuery = true)
    List<Bike> bikeById(@Param("_id") int _id);
}
