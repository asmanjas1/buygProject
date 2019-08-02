package com.buyg.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name = "carwasher")
@Data
public class CarwasherEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "carwasherid")
	private Integer carwasherId;
	@Column(name = "name")
	private String name;
	@Column(name = "email")
	private String email;
	@Column(name = "phonenumber")
	private String phoneNumber;
	@JsonIgnore
	@Column(name = "password")
	private String password;
	@CreationTimestamp
	@Column(name = "registrationdate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date registrationDate;
	@UpdateTimestamp
	@Column(name = "lastupdatedate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdateDate;
	@OneToOne(mappedBy = "carwasherEntity")
	private CarwasherAddressEntity carwasherAddressEntity;

}
