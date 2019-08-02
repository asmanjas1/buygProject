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
	private Consumer consumer;
	private Carwasher carwasher;

}
