package com.buyg.repository.carwasher;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.buyg.entity.CarwasherEntity;

@Repository
public interface ShopRepository extends JpaRepository<CarwasherEntity, Integer> {
	CarwasherEntity findByEmail(String email);

	@Transactional(readOnly = true)
	CarwasherEntity findByEmailAndPassword(String email, String password);

	@Transactional(readOnly = true)
	CarwasherEntity findBycarwasherId(Integer carwasherId);

}
