package com.buyg.repository.consumer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.buyg.entity.ConsumerFirebaseEntity;

@Repository
public interface ConsumerFirebaseRepository extends JpaRepository<ConsumerFirebaseEntity, Integer> {

}
