package ar.com.todopago.api.operations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import ar.com.todopago.api.ElementNames;
import ar.com.todopago.api.exceptions.EmptyFieldPassException;
import ar.com.todopago.api.exceptions.InvalidFieldException;
import ar.com.todopago.api.model.NotificationPushBSA;

public class NotificationPushValidate {

	public Boolean validateNotificationPush(NotificationPushBSA notificationPush) throws EmptyFieldPassException, InvalidFieldException {

		Boolean generalDataOK = true;
		Boolean operationDataOK = true;
		Boolean tokenizationDataOK = true;
		Boolean result = true;

		Map<String, Object> generalData = notificationPush.getGeneralData();
		generalDataOK = validateGeneralData(generalData);
		
		Map<String, Object> operationData = notificationPush.getOperationData();
		operationDataOK = validateOperationData(operationData);
		
		Map<String, Object> tokenizationData = notificationPush.getTokenizationData();
		operationDataOK = validateTokenizationData(tokenizationData);
		

		if (generalDataOK != true || operationDataOK != true || tokenizationDataOK != true ) {
			result = false;
		}
		return result;
	}

	public Boolean validateGeneralData(Map<String, Object> generalData) throws EmptyFieldPassException, InvalidFieldException {

		Boolean generalDataOK = true;
		String errorMessage = "";

		ArrayList<String> keyGeneralDataList = new ArrayList<String>(Arrays.asList(ElementNames.BSA_MERCHANT,
				ElementNames.BSA_SECURITY, ElementNames.BSA_PUBLIC_REQUEST_KEY,
				ElementNames.BSA_OPERATION_NAME));
		

		for (String key : keyGeneralDataList) {
			if (!generalData.containsKey(key)) {
				errorMessage = key + " is required";
				generalDataOK = false;
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
				
			}
		}
		
		if (generalDataOK != true){
            throw new EmptyFieldPassException(errorMessage);
        }

		return generalDataOK;
	}
	
	public Boolean validateOperationData(Map<String, Object> operationData) throws EmptyFieldPassException, InvalidFieldException {
		Boolean operationDataOK = true;
		String errorMessage = "";
		
		List<String> keyOperationDataList = new ArrayList<String>(Arrays.asList(ElementNames.BSA_OPERATION_ID,
				ElementNames.BSA_OPERATION_DATE_TIME,ElementNames.BSA_CURRENCY_CODE, ElementNames.BSA_AMOUNT, ElementNames.BSA_FACILITIES_PAYMENT));
		
		List<String> keyOperationDataListOptional = new ArrayList<String>(Arrays.asList(ElementNames.BSA_ID_GATEWAY,
				ElementNames.BSA_RESULT_CODE_GATEWAY,ElementNames.BSA_RESULT_CODE_MEDIOPAGO));

		for (String key : keyOperationDataList) {
			if (!operationData.containsKey(key)) {
				errorMessage = key + " is required";
				operationDataOK = false;
			} else {
					String value=(String)operationData.get(key);
					
					if (value == null || value.equals("")) {
						errorMessage = key + " is  empty/null";
						throw new EmptyFieldPassException(errorMessage);
					}
					
					if (Validations.isSpecialCharacter(value)) {
						errorMessage = key + " contains special characters";
						throw new InvalidFieldException(errorMessage);
					}
					
					if(key.equals(ElementNames.BSA_AMOUNT) && !Validations.isDecimalPattern(value)){
						errorMessage = key + " must be xx,xx";
						throw new InvalidFieldException(errorMessage);
					}
					
					if(key.equals(ElementNames.BSA_OPERATION_DATE_TIME) && !Validations.isDateTime(value)){
						errorMessage = key + " is not numeric";
						throw new InvalidFieldException(errorMessage);
					}	
			}
			
		}
		
		for (String key : keyOperationDataListOptional) {
			if (operationData.containsKey(key) && !operationData.get(key).equals("")) {
				
				String value=(String)operationData.get(key);
				
				notifitationPushFieldsValidate(key, value);
			}
			
		}
		
		return operationDataOK;
	}

	public Boolean validateTokenizationData(Map<String, Object> tokenizationData) throws EmptyFieldPassException {
		Boolean tokenizationDataOK = true;
		String errorMessage = "";
		
		List<String> keyOperationDataList = new ArrayList<String>(Arrays.asList(ElementNames.BSA_PUBLIC_TOKENIZATION_FIELD));

		for (String key : keyOperationDataList) {
			if (!tokenizationData.containsKey(key)) {
				errorMessage = key + " is required";
				tokenizationDataOK = false;
			} else {
					if (tokenizationData.get(key) == null || tokenizationData.get(key).equals("")) {
						errorMessage = key + " is  empty/null";
						tokenizationDataOK = false;
					}
			}
			
		}		
		
		if (tokenizationDataOK != true){
            throw new EmptyFieldPassException(errorMessage);
        }
		
		return tokenizationDataOK;
	}
	
	private void notifitationPushFieldsValidate(String key,String value) throws InvalidFieldException {
		String errorMessage;
		if(key.equals(ElementNames.BSA_ID_GATEWAY) && !Validations.isNumeric(value)){
			errorMessage = key + " is not numeric";
			throw new InvalidFieldException(errorMessage);
		}
		
		if(key.equals(ElementNames.BSA_RESULT_CODE_GATEWAY) && !Validations.isNumeric(value)){
			errorMessage = key + " is not numeric";
			throw new InvalidFieldException(errorMessage);
		}
		
		if(key.equals(ElementNames.BSA_RESULT_CODE_MEDIOPAGO) && !Validations.isNumeric(value)){
			errorMessage = key + " is not numeric";
			throw new InvalidFieldException(errorMessage);
		}
	}
	
}
