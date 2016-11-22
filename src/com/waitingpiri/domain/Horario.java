package com.waitingpiri.domain;

public class Horario {

	private int id;
	private String descripcion;
	
	public Horario(int id, String descripcion) {
		this.id = id;
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
}
