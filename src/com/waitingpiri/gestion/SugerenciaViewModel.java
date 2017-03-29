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

import com.waitingpiri.domain.Sugerencia;
import com.waitingpiri.domain.ConnectDB;
import com.waitingpiri.domain.Usuario;
import com.waitingpiri.util.DataUtil;
import com.waitingpiri.util.DBUtil;


	
	@SuppressWarnings("unused")
	public class SugerenciaViewModel implements ABM {
		private String filterID="";
		private String filterNOMBRE="";
		private String filterMail="";
		
		
		private Sugerencia selectedSugerencia;
		private Sugerencia nuevaSugerencia = new Sugerencia(0, "", "", "");
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
			@NotifyChange({"modoEdicion","selectedSugerencia"})
			public void nuevo() {				
			}


			@Command
			@NotifyChange({"modoEdicion", "selectedSugerencia"})
			public void editar() {
				this.modoEdicion=!this.modoEdicion;
				if (this.modoEdicion) {
					this.editando = true;
				} else {
				this.selectedSugerencia = null;
				this.editando = false;
				}		
			}	
			

			@Command
			@NotifyChange({})
			public void guardar() {
			}
				

			@Override
			@Command
			@NotifyChange({ "selectedSugerencia", "sugerencia"})
			public void eliminar() {
				if (Messagebox.show("Desea eliminar el registro..", "Eliminar registro..", Messagebox.OK | Messagebox.CANCEL,
						Messagebox.QUESTION) == Messagebox.OK) {
					ConnectDB conn = ConnectDB.getInstance();
					try {
						conn.deleteSugerencia(this.selectedSugerencia);
						this.selectedSugerencia = null;
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
				out = false;
			}
			return out;
		}

		@Override
		@DependsOn("selectedSugerencia")
		public boolean isEditarEnabled() {
			return this.selectedSugerencia != null;
		}

		@Override
		@DependsOn("modoEdicion")
		public boolean isGuardarEnabled() {
			return this.isModoEdicion();
		}

		@Override
		@DependsOn({ "selectedSugerencia", "modoEdicion" })
		public boolean isEliminarEnabled() {
			return this.selectedSugerencia != null && !this.isModoEdicion();
		}

		@Override
		public int getLastId() {
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Command
		@NotifyChange("*")
		public void addSugerencia() {
			this.nuevaSugerencia.setId(this.getLastId() + 1);
			ConnectDB conn = ConnectDB.getInstance();
			try {
				conn.insertSugerencia(this.nuevaSugerencia);
				this.nuevaSugerencia = new Sugerencia(0, "", "", "");
				Clients.showNotification("Sugerencia enviada..", 
						Clients.NOTIFICATION_TYPE_INFO, null, null, 0);
			} catch (Exception e) {
				e.printStackTrace();
				Clients.showNotification("Hubo un error al intentar enviar la sugerencia..", 
						Clients.NOTIFICATION_TYPE_ERROR, null, null, 0);
			}			
		}


		/** Get and set **/

		@DependsOn({ "filterID", "filterNOMBRE", "filterMail"})
		public List<Sugerencia> getSugerencia() {
			ConnectDB conn = ConnectDB.getInstance();
			return conn.getSugerencia(this.filterID, this.filterNOMBRE, this.filterMail);
		}
		
		public String getFilterMail() {
			return filterMail;
		}
		public void setFilterMail(String filterMail) {
			this.filterMail = filterMail;
		}
		public void setSelectedSugerencia(Sugerencia selectedSugerencia) {
			this.selectedSugerencia = selectedSugerencia;
		}
		public Sugerencia getSelectedSugerencia() {
			return selectedSugerencia;
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

		public String getFilterNOMBRE() {
			return filterNOMBRE;
		}

		public void setFilterNOMBRE(String filterNOMBRE) {
			this.filterNOMBRE = filterNOMBRE;
		}
		@Override
		public boolean validarDatos() {
			// TODO Auto-generated method stub
			return false;
		}
		@Override
		public boolean isConsulta() {
			// TODO Auto-generated method stub
			return false;
		}
		public Sugerencia getNuevaSugerencia() {
			return nuevaSugerencia;
		}
		public void setNuevaSugerencia(Sugerencia nuevaSugerencia) {
			this.nuevaSugerencia = nuevaSugerencia;
		}

}
