package com.buyg.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "vehicle")
public class VehicleEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "vehicleid")
	private Integer vehicleId;
	@Column(name = "vehiclename")
	private String vehicleName;
	@Column(name = "vehiclenumber")
	private String vehicleNumber;
	@Column(name = "vehicletype")
	private String vehicleType;
}
