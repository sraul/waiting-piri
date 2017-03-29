package com.waitingpiri.gestion;

import com.waitingpiri.domain.Colectivo;

public class Localizacion {

	private double latitud;
	private double longitud;
	private Colectivo colectivo;
	
	public Localizacion() {
	}
	
	public Localizacion(double latitud, double longitud) {
		this.latitud = latitud;
		this.longitud = longitud;
	}

	public double getLatitud() {
		return latitud;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	public double getLongitud() {
		return longitud;
	}

	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}

	public Colectivo getColectivo() {
		return colectivo;
	}

	public void setColectivo(Colectivo colectivo) {
		this.colectivo = colectivo;
	}
}
