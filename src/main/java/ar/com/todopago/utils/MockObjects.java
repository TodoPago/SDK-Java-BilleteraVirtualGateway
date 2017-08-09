package ar.com.todopago.utils;

import org.json.JSONException;
import org.json.JSONObject;

public class MockObjects {

	public static JSONObject createMockJsonUserResponseOk() throws JSONException {
		JSONObject jObject = new JSONObject(
				"{\"Credentials\": {\"codigoResultado\": 1,\"resultado\":{ \"codigoResultado\": 0,\"mensajeKey\": null, \"mensajeResultado\": \"Aceptado\" },\"merchantId\": 984,\"APIKey\": \"PRISMA 8385636DE4D6D32D176B74B80AA3C1E6\"}}");
		return jObject;
	}

	public static JSONObject createMockJsonUserResponseBadPass() throws JSONException {
		JSONObject jObject = new JSONObject(
				"{\"Credentials\": {\"codigoResultado\": 1,\"resultado\":{ \"codigoResultado\": 1055,\"mensajeKey\": 1055,\"mensajeResultado\":\"La contraseña es incorrecta\" },\"merchantId\": null,\"APIKey\": null}}");
		return jObject;
	}

	public static JSONObject createMockJsonUserResponseBadUser() throws JSONException {
		JSONObject jObject = new JSONObject(
				"{\"Credentials\": {\"codigoResultado\": 1,\"resultado\":{ \"codigoResultado\": 1050,\"mensajeKey\": 1050,\"mensajeResultado\": \"El usuario no se encuentra registrado\" },\"merchantId\": null,\"APIKey\": null}}");
		return jObject;
	}

}
