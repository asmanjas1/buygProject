package com.buyg.beans;

import java.util.List;

import lombok.Data;

@Data
public class Consumer {
	private Integer consumerId;
	private String name;
	private String email;
	private String phoneNumber;
	private String password;
	private String registrationDate;
	private String lastUpdateDate;
	private List<Vehicle> listOfVehicle;
	private List<ConsumerAddress> listOfAddress;

}
