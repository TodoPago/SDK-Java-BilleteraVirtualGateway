package ar.com.todopago.api.operations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import ar.com.todopago.api.ElementNames;
import ar.com.todopago.api.exceptions.ResponseException;
import ar.com.todopago.api.model.TransactionBSA;
import ar.com.todopago.api.rest.RestConnector;

public class TransactionParser extends OperationsParser {
	
	private final static Logger logger = Logger.getLogger(TransactionParser.class.getName());

	public static JSONObject generateTransactionJson(TransactionBSA transactionBSA) {
		
		JSONObject transactionJson = new JSONObject();
		try {
			JSONObject generalDataJson = parseMapToJson(transactionBSA.getGeneralData());
			JSONObject operationDataJson = parseMapToJson(transactionBSA.getOperationData());
			JSONObject technicalDataJson = parseMapToJson(transactionBSA.getTechnicalData());
			transactionJson.put(ElementNames.BSA_GENERAL_DATA,   generalDataJson);
			transactionJson.put(ElementNames.BSA_OPERATION_DATA, operationDataJson);
			transactionJson.put(ElementNames.BSA_TECHNICAL_DATA, technicalDataJson);

		} catch (JSONException e) {
			logger.log(Level.SEVERE, "Error on create transactionJson ", e.getMessage() + " - " + e.getLocalizedMessage());
		}
		return transactionJson;
	}

	public static TransactionBSA parseJsonToTransaction(InputStream inputStream) throws ResponseException, IOException {

		TransactionBSA transactionBSA = new TransactionBSA();
		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
		String output = br.readLine();
		try {
			transactionBSA = parseInputStreamJsonToTransaction(new JSONObject(output));
		} catch (JSONException e) {
			throw new ResponseException(e.getMessage());
		}
		return transactionBSA;
	}

	private static TransactionBSA parseInputStreamJsonToTransaction(JSONObject jsonObject)
			throws ResponseException, JSONException, IOException {

		TransactionBSA transactionBSA = new TransactionBSA();
		Map<String, Object> transactionBSAMap = parseJsonToMap(jsonObject);
		transactionBSA.setTransactionResponse(transactionBSAMap);

		return transactionBSA;
	}

}
