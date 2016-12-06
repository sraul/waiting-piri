package com.waitingpiri.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.waitingpiri.util.Util;

public class Usuario {

	private int id;
	private String nick;
	private String password;
	private String rol;
	private String perfil;
	
	private boolean cambiarPassword = false;
	
	public Usuario(int id, String nick, String password, String rol, String perfil) {
		this.id = id;
		this.nick = nick;
		this.password = password;
		this.rol = rol;
		this.perfil = perfil;
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
	
	public String getPasswordEncriptado() {
		return Util.encriptar(this.password);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
		this.cambiarPassword = true;
	}
	
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

	public boolean isCambiarPassword() {
		return cambiarPassword;
	}

	public void setCambiarPassword(boolean cambiarPassword) {
		this.cambiarPassword = cambiarPassword;
	}
}
