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
import ar.com.todopago.api.echoservice.DataServiceFault_Exception;
import ar.com.todopago.api.echoservice.EchoOperation;
import ar.com.todopago.api.echoservice.EchoServiceDSS;
import ar.com.todopago.api.echoservice.EchoServiceDSSPortType;
import ar.com.todopago.api.echoservice.EchoResponse;
import ar.com.todopago.api.echoservice.ObjectFactory;


public class TodoPagoConectorEchoService {

	private final static Logger logger = Logger.getLogger(TodoPagoConectorEchoService.class.getName());

	private ObjectFactory factory;
	private String endpoint = null;
	private EchoServiceDSSPortType service = null;

	/**
	 * 
	 * @param wsdl
	 * @param endpoint
	 * @param security
	 * @throws MalformedURLException
	 */
	public TodoPagoConectorEchoService(URL wsdl, String endpoint, Map<String, List<String>> auth)
			throws MalformedURLException {
		this.factory = new ObjectFactory();
		this.endpoint = endpoint;
		this.service = new  EchoServiceDSS(wsdl).getEchoServiceDSSHttpSoap11Endpoint();
		((BindingProvider) service).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, this.endpoint);
		((BindingProvider) service).getRequestContext().put(MessageContext.HTTP_REQUEST_HEADERS, auth);
	}
	
	
	public Map<String, Object> echoOperation(String message) throws DataServiceFault_Exception {
		logger.info("EchoOperation");
		
		EchoOperation echoOperation = this.parseToEchoOperation(message);		
		EchoResponse  echoResponse = service.echoOperation(echoOperation);
		Map<String, Object> response = this.parseEchoOperationToArray(echoResponse);
		
		return response;
	}
	
	private EchoOperation parseToEchoOperation(String message) {
		EchoOperation o = factory.createEchoOperation();
		if (message != null) {
			o.setMessage(message);		
		}
		return o;
	}


	private Map<String, Object> parseEchoOperationToArray(EchoResponse  echoResponse) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(ElementNames.MESSAGE, getValue(echoResponse.getResponse()));
		logger.log(Level.INFO, "Armando EchoOperationResponse: {0}", result);
		return result;
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
