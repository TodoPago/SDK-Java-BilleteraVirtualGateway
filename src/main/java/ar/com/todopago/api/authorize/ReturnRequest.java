
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
 *         &lt;element name="Security" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Merchant" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Channel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AuthorizationKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RequestKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CURRENCYCODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AMOUNT" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
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
    "security",
    "merchant",
    "channel",
    "authorizationKey",
    "requestKey",
    "currencycode",
    "amount"
})
@XmlRootElement(name = "ReturnRequest")
public class ReturnRequest {

    @XmlElementRef(name = "Security", namespace = "http://api.todopago.com.ar", type = JAXBElement.class, required = false)
    protected JAXBElement<String> security;
    @XmlElement(name = "Merchant", required = true)
    protected String merchant;
    @XmlElementRef(name = "Channel", namespace = "http://api.todopago.com.ar", type = JAXBElement.class, required = false)
    protected JAXBElement<String> channel;
    @XmlElementRef(name = "AuthorizationKey", namespace = "http://api.todopago.com.ar", type = JAXBElement.class, required = false)
    protected JAXBElement<String> authorizationKey;
    @XmlElementRef(name = "RequestKey", namespace = "http://api.todopago.com.ar", type = JAXBElement.class, required = false)
    protected JAXBElement<String> requestKey;
    @XmlElementRef(name = "CURRENCYCODE", namespace = "http://api.todopago.com.ar", type = JAXBElement.class, required = false)
    protected JAXBElement<String> currencycode;
    @XmlElementRef(name = "AMOUNT", namespace = "http://api.todopago.com.ar", type = JAXBElement.class, required = false)
    protected JAXBElement<Float> amount;

    /**
     * Gets the value of the security property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getSecurity() {
        return security;
    }

    /**
     * Sets the value of the security property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setSecurity(JAXBElement<String> value) {
        this.security = value;
    }

    /**
     * Gets the value of the merchant property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMerchant() {
        return merchant;
    }

    /**
     * Sets the value of the merchant property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMerchant(String value) {
        this.merchant = value;
    }

    /**
     * Gets the value of the channel property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getChannel() {
        return channel;
    }

    /**
     * Sets the value of the channel property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setChannel(JAXBElement<String> value) {
        this.channel = value;
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
     * Gets the value of the requestKey property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getRequestKey() {
        return requestKey;
    }

    /**
     * Sets the value of the requestKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setRequestKey(JAXBElement<String> value) {
        this.requestKey = value;
    }

    /**
     * Gets the value of the currencycode property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCURRENCYCODE() {
        return currencycode;
    }

    /**
     * Sets the value of the currencycode property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCURRENCYCODE(JAXBElement<String> value) {
        this.currencycode = value;
    }

    /**
     * Gets the value of the amount property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Float }{@code >}
     *     
     */
    public JAXBElement<Float> getAMOUNT() {
        return amount;
    }

    /**
     * Sets the value of the amount property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Float }{@code >}
     *     
     */
    public void setAMOUNT(JAXBElement<Float> value) {
        this.amount = value;
    }

}
