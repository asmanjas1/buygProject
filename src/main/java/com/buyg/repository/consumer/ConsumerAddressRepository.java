package com.buyg.repository.consumer;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.buyg.entity.ConsumerAddressEntity;
import com.buyg.entity.ConsumerEntity;

@Repository
public interface ConsumerAddressRepository extends JpaRepository<ConsumerAddressEntity, Integer> {
	List<ConsumerAddressEntity> findByConsumerEntity(ConsumerEntity entity);
}
