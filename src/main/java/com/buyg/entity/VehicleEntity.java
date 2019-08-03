package com.buyg.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "consumerid")
	@JsonBackReference
	private ConsumerEntity consumerEntity1;
}
