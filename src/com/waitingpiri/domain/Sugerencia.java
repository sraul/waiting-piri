package com.waitingpiri.domain;

public class Sugerencia {
	private int id;
	private String nombre;
	private String mail;
	private String sugerencia;
	
	public Sugerencia(int id,String nombre,String mail, String sugerencia){
		this.id=id;
		this.nombre=nombre;
		this.mail=mail;
		this.sugerencia= sugerencia;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getSugerencia() {
		return sugerencia;
	}

	public void setSugerencia(String sugerencia) {
		this.sugerencia = sugerencia;
	}
	
	
}
