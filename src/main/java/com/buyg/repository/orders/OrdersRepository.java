package com.buyg.repository.orders;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.buyg.entity.ConsumerEntity;
import com.buyg.entity.OrdersEntity;

@Repository
public interface OrdersRepository extends JpaRepository<OrdersEntity, Integer> {

	List<OrdersEntity> findByConsumerEntity(ConsumerEntity entity);

	@Transactional(readOnly = true)
	OrdersEntity findByOrderId(Integer orderID);

	@Query("from OrdersEntity where orderCarwasherId =:carwasherId and orderStatus =:orderStatus")
	List<OrdersEntity> fetchOrdersForCwarwasherByStatus(@Param("carwasherId") Integer carwasherId,
			@Param("orderStatus") String status);

	@Query("from OrdersEntity where orderAddressCity =:orderAddressCity and orderAddressState =:orderAddressState and orderStatus =:orderStatus")
	List<OrdersEntity> fetchAllNewOrderForCarwasher(@Param("orderAddressCity") String city,
			@Param("orderAddressState") String state, @Param("orderStatus") String status);

	@Modifying
	@Transactional
	@Query("update OrdersEntity o set o.orderCarwasherId =:carwasherId, o.orderStatus =:orderStatus where o.orderCarwasherId IS NULL and o.orderId =:orderId and o.orderStatus =:whereStatus")
	Integer confirmOrderByCarwasher(@Param("orderId") Integer orderId, @Param("carwasherId") Integer carwasherId,
			@Param("orderStatus") String status, @Param("whereStatus") String whereStatus);

	@Modifying
	@Transactional
	@Query("update OrdersEntity o set o.orderPaymentStatus =:paymentStatus, o.orderStatus =:orderStatus where o.orderId =:orderId")
	Integer completeOrderByOrderId(@Param("orderId") Integer orderId, @Param("orderStatus") String status,
			@Param("paymentStatus") String paymentStatus);
}
