package ar.com.todopago.api.operations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.json.JSONException;
import org.json.JSONObject;
import ar.com.todopago.api.exceptions.ResponseException;
import ar.com.todopago.api.model.User;

public class UserParser extends OperationsParser {
	
	public static User parseInputStreamJsonToUser(InputStream inputStream) throws JSONException, IOException, ResponseException{	
		User user = new User();	
		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String output = br.readLine();     
		user = parseInputStreamJsonToUser(new JSONObject(output));		
		return user;
	}
	
	public static User parseInputStreamJsonToUser(JSONObject jsonObject) throws JSONException, IOException, ResponseException{
		
		JSONObject jCredentials;
		JSONObject jresultado;
		
		User user = new User();	
		jCredentials =  jsonObject.getJSONObject("Credentials");
		jresultado =  jCredentials.getJSONObject("resultado");
		
		if (jCredentials.getInt("codigoResultado") == 1){
			if(jresultado.getInt("codigoResultado") != 0){
				throw new ResponseException(jresultado.getString("mensajeKey") + " - " + jresultado.getString("mensajeResultado"));	
			}else{
				user.setApiKey(jCredentials.getString("APIKey"));
				user.setMerchant(jCredentials.getString("merchantId"));	
			}	
		}else{		
			// throw new ResponseException("");	
		}
		return user;
	}

}
