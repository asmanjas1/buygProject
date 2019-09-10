package com.buyg.repository.carwasher;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.buyg.entity.CarwasherAddressEntity;
import com.buyg.entity.CarwasherEntity;

@Repository
public interface CarwasherAddressRepository extends JpaRepository<CarwasherAddressEntity, Integer> {
	@Query("from CarwasherAddressEntity where city =:city and state =:state")
	List<CarwasherAddressEntity> getCarwahserByAddressMatching(@Param("city") String city,
			@Param("state") String state);

	CarwasherAddressEntity findByCarwasherEntity(CarwasherEntity carwasherEntity);
}
