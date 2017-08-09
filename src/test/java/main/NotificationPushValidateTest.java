package main;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import ar.com.todopago.api.ElementNames;
import ar.com.todopago.api.exceptions.EmptyFieldPassException;
import ar.com.todopago.api.exceptions.InvalidFieldException;
import ar.com.todopago.api.operations.NotificationPushValidate;

public class NotificationPushValidateTest {
	
	
	private String resultCodeMedioPagoError="dd-1";
	
	private String resultCodeGatewayOK="-1";
	private String resultCodeGatewayError="32w";
	
	private String id_gatewayOK="8";
	private String id_gatewayError="ed3";
	
	private String operationDateTimeOK="20160704085736";
	private String operationDateTimeError="201502282311009999";
	
	private String amountOK="999,99";
	private String amountError="996,9";
	
	@Test
	public void validateGeneralDataTest() throws EmptyFieldPassException, InvalidFieldException{
		NotificationPushValidate npv=new NotificationPushValidate();
		
		npv.validateGeneralData(getGeneralData());
	}
	
	@Test(expected=InvalidFieldException.class)
	public void invalidResultCodeMedioPagoTest() throws EmptyFieldPassException, InvalidFieldException{
		NotificationPushValidate npb=new NotificationPushValidate();
		
		npb.validateOperationData(getOperationData(resultCodeMedioPagoError,resultCodeGatewayOK,id_gatewayOK,operationDateTimeOK,amountOK));
	}
	
	@Test(expected=InvalidFieldException.class)
	public void invalidResultCodeGatewayTest() throws EmptyFieldPassException, InvalidFieldException{
		NotificationPushValidate npb=new NotificationPushValidate();
		
		npb.validateOperationData(getOperationData(resultCodeMedioPagoError,resultCodeGatewayError,id_gatewayOK,operationDateTimeOK,amountOK));
	}
	
	@Test(expected=InvalidFieldException.class)
	public void invalidIDGatewayTest() throws EmptyFieldPassException, InvalidFieldException{
		NotificationPushValidate npb=new NotificationPushValidate();
		
		npb.validateOperationData(getOperationData(resultCodeMedioPagoError,resultCodeGatewayOK,id_gatewayError,operationDateTimeOK,amountOK));
	}
	
	@Test(expected=InvalidFieldException.class)
	public void invalidOperationDateTimeTest() throws EmptyFieldPassException, InvalidFieldException{
		NotificationPushValidate npb=new NotificationPushValidate();
		
		npb.validateOperationData(getOperationData(resultCodeMedioPagoError,resultCodeGatewayOK,id_gatewayOK,operationDateTimeError,amountOK));
	}
	
	@Test(expected=InvalidFieldException.class)
	public void invalidAmountTest() throws EmptyFieldPassException, InvalidFieldException{
		NotificationPushValidate npb=new NotificationPushValidate();
		
		npb.validateOperationData(getOperationData(resultCodeMedioPagoError,resultCodeGatewayOK,id_gatewayOK,operationDateTimeError,amountError));
	}
	
	@Test(expected=InvalidFieldException.class)
	public void specialCharacterOperationDataTest() throws EmptyFieldPassException, InvalidFieldException{
		NotificationPushValidate npb=new NotificationPushValidate();
		
		npb.validateOperationData(getOperationData("-1","-1","&&","20160704085736","45.89"));
	}
	
	
	
	/*---- Auxiliary methods ----*/
	
	private Map<String, Object> getGeneralData(){
		Map <String, Object> generalData = new HashMap<String, Object>();
	    generalData.put(ElementNames.BSA_MERCHANT, "1");
	    generalData.put(ElementNames.BSA_SECURITY, "PRISMA 86333EFD8AD0C71CEA3BF06D7BDEF90D");
	    generalData.put(ElementNames.BSA_REMOTE_IP_ADDRESS, "192.168.11.87");
	    generalData.put(ElementNames.BSA_PUBLIC_REQUEST_KEY, "f50208ea-be00-4519-bf85-035e2733d09e");
	    generalData.put(ElementNames.BSA_OPERATION_NAME, "Compra");
	    
	    return generalData;
	}
	
	private Map<String, Object> getOperationData(String resultCodeMedioPago,String resultCodeGateway,String id_gateway,String operationDateTime,String amount) {
		Map <String, Object> operationData = new HashMap<String, Object>();
	    operationData.put(ElementNames.BSA_RESULT_CODE_MEDIOPAGO, resultCodeMedioPago);
	    operationData.put(ElementNames.BSA_RESULT_CODE_GATEWAY, resultCodeGateway);
	    operationData.put(ElementNames.BSA_ID_GATEWAY, id_gateway);
	    operationData.put(ElementNames.BSA_RESULT_MESSAGE, "Aprobada");
	    operationData.put(ElementNames.BSA_OPERATION_DATE_TIME,operationDateTime);
	    operationData.put(ElementNames.BSA_TICKET_MUNBER, "7866463542424");
	    operationData.put(ElementNames.BSA_CODIGO_AUTORIZATION, "455422446756567");
	    operationData.put(ElementNames.BSA_CURRENCY_CODE, "032");
	    operationData.put(ElementNames.BSA_OPERATION_ID, "1234");
	    operationData.put(ElementNames.BSA_AMOUNT,amount);
	    operationData.put(ElementNames.BSA_FACILITIES_PAYMENT, "03");
		return operationData;
	}
}