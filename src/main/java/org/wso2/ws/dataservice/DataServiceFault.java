
package org.wso2.ws.dataservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 *         &lt;element name="current_params" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="current_request_name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="nested_exception" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="source_data_service">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="location" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="default_namespace" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="data_service_name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="ds_code" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "currentParams",
    "currentRequestName",
    "nestedException",
    "sourceDataService",
    "dsCode"
})
@XmlRootElement(name = "DataServiceFault")
public class DataServiceFault {

    @XmlElement(name = "current_params", required = true)
    protected String currentParams;
    @XmlElement(name = "current_request_name", required = true)
    protected String currentRequestName;
    @XmlElement(name = "nested_exception", required = true)
    protected String nestedException;
    @XmlElement(name = "source_data_service", required = true)
    protected DataServiceFault.SourceDataService sourceDataService;
    @XmlElement(name = "ds_code", required = true)
    protected String dsCode;

    /**
     * Gets the value of the currentParams property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrentParams() {
        return currentParams;
    }

    /**
     * Sets the value of the currentParams property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrentParams(String value) {
        this.currentParams = value;
    }

    /**
     * Gets the value of the currentRequestName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrentRequestName() {
        return currentRequestName;
    }

    /**
     * Sets the value of the currentRequestName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrentRequestName(String value) {
        this.currentRequestName = value;
    }

    /**
     * Gets the value of the nestedException property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNestedException() {
        return nestedException;
    }

    /**
     * Sets the value of the nestedException property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNestedException(String value) {
        this.nestedException = value;
    }

    /**
     * Gets the value of the sourceDataService property.
     * 
     * @return
     *     possible object is
     *     {@link DataServiceFault.SourceDataService }
     *     
     */
    public DataServiceFault.SourceDataService getSourceDataService() {
        return sourceDataService;
    }

    /**
     * Sets the value of the sourceDataService property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataServiceFault.SourceDataService }
     *     
     */
    public void setSourceDataService(DataServiceFault.SourceDataService value) {
        this.sourceDataService = value;
    }

    /**
     * Gets the value of the dsCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDsCode() {
        return dsCode;
    }

    /**
     * Sets the value of the dsCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDsCode(String value) {
        this.dsCode = value;
    }


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
     *         &lt;element name="location" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="default_namespace" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="data_service_name" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
        "location",
        "defaultNamespace",
        "description",
        "dataServiceName"
    })
    public static class SourceDataService {

        @XmlElement(required = true)
        protected String location;
        @XmlElement(name = "default_namespace", required = true)
        protected String defaultNamespace;
        @XmlElement(required = true)
        protected String description;
        @XmlElement(name = "data_service_name", required = true)
        protected String dataServiceName;

        /**
         * Gets the value of the location property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getLocation() {
            return location;
        }

        /**
         * Sets the value of the location property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setLocation(String value) {
            this.location = value;
        }

        /**
         * Gets the value of the defaultNamespace property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDefaultNamespace() {
            return defaultNamespace;
        }

        /**
         * Sets the value of the defaultNamespace property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDefaultNamespace(String value) {
            this.defaultNamespace = value;
        }

        /**
         * Gets the value of the description property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDescription() {
            return description;
        }

        /**
         * Sets the value of the description property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDescription(String value) {
            this.description = value;
        }

        /**
         * Gets the value of the dataServiceName property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDataServiceName() {
            return dataServiceName;
        }

        /**
         * Sets the value of the dataServiceName property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDataServiceName(String value) {
            this.dataServiceName = value;
        }

    }

}
