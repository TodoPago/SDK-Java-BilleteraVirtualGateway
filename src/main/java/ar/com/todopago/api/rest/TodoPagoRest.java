package ar.com.todopago.api.rest;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import ar.com.todopago.api.ElementNames;
import ar.com.todopago.api.exceptions.ConnectionException;
import ar.com.todopago.api.exceptions.ResponseException;
import ar.com.todopago.api.model.User;
import ar.com.todopago.api.operations.PaymentMethodsParser;
import ar.com.todopago.api.operations.UserParser;
import ar.com.todopago.api.operations.OperationsParser;;

public class TodoPagoRest extends RestConnector{

	public TodoPagoRest(String endpoint, Map<String, List<String>> headder) {
		super(endpoint, headder);
	}
	
	private final static Logger logger = Logger.getLogger(RestConnector.class.getName());
	
    private final String AUTHORIZE = "Authorize";
    private final String CREDENTIALS = "Credentials";	
    private final String OPERATIONS_GET_BY_RANGE_DATE_TIME = "Operations/GetByRangeDateTime/";
    private final String PAYMENT_METHODS_GET = "PaymentMethods/Get/";
    private final String OPERATIONS_GET_BY_OPERATION_ID = "Operations/GetByOperationId/";  
    private final String PAYMENT_METHODS_DISCOVER = "PaymentMethods/Discover";


	public User getCredentials(User user) throws ResponseException, ConnectionException {

		String url = endpoint + CREDENTIALS;
		url = url.replace("t/1.1/", "");
		url = url.replace("t/1.2/", "");
		logger.log(Level.INFO, "URLCreation getCredentials " + url);
		User userResponse = new User();

		try {
			
			JSONObject data = new JSONObject();
			data.put(ElementNames.USUARIO, user.getUser());
			data.put(ElementNames.CLAVE, user.getPassword());

			InputStream is = sendPost(url, data.toString(), false);
			userResponse = UserParser.parseInputStreamJsonToUser(is);

			} catch (JSONException e) {
				logger.log(Level.SEVERE, "Error on getCredentials", e.getMessage() + " - " + e.getLocalizedMessage());
				e.printStackTrace();
			} catch (IOException e) {
				logger.log(Level.SEVERE, "Error on getCredentials", e.getMessage() + " - " + e.getLocalizedMessage());
				e.printStackTrace();
			} 
		
		return userResponse;
	}



}
