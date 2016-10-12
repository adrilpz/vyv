
package es.udc.ws.app.client.service.soap.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para invalidarOferta complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="invalidarOferta">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
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
@XmlType(name = "invalidarOferta", propOrder = {
    "codOferta"
})
public class InvalidarOferta {

    protected Long codOferta;

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
