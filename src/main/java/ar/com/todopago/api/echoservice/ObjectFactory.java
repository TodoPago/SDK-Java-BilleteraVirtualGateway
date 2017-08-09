
package ar.com.todopago.api.echoservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.wso2.ws.dataservice package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _EchoResponse_QNAME = new QName("http://ws.wso2.org/dataservice", "echo_response");
    private final static QName _REQUESTSTATUS_QNAME = new QName("http://ws.wso2.org/dataservice", "REQUEST_STATUS");
    private final static QName _EchoResponseResponse_QNAME = new QName("http://ws.wso2.org/dataservice", "response");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.wso2.ws.dataservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DataServiceFault }
     * 
     */
    public DataServiceFault createDataServiceFault() {
        return new DataServiceFault();
    }

    /**
     * Create an instance of {@link DATASERVICERESPONSE }
     * 
     */
    public DATASERVICERESPONSE createDATASERVICERESPONSE() {
        return new DATASERVICERESPONSE();
    }

    /**
     * Create an instance of {@link DataServiceFault.SourceDataService }
     * 
     */
    public DataServiceFault.SourceDataService createDataServiceFaultSourceDataService() {
        return new DataServiceFault.SourceDataService();
    }

    /**
     * Create an instance of {@link EchoResponse }
     * 
     */
    public EchoResponse createEchoResponse() {
        return new EchoResponse();
    }

    /**
     * Create an instance of {@link EchoOperation }
     * 
     */
    public EchoOperation createEchoOperation() {
        return new EchoOperation();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EchoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.wso2.org/dataservice", name = "echo_response")
    public JAXBElement<EchoResponse> createEchoResponse(EchoResponse value) {
        return new JAXBElement<EchoResponse>(_EchoResponse_QNAME, EchoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.wso2.org/dataservice", name = "REQUEST_STATUS")
    public JAXBElement<String> createREQUESTSTATUS(String value) {
        return new JAXBElement<String>(_REQUESTSTATUS_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.wso2.org/dataservice", name = "response", scope = EchoResponse.class)
    public JAXBElement<String> createEchoResponseResponse(String value) {
        return new JAXBElement<String>(_EchoResponseResponse_QNAME, String.class, EchoResponse.class, value);
    }

}
