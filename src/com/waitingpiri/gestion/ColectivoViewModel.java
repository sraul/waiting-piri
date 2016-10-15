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
import com.waitingpiri.domain.Colectivo;
public class ColectivoViewModel implements ABM{
	
	private String filterID="";
	private String filterNROCOL="";
	private String filterCHAPA="";
	
	private List<Colectivo> colectivosNuevos=new ArrayList<Colectivo>();
	
	private Colectivo selectedColectivo;
	private boolean modoEdicion=false;
	private boolean editando=false;
	
	@Init
	public void init(){
		
	}
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireEventListeners(view, this);
	}
	/**
	 * FUNCIONES
	 */
	@Command
	@NotifyChange({"modoEdicion","selectedColectivo"})
	public void nuevo() {
		this.modoEdicion=true;
		this.selectedColectivo=new Colectivo(0,"","","","");
		
	}

	@Command
	@NotifyChange({"modoEdicion", "selectedColectivo"})
	public void editar() {
		this.modoEdicion=!this.modoEdicion;
		if (this.modoEdicion) {
			this.editando = true;
		} else {
		this.selectedColectivo = null;
		this.editando = false;
		}		
	}	
	

	@Command
	@NotifyChange({"modoEdicion","colectivosNuevos", "selectedColectivo", "colectivos"})
	public void guardar() {
		if (!this.validarDatos()) {
			Messagebox.show("Error de Datos, verifique..", "Validación de Datos..", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		if (!this.editando) {
			ConnectDB conn = ConnectDB.getInstance();
			try {
				conn.insertColectivo(this.selectedColectivo);
			} catch (Exception e) {
				Clients.showNotification("No se pudo guardar, hubo un error..", Clients.NOTIFICATION_TYPE_ERROR, null,
						null, 0);
			}			
		}		
		if(this.editando){
			ConnectDB conn = ConnectDB.getInstance();
			try {
				conn.updateColectivo(this.selectedColectivo);
			} catch (Exception e) {
				Clients.showNotification("No se pudo guardar, hubo un error..", Clients.NOTIFICATION_TYPE_ERROR, null,
						null, 0);
				return;
			}
		}
		this.selectedColectivo = null;
		this.modoEdicion = false;
		this.editando = false;
		Clients.showNotification("Registro Guardado..");
}

	@Override
	@Command
	@NotifyChange({ "selectedColectivo", "colectivos"})
	public void eliminar() {
		if (Messagebox.show("Desea eliminar el registro..", "Eliminar registro..", Messagebox.OK | Messagebox.CANCEL,
				Messagebox.QUESTION) == Messagebox.OK) {
			ConnectDB conn = ConnectDB.getInstance();
			try {
				conn.deleteColectivo(this.selectedColectivo);
				this.selectedColectivo = null;
				Clients.showNotification("Registro eliminado..");
			} catch (Exception e) {
				Clients.showNotification("No se pudo eliminar, hubo un error..", Clients.NOTIFICATION_TYPE_ERROR, null,
						null, 0);
		
			}}}
	@Override
	public int getLastId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean validarDatos() {
		boolean out=true;
		// campos obligatorios..
	if(this.selectedColectivo.getNroColec().trim().isEmpty()|| this.selectedColectivo.getNroChasis().trim().isEmpty()|| 
			this.selectedColectivo.getNroChapa().trim().isEmpty()){
		out=false;
		}	
		return out;
	}
	/**
	 * GET / SET
	 */		
	@DependsOn({ "filterID", "filterNROCOL", "filterCHAPA" })
	public List<Colectivo> getColectivos() {
		ConnectDB conn = ConnectDB.getInstance();
		return conn.getColectivos(this.filterID, this.filterNROCOL, this.filterCHAPA);
	}	
	

	@Override
	@DependsOn("modoEdicion")
	public boolean isNuevoEnabled() {
		boolean out = true;		
		if (this.isModoEdicion()) {
			out = false;}
		return out;
	}
	
	@Override
	@DependsOn("selectedColectivo")
	public boolean isEditarEnabled() {
		return this.selectedColectivo != null;
	}
	
	
	@Override
	@DependsOn("modoEdicion")
	public boolean isGuardarEnabled() {
		return this.isModoEdicion();
	}

	@Override
	@DependsOn({ "selectedColectivo", "modoEdicion" })
	public boolean isEliminarEnabled() {
		return this.selectedColectivo != null && !this.isModoEdicion();
	}

	public String getFilterID() {
		return filterID;
	}
	public void setFilterID(String filterID) {
		this.filterID = filterID;
	}
	public String getFilterNROCOL() {
		return filterNROCOL;
	}
	public void setFilterNROCOL(String filterNROCOL) {
		this.filterNROCOL = filterNROCOL;
	}
	public String getFilterCHAPA() {
		return filterCHAPA;
	}
	public void setFilterCHAPA(String filterCHAPA) {
		this.filterCHAPA = filterCHAPA;
	}
	public List<Colectivo> getColectivosNuevos() {
		return colectivosNuevos;
	}
	public void setColectivosNuevos(List<Colectivo> colectivosNuevos) {
		this.colectivosNuevos = colectivosNuevos;
	}
	public Colectivo getSelectedColectivo() {
		return selectedColectivo;
	}
	public void setSelectedColectivo(Colectivo selectedColectivo) {
		this.selectedColectivo = selectedColectivo;
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
	
	

}

