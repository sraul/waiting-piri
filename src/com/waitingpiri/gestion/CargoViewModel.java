package com.waitingpiri.gestion;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

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

import com.waitingpiri.domain.Cargo;
import com.waitingpiri.domain.Cargo;
import com.waitingpiri.domain.ConnectDB;
import com.waitingpiri.domain.Usuario;
import com.waitingpiri.util.DataUtil;
import com.waitingpiri.util.DBUtil;

@SuppressWarnings("unused")
public class CargoViewModel implements ABM {
	private String filterID="";
	private String filterDESCRIPCION="";
	
	private List<Cargo>cargosNuevos=new ArrayList<Cargo>();
	
	private Cargo selectedCargo;
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
		@NotifyChange({"modoEdicion","selectedCargo"})
		public void nuevo() {
			this.modoEdicion=true;
			this.selectedCargo=new Cargo(0,"");
			
		}


		@Command
		@NotifyChange({"modoEdicion", "selectedCargo"})
		public void editar() {
			this.modoEdicion=!this.modoEdicion;
			if (this.modoEdicion) {
				this.editando = true;
			} else {
			this.selectedCargo = null;
			this.editando = false;
			}		
		}	
		

		@Command
		@NotifyChange({"modoEdicion","cargosNuevos", "selectedCargo", " cargos"})
		public void guardar() {
			if (!this.validarDatos()) {
				Messagebox.show("Error de Datos, verifique..", "Validación de Datos..", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			if (!this.editando) {
				ConnectDB conn = ConnectDB.getInstance();
				try {
					conn.insertCargo(this.selectedCargo);
				} catch (Exception e) {
					Clients.showNotification("No se pudo guardar, hubo un error..", Clients.NOTIFICATION_TYPE_ERROR, null,
							null, 0);
				}			
			}		
			if(this.editando){
				ConnectDB conn = ConnectDB.getInstance();
				try {
					conn.updateCargo(this.selectedCargo);
				} catch (Exception e) {
					Clients.showNotification("No se pudo guardar, hubo un error..", Clients.NOTIFICATION_TYPE_ERROR, null,
							null, 0);
				}
			}
			this.selectedCargo = null;
			this.modoEdicion = false;
			this.editando = false;
			Clients.showNotification("Registro Guardado..");
	}

		@Override
		@Command
		@NotifyChange({ "selectedCargo", "cargo"})
		public void eliminar() {
			if (Messagebox.show("Desea eliminar el registro..", "Eliminar registro..", Messagebox.OK | Messagebox.CANCEL,
					Messagebox.QUESTION) == Messagebox.OK) {
				ConnectDB conn = ConnectDB.getInstance();
				try {
					conn.deleteCargo(this.selectedCargo);
					this.selectedCargo = null;
					Clients.showNotification("Registro eliminado..");
				} catch (Exception e) {
					Clients.showNotification("No se pudo eliminar, hubo un error..", Clients.NOTIFICATION_TYPE_ERROR, null,
							null, 0);
			
				}}}
		
		@Override
		@DependsOn("modoEdicion")
		public boolean isNuevoEnabled() {
			boolean out = true;		
			if (this.isModoEdicion()) {
				out = false;}
			return out;
		}
		
		@Override
		@DependsOn("selectedCargo")
		public boolean isEditarEnabled() {
			return this.selectedCargo != null;
		}
		@Override
		@DependsOn("modoEdicion")
		public boolean isGuardarEnabled() {
			return this.isModoEdicion();
		}
		@Override
		@DependsOn({ "selectedCargo", "modoEdicion" })
		public boolean isEliminarEnabled() {
			return this.selectedCargo != null && !this.isModoEdicion();
		}
		@Override
		public int getLastId() {
			// TODO Auto-generated method stub
			return 0;
		}
		/** Get and set **/
		
		@DependsOn({"filterID","filterDESCRIPCION"})
		public List<Cargo> getCargo(){
			ConnectDB conn = ConnectDB.getInstance();
		return conn.getCargos(this.filterID,this.filterDESCRIPCION);
		}
		@Override
		public boolean validarDatos() {
			boolean out=true;
			// campos obligatorios..
		if(this.selectedCargo.getDescripcion().trim().isEmpty()){
		 out=false;	
		}	
			return out;
		}
		public List<Cargo> getCargosNuevos() {
			return cargosNuevos;
		}
		public void setCargosNuevos(List<Cargo> cargosNuevos) {
			this.cargosNuevos = cargosNuevos;
		}
		public Cargo getSelectedCargo() {
			return selectedCargo;
		}
		public void setSelectedCargo(Cargo selectedCargo) {
			this.selectedCargo = selectedCargo;
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
		public String getFilterID() {
			return filterID;
		}
		public void setFilterID(String filterID) {
			this.filterID = filterID;
		}
		public String getFilterDESCRIPCION() {
			return filterDESCRIPCION;
		}
		public void setFilterDESCRIPCION(String filterDESCRIPCION) {
			this.filterDESCRIPCION = filterDESCRIPCION;
		}}