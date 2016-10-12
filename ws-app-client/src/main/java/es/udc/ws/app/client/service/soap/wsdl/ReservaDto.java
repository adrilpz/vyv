
package es.udc.ws.app.client.service.soap.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Clase Java para reservaDto complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="reservaDto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codOferta" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="codReserva" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="estado" type="{http://soap.ws.udc.es/}estadoReserva" minOccurs="0"/>
 *         &lt;element name="fechaReserva" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="precioOferta" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="tarjetaCredito" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "reservaDto", propOrder = {
    "codOferta",
    "codReserva",
    "email",
    "estado",
    "fechaReserva",
    "precioOferta",
    "tarjetaCredito"
})
public class ReservaDto {

    protected Long codOferta;
    protected Long codReserva;
    protected String email;
    @XmlSchemaType(name = "string")
    protected EstadoReserva estado;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaReserva;
    protected double precioOferta;
    protected String tarjetaCredito;

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

    /**
     * Obtiene el valor de la propiedad codReserva.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCodReserva() {
        return codReserva;
    }

    /**
     * Define el valor de la propiedad codReserva.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCodReserva(Long value) {
        this.codReserva = value;
    }

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
     * Obtiene el valor de la propiedad estado.
     * 
     * @return
     *     possible object is
     *     {@link EstadoReserva }
     *     
     */
    public EstadoReserva getEstado() {
        return estado;
    }

    /**
     * Define el valor de la propiedad estado.
     * 
     * @param value
     *     allowed object is
     *     {@link EstadoReserva }
     *     
     */
    public void setEstado(EstadoReserva value) {
        this.estado = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaReserva.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaReserva() {
        return fechaReserva;
    }

    /**
     * Define el valor de la propiedad fechaReserva.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaReserva(XMLGregorianCalendar value) {
        this.fechaReserva = value;
    }

    /**
     * Obtiene el valor de la propiedad precioOferta.
     * 
     */
    public double getPrecioOferta() {
        return precioOferta;
    }

    /**
     * Define el valor de la propiedad precioOferta.
     * 
     */
    public void setPrecioOferta(double value) {
        this.precioOferta = value;
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

}
