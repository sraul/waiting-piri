package com.waitingpiri.domain;

public class Colectivo {
	private int id;
	private String nroColec;
	private String nroChasis;
	private String nroChapa;
	private String imei;

	public Colectivo(int id, String nroColec, String nroChasis, String nroChapa, String imei) {
		this.id = id;
		this.nroColec = nroColec;
		this.nroChasis = nroChasis;
		this.nroChapa = nroChapa;
		this.imei=imei;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNroColec() {
		return nroColec;
	}

	public void setNroColec(String nroColec) {
		this.nroColec = nroColec;
	}

	public String getNroChasis() {
		return nroChasis;
	}

	public void setNroChasis(String nroChasis) {
		this.nroChasis = nroChasis;
	}

	public String getNroChapa() {
		return nroChapa;
	}

	public void setNroChapa(String nroChapa) {
		this.nroChapa = nroChapa;
	}
	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}
}
