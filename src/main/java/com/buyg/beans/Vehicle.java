package com.buyg.beans;

import lombok.Data;

@Data
public class Vehicle {
	private Integer vehicleId;
	private String vehicleName;
	private String vehicleNumber;
	private String vehicleType;
	private Consumer consumer;

}
