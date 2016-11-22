package com.waitingpiri.domain;

public class Tarifa {

	private int id;
	private String desde;
	private String hasta;
	private double precio;
	
	public Tarifa(int id, String desde, String hasta, double precio) {
		this.id = id;
		this.desde = desde;
		this.hasta = hasta;
		this.precio = precio;
	}

	public String getDesde() {
		return desde;
	}

	public void setDesde(String descripcion) {
		this.desde = descripcion;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHasta() {
		return hasta;
	}

	public void setHasta(String hasta) {
		this.hasta = hasta;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}
}
