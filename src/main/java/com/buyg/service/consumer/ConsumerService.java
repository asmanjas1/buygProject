package com.buyg.service.consumer;

import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buyg.beans.CommonBean;
import com.buyg.beans.Consumer;
import com.buyg.beans.ConsumerAddress;
import com.buyg.entity.ConsumerAddressEntity;
import com.buyg.entity.ConsumerEntity;
import com.buyg.repository.consumer.ConsumerAddressRepository;
import com.buyg.repository.consumer.ConsumerRepository;
import com.buyg.utils.BuyGConstants;
import com.buyg.validations.ConsumerValidation;

@Service
public class ConsumerService {
	@Autowired
	private ConsumerValidation consumerValidation;

	@Autowired
	private ConsumerRepository consumerRepository;

	@Autowired
	private ConsumerAddressRepository consumerAddressRepository;

	public Map<String, Object> signUp(Consumer consumer) {
		Map<String, Object> responseMap = new HashMap<>();
		int responseCode = 0;
		ConsumerEntity savedConsumerEntity = null;
		if (consumerValidation.validateConsumerForSignUp(consumer)) {
			ConsumerEntity cE = consumerRepository.findByEmail(consumer.getEmail());
			responseCode = 1;
			if (cE == null) {
				ConsumerEntity consumerEntity = new ConsumerEntity();
				consumerEntity.setEmail(consumer.getEmail());
				consumerEntity.setName(consumer.getName());
				consumerEntity.setPassword(consumer.getPassword());
				consumerEntity.setPhoneNumber(consumer.getPhoneNumber());
				savedConsumerEntity = consumerRepository.saveAndFlush(consumerEntity);
				savedConsumerEntity.setPassword(null);
				responseCode = 2;
			}
		}
		responseMap.put(BuyGConstants.DATA_STRING, savedConsumerEntity);
		responseMap.put(BuyGConstants.RESPONSE_CODE_STRING, responseCode);
		return responseMap;
	}

	public Map<String, Object> saveAddress(ConsumerAddress consumerAddress) {
		Map<String, Object> responseMap = new HashMap<>();
		int responseCode = 0;
		ConsumerAddressEntity consumerAddre = null;
		if (consumerValidation.validateConsumerAdddress(consumerAddress)) {
			responseCode = 2;
			ConsumerAddressEntity consumerAddressEntity = new ConsumerAddressEntity();
			consumerAddressEntity.setAddressLine(consumerAddress.getAddressLine());
			consumerAddressEntity.setLocality(consumerAddress.getLocality());
			consumerAddressEntity.setCity(consumerAddress.getCity());
			consumerAddressEntity.setState(consumerAddress.getState());
			consumerAddressEntity.setPincode(consumerAddress.getPincode());
			ConsumerEntity consumerEntity = new ConsumerEntity();
			consumerEntity.setConsumerId(consumerAddress.getConsumer().getConsumerId());
			consumerAddressEntity.setConsumerEntity(consumerEntity);
			consumerAddre = consumerAddressRepository.saveAndFlush(consumerAddressEntity);
		}
		responseMap.put(BuyGConstants.DATA_STRING, consumerAddre);
		responseMap.put(BuyGConstants.RESPONSE_CODE_STRING, responseCode);
		return responseMap;
	}

	public Map<String, Object> doLogin(Consumer consumer) {
		Map<String, Object> responseMap = new HashMap<>();
		int responseCode = 0;
		try {
			if (consumer != null) {
				String email = consumer.getEmail();
				String password = consumer.getPassword();
				if (nonNull(email) && nonNull(password)) {
					responseCode = 1;
					ConsumerEntity consumerEntity = consumerRepository.findByEmailAndPassword(email, password);
					if (nonNull(consumerEntity)) {
						consumer.setConsumerId(consumerEntity.getConsumerId());
						consumer.setName(consumerEntity.getName());
						consumer.setEmail(consumerEntity.getEmail());
						consumer.setPhoneNumber(consumerEntity.getPhoneNumber());
						consumer.setRegistrationDate(consumerEntity.getRegistrationDate());
						consumer.setLastUpdateDate(consumerEntity.getLastUpdateDate());
						List<ConsumerAddress> list = new ArrayList<ConsumerAddress>();
						List<ConsumerAddressEntity> listFromDatabase =  consumerEntity.getConsumerAddressEntities();
						for (ConsumerAddressEntity cde : listFromDatabase) {
							ConsumerAddress caa = new ConsumerAddress();
							caa.setAddressId(cde.getAddressId());
							caa.setAddressLine(cde.getAddressLine());
							caa.setLocality(cde.getLocality());
							caa.setState(cde.getState());
							caa.setCity(cde.getCity());
							caa.setPincode(cde.getPincode());
							list.add(caa);
						}
						consumer.setListOfAddress(list);
						responseCode = 2;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		consumer.setPassword(null);
		responseMap.put(BuyGConstants.DATA_STRING, consumer);
		responseMap.put(BuyGConstants.RESPONSE_CODE_STRING, responseCode);
		return responseMap;
	}

	public Map<String, Object> getAllAddressForConsumerId(Integer consumerId) {
		Map<String, Object> responseMap = new HashMap<>();
		int responseCode = 0;
		List<ConsumerAddress> list = new ArrayList<ConsumerAddress>();
		if (consumerId != null) {
			ConsumerEntity consumerEntity = new ConsumerEntity();
			consumerEntity.setConsumerId(consumerId);
			List<ConsumerAddressEntity> entityList = consumerAddressRepository.findByConsumerEntity(consumerEntity);
			for (ConsumerAddressEntity cde : entityList) {
				ConsumerAddress caa = new ConsumerAddress();
				caa.setAddressId(cde.getAddressId());
				caa.setAddressLine(cde.getAddressLine());
				caa.setLocality(cde.getLocality());
				caa.setState(cde.getState());
				caa.setCity(cde.getCity());
				caa.setPincode(cde.getPincode());
				list.add(caa);
			}
			 responseCode = 2;
		}
		responseMap.put(BuyGConstants.DATA_STRING, list);
		responseMap.put(BuyGConstants.RESPONSE_CODE_STRING, responseCode);
		return responseMap;
	}
	
	public Map<String, Object> updateConsumer(Consumer consumer, Integer id){
		Map<String, Object> responseMap = new HashMap<>();
		int responseCode = 0;
		ConsumerEntity consumerEntity = consumerRepository.findByConsumerId(id);
		if(consumerEntity != null) {
			if(consumer.getName() != null)
				consumerEntity.setName(consumer.getName());
			if(consumer.getPhoneNumber() != null)
				consumerEntity.setPhoneNumber(consumer.getPhoneNumber());
			consumerEntity.setConsumerId(id);
			consumerRepository.saveAndFlush(consumerEntity);
			responseCode = 2;
		}
		responseMap.put(BuyGConstants.DATA_STRING, consumerEntity);
		responseMap.put(BuyGConstants.RESPONSE_CODE_STRING, responseCode);
		return responseMap;

	}

	public Map<String, Object> updatePassword(CommonBean bean,Integer id) {
		Map<String, Object> responseMap = new HashMap<>();
		int responseCode = 0;
		ConsumerEntity consumerEntity = consumerRepository.findByConsumerId(id);
		try {
			if (bean.getOldPassword() != null && bean.getNewPassword() != null) {
				responseCode = 1;
				if (consumerEntity != null) {
					String oldPassword = consumerEntity.getPassword();
					if (bean.getOldPassword().equals(oldPassword)) {
						consumerEntity.setPassword(bean.getNewPassword());
						consumerEntity.setConsumerId(id);
						consumerRepository.saveAndFlush(consumerEntity);
						responseCode = 2;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		responseMap.put(BuyGConstants.DATA_STRING, consumerEntity);
		responseMap.put(BuyGConstants.RESPONSE_CODE_STRING, responseCode);
		return responseMap;
	}
	
}
