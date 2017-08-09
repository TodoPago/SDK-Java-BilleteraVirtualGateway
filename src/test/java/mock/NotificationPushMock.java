package mock;

import java.util.HashMap;
import java.util.Map;

import ar.com.todopago.api.ElementNames;
import ar.com.todopago.api.model.NotificationPushBSA;

public class NotificationPushMock {
	
	public static NotificationPushBSA getNotificationPushOKParameters(){
		
		return getNotificationPushParameters("123",BSAParametersMock.security);
	}
	
	public static NotificationPushBSA getNotificationPushErrorParameters(){
		
		return getNotificationPushParameters("",BSAParametersMock.security);
	}
	
	public static NotificationPushBSA getOkResponse(){
		
		return getResponse("-1","OK");
	}
	
	public static NotificationPushBSA getErrorResponse(){
		
		return getResponse("1014","Completá este campo.");
	}
	
	/*---- Auxiliary methods ----*/
	private static NotificationPushBSA getNotificationPushParameters(String merchant,String security){
		NotificationPushBSA notificationPush=new NotificationPushBSA();
		
		Map <String, Object> generalData = new HashMap<String, Object>();
	    generalData.put(ElementNames.BSA_MERCHANT,merchant);
	    generalData.put(ElementNames.BSA_SECURITY,security);
	    generalData.put(ElementNames.BSA_REMOTE_IP_ADDRESS, "192.168.11.87");
	    generalData.put(ElementNames.BSA_PUBLIC_REQUEST_KEY, "f50208ea-be00-4519-bf85-035e2733d09e");
	    generalData.put(ElementNames.BSA_OPERATION_NAME, "Compra");

	    Map <String, Object> operationData = new HashMap<String, Object>();
	    operationData.put(ElementNames.BSA_RESULT_CODE_MEDIOPAGO, "-1");
	    operationData.put(ElementNames.BSA_RESULT_CODE_GATEWAY, "-1");
	    operationData.put(ElementNames.BSA_ID_GATEWAY, "8");
	    operationData.put(ElementNames.BSA_RESULT_MESSAGE, "Aprobada");
	    operationData.put(ElementNames.BSA_OPERATION_DATE_TIME, "201607040857364");
	    operationData.put(ElementNames.BSA_TICKET_MUNBER, "7866463542424");
	    operationData.put(ElementNames.BSA_CODIGO_AUTORIZATION, "455422446756567");
	    operationData.put(ElementNames.BSA_CURRENCY_CODE, "032");
	    operationData.put(ElementNames.BSA_OPERATION_ID, "1234");
	    operationData.put(ElementNames.BSA_AMOUNT, "999,99");
	    operationData.put(ElementNames.BSA_FACILITIES_PAYMENT, "03");

	    Map <String, Object> tokenizationData = new HashMap<String, Object>();
	    tokenizationData.put(ElementNames.BSA_PUBLIC_TOKENIZATION_FIELD, "sydguyt3e862t76ierh76487638rhkh7");
	    tokenizationData.put(ElementNames.BSA_CREDENTIAL_MASK, "4510XXXXX00001");
	    
	    notificationPush.setGeneralData(generalData);
	    notificationPush.setOperationData(operationData);
	    notificationPush.setTokenizationData(tokenizationData);
	    
	    return notificationPush;
	}
	
	private static NotificationPushBSA getResponse(String statusCode,String statusMessage){
		
		NotificationPushBSA notification=new NotificationPushBSA();
		notification.setStatusCode(statusCode);
		notification.setStatusMessage(statusMessage);
		
		return notification;
	}
	
}