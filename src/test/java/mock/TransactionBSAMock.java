package mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.com.todopago.api.ElementNames;
import ar.com.todopago.api.model.TransactionBSA;

public class TransactionBSAMock {

	public static TransactionBSA getTransactionParameters(String merchant,String security){
		TransactionBSA transactionBSA = new TransactionBSA();

		Map<String, Object> generalData = new HashMap<String, Object>();
		generalData.put(ElementNames.BSA_MERCHANT,merchant);
		generalData.put(ElementNames.BSA_SECURITY,security);
		generalData.put(ElementNames.BSA_OPERATION_DATE_TIME, "201604251556134");
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
	    operationData.put(ElementNames.BSA_AVAILABLE_PAYMENT_BANK, list);

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
	
	public static Map<String,Object> getOKResponse(){
		Map<String,Object> response=new HashMap<String,Object>();
		
		response.put("publicRequestKey","411d188b-2c6d-4d39-977c-b6d9de119c80");
		response.put("merchantId",37581);
		response.put("channel",11);
		
		return response;
	}

	public static Map<String, Object> getErrorResponse() {
		return GenericParametersMock.getErrorGenericResponseWithChannel(1014,"Completá este campo.");
	}
	
	public static Map<String, Object> getError702Response() {
		return GenericParametersMock.getErrorGenericResponseWithChannel(702,"Cuenta de vendedor invalida");
	}
}