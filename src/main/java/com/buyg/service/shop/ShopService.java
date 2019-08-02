package com.buyg.service.shop;

import static java.util.Objects.nonNull;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buyg.beans.CommonBean;
import com.buyg.beans.Carwasher;
import com.buyg.beans.CarwasherAddress;
import com.buyg.entity.CarwasherAddressEntity;
import com.buyg.entity.CarwasherEntity;
import com.buyg.repository.shop.ShopAddressRepository;
import com.buyg.repository.shop.ShopRepository;
import com.buyg.utils.BuyGConstants;
import com.buyg.validations.ShopValidation;

@Service
public class ShopService {
	@Autowired
	private ShopValidation shopValidation;

	@Autowired
	private ShopRepository shopRepository;

	@Autowired
	private ShopAddressRepository shopAddressRepository;

	public Map<String, Object> signUp(Carwasher shop) {
		Map<String, Object> responseMap = new HashMap<>();
		int responseCode = 0;
		CarwasherEntity savedShopEntity = null;
		if (shopValidation.validateShopForSignUp(shop)) {
			CarwasherEntity se = shopRepository.findByEmail(shop.getEmail());
			responseCode = 1;
			if (se == null) {
				CarwasherEntity shopEntity = new CarwasherEntity();
				shopEntity.setEmail(shop.getEmail());
				shopEntity.setOwnerName(shop.getOwnerName());
				shopEntity.setPhoneNumber(shop.getPhoneNumber());
				shopEntity.setPassword(shop.getPassword());
				savedShopEntity = shopRepository.saveAndFlush(shopEntity);
				responseCode = 2;
			}
		}
		responseMap.put(BuyGConstants.DATA_STRING, savedShopEntity);
		responseMap.put(BuyGConstants.RESPONSE_CODE_STRING, responseCode);
		return responseMap;
	}

	public Map<String, Object> doLogin(Carwasher shop) {
		Map<String, Object> responseMap = new HashMap<>();
		int responseCode = 0;
		if (shop != null) {
			String email = shop.getEmail();
			String password = shop.getPassword();
			if (nonNull(email) && nonNull(password)) {
				responseCode = 1;
				CarwasherEntity shopEntity = shopRepository.findByEmailAndPassword(email, password);
				if (shopEntity != null) {
					shop.setShopId(shopEntity.getShopId());
					shop.setOwnerName(shopEntity.getOwnerName());
					shop.setPhoneNumber(shopEntity.getPhoneNumber());
					shop.setRegistrationDate(shopEntity.getRegistrationDate());
					shop.setLastUpdateDate(shopEntity.getLastUpdateDate());
					CarwasherAddressEntity sAddEntity = shopEntity.getShopAddressEnitiy();
					CarwasherAddress sAdd = null;
					if (sAddEntity != null) {
						sAdd = new CarwasherAddress();
						sAdd.setAddressId(sAddEntity.getAddressId());
						sAdd.setAddressLine(sAddEntity.getAddressLine());
						sAdd.setLocality(sAddEntity.getLocality());
						sAdd.setCity(sAddEntity.getCity());
						sAdd.setState(sAddEntity.getState());
						sAdd.setPincode(sAddEntity.getPincode());
						sAdd.setLatitude(sAddEntity.getLatitude());
						sAdd.setLongitude(sAddEntity.getLongitude());
					}
					shop.setAddress(sAdd);
					responseCode = 2;
				}
			}
		}
		shop.setPassword(null);
		responseMap.put(BuyGConstants.DATA_STRING, shop);
		responseMap.put(BuyGConstants.RESPONSE_CODE_STRING, responseCode);
		return responseMap;
	}

	public Map<String, Object> saveAddress(CarwasherAddress shopAddress) {
		Map<String, Object> responseMap = new HashMap<>();
		int responseCode = 0;
		CarwasherAddressEntity add = null;
		try {
			if (shopValidation.validateShopAdddress(shopAddress)) {
				responseCode = 2;
				CarwasherAddressEntity addressEntity = new CarwasherAddressEntity();
				addressEntity.setShopName(shopAddress.getShopName());
				addressEntity.setAddressLine(shopAddress.getAddressLine());
				addressEntity.setLocality(shopAddress.getLocality());
				addressEntity.setCity(shopAddress.getCity());
				addressEntity.setState(shopAddress.getState());
				addressEntity.setPincode(shopAddress.getPincode());
				addressEntity.setLongitude(shopAddress.getLongitude());
				addressEntity.setLatitude(shopAddress.getLatitude());
				CarwasherEntity shop = new CarwasherEntity();
				shop.setShopId(shopAddress.getShop().getShopId());
				addressEntity.setShopEntity(shop);
				add = shopAddressRepository.saveAndFlush(addressEntity);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		responseMap.put(BuyGConstants.DATA_STRING, add);
		responseMap.put(BuyGConstants.RESPONSE_CODE_STRING, responseCode);
		return responseMap;
	}

	public Map<String, Object> getAddressForshopId(Integer shopId) {
		Map<String, Object> responseMap = new HashMap<>();
		int responseCode = 0;
		CarwasherAddress shopAddress = null;
		if(shopId != null) {
			CarwasherEntity shopEntity = new CarwasherEntity();
			shopEntity.setShopId(shopId);
			CarwasherAddressEntity add = shopAddressRepository.findByShopEntity(shopEntity);
			shopAddress = new CarwasherAddress();
			shopAddress.setAddressId(add.getAddressId());
			shopAddress.setAddressLine(add.getAddressLine());
			shopAddress.setCity(add.getCity());
			shopAddress.setShopName(add.getShopName());
			shopAddress.setLocality(add.getLocality());
			shopAddress.setLongitude(add.getLongitude());
			shopAddress.setLatitude(add.getLatitude());
			shopAddress.setPincode(add.getPincode());
			responseCode = 2;
		}
		responseMap.put(BuyGConstants.DATA_STRING, shopAddress);
		responseMap.put(BuyGConstants.RESPONSE_CODE_STRING, responseCode);
		return responseMap;
	}

	
	public void exampleFunction() {
//		@DeleteMapping("/students/{id}")
//		public void deleteStudent(@PathVariable long id) {
//			studentRepository.deleteById(id);
//		}
	}

	public Map<String, Object> updateShop(Carwasher shop, Integer id) {
		Map<String, Object> responseMap = new HashMap<>();
		int responseCode = 0;
		CarwasherEntity shopEntity = shopRepository.findByShopId(id);
		if(shopEntity != null) {
			if(shop.getOwnerName() != null)
				shopEntity.setOwnerName(shop.getOwnerName());
			if(shop.getPhoneNumber() != null)
				shopEntity.setPhoneNumber(shop.getPhoneNumber());
			shopEntity.setShopId(id);
			shopRepository.save(shopEntity);
			responseCode = 2;
		}
		responseMap.put(BuyGConstants.DATA_STRING, shopEntity);
		responseMap.put(BuyGConstants.RESPONSE_CODE_STRING, responseCode);
		return responseMap;
	}
	
	public Map<String, Object> updatePassword(CommonBean bean,Integer id) {
		Map<String, Object> responseMap = new HashMap<>();
		int responseCode = 0;
		CarwasherEntity shopEntity = shopRepository.findByShopId(id);
		try {
			if (bean.getOldPassword() != null && bean.getNewPassword() != null) {
				responseCode = 1;
				if (shopEntity != null) {
					String oldPassword = shopEntity.getPassword();
					if (bean.getOldPassword().equals(oldPassword)) {
						shopEntity.setPassword(bean.getNewPassword());
						shopEntity.setShopId(id);
						shopRepository.saveAndFlush(shopEntity);
						responseCode = 2;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		responseMap.put(BuyGConstants.DATA_STRING, shopEntity);
		responseMap.put(BuyGConstants.RESPONSE_CODE_STRING, responseCode);
		return responseMap;
	}
}
