
package ar.com.todopago.api.authorize;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="StatusCode" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="StatusMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AuthorizationKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AUTHORIZATIONCODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "statusCode",
    "statusMessage",
    "authorizationKey",
    "authorizationcode"
})
@XmlRootElement(name = "ReturnResponse")
public class ReturnResponse {

    @XmlElement(name = "StatusCode")
    protected int statusCode;
    @XmlElementRef(name = "StatusMessage", namespace = "http://api.todopago.com.ar", type = JAXBElement.class, required = false)
    protected JAXBElement<String> statusMessage;
    @XmlElementRef(name = "AuthorizationKey", namespace = "http://api.todopago.com.ar", type = JAXBElement.class, required = false)
    protected JAXBElement<String> authorizationKey;
    @XmlElementRef(name = "AUTHORIZATIONCODE", namespace = "http://api.todopago.com.ar", type = JAXBElement.class, required = false)
    protected JAXBElement<String> authorizationcode;

    /**
     * Gets the value of the statusCode property.
     * 
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * Sets the value of the statusCode property.
     * 
     */
    public void setStatusCode(int value) {
        this.statusCode = value;
    }

    /**
     * Gets the value of the statusMessage property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getStatusMessage() {
        return statusMessage;
    }

    /**
     * Sets the value of the statusMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setStatusMessage(JAXBElement<String> value) {
        this.statusMessage = value;
    }

    /**
     * Gets the value of the authorizationKey property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getAuthorizationKey() {
        return authorizationKey;
    }

    /**
     * Sets the value of the authorizationKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setAuthorizationKey(JAXBElement<String> value) {
        this.authorizationKey = value;
    }

    /**
     * Gets the value of the authorizationcode property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getAUTHORIZATIONCODE() {
        return authorizationcode;
    }

    /**
     * Sets the value of the authorizationcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setAUTHORIZATIONCODE(JAXBElement<String> value) {
        this.authorizationcode = value;
    }

}
