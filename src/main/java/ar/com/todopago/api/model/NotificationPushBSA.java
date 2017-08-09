package ar.com.todopago.api.model;

import java.util.HashMap;
import java.util.Map;

import ar.com.todopago.api.ElementNames;

public class NotificationPushBSA {

    // Request
    private Map <String, Object> generalData;
    private Map <String, Object> operationData;
    private Map <String, Object> tokenizationData;

    // Response
    private String statusCode;
    private String statusMessage;


    public NotificationPushBSA(){}

    public NotificationPushBSA(Map <String, Object> generalData, Map <String, Object> operationData, Map <String, Object> tokenizationData)
    {
        this.setGeneralData(generalData);
        this.setOperationData(operationData);
        this.setTokenizationData(tokenizationData);
    }

    public Map<String, Object> getGeneralData() {
		return generalData;
	}

	public void setGeneralData(Map<String, Object> generalData){
		this.generalData = generalData;
	}

	public Map<String, Object> getOperationData() {
		return operationData;
	}

	public void setOperationData(Map<String, Object> operationData) {
		this.operationData = operationData;
	}

	public Map<String, Object> getTokenizationData() {
		return tokenizationData;
	}

	public void setTokenizationData(Map<String, Object> tokenizationData) {
		this.tokenizationData = tokenizationData;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public String toString(){
        String result = "NotificationPushBSA [" + ElementNames.BSA_STATUS_CODE + " = " + statusCode + ", " + ElementNames.BSA_STATUS_MESSAGE + " = " + statusMessage;
        return result;
    }

    public Map <String, Object> toMap(){
    	Map <String, Object> map = new HashMap<String, Object>();
    	map.put(ElementNames.BSA_STATUS_CODE, this.statusCode);
    	map.put(ElementNames.BSA_STATUS_MESSAGE, this.statusMessage);
        return map;
    }
}
