package com.buyg.notification;

import com.google.gson.annotations.SerializedName;

public class NotificationRequestModel {
	@SerializedName("notification")
	private NotificationData mData;
	@SerializedName("registration_ids")
	private String mTo[];

	public NotificationData getData() {
		return mData;
	}

	public void setData(NotificationData data) {
		mData = data;
	}

	public String[] getTo() {
		return mTo;
	}

	public void setTo(String[] to) {
		mTo = to;
	}
}
