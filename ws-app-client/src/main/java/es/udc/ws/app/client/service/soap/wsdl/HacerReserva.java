
package es.udc.ws.app.client.service.soap.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para hacerReserva complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="hacerReserva">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tarjetaCredito" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codOferta" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "hacerReserva", propOrder = {
    "email",
    "tarjetaCredito",
    "codOferta"
})
public class HacerReserva {

    protected String email;
    protected String tarjetaCredito;
    protected Long codOferta;

    /**
     * Obtiene el valor de la propiedad email.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmail() {
        return email;
    }

    /**
     * Define el valor de la propiedad email.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * Obtiene el valor de la propiedad tarjetaCredito.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTarjetaCredito() {
        return tarjetaCredito;
    }

    /**
     * Define el valor de la propiedad tarjetaCredito.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTarjetaCredito(String value) {
        this.tarjetaCredito = value;
    }

    /**
     * Obtiene el valor de la propiedad codOferta.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCodOferta() {
        return codOferta;
    }

    /**
     * Define el valor de la propiedad codOferta.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCodOferta(Long value) {
        this.codOferta = value;
    }

}
