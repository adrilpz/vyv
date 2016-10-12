package es.udc.pa.p007.apuestasapp.web.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import es.udc.pa.p007.apuestasapp.model.opcionApuesta.OpcionApuesta;


public class UserSession {

	private Long userProfileId;
	private String firstName;
	private boolean admin;
	private List<OpcionApuesta> opciones=new ArrayList<OpcionApuesta>();
	private List<OpcionApuesta> ganadoras=new ArrayList<OpcionApuesta>();
	private Long codEvento;
	private Long codOpcion;
	private Long codTipoApuesta;

	public Long getCodTipoApuesta() {
		return codTipoApuesta;
	}

	public void setCodTipoApuesta(Long codTipoApuesta) {
		this.codTipoApuesta = codTipoApuesta;
	}

	public List<OpcionApuesta> getGanadoras() {
		return ganadoras;
	}

	public void setGanadoras(List<OpcionApuesta> ganadoras) {
		this.ganadoras = ganadoras;
	}

	public Long getCodOpcion() {
		return codOpcion;
	}

	public void setCodOpcion(Long codOpcion) {
		this.codOpcion = codOpcion;
	}

	public Long getCodEvento() {
		return codEvento;
	}

	public void setCodEvento(Long codEvento) {
		this.codEvento = codEvento;
	}

	public List<OpcionApuesta> getOpciones() {
		return opciones;
	}

	public void setOpciones(List<OpcionApuesta> opciones) {
		this.opciones = opciones;
	}

	public Long getUserProfileId() {
		return userProfileId;
	}

	public void setUserProfileId(Long userProfileId) {
		this.userProfileId = userProfileId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
	

}
