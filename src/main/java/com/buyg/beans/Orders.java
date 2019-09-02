package com.buyg.beans;

import lombok.Data;

@Data
public class Orders {
	private Integer orderId;
	private String orderDate;
	private String orderCompletedDate;
	private Double orderAmount;
	private String orderStatus;
	private String orderPaymentStatus;
	private Consumer consumer;

	private Integer orderCarwasherId;
	private Integer orderVehicleId;
	private Integer orderAddressId;
	private String orderAddressCity;
	private String orderAddressState;

	private Carwasher carwasher;
	private Vehicle orderVehicle;
	private ConsumerAddress orderAddress;
}
