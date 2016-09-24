package com.waitingpiri.domain;

public class Sugerencia {

	private int id;
	private String fecha;
	private String descripcion;
	
	public Sugerencia(int id, String fecha, String descripcion) {
		this.id = id;
		this.fecha = fecha;
		this.descripcion = descripcion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
}
