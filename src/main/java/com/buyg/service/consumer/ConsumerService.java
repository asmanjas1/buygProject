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
import com.buyg.beans.Vehicle;
import com.buyg.entity.ConsumerAddressEntity;
import com.buyg.entity.ConsumerEntity;
import com.buyg.entity.VehicleEntity;
import com.buyg.repository.consumer.ConsumerAddressRepository;
import com.buyg.repository.consumer.ConsumerRepository;
import com.buyg.repository.consumer.ConsumerVehicleRepository;
import com.buyg.utils.BuyGConstants;
import com.buyg.validations.ConsumerValidation;
import com.google.gson.Gson;

@Service
public class ConsumerService {
	@Autowired
	private ConsumerValidation consumerValidation;

	@Autowired
	private ConsumerRepository consumerRepository;

	@Autowired
	private ConsumerAddressRepository consumerAddressRepository;

	@Autowired
	private ConsumerVehicleRepository consumerVehicleRepository;

	public static Gson gson = new Gson();

	public Map<String, Object> signUp(Consumer consumer) {
		Map<String, Object> responseMap = new HashMap<>();
		int responseCode = 500;
		String resMsg = "Error Occured";
		ConsumerEntity savedConsumerEntity = null;
		if (consumerValidation.validateConsumerForSignUp(consumer)) {
			ConsumerEntity cE = consumerRepository.findByEmail(consumer.getEmail());
			if (cE == null) {
				ConsumerEntity consumerEntity = new ConsumerEntity();
				consumerEntity.setEmail(consumer.getEmail());
				consumerEntity.setName(consumer.getName());
				consumerEntity.setPassword(consumer.getPassword());
				consumerEntity.setPhoneNumber(consumer.getPhoneNumber());
				savedConsumerEntity = consumerRepository.saveAndFlush(consumerEntity);
				savedConsumerEntity.setPassword(null);
				responseCode = 200;
				resMsg = "Successfully Added User";
			} else {
				responseCode = 900;
				resMsg = "User Already Exist";
			}
		} else {
			responseCode = 400;
			resMsg = "consumer validation failed";
		}
		responseMap.put(BuyGConstants.DATA_STRING, savedConsumerEntity);
		responseMap.put(BuyGConstants.RESPONSE_CODE_STRING, responseCode);
		responseMap.put(BuyGConstants.RESPONSE_MSG, resMsg);
		return responseMap;
	}

	public Map<String, Object> saveAddress(ConsumerAddress consumerAddress) {
		Map<String, Object> responseMap = new HashMap<>();
		int responseCode = 500;
		String resMsg = "Error Occured";
		ConsumerAddressEntity consumerAddre = null;
		if (consumerValidation.validateConsumerAdddress(consumerAddress)) {
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
			responseCode = 200;
			resMsg = "Successfully Added consumer address";
		} else {
			responseCode = 400;
			resMsg = "consumer validation failed";
		}
		responseMap.put(BuyGConstants.DATA_STRING, consumerAddre);
		responseMap.put(BuyGConstants.RESPONSE_CODE_STRING, responseCode);
		responseMap.put(BuyGConstants.RESPONSE_MSG, resMsg);
		return responseMap;
	}

	public Map<String, Object> saveVehicle(Vehicle vehicle) {
		Map<String, Object> responseMap = new HashMap<>();
		int responseCode = 500;
		String resMsg = "Error Occured";
		VehicleEntity vehicleEnt = null;
		if (consumerValidation.validateConsumerVehicle(vehicle)) {
			VehicleEntity vehicleEntity = new VehicleEntity();
			vehicleEntity.setVehicleName(vehicle.getVehicleName());
			vehicleEntity.setVehicleNumber(vehicle.getVehicleNumber());
			vehicleEntity.setVehicleType(vehicle.getVehicleType());
			ConsumerEntity consumerEntity = new ConsumerEntity();
			consumerEntity.setConsumerId(vehicle.getConsumer().getConsumerId());
			vehicleEntity.setConsumerEntity1(consumerEntity);
			vehicleEnt = consumerVehicleRepository.saveAndFlush(vehicleEntity);
			responseCode = 200;
			resMsg = "Successfully Added consumer vehicle";

		} else {
			responseCode = 400;
			resMsg = "consumer validation failed";
		}
		responseMap.put(BuyGConstants.DATA_STRING, vehicleEnt);
		responseMap.put(BuyGConstants.RESPONSE_CODE_STRING, responseCode);
		responseMap.put(BuyGConstants.RESPONSE_MSG, resMsg);
		return responseMap;
	}

	public Map<String, Object> doLogin(Consumer consumer) {
		Map<String, Object> responseMap = new HashMap<>();
		int responseCode = 500;
		String resMsg = "Error Occured";
		try {
			if (consumer != null) {
				String email = consumer.getEmail();
				String password = consumer.getPassword();
				if (nonNull(email) && nonNull(password)) {
					responseCode = 900;
					resMsg = "General Error";
					ConsumerEntity consumerEntity = consumerRepository.findByEmailAndPassword(email, password);
					if (nonNull(consumerEntity)) {
						consumer.setConsumerId(consumerEntity.getConsumerId());
						consumer.setName(consumerEntity.getName());
						consumer.setEmail(consumerEntity.getEmail());
						consumer.setPhoneNumber(consumerEntity.getPhoneNumber());
						consumer.setRegistrationDate(consumerEntity.getRegistrationDate().toString());
						consumer.setLastUpdateDate(consumerEntity.getLastUpdateDate().toString());
						List<ConsumerAddress> list = new ArrayList<ConsumerAddress>();
						List<ConsumerAddressEntity> listFromDatabase = consumerEntity.getConsumerAddressEntities();
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

						List<VehicleEntity> listVehicleEntity = consumerEntity.getVehicleEntities();
						List<Vehicle> list1 = new ArrayList<>();
						if (listVehicleEntity != null) {
							for (VehicleEntity ve : listVehicleEntity) {
								Vehicle vehicle = new Vehicle();
								vehicle.setVehicleId(ve.getVehicleId());
								vehicle.setVehicleName(ve.getVehicleName());
								vehicle.setVehicleNumber(ve.getVehicleNumber());
								vehicle.setVehicleType(ve.getVehicleType());
								list1.add(vehicle);
							}

							consumer.setListOfVehicle(list1);
						}
						responseCode = 200;
						resMsg = "Successfully LoggedIn";
					}
				} else {
					responseCode = 400;
					resMsg = "login validation failed";
				}
			} else {
				responseCode = 400;
				resMsg = "login validation failed";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		consumer.setPassword(null);
		responseMap.put(BuyGConstants.DATA_STRING, gson.toJson(consumer));
		responseMap.put(BuyGConstants.RESPONSE_CODE_STRING, responseCode);
		responseMap.put(BuyGConstants.RESPONSE_MSG, resMsg);
		return responseMap;
	}

	public Map<String, Object> getConsumerById(Integer id) {
		Map<String, Object> responseMap = new HashMap<>();
		int responseCode = 500;
		String resMsg = "Error Occured";
		Consumer consumer = new Consumer();
		try {
			if (id != null) {
				responseCode = 900;
				resMsg = "General Error";
				ConsumerEntity consumerEntity = consumerRepository.findByConsumerId(id);
				if (nonNull(consumerEntity)) {
					consumer.setConsumerId(consumerEntity.getConsumerId());
					consumer.setName(consumerEntity.getName());
					consumer.setEmail(consumerEntity.getEmail());
					consumer.setPhoneNumber(consumerEntity.getPhoneNumber());
					consumer.setRegistrationDate(consumerEntity.getRegistrationDate().toString());
					consumer.setLastUpdateDate(consumerEntity.getLastUpdateDate().toString());
					List<ConsumerAddress> list = new ArrayList<ConsumerAddress>();
					List<ConsumerAddressEntity> listFromDatabase = consumerEntity.getConsumerAddressEntities();
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

					List<VehicleEntity> listVehicleEntity = consumerEntity.getVehicleEntities();
					List<Vehicle> list1 = new ArrayList<>();
					if (listVehicleEntity != null) {
						for (VehicleEntity ve : listVehicleEntity) {
							Vehicle vehicle = new Vehicle();
							vehicle.setVehicleId(ve.getVehicleId());
							vehicle.setVehicleName(ve.getVehicleName());
							vehicle.setVehicleNumber(ve.getVehicleNumber());
							vehicle.setVehicleType(ve.getVehicleType());
							list1.add(vehicle);
						}

						consumer.setListOfVehicle(list1);
					}
					responseCode = 200;
					resMsg = "Successfully LoggedIn";
				}
			} else {
				responseCode = 400;
				resMsg = "login validation failed";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		consumer.setPassword(null);
		responseMap.put(BuyGConstants.DATA_STRING, gson.toJson(consumer));
		responseMap.put(BuyGConstants.RESPONSE_CODE_STRING, responseCode);
		responseMap.put(BuyGConstants.RESPONSE_MSG, resMsg);
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

	public Map<String, Object> updateConsumer(Consumer consumer, Integer id) {
		Map<String, Object> responseMap = new HashMap<>();
		int responseCode = 0;
		ConsumerEntity consumerEntity = consumerRepository.findByConsumerId(id);
		if (consumerEntity != null) {
			if (consumer.getName() != null)
				consumerEntity.setName(consumer.getName());
			if (consumer.getPhoneNumber() != null)
				consumerEntity.setPhoneNumber(consumer.getPhoneNumber());
			consumerEntity.setConsumerId(id);
			consumerRepository.saveAndFlush(consumerEntity);
			responseCode = 2;
		}
		responseMap.put(BuyGConstants.DATA_STRING, consumerEntity);
		responseMap.put(BuyGConstants.RESPONSE_CODE_STRING, responseCode);
		return responseMap;

	}

	public Map<String, Object> updatePassword(CommonBean bean, Integer id) {
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
