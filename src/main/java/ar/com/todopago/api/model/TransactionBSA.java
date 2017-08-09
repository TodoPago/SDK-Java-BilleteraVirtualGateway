package ar.com.todopago.api.model;

import java.util.Map;

public class TransactionBSA {
	
	// Request
    private Map<String, Object> generalData;
    private Map<String, Object> operationData;
    private Map<String, Object> technicalData;

    // Response
    private Map<String, Object> transactionResponse;
 
    public TransactionBSA(){}

    public TransactionBSA(Map<String, Object> generalData, Map<String, Object> operationData, Map<String, Object> technicalData)
    {
        this.generalData = generalData;
        this.operationData = operationData;
        this.technicalData = technicalData;
    }

    public Map<String, Object> getGeneralData(){
        return this.generalData;
    }

    public void setGeneralData(Map<String, Object> generalData){
        this.generalData = generalData;
    }

    public Map<String, Object> getOperationData() {
        return this.operationData;
    }

    public void setOperationData(Map<String, Object> operationData){
        this.operationData = operationData;
    }

    public Map<String, Object> getTechnicalData(){
        return this.technicalData;
    }

    public void setTechnicalData(Map<String, Object> technicalData){
        this.technicalData = technicalData;
    }
    
    public Map<String, Object> getTransactionResponse() {
		return transactionResponse;
	}

	public void setTransactionResponse(Map<String, Object> transactionResponse) {
		this.transactionResponse = transactionResponse;
	}

	@Override
	public String toString() {
		return "TransactionBSA [transactionResponse=" + transactionResponse.toString() + "]";
	}
}