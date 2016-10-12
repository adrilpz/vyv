
package es.udc.ws.app.client.service.soap.wsdl;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para estadoReserva.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * <p>
 * <pre>
 * &lt;simpleType name="estadoReserva">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ANULADA"/>
 *     &lt;enumeration value="ACTIVA"/>
 *     &lt;enumeration value="RECLAMADA"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "estadoReserva")
@XmlEnum
public enum EstadoReserva {

    ANULADA,
    ACTIVA,
    RECLAMADA;

    public String value() {
        return name();
    }

    public static EstadoReserva fromValue(String v) {
        return valueOf(v);
    }

}
