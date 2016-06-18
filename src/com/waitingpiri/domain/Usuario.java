package com.waitingpiri.domain;

public class Usuario {

	private int id;
	private String nick;
	private String password;
	//private boolean activo;
	
	public Usuario(int id, String nick, String password) {
		this.id = id;
		this.nick = nick;
		this.password = password;
		//this.activo = activo;
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
}
