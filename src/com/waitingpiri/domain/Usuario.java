package com.waitingpiri.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Usuario {

	private int id;
	private String nick;
	private String password;
	private String rol;
	private String perfil;
	//private boolean activo;
	
	public Usuario(int id, String nick, String password, String rol, String perfil) {
		this.id = id;
		this.nick = nick;
		this.password = password;
		this.rol = rol;
		this.perfil = perfil;
		//this.activo = activo;
	}
	
	public List<String> getPerfiles() {
		if (this.perfil.isEmpty()) return null;
		String[] perfs = this.perfil.split(";");
		return new ArrayList<String>(Arrays.asList(perfs));
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

/*	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
*/
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}
}
