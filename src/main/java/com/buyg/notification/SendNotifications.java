package com.buyg.notification;

import java.lang.reflect.Type;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Service
@SuppressWarnings("deprecation")
public class SendNotifications {
	private static HttpPost postRequest = new HttpPost("https://fcm.googleapis.com/fcm/send");

	private static String apiKey = "AIzaSyDr1uxEaAxmWNd9jct5RWwQpo5NyXyjWVI";

	public void sendNotifications(String title, String details, String[] to) {
		try (DefaultHttpClient httpClient = new DefaultHttpClient();) {
			NotificationRequestModel notificationRequestModel = new NotificationRequestModel();
			NotificationData notificationData = new NotificationData();

			notificationData.setDetail(details);
			notificationData.setTitle(title);
			notificationData.setSound("default");
			notificationRequestModel.setData(notificationData);
			notificationRequestModel.setTo(to);
			notificationRequestModel.setmPriority("high");

			Gson gson = new Gson();
			Type type = new TypeToken<NotificationRequestModel>() {
			}.getType();

			String json = gson.toJson(notificationRequestModel, type);

			StringEntity input = new StringEntity(json);
			input.setContentType("application/json");

			postRequest.addHeader("Authorization", "key=" + apiKey);
			postRequest.setEntity(input);

			HttpResponse response = httpClient.execute(postRequest);

			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
			} else if (response.getStatusLine().getStatusCode() == 200) {

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
