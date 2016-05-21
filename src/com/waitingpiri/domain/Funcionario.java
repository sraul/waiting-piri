package com.waitingpiri.domain;

public class Funcionario {

	private int id;
	private String nombreApellido;
	private String cedula;
	private String direccion;
	private String telefono;
	private int cargo;
	
	public Funcionario(int id, String nombreApellido, String cedula, String direccion, String telefono) {
		this.id = id;
		this.nombreApellido = nombreApellido;
		this.cedula = cedula;
		this.direccion = direccion;
		this.telefono = telefono;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombreApellido() {
		return nombreApellido;
	}

	public void setNombreApellido(String nombreApellido) {
		this.nombreApellido = nombreApellido;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public int getCargo() {
		return cargo;
	}

	public void setCargo(int cargo) {
		this.cargo = cargo;
	}
	
}
