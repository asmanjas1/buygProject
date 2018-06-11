package com.buyg.repository.consumer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.buyg.entity.ConsumerEntity;

@Repository
public interface ConsumerRepository extends JpaRepository<ConsumerEntity, Integer> {
	ConsumerEntity findByEmail(String email);

	@Transactional(readOnly = true)
	ConsumerEntity findByEmailAndPassword(String email, String password);

	@Query("from ConsumerEntity where email =:email and password =:password")
	ConsumerEntity fetchUser(@Param("email") String email, @Param("password") String password);
	
	@Transactional(readOnly = true)
	ConsumerEntity findByConsumerId(Integer consumerid);

}
