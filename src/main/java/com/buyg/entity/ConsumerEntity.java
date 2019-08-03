package com.buyg.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Entity
@Table(name = "consumer")
@Data
public class ConsumerEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "consumerid")
	private Integer consumerId;
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
	@OneToMany(mappedBy = "consumerEntity1")
	@Column(nullable = true)
	@JsonManagedReference
	private List<VehicleEntity> vehicleEntities;
	@OneToMany(mappedBy = "consumerEntity", fetch = FetchType.EAGER)
	@Column(nullable = true)
	@JsonManagedReference
	private List<ConsumerAddressEntity> consumerAddressEntities;

}
