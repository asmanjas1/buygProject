package com.buyg.validations;

import static com.buyg.utils.CommonUtils.isNullOrEmpty;
import static java.util.Objects.nonNull;

import org.springframework.stereotype.Service;

import com.buyg.beans.Carwasher;
import com.buyg.beans.CarwasherAddress;

@Service
public class ShopValidation {

	public boolean validateShopForSignUp(Carwasher shop) {
		if (nonNull(shop) && !isNullOrEmpty(shop.getEmail()) && !isNullOrEmpty(shop.getOwnerName())
				&& !isNullOrEmpty(shop.getPassword()) && !isNullOrEmpty(String.valueOf(shop.getPhoneNumber()))) {
			return true;
		}
		return false;
	}

	public boolean validateShopAdddress(CarwasherAddress shopAddress) {
		if (nonNull(shopAddress) && !isNullOrEmpty(shopAddress.getAddressLine())
				&& !isNullOrEmpty(shopAddress.getLocality()) && !isNullOrEmpty(shopAddress.getCity())
				&& !isNullOrEmpty(shopAddress.getState()) && nonNull(shopAddress.getPincode())) {
			return true;
		}
		return false;
	}
}
