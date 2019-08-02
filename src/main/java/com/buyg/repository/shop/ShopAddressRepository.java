package com.buyg.repository.shop;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.buyg.entity.CarwasherAddressEntity;
import com.buyg.entity.CarwasherEntity;

@Repository
public interface ShopAddressRepository extends JpaRepository<CarwasherAddressEntity, Integer> {
	CarwasherAddressEntity findByShopEntity(CarwasherEntity shopEntity);
}
