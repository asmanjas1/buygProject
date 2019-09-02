package com.buyg.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "carwasheraddress")
@Data
public class CarwasherAddressEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "addressid")
	private Integer addressId;
	@Column(name = "addressline")
	private String addressLine;
	@Column(name = "locality")
	private String locality;
	@Column(name = "city")
	private String city;
	@Column(name = "state")
	private String state;
	@Column(name = "pincode")
	private Integer pincode;
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "carwasherid")
	private CarwasherEntity carwasherEntity;
}
