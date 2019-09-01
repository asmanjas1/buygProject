package com.buyg.repository.carwasher;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.buyg.entity.CarwasherFirebaseEntity;

@Repository
public interface CarwasherFirebaseRepository extends JpaRepository<CarwasherFirebaseEntity, Integer> {
	List<CarwasherFirebaseEntity> findByconsumerFirebaseIdIn(List<Integer> list);
}
