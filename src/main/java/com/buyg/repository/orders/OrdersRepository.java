package com.buyg.repository.orders;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.buyg.entity.ConsumerEntity;
import com.buyg.entity.OrdersEntity;

@Repository
public interface OrdersRepository extends JpaRepository<OrdersEntity, Integer> {

	List<OrdersEntity> findByConsumerEntity(ConsumerEntity entity);
}
