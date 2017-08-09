package ar.com.todopago.api.operations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import ar.com.todopago.api.ElementNames;
import ar.com.todopago.api.exceptions.ResponseException;
import ar.com.todopago.api.model.NotificationPushBSA;


public class NotificationPushParser extends OperationsParser {
	
	private final static Logger logger = Logger.getLogger(NotificationPushParser.class.getName());

	public static JSONObject generateNotificationPushJson(NotificationPushBSA notificationPush) {
		
		JSONObject notificationPushJson = new JSONObject();
		try {
			JSONObject generalDataJson = parseMapToJson(notificationPush.getGeneralData());
			JSONObject operationDataJson = parseMapToJson(notificationPush.getOperationData());
			JSONObject tokenizationDataJson = parseMapToJson(notificationPush.getTokenizationData());
			
			notificationPushJson.put(ElementNames.BSA_GENERAL_DATA,   generalDataJson);
			notificationPushJson.put(ElementNames.BSA_OPERATION_DATA, operationDataJson);
			notificationPushJson.put(ElementNames.BSA_TOKENIZATION_DATA, tokenizationDataJson);

		} catch (JSONException e) {
			logger.log(Level.SEVERE, "Error on create notificationPushJson ", e.getMessage() + " - " + e.getLocalizedMessage());
		}
		return notificationPushJson;
	}

	public static NotificationPushBSA parseJsonToNotificationPush(InputStream inputStream) throws ResponseException, IOException {

		NotificationPushBSA notificationPushBSA = new NotificationPushBSA();
		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
		String output = br.readLine();
		try {
			notificationPushBSA = parseInputStreamJsonToNotificationPush (new JSONObject(output));
		} catch (JSONException e) {
			throw new ResponseException(e.getMessage());
		}
		return notificationPushBSA;
	}

	private static NotificationPushBSA parseInputStreamJsonToNotificationPush(JSONObject jsonObject)
			throws ResponseException, JSONException, IOException {

		NotificationPushBSA notificationPushBSA = new NotificationPushBSA();
		Map<String, Object> notificationPushBSAMap = parseJsonToMap(jsonObject);
		notificationPushBSA.setStatusCode((String)notificationPushBSAMap.get(ElementNames.BSA_STATUS_CODE));
		notificationPushBSA.setStatusMessage((String)notificationPushBSAMap.get(ElementNames.BSA_STATUS_MESSAGE));

		return notificationPushBSA;
	}

}
