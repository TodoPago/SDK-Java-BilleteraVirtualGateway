package main;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import ar.com.todopago.api.ElementNames; 
import ar.com.todopago.api.TodoPagoConector;
import ar.com.todopago.api.exceptions.ConnectionException;
import ar.com.todopago.api.exceptions.EmptyFieldException;
import ar.com.todopago.api.exceptions.EmptyFieldPassException;
import ar.com.todopago.api.exceptions.InvalidFieldException;
import ar.com.todopago.api.exceptions.ResponseException;
import ar.com.todopago.api.model.NotificationPushBSA;
import ar.com.todopago.api.model.PaymentMethodsBSA;
import ar.com.todopago.api.model.TransactionBSA;
import ar.com.todopago.api.model.User;

/**
 *
 */
public class Test {

	// Verticales para CS
	public final static int RETAIL = 0;
	public static int vertical = RETAIL;// Configurar vertical a usar
	private final static Logger logger = Logger.getLogger(Test.class.getName());

	public static final String APIKEY = "TODOPAGO 0b27699db496431a8391096be5ffc";
	public static final String MERCHANT = "157";
	public static final String SECURITY = "0b27699db496431a8391096be5ffc";
	

	public static void main(String[] args) throws MalformedURLException, InvalidFieldException {

		boolean overrideSSL = true;

		// Production
		// TodoPagoConector tpc = new
		// TodoPagoConector(TodoPagoConector.productionEndpoint,
		// getAuthorization());

		// Developer
		 TodoPagoConector tpc = new TodoPagoConector(TodoPagoConector.QAEndpointLocal, getAuthorization());

		// Developer without APYKey
		// TodoPagoConector tpc = new
		// TodoPagoConector(TodoPagoConector.developerEndpoint);

		System.out.println("------------ GET CREDENTIALS ---------");
		getCredentials(tpc);

		System.out.println("------------ DiscoverPaymentMethod ---------");
		discoverPaymentMethodBSA(tpc);

		System.out.println("------------ Transaction ---------");
		transactionBSA(tpc);

		System.out.println("------------ Notification Push ---------");
	    notificationPushBSA(tpc);
	

	}

	private static void printMap(Map<String, Object> pr, String tab) {

		if (pr != null) {
			List<String> keys = new ArrayList<String>();
			keys.addAll(pr.keySet());

			for (int i = 0; i < keys.size(); i++) {
				Map<String, Object> aux = new HashMap<String, Object>();
				Object tmp = pr.get(keys.get(i));

				if (tmp != null && tmp.getClass().isInstance(aux)) {
					System.out.println(tab + "- " + keys.get(i));
					aux = (Map<String, Object>) (tmp);
					printMap(aux, tab + "  ");

				} else {
					System.out.println(tab + "- " + keys.get(i) + " : " + pr.get(keys.get(i)));
				}
			}
		}
	}


	
	private static void getCredentials(TodoPagoConector tpc) {

		User user = new User();
		try {

			user = tpc.getCredentials(getUser());
			tpc.setAuthorize(getAuthorization(user));

		} catch (EmptyFieldException e) {
			logger.log(Level.WARNING, e.getMessage());
		} catch (MalformedURLException e) {
			logger.log(Level.WARNING, e.getMessage());
		} catch (ResponseException e) {
			logger.log(Level.WARNING, e.getMessage());
		} catch (ConnectionException e) {
			logger.log(Level.WARNING, e.getMessage());
		}

		System.out.println(user.toString());
	}

	private static User getUser() {
		String mail = "test@test.com.ar"; // The email is only as example
		String pass = "test1234"; // The pass is only as example
		User user = new User(mail, pass);
		return user;
	}

	private static void discoverPaymentMethodBSA(TodoPagoConector tpc) {

		PaymentMethodsBSA paymentMethodBSA = new PaymentMethodsBSA();
		try {
			paymentMethodBSA = tpc.discoverPaymentMethodBSA();
			if (paymentMethodBSA.getPaymentMethodsBSAList() != null) {
				System.out.println(paymentMethodBSA.getPaymentMethodsBSAList().toString());
			}
		} catch (ResponseException e) {
			logger.log(Level.WARNING, e.getMessage());
		} catch (ConnectionException e) {
			logger.log(Level.WARNING, e.getMessage());
		}

	}

	private static void transactionBSA(TodoPagoConector tpc) {

		TransactionBSA transactionBSA = new TransactionBSA();
		try {
			transactionBSA = tpc.transaction(getTransactionBSA());
			printMap(transactionBSA.getTransactionResponse(), "");
		} catch (EmptyFieldPassException e) {
			logger.log(Level.WARNING, e.getMessage());
		}
		catch (InvalidFieldException e) {
			logger.log(Level.WARNING, e.getMessage());
		}catch (ResponseException e) {
			logger.log(Level.WARNING, e.getMessage());
		} catch (ConnectionException e) {
			logger.log(Level.WARNING, e.getMessage());
		}
	}

	private static TransactionBSA getTransactionBSA() {

		TransactionBSA transactionBSA = new TransactionBSA();

		Map<String, Object> generalData = new HashMap<String, Object>();
		generalData.put(ElementNames.BSA_MERCHANT, "41702");
		generalData.put(ElementNames.BSA_SECURITY, "TODOPAGO 8A891C0676A25FBF052D1C2FFBC82DEE");
		generalData.put(ElementNames.BSA_OPERATION_DATE_TIME, "20160425155613");
		generalData.put(ElementNames.BSA_REMOTE_IP_ADDRESS, "192.168.11.87");

		Map<String, Object> operationData = new HashMap<String, Object>();
		operationData.put(ElementNames.BSA_OPERATION_TYPE, "Compra");
		operationData.put(ElementNames.BSA_OPERATION_ID, "1234");
		operationData.put(ElementNames.BSA_CURRENCY_CODE, "032");
		operationData.put(ElementNames.BSA_CONCEPT, "compra");
		operationData.put(ElementNames.BSA_AMOUNT, "999,99");

		List<String> list = new ArrayList<String>();
		list.add("1");
		list.add("42");
		operationData.put(ElementNames.BSA_AVAILABLE_PAYMENT_METHODS, list);
		operationData.put(ElementNames.BSA_AVAILABLE_BANKS, list);
		Map<String, Object> buyerPreselection = new HashMap<String, Object>();
		buyerPreselection.put(ElementNames.BSA_PAYMENT_METHODS_ID, "42");
		buyerPreselection.put(ElementNames.BSA_BANK_id, "11");
		operationData.put(ElementNames.BSA_BUYER_PRESELECTION, buyerPreselection);

		Map<String, Object> technicalData = new HashMap<String, Object>();
		technicalData.put(ElementNames.BSA_SDK, "Java");
		technicalData.put(ElementNames.BSA_SDK_VERSION, "2.0");
		technicalData.put(ElementNames.BSA_LANGUAGE_VERSION, "1.8");
		technicalData.put(ElementNames.BSA_PLUGIN_VERSION, "2.1");
		technicalData.put(ElementNames.BSA_ECOMMERCE_NAME, "bla");
		technicalData.put(ElementNames.BSA_ECOMMERCE_VERSION, "3.1");
		technicalData.put(ElementNames.BSA_CM_VERSION, "2.4");

		transactionBSA.setGeneralData(generalData);
		transactionBSA.setOperationData(operationData);
		transactionBSA.setTechnicalData(technicalData);

		return transactionBSA;
	}
	
	
	
	private static void notificationPushBSA(TodoPagoConector tpc) throws InvalidFieldException{
        NotificationPushBSA notificationPushBSA = new NotificationPushBSA();

        try{
             notificationPushBSA = tpc.notificationPush(getNotificationPushBSA());
             printMap(notificationPushBSA.toMap(), "");

 		} catch (EmptyFieldPassException e) {
			logger.log(Level.WARNING, e.getMessage());
		} catch (ResponseException e) {
			logger.log(Level.WARNING, e.getMessage());
		} catch (ConnectionException e) {
			logger.log(Level.WARNING, e.getMessage());
		}
	}
        
    
    private static NotificationPushBSA getNotificationPushBSA() {

    	Map <String, Object> generalData = new HashMap<String, Object>();

        generalData.put(ElementNames.BSA_MERCHANT, "41702");
        generalData.put(ElementNames.BSA_SECURITY, "TODOPAGO 8A891C0676A25FBF052D1C2FFBC82DEE");
        generalData.put(ElementNames.BSA_REMOTE_IP_ADDRESS, "192.168.11.87");
        generalData.put(ElementNames.BSA_PUBLIC_REQUEST_KEY, "3fc8dcee-dd46-40f2-a178-0f74f01221eb");
        generalData.put(ElementNames.BSA_OPERATION_NAME, "Compra");
        
    	Map <String, Object> operationData = new HashMap<String, Object>();
    	operationData.put(ElementNames.BSA_RESULT_CODE_MEDIOPAGO, "-1");
        operationData.put(ElementNames.BSA_RESULT_CODE_GATEWAY, "-1");
        operationData.put(ElementNames.BSA_ID_GATEWAY, "8");
        operationData.put(ElementNames.BSA_RESULT_MESSAGE, "Aprobada");
        operationData.put(ElementNames.BSA_OPERATION_DATE_TIME, "20170704085736");
        operationData.put(ElementNames.BSA_TICKET_MUNBER, "1231122");
        operationData.put(ElementNames.BSA_CODIGO_AUTORIZATION, "45007799");
        operationData.put(ElementNames.BSA_CURRENCY_CODE, "032");
        operationData.put(ElementNames.BSA_OPERATION_ID, "1234");
        operationData.put(ElementNames.BSA_AMOUNT, "20,12");
        operationData.put(ElementNames.BSA_FACILITIES_PAYMENT, "03");

        
    	Map <String, Object> tokenizationData = new HashMap<String, Object>();
        tokenizationData.put(ElementNames.BSA_PUBLIC_TOKENIZATION_FIELD, "4444444444444444");
        tokenizationData.put(ElementNames.BSA_CREDENTIAL_MASK, "4510XXXXX00001");
        
        NotificationPushBSA notificationPushBSA = new NotificationPushBSA(generalData, operationData, tokenizationData);
        return notificationPushBSA;
    }

	private static Map<String, List<String>> getAuthorization() {
		Map<String, List<String>> parameters = new HashMap<String, List<String>>();
		parameters.put(ElementNames.Authorization, Collections.singletonList(APIKEY));
		// include all aditional Http headers to map, all of them will be used
		return parameters;
	}

	private static Map<String, List<String>> getAuthorization(User user) {
		Map<String, List<String>> parameters = new HashMap<String, List<String>>();
		parameters.put(ElementNames.Authorization, Collections.singletonList(user.getApiKey()));
		// include all aditional Http headers to map, all of them will be used
		return parameters;
	}

}
