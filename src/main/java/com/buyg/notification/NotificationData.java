package com.buyg.notification;

import com.google.gson.annotations.SerializedName;

public class NotificationData {
	@SerializedName("body")
	private String mDetail;

	@SerializedName("title")
	private String mTitle;

	@SerializedName("sound")
	private String sound;

	@SerializedName("orderId")
	private String orderId;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getSound() {
		return sound;
	}

	public void setSound(String sound) {
		this.sound = sound;
	}

	public String getDetail() {
		return mDetail;
	}

	public void setDetail(String detail) {
		mDetail = detail;
	}

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String title) {
		mTitle = title;
	}

}
