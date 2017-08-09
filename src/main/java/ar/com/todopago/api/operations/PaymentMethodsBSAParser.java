package ar.com.todopago.api.operations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ar.com.todopago.api.exceptions.ResponseException;
import ar.com.todopago.api.model.PaymentMethodsBSA;

public class PaymentMethodsBSAParser extends OperationsParser {
	
	public static PaymentMethodsBSA parseJsonToPaymentMethod(InputStream inputStream)
			throws ResponseException, IOException {
		
		PaymentMethodsBSA paymentMethodsBSA = new PaymentMethodsBSA();		
		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
		String output = br.readLine();
		try {
			paymentMethodsBSA = parseInputStreamJsonToPaymentMethod(new JSONArray(output));
		} catch (JSONException e) {		
			throw new ResponseException(e.getMessage());
		}	
		return paymentMethodsBSA;
	}

	private static PaymentMethodsBSA parseInputStreamJsonToPaymentMethod(JSONArray jsonArray)
			throws ResponseException, JSONException, IOException {

		PaymentMethodsBSA paymentMethodsBSA = new PaymentMethodsBSA();
		List <Map<String, Object>> paymentMethodsList= new ArrayList<Map<String, Object>>();
				
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject json = jsonArray.getJSONObject(i);		
			Map<String, Object> paymentMethodMap = parseJsonToMap(json);
			paymentMethodsList.add(paymentMethodMap);
		}		
		paymentMethodsBSA.setPaymentMethodsBSAList(paymentMethodsList);		
		return paymentMethodsBSA;
	}

}
