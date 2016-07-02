package com.waitingpiri.domain;

import java.util.HashMap;
import java.util.Map;


public class Funcionario {
	
	private int id;
	private String nombre;
	private String apellido;
	private String cedula;
	private String direccion;
	private String telefono;
	private int cargo;
	
	public Funcionario(int id, String nombre, String apellido, String cedula, String direccion, String telefono, int cargo) {
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.cedula = cedula;
		this.direccion = direccion;
		this.telefono = telefono;
		this.cargo= cargo;
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

	public void setNombre(String nombreApellido) {
		this.nombre = nombreApellido;
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

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	
}
