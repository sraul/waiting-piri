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
import com.waitingpiri.domain.Horario;

public class InformacionesViewModel implements ABM {
	
	private String filterID = "";
	private String filterSalida= "";
	private String filterLlegada= "";
	
	private List<Horario> horariosNuevos = new ArrayList<Horario>();
	
	private Horario selectedHorario;
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
	@NotifyChange({ "modoEdicion", "selectedHorario" })
	public void nuevo() {
		this.modoEdicion=true;
		this.selectedHorario = new Horario( 0, "","" );		
	}

	@Command
	@NotifyChange({ "modoEdicion", "selectedHorario" })
	public void editar() {
		this.modoEdicion=!this.modoEdicion;
		if (this.modoEdicion) {
			this.editando = true;
		} else {
		this.selectedHorario = null;
		this.editando = false;
		}		
	}	

	@Command
	@NotifyChange({"modoEdicion","horariosNuevos", "selectedHorario", "horarios"})
	public void guardar() {
		if (!this.validarDatos()) {
			Messagebox.show("Error de Datos, verifique..", "Validacion de Datos..", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		if (!this.editando) {
			ConnectDB conn = ConnectDB.getInstance();
			try {
				conn.insertHorario(this.selectedHorario);
			} catch (Exception e) {
				Clients.showNotification("No se pudo guardar, hubo un error..", Clients.NOTIFICATION_TYPE_ERROR, null,
						null, 0);
			}
		}
		if (this.editando) {
			ConnectDB conn = ConnectDB.getInstance();
			try {
				conn.updateHorario(this.selectedHorario);
			} catch (Exception e) {
				Clients.showNotification("No se pudo guardar, hubo un error..", Clients.NOTIFICATION_TYPE_ERROR, null,
						null, 0);
				return;
			}
		}
		this.selectedHorario = null;
		this.modoEdicion = false;
		this.editando = false;
		Clients.showNotification("Registro Guardado..");
	}

	@Override
	@Command
	@NotifyChange({ "selectedHorario", "horarios" })
	public void eliminar() {
		if (Messagebox.show("Desea eliminar el registro..", "Eliminar registro..", Messagebox.OK | Messagebox.CANCEL,
				Messagebox.QUESTION) == Messagebox.OK) {
			ConnectDB conn = ConnectDB.getInstance();
			try {
				conn.deleteHorario(this.selectedHorario);
				this.selectedHorario = null;
				Clients.showNotification("Registro eliminado..");
			} catch (Exception e) {
				Clients.showNotification("No se pudo eliminar, hubo un error..", Clients.NOTIFICATION_TYPE_ERROR, null,
						null, 0);
			}
		}
	}
	
	@Override
	public int getLastId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean validarDatos() {
		boolean out = true;
		// campos obligatorios..
		if (this.selectedHorario.getSalida().trim().isEmpty()|| this.selectedHorario.getLlegada().trim().isEmpty()) {
			out = false;
		}
		return out;
	}
	
	
	/**
	 * GET / SET
	 */		
	@DependsOn({ "filterID", "filterSalida", "filterLlegada" })
	public List<Horario> getHorarios() {
		ConnectDB conn = ConnectDB.getInstance();
		return conn.getHorarios(this.filterID, this.filterSalida, this.filterLlegada);
	}	
	
	@Override
	@DependsOn("modoEdicion")
	public boolean isNuevoEnabled() {
		boolean out = true;		
		if (this.isModoEdicion()) {
			out = false;}
		return out;
	}
	
	public String getFilterLlegada() {
		return filterLlegada;
	}
	public void setFilterLlegada(String filterLlegada) {
		this.filterLlegada = filterLlegada;
	}
	@Override
	@DependsOn("selectedHorario")
	public boolean isEditarEnabled() {
		return this.selectedHorario != null;
	}

	@Override
	@DependsOn("modoEdicion")
	public boolean isGuardarEnabled() {
		return this.isModoEdicion();
	}

	@Override
	@DependsOn({ "selectedHorario", "modoEdicion" })
	public boolean isEliminarEnabled() {
		return this.selectedHorario != null && !this.isModoEdicion();
	}

	public String getFilterID() {
		return filterID;
	}

	public void setFilterID(String filterID) {
		this.filterID = filterID;
	}

	public String getFilterSalida() {
		return filterSalida;
	}

	public void setFiltersalida(String Filtersalida) {
		this.filterSalida = Filtersalida;
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

	public List<Horario> getHorariosNuevos() {
		return horariosNuevos;
	}

	public void setHorariosNuevos(List<Horario> horariosNuevos) {
		this.horariosNuevos = horariosNuevos;
	}

	public Horario getSelectedHorario() {
		return selectedHorario;
	}

	public void setSelectedHorario(Horario selectedHorario) {
		this.selectedHorario = selectedHorario;
	}
}

