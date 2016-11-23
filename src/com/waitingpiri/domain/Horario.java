package com.waitingpiri.domain;

public class Horario {

	private int id;
	private String salida;
	private String llegada;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSalida() {
		return salida;
	}

	public void setSalida(String salida) {
		this.salida = salida;
	}

	public String getLlegada() {
		return llegada;
	}

	public void setLlegada(String llegada) {
		this.llegada = llegada;
	}

	public Horario(int id, String salida, String llegada) {
		this.id = id;
		this.salida = salida;
		this.llegada=llegada;
	}

	
}
