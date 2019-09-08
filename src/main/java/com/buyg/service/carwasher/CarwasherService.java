package com.buyg.service.carwasher;

import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buyg.beans.Carwasher;
import com.buyg.beans.CarwasherAddress;
import com.buyg.beans.CarwasherFirebasenBean;
import com.buyg.entity.CarwasherAddressEntity;
import com.buyg.entity.CarwasherEntity;
import com.buyg.entity.CarwasherFirebaseEntity;
import com.buyg.repository.carwasher.CarwasherAddressRepository;
import com.buyg.repository.carwasher.CarwasherFirebaseRepository;
import com.buyg.repository.carwasher.CarwasherRepository;
import com.buyg.utils.BuyGConstants;
import com.buyg.validations.ShopValidation;
import com.google.gson.Gson;

@Service
public class CarwasherService {
	@Autowired
	private ShopValidation shopValidation;

	@Autowired
	private CarwasherRepository carwasherRepository;

	@Autowired
	private CarwasherAddressRepository carwasherAddressRepository;

	@Autowired
	private CarwasherFirebaseRepository carwasherFirebaseRepository;

	public static Gson gson = new Gson();

	public Map<String, Object> signUp(Carwasher shop) {
		Map<String, Object> responseMap = new HashMap<>();
		int responseCode = 500;
		String resMsg = "Error Occured";
		CarwasherEntity savedShopEntity = null;
		if (shopValidation.validateShopForSignUp(shop)) {
			CarwasherEntity se = carwasherRepository.findByEmail(shop.getEmail());
			if (se == null) {
				CarwasherEntity shopEntity = new CarwasherEntity();
				shopEntity.setName(shop.getName());
				shopEntity.setEmail(shop.getEmail());
				shopEntity.setPhoneNumber(shop.getPhoneNumber());
				shopEntity.setPassword(shop.getPassword());
				savedShopEntity = carwasherRepository.saveAndFlush(shopEntity);
				responseCode = 200;
				resMsg = "Successfully Added User";
			} else {
				responseCode = 900;
				resMsg = "User Already Exist";
			}
		} else {
			responseCode = 400;
			resMsg = "carwasher validation failed";
		}
		responseMap.put(BuyGConstants.DATA_STRING, savedShopEntity);
		responseMap.put(BuyGConstants.RESPONSE_CODE_STRING, responseCode);
		responseMap.put(BuyGConstants.RESPONSE_MSG, resMsg);
		return responseMap;
	}

	public Map<String, Object> doLogin(Carwasher shop) {
		Map<String, Object> responseMap = new HashMap<>();
		int responseCode = 500;
		String resMsg = "Error Occured";
		if (shop != null) {
			String email = shop.getEmail();
			String password = shop.getPassword();
			if (nonNull(email) && nonNull(password)) {
				responseCode = 900;
				resMsg = "General Error";
				CarwasherEntity shopEntity = carwasherRepository.findByEmailAndPassword(email, password);
				if (shopEntity != null) {
					shop.setCarwasherId(shopEntity.getCarwasherId());
					shop.setName(shopEntity.getName());
					shop.setEmail(shopEntity.getEmail());
					shop.setPhoneNumber(shopEntity.getPhoneNumber());
					shop.setRegistrationDate(shopEntity.getRegistrationDate().toString());
					shop.setLastUpdateDate(shopEntity.getLastUpdateDate().toString());
					CarwasherAddressEntity sAddEntity = shopEntity.getCarwasherAddressEntity();
					CarwasherAddress sAdd = null;
					if (sAddEntity != null) {
						sAdd = new CarwasherAddress();
						sAdd.setAddressId(sAddEntity.getAddressId());
						sAdd.setAddressLine(sAddEntity.getAddressLine());
						sAdd.setLocality(sAddEntity.getLocality());
						sAdd.setCity(sAddEntity.getCity());
						sAdd.setState(sAddEntity.getState());
						sAdd.setPincode(sAddEntity.getPincode());
					}
					shop.setCarwasherAddress(sAdd);
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
		shop.setPassword(null);
		responseMap.put(BuyGConstants.DATA_STRING, gson.toJson(shop));
		responseMap.put(BuyGConstants.RESPONSE_CODE_STRING, responseCode);
		responseMap.put(BuyGConstants.RESPONSE_MSG, resMsg);
		return responseMap;
	}

	public Map<String, Object> getCarwasherById(Integer id) {
		Map<String, Object> responseMap = new HashMap<>();
		int responseCode = 500;
		String resMsg = "Error Occured";
		Carwasher carwasher = new Carwasher();
		if (id != null) {
			responseCode = 900;
			resMsg = "General Error";
			CarwasherEntity shopEntity = carwasherRepository.findBycarwasherId(id);
			if (shopEntity != null) {
				carwasher.setCarwasherId(shopEntity.getCarwasherId());
				carwasher.setName(shopEntity.getName());
				carwasher.setPhoneNumber(shopEntity.getPhoneNumber());
				carwasher.setRegistrationDate(shopEntity.getRegistrationDate().toString());
				carwasher.setLastUpdateDate(shopEntity.getLastUpdateDate().toString());
				CarwasherAddressEntity sAddEntity = shopEntity.getCarwasherAddressEntity();
				CarwasherAddress sAdd = null;
				if (sAddEntity != null) {
					sAdd = new CarwasherAddress();
					sAdd.setAddressId(sAddEntity.getAddressId());
					sAdd.setAddressLine(sAddEntity.getAddressLine());
					sAdd.setLocality(sAddEntity.getLocality());
					sAdd.setCity(sAddEntity.getCity());
					sAdd.setState(sAddEntity.getState());
					sAdd.setPincode(sAddEntity.getPincode());
				}
				carwasher.setCarwasherAddress(sAdd);
				responseCode = 200;
				resMsg = "Successfully LoggedIn";
			}

		} else {
			responseCode = 400;
			resMsg = "login validation failed";
		}
		carwasher.setPassword(null);
		responseMap.put(BuyGConstants.DATA_STRING, gson.toJson(carwasher));
		responseMap.put(BuyGConstants.RESPONSE_CODE_STRING, responseCode);
		responseMap.put(BuyGConstants.RESPONSE_MSG, resMsg);
		return responseMap;
	}

	public Map<String, Object> saveAddress(CarwasherAddress shopAddress) {
		Map<String, Object> responseMap = new HashMap<>();
		int responseCode = 500;
		String resMsg = "Error Occured";
		CarwasherAddressEntity add = null;
		try {
			responseCode = 900;
			resMsg = "General Error";
			if (shopValidation.validateShopAdddress(shopAddress)) {
				CarwasherAddressEntity addressEntity = new CarwasherAddressEntity();
				addressEntity.setAddressLine(shopAddress.getAddressLine());
				addressEntity.setLocality(shopAddress.getLocality());
				addressEntity.setCity(shopAddress.getCity());
				addressEntity.setState(shopAddress.getState());
				addressEntity.setPincode(shopAddress.getPincode());
				CarwasherEntity carwasherEntity = new CarwasherEntity();

				carwasherEntity.setCarwasherId(shopAddress.getCarwasher().getCarwasherId());
				carwasherEntity.setEmail(shopAddress.getCarwasher().getEmail());
				carwasherEntity.setName(shopAddress.getCarwasher().getName());
				carwasherEntity.setPassword(shopAddress.getCarwasher().getPassword());
				carwasherEntity.setPhoneNumber(shopAddress.getCarwasher().getPhoneNumber());

				addressEntity.setCarwasherEntity(carwasherEntity);
				add = carwasherAddressRepository.saveAndFlush(addressEntity);
				responseCode = 200;
				resMsg = "Successfully Added carwasher address";
			} else {
				responseCode = 400;
				resMsg = "address validation failed";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		responseMap.put(BuyGConstants.DATA_STRING, add);
		responseMap.put(BuyGConstants.RESPONSE_CODE_STRING, responseCode);
		responseMap.put(BuyGConstants.RESPONSE_MSG, resMsg);
		return responseMap;
	}

	public Map<String, Object> saveFirebaseToken(CarwasherFirebasenBean carwasherFirebaseEntity) {
		Map<String, Object> responseMap = new HashMap<>();
		int responseCode = 500;
		String resMsg = "Error Occured";
		boolean success = false;
		try {
			CarwasherFirebaseEntity cfe = new CarwasherFirebaseEntity();
			cfe.setCarwasherFirebaseId(carwasherFirebaseEntity.getConsumerFirebaseId());
			cfe.setFirebaseToken(carwasherFirebaseEntity.getFirebaseToken());

			carwasherFirebaseRepository.saveAndFlush(cfe);

			responseCode = 200;
			resMsg = "Successfully Added Consumer Firebase Token.";
		} catch (Exception e) {
			e.printStackTrace();
		}
		responseMap.put(BuyGConstants.DATA_STRING, success);
		responseMap.put(BuyGConstants.RESPONSE_CODE_STRING, responseCode);
		responseMap.put(BuyGConstants.RESPONSE_MSG, resMsg);
		return responseMap;
	}

	public List<CarwasherEntity> getCarwahserByAddressMatching(String city, String state) {
		List<CarwasherEntity> list = new ArrayList<>();
		try {

			List<CarwasherAddressEntity> addList = carwasherAddressRepository.getCarwahserByAddressMatching(city,
					state);
			for (CarwasherAddressEntity ce : addList) {
				list.add(ce.getCarwasherEntity());
			}
		} catch (Exception e) {
		}
		return list;
	}

	public Map<String, Object> doLoginByNumber(String phoneNumber) {
		Map<String, Object> responseMap = new HashMap<>();
		int responseCode = 500;
		String resMsg = "Error Occured";
		Carwasher shop = new Carwasher();
		if (nonNull(phoneNumber)) {
			responseCode = 900;
			resMsg = "General Error";
			CarwasherEntity shopEntity = carwasherRepository.fetchUserByPhoneNumber(phoneNumber);
			if (shopEntity != null) {
				shop.setCarwasherId(shopEntity.getCarwasherId());
				shop.setEmail(shopEntity.getEmail());
				shop.setName(shopEntity.getName());
				shop.setPhoneNumber(shopEntity.getPhoneNumber());
				shop.setRegistrationDate(shopEntity.getRegistrationDate().toString());
				shop.setLastUpdateDate(shopEntity.getLastUpdateDate().toString());
				CarwasherAddressEntity sAddEntity = shopEntity.getCarwasherAddressEntity();
				CarwasherAddress sAdd = null;
				if (sAddEntity != null) {
					sAdd = new CarwasherAddress();
					sAdd.setAddressId(sAddEntity.getAddressId());
					sAdd.setAddressLine(sAddEntity.getAddressLine());
					sAdd.setLocality(sAddEntity.getLocality());
					sAdd.setCity(sAddEntity.getCity());
					sAdd.setState(sAddEntity.getState());
					sAdd.setPincode(sAddEntity.getPincode());
				}
				shop.setCarwasherAddress(sAdd);
				responseCode = 200;
				resMsg = "Successfully LoggedIn";
			} else {
				shop.setCarwasherId(0);
				responseCode = 200;
				resMsg = "no user find with phone number";
			}
		} else {
			responseCode = 400;
			resMsg = "login validation failed";
		}
		shop.setPassword(null);
		responseMap.put(BuyGConstants.DATA_STRING, gson.toJson(shop));
		responseMap.put(BuyGConstants.RESPONSE_CODE_STRING, responseCode);
		responseMap.put(BuyGConstants.RESPONSE_MSG, resMsg);
		return responseMap;
	}
	/*
	 * public Map<String, Object> getAddressForshopId(Integer shopId) {
	 * Map<String, Object> responseMap = new HashMap<>(); int responseCode = 0;
	 * CarwasherAddress shopAddress = null; if (shopId != null) {
	 * CarwasherEntity shopEntity = new CarwasherEntity();
	 * shopEntity.setShopId(shopId); CarwasherAddressEntity add =
	 * carwasherAddressRepository.findByShopEntity(shopEntity); shopAddress =
	 * new CarwasherAddress(); shopAddress.setAddressId(add.getAddressId());
	 * shopAddress.setAddressLine(add.getAddressLine());
	 * shopAddress.setCity(add.getCity());
	 * shopAddress.setShopName(add.getShopName());
	 * shopAddress.setLocality(add.getLocality());
	 * shopAddress.setLongitude(add.getLongitude());
	 * shopAddress.setLatitude(add.getLatitude());
	 * shopAddress.setPincode(add.getPincode()); responseCode = 2; }
	 * responseMap.put(BuyGConstants.DATA_STRING, shopAddress);
	 * responseMap.put(BuyGConstants.RESPONSE_CODE_STRING, responseCode); return
	 * responseMap; }
	 */

	/*
	 * public Map<String, Object> updateShop(Carwasher shop, Integer id) {
	 * Map<String, Object> responseMap = new HashMap<>(); int responseCode = 0;
	 * CarwasherEntity shopEntity = carwasherRepository.findByShopId(id); if
	 * (shopEntity != null) { if (shop.getOwnerName() != null)
	 * shopEntity.setOwnerName(shop.getOwnerName()); if (shop.getPhoneNumber()
	 * != null) shopEntity.setPhoneNumber(shop.getPhoneNumber());
	 * shopEntity.setShopId(id); carwasherRepository.save(shopEntity);
	 * responseCode = 2; } responseMap.put(BuyGConstants.DATA_STRING,
	 * shopEntity); responseMap.put(BuyGConstants.RESPONSE_CODE_STRING,
	 * responseCode); return responseMap; }
	 */

	/*
	 * public Map<String, Object> updatePassword(CommonBean bean, Integer id) {
	 * Map<String, Object> responseMap = new HashMap<>(); int responseCode = 0;
	 * CarwasherEntity shopEntity = carwasherRepository.findByShopId(id); try {
	 * if (bean.getOldPassword() != null && bean.getNewPassword() != null) {
	 * responseCode = 1; if (shopEntity != null) { String oldPassword =
	 * shopEntity.getPassword(); if (bean.getOldPassword().equals(oldPassword))
	 * { shopEntity.setPassword(bean.getNewPassword());
	 * shopEntity.setShopId(id); carwasherRepository.saveAndFlush(shopEntity);
	 * responseCode = 2; } } } } catch (Exception e) { e.printStackTrace(); }
	 * responseMap.put(BuyGConstants.DATA_STRING, shopEntity);
	 * responseMap.put(BuyGConstants.RESPONSE_CODE_STRING, responseCode); return
	 * responseMap; }
	 */
}
