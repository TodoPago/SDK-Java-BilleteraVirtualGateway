package ar.com.todopago.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBElement;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ar.com.todopago.api.ElementNames;
import ar.com.todopago.api.authorize.Authorize;
import ar.com.todopago.api.authorize.AuthorizePortType;
import ar.com.todopago.api.authorize.GetAuthorizeAnswer;
import ar.com.todopago.api.authorize.GetAuthorizeAnswerResponse;
import ar.com.todopago.api.authorize.ObjectFactory;
import ar.com.todopago.api.authorize.ReturnRequest;
import ar.com.todopago.api.authorize.ReturnResponse;
import ar.com.todopago.api.authorize.SendAuthorizeRequest;
import ar.com.todopago.api.authorize.SendAuthorizeRequestResponse;
import ar.com.todopago.api.authorize.VoidRequest;
import ar.com.todopago.api.authorize.VoidResponse;

public class TodoPagoConectorAuthorize {

	private final static Logger logger = Logger.getLogger(TodoPagoConectorAuthorize.class.getName());

	private ObjectFactory factory;
	private String endpoint = null;
	private AuthorizePortType service = null;

	/**
	 * 
	 * @param wsdl
	 * @param endpoint
	 * @param security
	 * @throws MalformedURLException
	 */
	public TodoPagoConectorAuthorize(URL wsdl, String endpoint, Map<String, List<String>> auth)
			throws MalformedURLException {
		this.factory = new ObjectFactory();
		this.endpoint = endpoint;

		// this.service = new Authorize(new
		// URL(this.wsdl)).getAuthorizeHttpsSoap11Endpoint();
		this.service = new Authorize(wsdl).getAuthorizeHttpsSoap11Endpoint();
		((BindingProvider) service).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, this.endpoint);
		((BindingProvider) service).getRequestContext().put(MessageContext.HTTP_REQUEST_HEADERS, auth);
	}

	/**
	 * 
	 * @param parameters
	 * @return
	 */
	public Map<String, Object> getPaymentValuesPCI(Map<String, String> parameters) {
		logger.info("Deshabilitando verificacion SSL");
		GetAuthorizeAnswer getAuthorizeAnswer = this.parseToAuthorizeAnswer(parameters);
		GetAuthorizeAnswerResponse getAuthorizeAnswerResponse = service.getAuthorizeAnswer(getAuthorizeAnswer);
		System.err.println("-------------------------" + getAuthorizeAnswerResponse);
		// devuelve el formato de array el resultado de de la operación
		// Authorize
		Map<String, Object> response = this.parseAuthorizeResponseToArray(getAuthorizeAnswerResponse);
		return response;
	}

	/**
	 * 
	 * @param parameters
	 * @return
	 */
	public Map<String, Object> getpaymentValues(Map<String, String> parameters, Map<String, String> fraudControl) {
		// parseo de los valores enviados por el e-commerce/custompage
		SendAuthorizeRequest sendAuthorizeRequest = this.parseToSendAuthorizeRequest(parameters, fraudControl);
		SendAuthorizeRequestResponse sendAuthorizeRequestResponse = service.sendAuthorizeRequest(sendAuthorizeRequest);
		// devuelve el formato de array el resultado de de la operación
		// Authorize
		Map<String, Object> response = this.parseSendAuthorizeRequestResponseToArray(sendAuthorizeRequestResponse);
		return response;
	}

	// Devoluciones
	public Map<String, Object> voidRequest(Map<String, String> parameters) {
		logger.info("VoidRequest");
		VoidRequest voidRequest = this.parseToVoidRequest(parameters);
		VoidResponse voidResponse = service.voidRequest(voidRequest);
		System.err.println("-------------------------" + voidResponse);
		// devuelve el formato de array el resultado de de la operación
		// Authorize
		Map<String, Object> response = this.parseVoidRequestToArray(voidResponse);
		return response;
	}

	// Devoluciones
	public Map<String, Object> returnRequest(Map<String, String> parameters) {
		logger.info("ReturnRequest");
		ReturnRequest returnRequest = this.parseToReturnRequest(parameters);
		ReturnResponse returnResponse = service.returnRequest(returnRequest);
		System.err.println("-------------------------" + returnResponse);
		// devuelve el formato de array el resultado de de la operación
		// Authorize
		Map<String, Object> response = this.parseReturnRequestToArray(returnResponse);
		return response;
	}

	private VoidRequest parseToVoidRequest(Map<String, String> parameters) {
		VoidRequest o = factory.createVoidRequest();
		if (parameters != null) {
			o.setAuthorizationKey(
					factory.createVoidRequestAuthorizationKey(parameters.get(ElementNames.AuthorizationKey)));
			o.setChannel(factory.createVoidRequestChannel(parameters.get(ElementNames.RequestChannel)));
			o.setMerchant(parameters.get(ElementNames.Merchant));
			o.setRequestKey(factory.createVoidRequestRequestKey(parameters.get(ElementNames.RequestKey)));
			o.setSecurity(factory.createVoidRequestSecurity(parameters.get(ElementNames.Security)));
		}
		return o;
	}

	private Map<String, Object> parseVoidRequestToArray(VoidResponse voidResponse) {
		Map<String, Object> result = new HashMap<String, Object>();

		result.put(ElementNames.AuthorizationKey, getValue(voidResponse.getAuthorizationKey()));
		result.put(ElementNames.StatusCode, getValue(voidResponse.getStatusCode()));
		result.put(ElementNames.StatusMessage, getValue(voidResponse.getStatusMessage()));
		result.put(ElementNames.Authorizationcode, getValue(voidResponse.getAUTHORIZATIONCODE()));

		logger.log(Level.INFO, "Armando voidRequestResponse: {0}", result);
		return result;
	}

	private ReturnRequest parseToReturnRequest(Map<String, String> parameters) {
		ReturnRequest o = factory.createReturnRequest();
		if (parameters != null) {

			o.setAuthorizationKey(
					factory.createVoidRequestAuthorizationKey(parameters.get(ElementNames.AuthorizationKey)));
			o.setChannel(factory.createVoidRequestChannel(parameters.get(ElementNames.RequestChannel)));
			o.setMerchant(parameters.get(ElementNames.Merchant));
			o.setRequestKey(factory.createVoidRequestRequestKey(parameters.get(ElementNames.RequestKey)));
			o.setSecurity(factory.createVoidRequestSecurity(parameters.get(ElementNames.Security)));
			try {
				o.setAMOUNT(factory.createReturnRequestAMOUNT(Float.valueOf(parameters.get(ElementNames.Amount))));
			} catch (NumberFormatException e) {
				logger.log(Level.INFO, "The amount is incorrect", e);
				e.printStackTrace();
			}
			o.setCURRENCYCODE(factory.createReturnRequestCURRENCYCODE(parameters.get(ElementNames.CurrencyCode)));

		}
		return o;
	}

	private Map<String, Object> parseReturnRequestToArray(ReturnResponse returnResponse) {
		Map<String, Object> result = new HashMap<String, Object>();

		result.put(ElementNames.AuthorizationKey, getValue(returnResponse.getAuthorizationKey()));
		result.put(ElementNames.StatusCode, getValue(returnResponse.getStatusCode()));
		result.put(ElementNames.StatusMessage, getValue(returnResponse.getStatusMessage()));
		result.put(ElementNames.Authorizationcode, getValue(returnResponse.getAUTHORIZATIONCODE()));

		logger.log(Level.INFO, "Armando returnRequestResponse: {0}", result);
		return result;
	}

	private GetAuthorizeAnswer parseToAuthorizeAnswer(Map<String, String> parameters) {
		GetAuthorizeAnswer o = factory.createGetAuthorizeAnswer();
		if (parameters != null) {
			o.setAnswerKey(factory.createGetAuthorizeAnswerAnswerKey(parameters.get(ElementNames.AnswerKey)));
			o.setRequestKey(factory.createGetAuthorizeAnswerRequestKey(parameters.get(ElementNames.RequestKey)));
			o.setMerchant(parameters.get(ElementNames.Merchant));
			o.setSecurity(parameters.get(ElementNames.Security));
			o.setSession(factory.createGetAuthorizeAnswerSession(parameters.get(ElementNames.Session)));
		}
		return o;
	}

	private String getValue(JAXBElement element) {
		String s = "";
		if (element != null) {
			s = element.getValue().toString();
		}
		return s;
	}

	private String getValue(int element) {
		String s = String.valueOf(element);
		return s;
	}

	private Map<String, Object> parseSendAuthorizeRequestResponseToArray(
			SendAuthorizeRequestResponse sendAuthorizeRequestResponse) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(ElementNames.RequestKey, getValue(sendAuthorizeRequestResponse.getRequestKey()));
		result.put(ElementNames.StatusCode, getValue(sendAuthorizeRequestResponse.getStatusCode()));
		result.put(ElementNames.StatusMessage, getValue(sendAuthorizeRequestResponse.getStatusMessage()));
		result.put(ElementNames.UrlRequest, getValue(sendAuthorizeRequestResponse.getURLRequest()));
		result.put(ElementNames.PublicRequestKey, getValue(sendAuthorizeRequestResponse.getPublicRequestKey()));
		logger.log(Level.INFO, "Armando sendAuthorizeRequestResponse: {0}", result);
		return result;
	}

	private SendAuthorizeRequest parseToSendAuthorizeRequest(Map<String, String> parameters,
			Map<String, String> fraudControl) {
		SendAuthorizeRequest o = factory.createSendAuthorizeRequest();
		if (parameters != null) {
			o.setEncodingMethod(
					factory.createSendAuthorizeRequestEncodingMethod(parameters.get(ElementNames.EncodingMethod)));
			o.setMerchant(parameters.get(ElementNames.Merchant));
			o.setPayload(factory.createSendAuthorizeRequestPayload(createPayload(parameters, fraudControl)));
			o.setSecurity(parameters.get(ElementNames.Security));
			o.setSession(factory.createSendAuthorizeRequestSession(parameters.get(ElementNames.Session)));
			o.setURLERROR(factory.createSendAuthorizeRequestURLERROR(parameters.get(ElementNames.UrlError)));
			o.setURLOK(factory.createSendAuthorizeRequestURLOK(parameters.get(ElementNames.UrlOK)));
		}
		logger.log(Level.INFO, "Armando SendAuthorizeRequest: {0}", o);
		return o;
	}

	private String createPayload(Map<String, String> parameters, Map<String, String> fraudControl) {
		StringBuilder payload = new StringBuilder("<Request>");
		
//		parameters.put("SDK", "JAVA");
//		parameters.put("SDKVERSION", TodoPagoConector.versionTodoPago);
//		parameters.put("LENGUAGEVERSION" , System.getProperty("java.version"));
				
		for (Map.Entry<String, String> entry : parameters.entrySet()) {
			payload.append("<");
			payload.append(entry.getKey().toUpperCase());
			payload.append(">");

			// Crop String a 256 chars
			String aux = (entry.getValue().length() > 254) ? entry.getValue().substring(0, 253) : entry.getValue();
			payload.append(aux);
			// End Crop

			payload.append("</");
			payload.append(entry.getKey().toUpperCase());
			payload.append(">");
		}
		for (Map.Entry<String, String> entry : fraudControl.entrySet()) {
			payload.append("<");
			payload.append(entry.getKey().toUpperCase());
			payload.append(">");

			// Crop String a 256 chars
			String aux = (entry.getValue().length() > 254) ? entry.getValue().substring(0, 253) : entry.getValue();
			payload.append(aux);
			// End Crop

			payload.append("</");
			payload.append(entry.getKey().toUpperCase());
			payload.append(">");
		}

		payload.append("</Request>");
		String result = payload.toString();
		// System.out.println(result);
		logger.log(Level.INFO, "Armando payload: {0}", result);
		return result;
	}

	private Map<String, Object> parseAuthorizeResponseToArray(GetAuthorizeAnswerResponse authorizeAnswerResponse) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(ElementNames.AuthorizationKey, getValue(authorizeAnswerResponse.getAuthorizationKey()));
		result.put(ElementNames.EncodingMethod, getValue(authorizeAnswerResponse.getEncodingMethod()));
		payloadToArray(result, authorizeAnswerResponse.getPayload());
		// TODO fix previos line remove following
		// result.put("payload",
		// getValue(authorizeAnswerResponse.getPayload()));
		// result.put("payload", authorizeAnswerResponse.getPayload());
		result.put(ElementNames.StatusCode, getValue(authorizeAnswerResponse.getStatusCode()));
		result.put(ElementNames.StatusMessage, getValue(authorizeAnswerResponse.getStatusMessage()));
		return result;
	}

	private void payloadToArray(Map<String, Object> result, Node node) {
		if (node != null) {
			Node answer = node.getFirstChild();
			Node request = answer.getNextSibling();
			nodeToMap(answer, result);
			nodeToMap(request, result);
		}
	}

	private void nodeToMap(Node node, Map<String, Object> result) {
		if (node != null) {
			NodeList nodeList = node.getChildNodes();
			for (int i = 0; nodeList.getLength() > i; i++) {
				Node child = nodeList.item(i);
				result.put(child.getNodeName(), child.getNodeValue()==null?child.getTextContent():child.getNodeValue());
			}
		}
	}

}
