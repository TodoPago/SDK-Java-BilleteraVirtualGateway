package ar.com.todopago.api.rest;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

import ar.com.todopago.api.ElementNames;
import ar.com.todopago.api.exceptions.ConnectionException;
import ar.com.todopago.api.exceptions.ResponseException;
import ar.com.todopago.api.model.NotificationPushBSA;
import ar.com.todopago.api.model.PaymentMethodsBSA;
import ar.com.todopago.api.model.TransactionBSA;
import ar.com.todopago.api.operations.NotificationPushParser;
import ar.com.todopago.api.operations.PaymentMethodsBSAParser;
import ar.com.todopago.api.operations.TransactionParser;

public class BSARest extends RestConnector {

	public BSARest(String endpoint, Map<String, List<String>> headder) {
		super(endpoint, headder);
	}

	private final static Logger logger = Logger.getLogger(RestConnector.class.getName());
	private final String BVTP_TRANSACTION = "tx/v1/bsa";
	private final String BVTP_PAYMENTMETHOD_DISCOVER = "discover/api/BSA/paymentMethod/discover";
	private final String BVTP_NOTIFICATION_PUSH = "tx/v1/bsa";
	
	//SOLO PARA QA
	//private final String BVTP_QA = "https://portalqa.visa2.com.ar/t/1.2/api/";
	//private final String BVTP_QALOCAL = "https://127.0.0.1:3000/";
									
	public TransactionBSA transaction(TransactionBSA transaction) throws ConnectionException, ResponseException {
		
		StringBuilder sb = new StringBuilder(endpoint + BVTP_TRANSACTION);
				
		/*		
		if(endpoint.equals(BVTP_QA)){
			sb.replace(0, sb.length(), "https://172.16.2.105/ms/" + BVTP_TRANSACTION);
		}
		
		if(endpoint.equals(BVTP_QALOCAL)){
			sb.replace(0, sb.length(), endpoint + "bsa/transactions/api/" + BVTP_TRANSACTION);
		}
		*/
		
		logger.log(Level.INFO, "URLCreation transaction " + sb.toString());
		
		TransactionBSA result = new TransactionBSA();

		JSONObject data = new JSONObject();
		data = TransactionParser.generateTransactionJson(transaction);

		InputStream is;
		try {
			is = sendPost(sb.toString(), data.toString(), true);
			result = TransactionParser.parseJsonToTransaction(is);

		} catch (IOException e) {
			logger.log(Level.SEVERE, "Error on transaction", e.getMessage() + " - " + e.getLocalizedMessage());
		}

		return result;
	}

	public PaymentMethodsBSA discoverPaymentMethodBSA() throws ConnectionException, ResponseException {

		StringBuilder sb = new StringBuilder(endpoint + BVTP_PAYMENTMETHOD_DISCOVER);
		
		if(endpoint.equals(BVTP_QA)){
			sb.replace(0, sb.length(), "https://172.16.2.105/bsa/discover/api/" + BVTP_PAYMENTMETHOD_DISCOVER);
		}
		
		if(endpoint.equals(BVTP_QALOCAL)){
		//	sb.replace(0, sb.length(), endpoint + "bsa/discover/api/" + BVTP_PAYMENTMETHOD_DISCOVER);
			// http://localhost:3000/bsa/discover/api/BSA/paymentMethod/discover
			sb.replace(0, sb.length(), " http://localhost:3000/bsa/discover/api/"  + BVTP_PAYMENTMETHOD_DISCOVER);
		}
		
		logger.log(Level.INFO, "URLCreation discoverPaymentMethodBSA " + sb.toString());

		PaymentMethodsBSA result = new PaymentMethodsBSA();
		try {
			InputStream is = sendGet(sb.toString(), true);
			result = PaymentMethodsBSAParser.parseJsonToPaymentMethod(is);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Error on discoverPaymentMethodBSA",
					e.getMessage() + " - " + e.getLocalizedMessage());
		}
		return result;
	}
	
	
	public NotificationPushBSA notificationPush(NotificationPushBSA notificationPush) throws ConnectionException, ResponseException {
		
		String publicRequestKey=(String)notificationPush.getGeneralData().get("publicRequestKey");
		String url=BVTP_NOTIFICATION_PUSH + "/" + publicRequestKey;
		StringBuilder sb = new StringBuilder(endpoint + url);
		
		if(endpoint.equals(BVTP_QA)){
			sb.replace(0, sb.length(), "https://172.16.2.105/ms/" + url);
		}
		
		if(endpoint.equals(BVTP_QALOCAL)){
			sb.replace(0, sb.length(), endpoint + "bsa/transactions/api/" + url );
		}
		
		logger.log(Level.INFO, "URLCreation notificacionPush " + sb.toString());

		NotificationPushBSA result = new NotificationPushBSA();

		JSONObject data = new JSONObject();
		data = NotificationPushParser.generateNotificationPushJson(notificationPush);

		InputStream is;
		try {
			is = sendPut(sb.toString(), data.toString(), true);
			result = NotificationPushParser.parseJsonToNotificationPush(is);

		} catch (IOException e) {
			logger.log(Level.SEVERE, "Error on notificacionPush", e.getMessage() + " - " + e.getLocalizedMessage());
		}

		return result;
	}
		

}
