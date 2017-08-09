package ar.com.todopago.api.operations;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validations{

	public static boolean isNumeric(String inputToValidate){
		String regex="^[+-]?[0-9]+(.[0-9]+)?$";
		
		return Validations.validateRegEx(regex, inputToValidate);
	}
	
	public static boolean isIPV4(String inputToValidate){
		String regex="^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
				"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
				"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
				"([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
		
		return Validations.validateRegEx(regex, inputToValidate);
	}
	
	public static boolean isDateTime(String inputToValidate){
		String regex="(?:(?:(?:(?:(?:[13579][26]|[2468][048])00)|(?:[0-9]{2}(?:(?:[13579][26])|(?:[2468][048]|0[48]))))(?:(?:(?:09|04|06|11)(?:0[1-9]|1[0-9]|2[0-9]|30))|(?:(?:01|03|05|07|08|10|12)(?:0[1-9]|1[0-9]|2[0-9]|3[01]))|(?:02(?:0[1-9]|1[0-9]|2[0-9]))))|(?:[0-9]{4}(?:(?:(?:09|04|06|11)(?:0[1-9]|1[0-9]|2[0-9]|30))|(?:(?:01|03|05|07|08|10|12)(?:0[1-9]|1[0-9]|2[0-9]|3[01]))|(?:02(?:[01][0-9]|2[0-8])))))(?:0[0-9]|1[0-9]|2[0-3])(?:[0-5][0-9]){2}";
		
		return validateRegEx(regex, inputToValidate);	
	} 
	
	public static boolean isDecimalPattern(String inputToValidate){
		String regex="^[0-9]+(,[0-9]{2})?$";
		
		return validateRegEx(regex, inputToValidate);
	} 

	
	public static boolean isSpecialCharacter(String inputToValidate){
		String[] prohibitedCharacters = { "?", "=", "&", "/", "\"", "'", "\\", ":", "#", ";" };
		for(int i=0;i<inputToValidate.length();i++){
			for(int j=0;j<prohibitedCharacters.length;j++){
				if(inputToValidate.charAt(i)==prohibitedCharacters[j].charAt(0)){
					return true;
				}
			}
		}
		
		
		return false;
	}
	
	public static boolean isNumericArray(List<String> params){
		boolean isNumeric=true;
		
		for(int i=0;i<params.size();i++){
			if(!(Validations.isNumeric(params.get(i)))){
				isNumeric= false;
			}
		}

		return isNumeric;
	}
	
	
//	public static boolean isArray(Array params){
//		for(int i=0;i<params.length;i++){
//			if(!(params[i] instanceof Integer)){
//				return false;
//			}
//		}
//		
//		return true;
//	}
	
	/*----- Auxiliary methods ----*/
	private static boolean validateRegEx(String regex,String inputToValidate) {
		Pattern pattern;
		Matcher matcher;
		
		pattern = Pattern.compile(regex);
		matcher = pattern.matcher(inputToValidate);
		
		return matcher.matches();
	}
}
