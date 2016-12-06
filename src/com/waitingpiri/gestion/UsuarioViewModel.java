package com.waitingpiri.gestion;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.DependsOn;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;

import com.waitingpiri.domain.ConnectDB;
import com.waitingpiri.domain.Usuario;
import com.waitingpiri.util.DataUtil;

public class UsuarioViewModel implements ABM {
	
	private String filterID = "";
	private String filterNICK = "";
	
	private List<Usuario> usuariosNuevos = new ArrayList<Usuario>();
	
	private Usuario selectedUsuario;
	private String selectedPerfil;
	private String selectedPerfilUsuario;
	
	private boolean modoEdicion = false;
	private boolean editando = false;
	
	@Init
	public void init() {		
	}
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireEventListeners(view, this);
	}
	
	@Command
	@NotifyChange({ "selectedPerfil", "selectedUsuario" })
	public void addPerfil() {
		if (Messagebox.show("Desea agregar el Perfil?", "Agregar Perfil",Messagebox.OK | Messagebox.CANCEL,
				Messagebox.QUESTION) == Messagebox.OK) {
			String old = this.selectedUsuario.getPerfil();
			if (!old.isEmpty()) {
				old += ";";
			}
			this.selectedUsuario.setPerfil(old + this.selectedPerfil);
			this.selectedPerfil = null;
		}
	}
	
	@Command
	@NotifyChange({ "selectedPerfilUsuario", "selectedUsuario" })
	public void removePerfil() {
		if (Messagebox.show("Desea remover el Perfil?", "Remover Perfil",Messagebox.OK | Messagebox.CANCEL,
				Messagebox.QUESTION) == Messagebox.OK) {
			String replace = this.selectedPerfilUsuario;
			if(this.selectedUsuario.getPerfil().contains(";")) replace = ";" + this.selectedPerfilUsuario;
			this.selectedUsuario.setPerfil(this.selectedUsuario.getPerfil().replace(replace, ""));
			this.selectedPerfilUsuario = null;
		}
	}
	
	
	@Command
	@NotifyChange({"modoEdicion", "selectedUsuario"})
	public void nuevo() {
		this.modoEdicion = true;
		this.selectedUsuario = new Usuario(0,"","", "", "");
	}
	

	@Command
	@NotifyChange({"modoEdicion", "selectedUsuario"})
	public void editar() {
		this.modoEdicion=!this.modoEdicion;
		if (this.modoEdicion) {
			this.editando = true;
		} else {
		this.selectedUsuario = null;
		this.editando = false;
		}		
	}	
	

	@Command
	@NotifyChange({ "modoEdicion", "usuariosNuevos", "selectedUsuario", "usuario" })
	public void guardar() {
		if (!this.validarDatos()) {
			Messagebox.show("Error de Datos, verifique..", "Validacion de Datos..", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		if (!this.editando) {
			ConnectDB conn = ConnectDB.getInstance();
			try {
				conn.insertUsuario(this.selectedUsuario);
			} catch (Exception e) {
				Clients.showNotification("No se pudo guardar, hubo un error..", Clients.NOTIFICATION_TYPE_ERROR, null,
						null, 0);
			}
		}
		if (this.editando) {
			ConnectDB conn = ConnectDB.getInstance();
			try {
				if (this.selectedUsuario.isCambiarPassword()) {
					conn.updateUsuarioAndPassword(this.selectedUsuario);
				} else {
					conn.updateUsuario(this.selectedUsuario);
				}				
			} catch (Exception e) {
				Clients.showNotification("No se pudo guardar, hubo un error..", Clients.NOTIFICATION_TYPE_ERROR, null,
						null, 0);
			}
		}

		this.selectedUsuario = null;
		this.modoEdicion = false;
		this.editando = false;
		Clients.showNotification("Registro Guardado..");
	}

	@Override
	@Command
	@NotifyChange({ "selectedUsuario", "usuario" })
	public void eliminar() {
		if (Messagebox.show("Desea eliminar el Registro?", "Eliminar Registro", 
				Messagebox.OK | Messagebox.CANCEL,
				Messagebox.QUESTION) == Messagebox.OK) {
			ConnectDB conn = ConnectDB.getInstance();
			try {
				conn.deleteUsuario(this.selectedUsuario);
				this.selectedUsuario = null;
				Clients.showNotification("Registro Eliminado..");

			} catch (Exception e) {
				Clients.showNotification("No se pudo eliminar, hubo un error..", Clients.NOTIFICATION_TYPE_ERROR, null,
						null, 0);
			}
		}
	}

	@Override
	public int getLastId() {
		return 0;
	}

	@Override
	public boolean validarDatos() {
		boolean out=true;
		
			if(this.selectedUsuario.getNick().trim().isEmpty()||this.selectedUsuario.getPassword().trim().isEmpty()){
				out=false;
			}
		return out;
	}
	
	
	/**
	 * GET / SET
	 */
	
	@DependsOn({ "filterID", "filterNICK" })
	public List<Usuario> getUsuario() {
		ConnectDB conn = ConnectDB.getInstance();
		return conn.getUsuarios(this.filterID, this.filterNICK);

	}

	@Override
	@DependsOn("modoEdicion")
	public boolean isGuardarEnabled() {
		return this.isModoEdicion();
	}

	@Override
	@DependsOn("selectedUsuario")
	public boolean isEditarEnabled() {

		return this.selectedUsuario != null;
	}

	@Override
	@DependsOn("modoEdicion")
	public boolean isNuevoEnabled() {
		boolean out = true;
		if (this.isModoEdicion()) {
			out = false;
		}
		return out;
	}

	@Override
	@DependsOn({ "selectedUsuario", "modoEdicion" })
	public boolean isEliminarEnabled() {
		return this.selectedUsuario != null && !this.isModoEdicion();
	}

	@Override
	public boolean isConsulta() {
		return InicioViewModel.rol.equals(DataUtil.ROL_CONSULTA);
	}

	public List<String> getRoles() {
		return DataUtil.getRoles();
	}

	@DependsOn("selectedUsuario.perfil")
	public List<String> getPerfiles() {
		List<String> out = new ArrayList<String>();
		out.addAll(DataUtil.getPerfiles());
		if(this.selectedUsuario != null && this.selectedUsuario.getPerfiles() != null)
		out.removeAll(this.selectedUsuario.getPerfiles());
		return out;
	}

	public String getFilterID() {
		return filterID;
	}

	public void setFilterID(String filterID) {
		this.filterID = filterID;
	}

	public String getFilterNICK() {
		return filterNICK;
	}

	public void setFilterNICK(String filterNICK) {
		this.filterNICK = filterNICK;
	}

	public List<Usuario> getUsuariosNuevos() {
		return usuariosNuevos;
	}

	public void setUsuariosNuevos(List<Usuario> usuariosNuevos) {
		this.usuariosNuevos = usuariosNuevos;
	}

	public Usuario getSelectedUsuario() {
		return selectedUsuario;
	}

	public void setSelectedUsuario(Usuario selectedUsuario) {
		this.selectedUsuario = selectedUsuario;
	}

	public boolean isModoEdicion() {
		return modoEdicion;
	}

	public void setModoEdicion(boolean modoEdicion) {
		this.modoEdicion = modoEdicion;
	}

	public boolean isEditando() {
		return editando;
	}

	public void setEditando(boolean editando) {
		this.editando = editando;
	}

	public String getSelectedPerfil() {
		return selectedPerfil;
	}

	public void setSelectedPerfil(String selectedPerfil) {
		this.selectedPerfil = selectedPerfil;
	}

	public String getSelectedPerfilUsuario() {
		return selectedPerfilUsuario;
	}

	public void setSelectedPerfilUsuario(String selectedPerfilUsuario) {
		this.selectedPerfilUsuario = selectedPerfilUsuario;
	}		
}
	


