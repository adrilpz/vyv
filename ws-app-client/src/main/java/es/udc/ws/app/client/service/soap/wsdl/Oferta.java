
package es.udc.ws.app.client.service.soap.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Clase Java para oferta complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="oferta">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codOferta" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="comisionVenta" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="descripcion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fechaLimiteOferta" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="fechaLimiteReserva" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="nombre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="precioDescontado" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="precioReal" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="valida" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "oferta", propOrder = {
    "codOferta",
    "comisionVenta",
    "descripcion",
    "fechaLimiteOferta",
    "fechaLimiteReserva",
    "nombre",
    "precioDescontado",
    "precioReal",
    "valida"
})
public class Oferta {

    protected Long codOferta;
    protected double comisionVenta;
    protected String descripcion;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaLimiteOferta;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaLimiteReserva;
    protected String nombre;
    protected double precioDescontado;
    protected double precioReal;
    protected boolean valida;

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
     * Obtiene el valor de la propiedad comisionVenta.
     * 
     */
    public double getComisionVenta() {
        return comisionVenta;
    }

    /**
     * Define el valor de la propiedad comisionVenta.
     * 
     */
    public void setComisionVenta(double value) {
        this.comisionVenta = value;
    }

    /**
     * Obtiene el valor de la propiedad descripcion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Define el valor de la propiedad descripcion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescripcion(String value) {
        this.descripcion = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaLimiteOferta.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaLimiteOferta() {
        return fechaLimiteOferta;
    }

    /**
     * Define el valor de la propiedad fechaLimiteOferta.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaLimiteOferta(XMLGregorianCalendar value) {
        this.fechaLimiteOferta = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaLimiteReserva.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaLimiteReserva() {
        return fechaLimiteReserva;
    }

    /**
     * Define el valor de la propiedad fechaLimiteReserva.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaLimiteReserva(XMLGregorianCalendar value) {
        this.fechaLimiteReserva = value;
    }

    /**
     * Obtiene el valor de la propiedad nombre.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Define el valor de la propiedad nombre.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombre(String value) {
        this.nombre = value;
    }

    /**
     * Obtiene el valor de la propiedad precioDescontado.
     * 
     */
    public double getPrecioDescontado() {
        return precioDescontado;
    }

    /**
     * Define el valor de la propiedad precioDescontado.
     * 
     */
    public void setPrecioDescontado(double value) {
        this.precioDescontado = value;
    }

    /**
     * Obtiene el valor de la propiedad precioReal.
     * 
     */
    public double getPrecioReal() {
        return precioReal;
    }

    /**
     * Define el valor de la propiedad precioReal.
     * 
     */
    public void setPrecioReal(double value) {
        this.precioReal = value;
    }

    /**
     * Obtiene el valor de la propiedad valida.
     * 
     */
    public boolean isValida() {
        return valida;
    }

    /**
     * Define el valor de la propiedad valida.
     * 
     */
    public void setValida(boolean value) {
        this.valida = value;
    }

}
