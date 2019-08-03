package com.buyg.repository.consumer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.buyg.entity.VehicleEntity;

@Repository
public interface ConsumerVehicleRepository extends JpaRepository<VehicleEntity, Integer> {

}
