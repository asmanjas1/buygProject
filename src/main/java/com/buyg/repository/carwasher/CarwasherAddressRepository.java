package com.buyg.repository.carwasher;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.buyg.entity.CarwasherAddressEntity;

@Repository
public interface CarwasherAddressRepository extends JpaRepository<CarwasherAddressEntity, Integer> {

}
