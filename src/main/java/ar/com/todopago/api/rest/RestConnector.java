package ar.com.todopago.api.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.X509Certificate;

import ar.com.todopago.api.ElementNames;
import ar.com.todopago.api.exceptions.ConnectionException;
import ar.com.todopago.api.exceptions.ResponseException;

public class RestConnector {

	private final static Logger logger = Logger.getLogger(RestConnector.class.getName());

	protected final String USER_AGENT = "Mozilla/5.0";
	protected String endpoint;
	protected Map<String, String> headders;

	protected final String CONTENT_TYPE_APP_JSON = "application/json";
	protected final String METHOD_POST = "POST";
	protected final String METHOD_GET = "GET";
	protected final String METHOD_PUT = "PUT";

	public RestConnector(String endpoint, Map<String, List<String>> headder) {

		this.endpoint = endpoint;
		this.headders = new HashMap<String, String>();

		if (headder != null) {
			List<String> aux = headder.get(ElementNames.Authorization);
			this.headders.put(ElementNames.Authorization, aux.get(0));
		}
	}

	private HttpURLConnection generateHttpURLConnection(String url, String contentType, String method,
			Boolean withApiKey) throws IOException {

		URL obj = new URL(url);
		HttpURLConnection client = (HttpURLConnection) obj.openConnection();

		client.setRequestProperty("User-Agent", USER_AGENT);

		if (method != null && !method.equals("")) {
			client.setRequestMethod(method);
		} else {
			client.setRequestMethod(METHOD_POST);
		}

		if (client.getRequestMethod().equals(METHOD_POST) || client.getRequestMethod().equals(METHOD_PUT)) {

			if (contentType != null && !contentType.equals("")) {
				client.setRequestProperty("Content-Type", contentType);
			} else {
				client.setRequestProperty("Content-Type", CONTENT_TYPE_APP_JSON);
			}
			client.setDoInput(true);
			client.setDoOutput(true);
			client.setUseCaches(false);
		}

		if (withApiKey) {
			client.setRequestProperty(ElementNames.Authorization, headders.get("Authorization"));
		}

		return client;
	}

	private InputStream send(HttpURLConnection httpURLConnection) throws IOException, ConnectionException, ResponseException {

		int responseCode = httpURLConnection.getResponseCode();
		InputStream is;

		if (responseCode != 200 && responseCode != 201) {		
			is = httpURLConnection.getErrorStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String output = br.readLine();			
			if(responseCode == 400){
				throw new ResponseException( responseCode + " - " + httpURLConnection.getResponseMessage() + " - " + output);
			}else{
				throw new ConnectionException( responseCode + " - " + httpURLConnection.getResponseMessage() + " - " + output);
			}			
		}else{
			is = httpURLConnection.getInputStream();		
		}

		logger.log(Level.INFO, "Response Code : " + responseCode);

		return is;
	}

	public InputStream sendGet(String url, Boolean withApiKey) throws IOException, ConnectionException, ResponseException {

		HttpURLConnection con = generateHttpURLConnection(url, null, METHOD_GET, withApiKey);
		InputStream is = send(con);
		logger.log(Level.INFO, "\nSending 'GET' request to URL : " + url);
		return is;
	}

	public InputStream sendPost(String url, String json, Boolean withApiKey) throws IOException, ConnectionException, ResponseException {

		HttpURLConnection con = generateHttpURLConnection(url, CONTENT_TYPE_APP_JSON, METHOD_POST, true);
		OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
		wr.write(json);
		wr.flush();
		InputStream is = send(con);
		logger.log(Level.INFO, "\nSending 'POST' request to URL : " + url);
		return is;
	}
	
	public InputStream sendPut(String url, String json, Boolean withApiKey) throws IOException, ConnectionException, ResponseException {

		HttpURLConnection con = generateHttpURLConnection(url, CONTENT_TYPE_APP_JSON, METHOD_PUT, true);
		OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
		wr.write(json);
		wr.flush();
		InputStream is = send(con);
		logger.log(Level.INFO, "\nSending 'PUT' request to URL : " + url);
		return is;
	}
		
	static {
	    disableSslVerification();
	}

	private static void disableSslVerification() {
	    try
	    {
	        // Create a trust manager that does not validate certificate chains
	        TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
	            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
	                return null;
	            }
				public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType)
						throws CertificateException {				
				}
				public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType)
						throws CertificateException {				
				}
	        }
	        };

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
	
	

}
