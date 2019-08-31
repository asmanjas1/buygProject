package com.buyg.repository.orders;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.buyg.entity.ConsumerEntity;
import com.buyg.entity.OrdersEntity;

@Repository
public interface OrdersRepository extends JpaRepository<OrdersEntity, Integer> {

	List<OrdersEntity> findByConsumerEntity(ConsumerEntity entity);

	@Query("from OrdersEntity where orderCarwasherId =:carwasherId and orderStatus =:orderStatus")
	List<OrdersEntity> fetchOrdersForCwarwasherByStatus(@Param("carwasherId") Integer carwasherId,
			@Param("orderStatus") String status);

	@Query("from OrdersEntity where orderAddressCity =:orderAddressCity and orderAddressState =:orderAddressState and orderStatus =:orderStatus")
	List<OrdersEntity> fetchAllNewOrderForCarwasher(@Param("orderAddressCity") String city,
			@Param("orderAddressState") String state, @Param("orderStatus") String status);
}
