package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import ar.com.todopago.api.ElementNames;
import ar.com.todopago.api.exceptions.EmptyFieldPassException;
import ar.com.todopago.api.exceptions.InvalidFieldException;
import ar.com.todopago.api.operations.TransactionValidate;

public class TransactionValidateTest {
	
	///////General Data
	private String merchantOk="35";
	private String merchantError="p34";
	private String operationDateTimeOK="20160704085736";
	private String operationDateTimeError="201502282311009999";
	private String ipOK="1.1.1.1";
	private String ipError="10.10";
	///////Operation Data
	private String amountOK="999,99";
	private String amountError="996,9";
	
	private String paymentMethodIDOK="42";
	private String paymentMethodIDError="43scd";
	private String bankIDOK="11";
	private String bankIDError="11hdgcd";

	public void validateGeneralDataOKTest() throws EmptyFieldPassException, InvalidFieldException
	{
		TransactionValidate tv=new TransactionValidate();
		
		tv.validateGeneralData(getGeneralData(merchantOk,operationDateTimeOK,ipOK));	
	}
	
	@Test(expected=EmptyFieldPassException.class)
	public void EmptyFieldGeneralDataTest() throws EmptyFieldPassException, InvalidFieldException
	{
		TransactionValidate tv=new TransactionValidate();
		
		tv.validateGeneralData(getGeneralData("",operationDateTimeOK,ipOK));	
	}
	
	@Test(expected=InvalidFieldException.class)
	public void specialCharacterGeneralDataTest() throws EmptyFieldPassException, InvalidFieldException
	{
		TransactionValidate tv=new TransactionValidate();
		
		tv.validateGeneralData(getGeneralData("12&???",operationDateTimeOK,ipOK));	
	}
	
	@Test(expected=InvalidFieldException.class)
	public void invalidateMerchantGeneralDataTest() throws EmptyFieldPassException, InvalidFieldException
	{
		TransactionValidate tv=new TransactionValidate();
		
		tv.validateGeneralData(getGeneralData(merchantError,operationDateTimeOK,ipOK));	
	}
	
	@Test(expected=InvalidFieldException.class)
	public void invalidOperationDateTimeGeneralDataTest() throws EmptyFieldPassException, InvalidFieldException
	{
		TransactionValidate tv=new TransactionValidate();
		
		tv.validateGeneralData(getGeneralData(merchantOk,operationDateTimeError,ipOK));	
	}
	
	@Test(expected=InvalidFieldException.class)
	public void invalidIPAdressGeneralDataTest() throws EmptyFieldPassException, InvalidFieldException
	{
		TransactionValidate tv=new TransactionValidate();
		
		tv.validateGeneralData(getGeneralData(merchantOk,operationDateTimeOK,ipError));	
	}
	
	@Test
	public void operationDataOKTest() throws EmptyFieldPassException, InvalidFieldException
	{
		TransactionValidate tv=new TransactionValidate();
		
		tv.validateOperationData(operationData(amountOK,availablePaymentMethodsOK(),availablePaymentMethodsOK(),paymentMethodIDOK,bankIDOK));	
	}
	
	@Test(expected=InvalidFieldException.class)
	public void invalidAmountOperationDataOKTest() throws EmptyFieldPassException, InvalidFieldException
	{
		TransactionValidate tv=new TransactionValidate();
		
		tv.validateOperationData(operationData(amountError,availablePaymentMethodsOK(),availablePaymentMethodsOK(),paymentMethodIDOK,bankIDOK));	
	}
	
	@Test(expected=InvalidFieldException.class)
	public void invalidAvailablePaymentMethodIDOperationDataTest() throws EmptyFieldPassException, InvalidFieldException
	{
		TransactionValidate tv=new TransactionValidate();
		
		tv.validateOperationData(operationData(amountOK,availablePaymentMethodsOK(),availablePaymentMethodsOK(),paymentMethodIDError,bankIDOK));
	}
	
	@Test(expected=InvalidFieldException.class)
	public void invalidAvailableBankOperationDataTest() throws EmptyFieldPassException, InvalidFieldException
	{
		TransactionValidate tv=new TransactionValidate();
		
		tv.validateOperationData(operationData(amountOK,availablePaymentMethodsOK(),availablePaymentMethodsOK(),paymentMethodIDOK,bankIDError));
	}

	@Test(expected=InvalidFieldException.class)
	public void invalidAvailablePaymentMethodsTest() throws EmptyFieldPassException, InvalidFieldException
	{
		TransactionValidate tv=new TransactionValidate();
		
		tv.validateOperationData(operationData(amountOK,availablePaymentMethodsError(),availablePaymentMethodsOK(),paymentMethodIDOK,bankIDOK));
	}
	
	@Test(expected=InvalidFieldException.class)
	public void invalidAvailableBanksTest() throws EmptyFieldPassException, InvalidFieldException
	{
		TransactionValidate tv=new TransactionValidate();
		
		tv.validateOperationData(operationData(amountOK,availablePaymentMethodsOK(),availablePaymentMethodsError(),paymentMethodIDOK,bankIDOK));
	}
	
	/*---- Auxiliary methods ----*/
	private Map<String,Object> getGeneralData(String merchant,String operationDateTime,String ip)
	{
		Map<String, Object> generalData = new HashMap<String, Object>();
		generalData.put(ElementNames.BSA_MERCHANT,merchant);
		generalData.put(ElementNames.BSA_SECURITY, "PRISMA 86333EFD8AD0C71CEA3BF06D7BDEF90D");
		generalData.put(ElementNames.BSA_OPERATION_DATE_TIME,operationDateTime);
		generalData.put(ElementNames.BSA_REMOTE_IP_ADDRESS,ip);
		generalData.put(ElementNames.BSA_CHANNEL, "11");
		
		return generalData;
	}
	
	private Map<String, Object> operationData(String amount,List<String> APMethods,List<String> ABanks,String PMID,String bankID) {
		Map<String, Object> operationData = new HashMap<String, Object>();
		operationData.put(ElementNames.BSA_OPERATION_TYPE, "Compra");
		operationData.put(ElementNames.BSA_OPERATION_ID, "1234");
		operationData.put(ElementNames.BSA_CURRENCY_CODE, "032");
		operationData.put(ElementNames.BSA_CONCEPT, "compra");
		operationData.put(ElementNames.BSA_AMOUNT,amount);

		operationData.put(ElementNames.BSA_AVAILABLE_PAYMENT_METHODS, APMethods);
	    operationData.put("availableBanks",ABanks);

	    Map<String,Object> buyerPreselection=new HashMap<String, Object>();
	    buyerPreselection.put(ElementNames.BSA_PAYMENT_METHODS_ID,PMID);
	    buyerPreselection.put(ElementNames.BSA_BANK_id,bankID);
		operationData.put(ElementNames.BSA_BUYER_PRESELECTION, buyerPreselection);
		
		return operationData;
	}
	
	private List<String> availablePaymentMethodsOK()
	{
		List<String> list = new ArrayList<String>();
		list.add("1");
		list.add("42");
		
		return list;
	}
	
	private List<String> availablePaymentMethodsError()
	{
		List<String> list = new ArrayList<String>();
		list.add("1dd");
		list.add("42x");
		
		return list;
	}
}