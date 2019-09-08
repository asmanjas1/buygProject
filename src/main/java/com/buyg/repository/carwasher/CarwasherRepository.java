package com.buyg.repository.carwasher;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.buyg.entity.CarwasherEntity;

@Repository
public interface CarwasherRepository extends JpaRepository<CarwasherEntity, Integer> {
	CarwasherEntity findByEmail(String email);

	@Transactional(readOnly = true)
	CarwasherEntity findByEmailAndPassword(String email, String password);

	@Transactional(readOnly = true)
	CarwasherEntity findBycarwasherId(Integer carwasherId);

	@Query("from CarwasherEntity where phoneNumber =:phoneNumber")
	CarwasherEntity fetchUserByPhoneNumber(@Param("phoneNumber") String phoneNumber);

}
