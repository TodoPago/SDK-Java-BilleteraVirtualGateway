package ar.com.todopago.api.operations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.com.todopago.api.ElementNames;
import ar.com.todopago.api.exceptions.EmptyFieldPassException;
import ar.com.todopago.api.exceptions.InvalidFieldException;
import ar.com.todopago.api.model.TransactionBSA;

public class TransactionValidate {

	public Boolean validateTransaction(TransactionBSA transaction) throws EmptyFieldPassException, InvalidFieldException {

		Boolean generalDataOK = true;
		Boolean operationDataOK = true;
		Boolean result = true;

		Map<String, Object> generalData = transaction.getGeneralData();
		generalDataOK = validateGeneralData(generalData);
		
		Map<String, Object> operationData = transaction.getOperationData();
		operationDataOK = validateOperationData(operationData);

		if (generalDataOK != true || operationDataOK != true) {
			result = false;
		}
		return result;
	}

	public Boolean validateGeneralData(Map<String, Object> generalData) throws EmptyFieldPassException,InvalidFieldException {

		Boolean generalDataOK = true;
		String errorMessage = "";

		ArrayList<String> keyGeneralDataList = new ArrayList<String>(Arrays.asList(ElementNames.BSA_MERCHANT,
				ElementNames.BSA_SECURITY, ElementNames.BSA_OPERATION_DATE_TIME, ElementNames.BSA_REMOTE_IP_ADDRESS,
				ElementNames.BSA_CHANNEL));

		for (String key : keyGeneralDataList) {
			
			if (!generalData.containsKey(key)) {
				errorMessage = key + " is required";
				throw new EmptyFieldPassException(errorMessage);
			} else {
				
				String value=(String)generalData.get(key);
				
				if (value == null || value.equals("")) {
					errorMessage = key + " is  empty/null";
					throw new EmptyFieldPassException(errorMessage);
				}
				
				if (Validations.isSpecialCharacter(value)) {
					errorMessage = key + " contains special characters";
					throw new InvalidFieldException(errorMessage);
				}
				
				if(key.equals(ElementNames.BSA_OPERATION_DATE_TIME) && !Validations.isDateTime(value)){
					errorMessage = key + " is not a valid date time";
					throw new InvalidFieldException(errorMessage);
				}
				
				if(key.equals(ElementNames.BSA_MERCHANT) && !Validations.isNumeric(value)){
					errorMessage = key + " is not numeric";
					throw new InvalidFieldException(errorMessage);
				}	
				
				if(key.equals(ElementNames.BSA_REMOTE_IP_ADDRESS) && !Validations.isIPV4(value)){
					errorMessage = key + " is not numeric";
					throw new InvalidFieldException(errorMessage);
				}
				
				
			}
		}
		
		return generalDataOK;
	}
	
	@SuppressWarnings("unchecked")
	public Boolean validateOperationData(Map<String, Object> operationData) throws EmptyFieldPassException, InvalidFieldException {
		Boolean operationDataOK = true;
		String errorMessage = "";
		
		List<String> keyOperationDataList = new ArrayList<String>(Arrays.asList(ElementNames.BSA_OPERATION_ID,
				ElementNames.BSA_CURRENCY_CODE, ElementNames.BSA_AMOUNT));
		
		List<String> keyOperationDataListOptional = new ArrayList<String>(Arrays.asList(ElementNames.BSA_AVAILABLE_PAYMENT_METHODS,
				ElementNames.BSA_AVAILABLE_BANKS));

		
		for (String key : keyOperationDataList) {
			
			if (!operationData.containsKey(key) && !key.equals(ElementNames.BSA_PAYMENT_METHODS_ID) && !key.equals(ElementNames.BSA_BANK_id)) {
				errorMessage = key + " is required";
				throw new EmptyFieldPassException(errorMessage);
			} else {
					String value=(String)operationData.get(key);
					
					if (Validations.isSpecialCharacter(value)) {
						errorMessage = key + " contains special characters";
						throw new InvalidFieldException(errorMessage);
					}
					
					if(key.equals("amount") && !Validations.isDecimalPattern(value)){
						errorMessage = key + " must be type ..,22";
						throw new InvalidFieldException(errorMessage);
					}
					
					
					if(key.equals(ElementNames.BSA_AMOUNT) && !Validations.isDecimalPattern(value)){
						errorMessage = key + " must be xx.12";
						throw new InvalidFieldException(errorMessage);
					}

//				}
			}
		}

		if (operationData.containsKey(ElementNames.BSA_BUYER_PRESELECTION)) {
			Map<String,Object> buyerPreselection=(HashMap<String,Object>)operationData.get(ElementNames.BSA_BUYER_PRESELECTION);

			if (buyerPreselection.containsKey(ElementNames.BSA_PAYMENT_METHODS_ID)) {
				if(! Validations.isNumeric((String)buyerPreselection.get(ElementNames.BSA_PAYMENT_METHODS_ID))) {
					String message=ElementNames.BSA_PAYMENT_METHODS_ID+" must be an integer";
					throw new InvalidFieldException(message);
				}
			}

			if (buyerPreselection.containsKey(ElementNames.BSA_BANK_id)) {
				if(! Validations.isNumeric((String)buyerPreselection.get(ElementNames.BSA_BANK_id))) {
					String message=ElementNames.BSA_BANK_id+" must be an integer";
					throw new InvalidFieldException(message);
				}
			}
                }


		for (String key : keyOperationDataListOptional) {
			if (operationData.containsKey(key)) {
				List<String> list=(ArrayList<String>)operationData.get(key);
				int size=list.size();

				if(size > 0 && !Validations.isNumericArray(list)){
					String message=key+" must be an integer array";
					throw new InvalidFieldException(message);
				}

			}
		}
		return operationDataOK;
	}
}
