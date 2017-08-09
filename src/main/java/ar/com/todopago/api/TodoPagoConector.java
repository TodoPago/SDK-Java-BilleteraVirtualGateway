package ar.com.todopago.api;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import ar.com.todopago.api.echoservice.DataServiceFault_Exception;
import ar.com.todopago.api.exceptions.ConnectionException;
import ar.com.todopago.api.exceptions.EmptyFieldException;
import ar.com.todopago.api.exceptions.EmptyFieldPassException;
import ar.com.todopago.api.exceptions.EmptyFieldUserException;
import ar.com.todopago.api.exceptions.InvalidFieldException;
import ar.com.todopago.api.exceptions.ResponseException;
import ar.com.todopago.api.model.NotificationPushBSA;
import ar.com.todopago.api.model.PaymentMethodsBSA;
import ar.com.todopago.api.model.TransactionBSA;
import ar.com.todopago.api.model.User;
import ar.com.todopago.api.operations.FraudControlValidate;
import ar.com.todopago.api.operations.NotificationPushValidate;
import ar.com.todopago.api.operations.TransactionValidate;
import ar.com.todopago.api.rest.BSARest;
import ar.com.todopago.api.rest.TodoPagoRest;
import ar.com.todopago.utils.TodoPagoConectorAuthorize;
import ar.com.todopago.utils.TodoPagoConectorEchoService;

public class TodoPagoConector {

	public static final String versionTodoPago = "1.9.0";

	private final String soapAppend = "services/";
	private final String restAppend = "api/";
	private final String tenant = "t/1.1/";
	private final String authorizeSOAPAppend = "Authorize";
	private final String authorizeWSDL = "/todopago/Authorize.wsdl";
	
	private final String echoServiceDSSSOAPAppend = "EchoServiceDSS";
	private final String echoServiceESBSOAPAppend = "EchoServiceESB";
	
	private final String echoServiceDSSWSDL = "/todopago/EchoServiceDSS.wsdl";

	// endpoints
	private final String endPointDev = "https://developers.todopago.com.ar/";
	private final String endPointPrd = "https://apis.todopago.com.ar/";

	public final static int developerEndpoint = 0;
	public final static int productionEndpoint = 1;
		
	//QA
	private final String tenantQA = "t/1.2/";
	private final String endPointQA = "https://portalqa.visa2.com.ar/";
	//private final String endPointQA = "https://127.0.0.1:8280/";
	public final static int QAEndpoint = 3;
	private String t;
	
	//QAlocal
	private final String endPointQAlocal ="https://127.0.0.1:10443/";
	public final static int QAEndpointLocal = 4;
	
	private String ep;
	private TodoPagoConectorAuthorize authorize;
	private TodoPagoConectorEchoService echoServiceDSS;
	private TodoPagoConectorEchoService echoServiceESB;
	
    private TodoPagoRest todoPagoRest;
    private BSARest bsaRest;

	Map<String, String> wsdl;
	Map<String, String> endpoint;

	String soapEndpoint;
	String echoSoapEndpointDSS;
	String echoSoapEndpointESB;
	String restEndpoint;

	private String bsaEndpoint;
	private final String bsaTenant = "ms/";
	
	public TodoPagoConector(int endpoint) throws MalformedURLException {
		this(endpoint, null, false);
	}

	public TodoPagoConector(int endpoint, Map<String, List<String>> auth) throws MalformedURLException {
		this(endpoint, auth, false);
	}

	public TodoPagoConector(int endpoint, Map<String, List<String>> auth, boolean security)
			throws MalformedURLException {
		if (security) {
			this.disableSslVerification();
		}
		this.disableSslVerification();
		switch (endpoint) {
		case developerEndpoint:
			t = tenant;
			ep = endPointDev;
			t = tenant;
			break;
		case productionEndpoint:
			t = tenant;
			ep = endPointPrd;
			t = tenant;
			break;
		case QAEndpoint:
			ep = endPointQA;
			t = tenantQA;
			break;
		case QAEndpointLocal:
			ep = endPointQAlocal;
			t = null;
			break;
		default:
			t = tenant;
			ep = endPointPrd;
			t = tenant;
			break;
		}

		if(t!=null){
			this.soapEndpoint = ep + soapAppend +  t + authorizeSOAPAppend;
		}else{
			this.soapEndpoint = ep + soapAppend + authorizeSOAPAppend;
		}
		
		this.echoSoapEndpointDSS = ep + soapAppend + echoServiceDSSSOAPAppend;
		this.echoSoapEndpointESB = ep + soapAppend + echoServiceESBSOAPAppend;

		if(auth != null){
			try {
				this.authorize = new TodoPagoConectorAuthorize(TodoPagoConector.class.getResource(this.authorizeWSDL).toURI().toURL(), this.soapEndpoint, auth);
				this.echoServiceDSS = new TodoPagoConectorEchoService(TodoPagoConector.class.getResource(this.echoServiceDSSWSDL).toURI().toURL(), this.echoSoapEndpointDSS, auth);
				this.echoServiceESB = new TodoPagoConectorEchoService(TodoPagoConector.class.getResource(this.echoServiceDSSWSDL).toURI().toURL(), this.echoSoapEndpointESB, auth);
			} catch (URISyntaxException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		
		if(t!=null){
			this.restEndpoint = ep +  t + restAppend;	
		}else{
			this.restEndpoint = ep ;		
		}

		this.bsaEndpoint = ep + this.bsaTenant;

		creteClients(auth);			
	}
	
	private void creteClients(Map<String, List<String>> auth){
        this.todoPagoRest = new TodoPagoRest(this.restEndpoint, auth);
        this.bsaRest = new BSARest(this.bsaEndpoint, auth);
    }

	public User getCredentials(User user) throws EmptyFieldException, ResponseException, ConnectionException {
		
		User result = user;		
		if(user!=null){
			if(user.getUser()== null  ||  user.getUser().isEmpty()){
				throw new EmptyFieldUserException("User/Mail is empty");			
			}
			if(user.getPassword()== null  || user.getPassword().isEmpty()){
				throw new EmptyFieldPassException("Pass is empty");			
			}						 
			result = todoPagoRest.getCredentials(user);			
		}else{
			throw new EmptyFieldPassException("User is null");				
		}
		return result;		
	}

    public TransactionBSA transaction(TransactionBSA transaction) throws EmptyFieldPassException, ConnectionException, ResponseException, InvalidFieldException{
        TransactionBSA result = transaction;
        TransactionValidate tv = new TransactionValidate();
        
        transaction.getGeneralData().put(ElementNames.BSA_CHANNEL, "BSA");
               
        if (tv.validateTransaction(transaction) != false) {
            result = bsaRest.transaction(transaction);
        }
        return result;  
    }
    
    public PaymentMethodsBSA discoverPaymentMethodBSA() throws ConnectionException, ResponseException{
        PaymentMethodsBSA result = bsaRest.discoverPaymentMethodBSA();     
        return result;		
    }
    
    public NotificationPushBSA notificationPush(NotificationPushBSA notificationPush)throws EmptyFieldPassException, ConnectionException, ResponseException, InvalidFieldException{
        NotificationPushBSA result = notificationPush;
        NotificationPushValidate nv = new NotificationPushValidate();
        if (nv.validateNotificationPush(notificationPush)){
            result = bsaRest.notificationPush(notificationPush);
        }
        return result;		
    }

	public void setAuthorize(Map<String, List<String>> auth) throws MalformedURLException, ResponseException {
			
		if(auth != null && auth.get(ElementNames.Authorization)!= null && auth.get(ElementNames.Authorization).iterator().next()!= null ){			
			try {
				 this.authorize = new TodoPagoConectorAuthorize(
						TodoPagoConector.class.getResource(this.authorizeWSDL).toURI().toURL(), this.soapEndpoint, auth);
			} catch (URISyntaxException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
			creteClients(auth);
		}else{		
			throw new ResponseException("ApiKey is null");
		}

	}
	
	private void disableSslVerification() {
		try {
			// Create a trust manager that does not validate certificate chains
			TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				public void checkClientTrusted(X509Certificate[] certs, String authType) {
				}

				public void checkServerTrusted(X509Certificate[] certs, String authType) {
				}
			} };

			// Install the all-trusting trust manager
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

			// Create all-trusting host name verifier
			HostnameVerifier allHostsValid = new HostnameVerifier() {
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			};
			// Install the all-trusting host verifier
			HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		}
	}

	public static String getVersionTodoTago() {
		return versionTodoPago;
	}
	
	private Map<String, Object> setMapValidate(Map<String, String>  map) {
		Map<String, Object> mapValidate = new HashMap <String, Object>() ;			
		mapValidate.put("StatusCode", "9999");
		mapValidate.put("PublicRequestKey", "");
		mapValidate.put("URL_Request", "");
		mapValidate.put("StatusMessage", "Campos invalidos " + map.get(ElementNames.ERROR));
		mapValidate.put("RequestKey", "");
		mapValidate.put("Error", map );		
		return mapValidate;
	}

}
