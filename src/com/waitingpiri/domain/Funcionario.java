package com.waitingpiri.domain;

import java.util.HashMap;
import java.util.Map;

public class Funcionario {
	
	public static final int ID_CARGO_GERENTE = 1;
	public static final int ID_CARGO_AUXILIAR = 2;
	public static final int ID_CARGO_CHOFER = 3;

	private int id;
	private String nombreApellido;
	private String cedula;
	private String direccion;
	private String telefono;
	private int cargo;
	
	public Funcionario(int id, String nombreApellido, String cedula, String direccion, String telefono, int cargo) {
		this.id = id;
		this.nombreApellido = nombreApellido;
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
