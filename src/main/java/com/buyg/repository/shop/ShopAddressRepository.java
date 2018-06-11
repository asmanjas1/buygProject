package com.buyg.repository.shop;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.buyg.entity.ShopAddressEntity;
import com.buyg.entity.ShopEntity;

@Repository
public interface ShopAddressRepository extends JpaRepository<ShopAddressEntity, Integer> {
	ShopAddressEntity findByShopEntity(ShopEntity shopEntity);
}
