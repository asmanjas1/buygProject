package com.buyg.beans;

import java.util.Date;

import lombok.Data;

@Data
public class Orders {
	private Integer orderId;
	private Date orderDate;
	private Date orderCompletedDate;
	private Double orderAmount;
	private String orderStatus;
	private String orderPaymentStatus;
	private Consumer consumer;

	private Integer orderCarwasherId;
	private Integer orderVehicleId;
	private Integer orderAddressId;
	private String orderAddressCity;
	private String orderAddressState;
}
