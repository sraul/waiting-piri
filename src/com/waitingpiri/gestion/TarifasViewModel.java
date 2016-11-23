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
import com.waitingpiri.domain.Tarifa;

public class TarifasViewModel implements ABM {

	private String filterID = "";
	private String filterDESCRIPCION = "";

	private List<Tarifa> tarifasNuevas = new ArrayList<Tarifa>();

	private Tarifa selectedTarifa;
	private boolean modoEdicion = false;
	private boolean editando = false;

	@Init
	public void init() {

	}

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireEventListeners(view, this);
	}

	/**
	 * FUNCIONES
	 */
	@Command
	@NotifyChange({ "modoEdicion", "selectedTarifa" })
	public void nuevo() {
		this.modoEdicion = true;
		this.selectedTarifa = new Tarifa(0, "", 0.0);
	}

	@Command
	@NotifyChange({ "modoEdicion", "selectedTarifa" })
	public void editar() {
		this.modoEdicion = !this.modoEdicion;
		if (this.modoEdicion) {
			this.editando = true;
		} else {
			this.selectedTarifa = null;
			this.editando = false;
		}
	}

	@Command
	@NotifyChange({ "modoEdicion", "tarifasNuevas", "selectedTarifa", "tarifas" })
	public void guardar() {
		if (!this.validarDatos()) {
			Messagebox.show("Error de Datos, verifique..", "Validacion de Datos..", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		if (!this.editando) {
			ConnectDB conn = ConnectDB.getInstance();
			try {
				conn.insertTarifa(this.selectedTarifa);
			} catch (Exception e) {
				Clients.showNotification("No se pudo guardar, hubo un error..", Clients.NOTIFICATION_TYPE_ERROR, null,
						null, 0);
			}
		}
		if (this.editando) {
			ConnectDB conn = ConnectDB.getInstance();
			try {
				conn.updateTarifa(this.selectedTarifa);
			} catch (Exception e) {
				Clients.showNotification("No se pudo guardar, hubo un error..", Clients.NOTIFICATION_TYPE_ERROR, null,
						null, 0);
				return;
			}
		}
		this.selectedTarifa = null;
		this.modoEdicion = false;
		this.editando = false;
		Clients.showNotification("Registro Guardado..");
	}

	@Override
	@Command
	@NotifyChange({ "selectedTarifa", "tarifas" })
	public void eliminar() {
		if (Messagebox.show("Desea eliminar el registro..", "Eliminar registro..", Messagebox.OK | Messagebox.CANCEL,
				Messagebox.QUESTION) == Messagebox.OK) {
			ConnectDB conn = ConnectDB.getInstance();
			try {
				conn.deleteTarifa(this.selectedTarifa);
				this.selectedTarifa = null;
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
		if (this.selectedTarifa.getDescripcion().trim().isEmpty()){
			out=false;
		}
		return out;
	}

	/**
	 * GET / SET
	 */
	@DependsOn({ "filterID", "filterDESCRIPCION" })
	public List<Tarifa> getTarifas() {
		ConnectDB conn = ConnectDB.getInstance();
		return conn.getTarifas(this.filterID, this.filterDESCRIPCION);
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
	@DependsOn("selectedTarifa")
	public boolean isEditarEnabled() {
		return this.selectedTarifa != null;
	}

	@Override
	@DependsOn("modoEdicion")
	public boolean isGuardarEnabled() {
		return this.isModoEdicion();
	}

	@Override
	@DependsOn({ "selectedTarifa", "modoEdicion" })
	public boolean isEliminarEnabled() {
		return this.selectedTarifa != null && !this.isModoEdicion();
	}

	public String getFilterID() {
		return filterID;
	}

	public void setFilterID(String filterID) {
		this.filterID = filterID;
	}

	public String getfilterDESCRIPCION() {
		return filterDESCRIPCION;
	}

	public void setfilterDESCRIPCION(String filterDESCRIPCION) {
		this.filterDESCRIPCION = filterDESCRIPCION;
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


	public List<Tarifa> getTarifasNuevas() {
		return tarifasNuevas;
	}

	public void setTarifasNuevas(List<Tarifa> tarifasNuevas) {
		this.tarifasNuevas = tarifasNuevas;
	}

	public Tarifa getSelectedTarifa() {
		return selectedTarifa;
	}

	public void setSelectedTarifa(Tarifa selectedTarifa) {
		this.selectedTarifa = selectedTarifa;
	}
}
