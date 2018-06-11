package com.buyg.repository.shop;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.buyg.entity.ShopEntity;

@Repository
public interface ShopRepository extends JpaRepository<ShopEntity, Integer> {
	ShopEntity findByEmail(String email);

	@Transactional(readOnly = true)
	ShopEntity findByEmailAndPassword(String email, String password);
	
	@Transactional(readOnly = true)
	ShopEntity findByShopId(Integer shopId);

}
