
package es.udc.ws.app.client.service.soap.wsdl;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the es.udc.ws.app.client.service.soap.wsdl package. 
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

    private final static QName _ReclamarReservaResponse_QNAME = new QName("http://soap.ws.udc.es/", "reclamarReservaResponse");
    private final static QName _ObtenerReservasUsuario_QNAME = new QName("http://soap.ws.udc.es/", "obtenerReservasUsuario");
    private final static QName _ObtenerReservasResponse_QNAME = new QName("http://soap.ws.udc.es/", "obtenerReservasResponse");
    private final static QName _BuscarOfertaIDResponse_QNAME = new QName("http://soap.ws.udc.es/", "buscarOfertaIDResponse");
    private final static QName _BuscarOfertasUsuarioResponse_QNAME = new QName("http://soap.ws.udc.es/", "buscarOfertasUsuarioResponse");
    private final static QName _HacerReserva_QNAME = new QName("http://soap.ws.udc.es/", "hacerReserva");
    private final static QName _InvalidarOfertaResponse_QNAME = new QName("http://soap.ws.udc.es/", "invalidarOfertaResponse");
    private final static QName _ObtenerReservas_QNAME = new QName("http://soap.ws.udc.es/", "obtenerReservas");
    private final static QName _ObtenerReservasUsuarioResponse_QNAME = new QName("http://soap.ws.udc.es/", "obtenerReservasUsuarioResponse");
    private final static QName _SoapInstanceNotFoundException_QNAME = new QName("http://soap.ws.udc.es/", "SoapInstanceNotFoundException");
    private final static QName _CrearOferta_QNAME = new QName("http://soap.ws.udc.es/", "crearOferta");
    private final static QName _BorrarOferta_QNAME = new QName("http://soap.ws.udc.es/", "borrarOferta");
    private final static QName _SoapInvalidOfferReservationException_QNAME = new QName("http://soap.ws.udc.es/", "SoapInvalidOfferReservationException");
    private final static QName _CrearOfertaResponse_QNAME = new QName("http://soap.ws.udc.es/", "crearOfertaResponse");
    private final static QName _InvalidarOferta_QNAME = new QName("http://soap.ws.udc.es/", "invalidarOferta");
    private final static QName _SoapInvalidOfferDeleteException_QNAME = new QName("http://soap.ws.udc.es/", "SoapInvalidOfferDeleteException");
    private final static QName _BorrarOfertaResponse_QNAME = new QName("http://soap.ws.udc.es/", "borrarOfertaResponse");
    private final static QName _ActualizarOfertaResponse_QNAME = new QName("http://soap.ws.udc.es/", "actualizarOfertaResponse");
    private final static QName _ActualizarOferta_QNAME = new QName("http://soap.ws.udc.es/", "actualizarOferta");
    private final static QName _BuscarOfertasResponse_QNAME = new QName("http://soap.ws.udc.es/", "buscarOfertasResponse");
    private final static QName _HacerReservaResponse_QNAME = new QName("http://soap.ws.udc.es/", "hacerReservaResponse");
    private final static QName _BuscarOfertas_QNAME = new QName("http://soap.ws.udc.es/", "buscarOfertas");
    private final static QName _BuscarOfertaID_QNAME = new QName("http://soap.ws.udc.es/", "buscarOfertaID");
    private final static QName _BuscarOfertasUsuario_QNAME = new QName("http://soap.ws.udc.es/", "buscarOfertasUsuario");
    private final static QName _SoapInvalidOfferReclamationException_QNAME = new QName("http://soap.ws.udc.es/", "SoapInvalidOfferReclamationException");
    private final static QName _SoapInputValidationException_QNAME = new QName("http://soap.ws.udc.es/", "SoapInputValidationException");
    private final static QName _SoapInvalidOfferUpdateException_QNAME = new QName("http://soap.ws.udc.es/", "SoapInvalidOfferUpdateException");
    private final static QName _ReclamarReserva_QNAME = new QName("http://soap.ws.udc.es/", "reclamarReserva");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: es.udc.ws.app.client.service.soap.wsdl
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SoapInstanceNotFoundExceptionInfo }
     * 
     */
    public SoapInstanceNotFoundExceptionInfo createSoapInstanceNotFoundExceptionInfo() {
        return new SoapInstanceNotFoundExceptionInfo();
    }

    /**
     * Create an instance of {@link CrearOferta }
     * 
     */
    public CrearOferta createCrearOferta() {
        return new CrearOferta();
    }

    /**
     * Create an instance of {@link BorrarOferta }
     * 
     */
    public BorrarOferta createBorrarOferta() {
        return new BorrarOferta();
    }

    /**
     * Create an instance of {@link CrearOfertaResponse }
     * 
     */
    public CrearOfertaResponse createCrearOfertaResponse() {
        return new CrearOfertaResponse();
    }

    /**
     * Create an instance of {@link ReclamarReservaResponse }
     * 
     */
    public ReclamarReservaResponse createReclamarReservaResponse() {
        return new ReclamarReservaResponse();
    }

    /**
     * Create an instance of {@link ObtenerReservasUsuario }
     * 
     */
    public ObtenerReservasUsuario createObtenerReservasUsuario() {
        return new ObtenerReservasUsuario();
    }

    /**
     * Create an instance of {@link ObtenerReservasResponse }
     * 
     */
    public ObtenerReservasResponse createObtenerReservasResponse() {
        return new ObtenerReservasResponse();
    }

    /**
     * Create an instance of {@link ObtenerReservasUsuarioResponse }
     * 
     */
    public ObtenerReservasUsuarioResponse createObtenerReservasUsuarioResponse() {
        return new ObtenerReservasUsuarioResponse();
    }

    /**
     * Create an instance of {@link BuscarOfertaIDResponse }
     * 
     */
    public BuscarOfertaIDResponse createBuscarOfertaIDResponse() {
        return new BuscarOfertaIDResponse();
    }

    /**
     * Create an instance of {@link BuscarOfertasUsuarioResponse }
     * 
     */
    public BuscarOfertasUsuarioResponse createBuscarOfertasUsuarioResponse() {
        return new BuscarOfertasUsuarioResponse();
    }

    /**
     * Create an instance of {@link HacerReserva }
     * 
     */
    public HacerReserva createHacerReserva() {
        return new HacerReserva();
    }

    /**
     * Create an instance of {@link InvalidarOfertaResponse }
     * 
     */
    public InvalidarOfertaResponse createInvalidarOfertaResponse() {
        return new InvalidarOfertaResponse();
    }

    /**
     * Create an instance of {@link ObtenerReservas }
     * 
     */
    public ObtenerReservas createObtenerReservas() {
        return new ObtenerReservas();
    }

    /**
     * Create an instance of {@link BuscarOfertas }
     * 
     */
    public BuscarOfertas createBuscarOfertas() {
        return new BuscarOfertas();
    }

    /**
     * Create an instance of {@link BuscarOfertaID }
     * 
     */
    public BuscarOfertaID createBuscarOfertaID() {
        return new BuscarOfertaID();
    }

    /**
     * Create an instance of {@link BuscarOfertasUsuario }
     * 
     */
    public BuscarOfertasUsuario createBuscarOfertasUsuario() {
        return new BuscarOfertasUsuario();
    }

    /**
     * Create an instance of {@link ReclamarReserva }
     * 
     */
    public ReclamarReserva createReclamarReserva() {
        return new ReclamarReserva();
    }

    /**
     * Create an instance of {@link InvalidarOferta }
     * 
     */
    public InvalidarOferta createInvalidarOferta() {
        return new InvalidarOferta();
    }

    /**
     * Create an instance of {@link BorrarOfertaResponse }
     * 
     */
    public BorrarOfertaResponse createBorrarOfertaResponse() {
        return new BorrarOfertaResponse();
    }

    /**
     * Create an instance of {@link ActualizarOfertaResponse }
     * 
     */
    public ActualizarOfertaResponse createActualizarOfertaResponse() {
        return new ActualizarOfertaResponse();
    }

    /**
     * Create an instance of {@link ActualizarOferta }
     * 
     */
    public ActualizarOferta createActualizarOferta() {
        return new ActualizarOferta();
    }

    /**
     * Create an instance of {@link BuscarOfertasResponse }
     * 
     */
    public BuscarOfertasResponse createBuscarOfertasResponse() {
        return new BuscarOfertasResponse();
    }

    /**
     * Create an instance of {@link HacerReservaResponse }
     * 
     */
    public HacerReservaResponse createHacerReservaResponse() {
        return new HacerReservaResponse();
    }

    /**
     * Create an instance of {@link ReservaDto }
     * 
     */
    public ReservaDto createReservaDto() {
        return new ReservaDto();
    }

    /**
     * Create an instance of {@link OfertaDto }
     * 
     */
    public OfertaDto createOfertaDto() {
        return new OfertaDto();
    }

    /**
     * Create an instance of {@link OfertaDtoUsuario }
     * 
     */
    public OfertaDtoUsuario createOfertaDtoUsuario() {
        return new OfertaDtoUsuario();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReclamarReservaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "reclamarReservaResponse")
    public JAXBElement<ReclamarReservaResponse> createReclamarReservaResponse(ReclamarReservaResponse value) {
        return new JAXBElement<ReclamarReservaResponse>(_ReclamarReservaResponse_QNAME, ReclamarReservaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerReservasUsuario }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "obtenerReservasUsuario")
    public JAXBElement<ObtenerReservasUsuario> createObtenerReservasUsuario(ObtenerReservasUsuario value) {
        return new JAXBElement<ObtenerReservasUsuario>(_ObtenerReservasUsuario_QNAME, ObtenerReservasUsuario.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerReservasResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "obtenerReservasResponse")
    public JAXBElement<ObtenerReservasResponse> createObtenerReservasResponse(ObtenerReservasResponse value) {
        return new JAXBElement<ObtenerReservasResponse>(_ObtenerReservasResponse_QNAME, ObtenerReservasResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BuscarOfertaIDResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "buscarOfertaIDResponse")
    public JAXBElement<BuscarOfertaIDResponse> createBuscarOfertaIDResponse(BuscarOfertaIDResponse value) {
        return new JAXBElement<BuscarOfertaIDResponse>(_BuscarOfertaIDResponse_QNAME, BuscarOfertaIDResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BuscarOfertasUsuarioResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "buscarOfertasUsuarioResponse")
    public JAXBElement<BuscarOfertasUsuarioResponse> createBuscarOfertasUsuarioResponse(BuscarOfertasUsuarioResponse value) {
        return new JAXBElement<BuscarOfertasUsuarioResponse>(_BuscarOfertasUsuarioResponse_QNAME, BuscarOfertasUsuarioResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HacerReserva }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "hacerReserva")
    public JAXBElement<HacerReserva> createHacerReserva(HacerReserva value) {
        return new JAXBElement<HacerReserva>(_HacerReserva_QNAME, HacerReserva.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InvalidarOfertaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "invalidarOfertaResponse")
    public JAXBElement<InvalidarOfertaResponse> createInvalidarOfertaResponse(InvalidarOfertaResponse value) {
        return new JAXBElement<InvalidarOfertaResponse>(_InvalidarOfertaResponse_QNAME, InvalidarOfertaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerReservas }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "obtenerReservas")
    public JAXBElement<ObtenerReservas> createObtenerReservas(ObtenerReservas value) {
        return new JAXBElement<ObtenerReservas>(_ObtenerReservas_QNAME, ObtenerReservas.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerReservasUsuarioResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "obtenerReservasUsuarioResponse")
    public JAXBElement<ObtenerReservasUsuarioResponse> createObtenerReservasUsuarioResponse(ObtenerReservasUsuarioResponse value) {
        return new JAXBElement<ObtenerReservasUsuarioResponse>(_ObtenerReservasUsuarioResponse_QNAME, ObtenerReservasUsuarioResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SoapInstanceNotFoundExceptionInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "SoapInstanceNotFoundException")
    public JAXBElement<SoapInstanceNotFoundExceptionInfo> createSoapInstanceNotFoundException(SoapInstanceNotFoundExceptionInfo value) {
        return new JAXBElement<SoapInstanceNotFoundExceptionInfo>(_SoapInstanceNotFoundException_QNAME, SoapInstanceNotFoundExceptionInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CrearOferta }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "crearOferta")
    public JAXBElement<CrearOferta> createCrearOferta(CrearOferta value) {
        return new JAXBElement<CrearOferta>(_CrearOferta_QNAME, CrearOferta.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BorrarOferta }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "borrarOferta")
    public JAXBElement<BorrarOferta> createBorrarOferta(BorrarOferta value) {
        return new JAXBElement<BorrarOferta>(_BorrarOferta_QNAME, BorrarOferta.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "SoapInvalidOfferReservationException")
    public JAXBElement<String> createSoapInvalidOfferReservationException(String value) {
        return new JAXBElement<String>(_SoapInvalidOfferReservationException_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CrearOfertaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "crearOfertaResponse")
    public JAXBElement<CrearOfertaResponse> createCrearOfertaResponse(CrearOfertaResponse value) {
        return new JAXBElement<CrearOfertaResponse>(_CrearOfertaResponse_QNAME, CrearOfertaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InvalidarOferta }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "invalidarOferta")
    public JAXBElement<InvalidarOferta> createInvalidarOferta(InvalidarOferta value) {
        return new JAXBElement<InvalidarOferta>(_InvalidarOferta_QNAME, InvalidarOferta.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "SoapInvalidOfferDeleteException")
    public JAXBElement<String> createSoapInvalidOfferDeleteException(String value) {
        return new JAXBElement<String>(_SoapInvalidOfferDeleteException_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BorrarOfertaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "borrarOfertaResponse")
    public JAXBElement<BorrarOfertaResponse> createBorrarOfertaResponse(BorrarOfertaResponse value) {
        return new JAXBElement<BorrarOfertaResponse>(_BorrarOfertaResponse_QNAME, BorrarOfertaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ActualizarOfertaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "actualizarOfertaResponse")
    public JAXBElement<ActualizarOfertaResponse> createActualizarOfertaResponse(ActualizarOfertaResponse value) {
        return new JAXBElement<ActualizarOfertaResponse>(_ActualizarOfertaResponse_QNAME, ActualizarOfertaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ActualizarOferta }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "actualizarOferta")
    public JAXBElement<ActualizarOferta> createActualizarOferta(ActualizarOferta value) {
        return new JAXBElement<ActualizarOferta>(_ActualizarOferta_QNAME, ActualizarOferta.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BuscarOfertasResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "buscarOfertasResponse")
    public JAXBElement<BuscarOfertasResponse> createBuscarOfertasResponse(BuscarOfertasResponse value) {
        return new JAXBElement<BuscarOfertasResponse>(_BuscarOfertasResponse_QNAME, BuscarOfertasResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HacerReservaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "hacerReservaResponse")
    public JAXBElement<HacerReservaResponse> createHacerReservaResponse(HacerReservaResponse value) {
        return new JAXBElement<HacerReservaResponse>(_HacerReservaResponse_QNAME, HacerReservaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BuscarOfertas }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "buscarOfertas")
    public JAXBElement<BuscarOfertas> createBuscarOfertas(BuscarOfertas value) {
        return new JAXBElement<BuscarOfertas>(_BuscarOfertas_QNAME, BuscarOfertas.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BuscarOfertaID }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "buscarOfertaID")
    public JAXBElement<BuscarOfertaID> createBuscarOfertaID(BuscarOfertaID value) {
        return new JAXBElement<BuscarOfertaID>(_BuscarOfertaID_QNAME, BuscarOfertaID.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BuscarOfertasUsuario }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "buscarOfertasUsuario")
    public JAXBElement<BuscarOfertasUsuario> createBuscarOfertasUsuario(BuscarOfertasUsuario value) {
        return new JAXBElement<BuscarOfertasUsuario>(_BuscarOfertasUsuario_QNAME, BuscarOfertasUsuario.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "SoapInvalidOfferReclamationException")
    public JAXBElement<String> createSoapInvalidOfferReclamationException(String value) {
        return new JAXBElement<String>(_SoapInvalidOfferReclamationException_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "SoapInputValidationException")
    public JAXBElement<String> createSoapInputValidationException(String value) {
        return new JAXBElement<String>(_SoapInputValidationException_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "SoapInvalidOfferUpdateException")
    public JAXBElement<String> createSoapInvalidOfferUpdateException(String value) {
        return new JAXBElement<String>(_SoapInvalidOfferUpdateException_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReclamarReserva }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "reclamarReserva")
    public JAXBElement<ReclamarReserva> createReclamarReserva(ReclamarReserva value) {
        return new JAXBElement<ReclamarReserva>(_ReclamarReserva_QNAME, ReclamarReserva.class, null, value);
    }

}
