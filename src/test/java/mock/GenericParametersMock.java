package mock;

import java.util.HashMap;
import java.util.Map;

public class GenericParametersMock {
	
	public static Map<String,Object> getErrorGenericResponseWithChannel(int errorCode,String errorMessage){
		Map<String,Object> response=new HashMap<String,Object>();
		
		response.put("errorCode",errorCode);
		response.put("errorMessage",errorMessage);
		response.put("channel",11);
		
		return response;
	}
	
	public static Map<String,Object> getErrorGenericResponseWithoutChannel(int statusCode,String statusMessage){
		Map<String,Object> response=new HashMap<String,Object>();
		
		response.put("statusCode",statusCode);
		response.put("statusMessage",statusMessage);
		
		return response;
	}
}