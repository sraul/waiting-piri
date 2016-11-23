package com.waitingpiri.gestion;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.Clients;

import com.waitingpiri.domain.ConnectDB;
import com.waitingpiri.domain.Usuario;

public class InicioViewModel {
	
	private String nick = "";
	private String password = "";
	
	public static Map<String, String> perfiles = new HashMap<String, String>();
	public static String rol = "";

	@Init
	public void init() {
	}
	
	@AfterCompose
	public void afterCompose() {
	}
	
	@Command
	public void login() {
		ConnectDB conn = ConnectDB.getInstance();
		Usuario usuario = conn.getUsuario(this.nick, this.password);
		if (usuario == null) {
			Clients.showNotification("No se encontro un usuario con los datos ingresados..",
					Clients.NOTIFICATION_TYPE_ERROR, null, null, 0);
		} else {
			rol = usuario.getRol();
			perfiles = new HashMap<String, String>();
			for (String perfil : usuario.getPerfiles()) {
				perfiles.put(perfil, perfil);
			}			
			Executions.sendRedirect("/waitingpiri/menuprincipal.zul");
		}
	}

	
	/**
	 * GETS / SETS
	 */
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
}
