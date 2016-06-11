package com.waitingpiri.domain;

import java.util.HashMap;
import java.util.Map;

public class Funcionario {
	
	public static final int ID_CARGO_GERENTE = 1;
	public static final int ID_CARGO_AUXILIAR = 2;
	public static final int ID_CARGO_CHOFER = 3;

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
		this.cargo=cargo;
	}
	
	/**
	 * @return los cargos disponibles..
	 */
	public static final Map<Integer, String> getCargos() {
		Map<Integer, String> cargos = new HashMap<Integer, String>();		
		cargos.put(ID_CARGO_GERENTE, "Gerente Administrativo");
		cargos.put(ID_CARGO_AUXILIAR, "Auxiliar Administrativo");
		cargos.put(ID_CARGO_CHOFER, "Chofer");
		return cargos;
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
