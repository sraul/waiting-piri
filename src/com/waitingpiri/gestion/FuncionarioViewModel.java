package com.waitingpiri.gestion;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.DependsOn;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.io.Files;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;

import com.waitingpiri.domain.Cargo;
import com.waitingpiri.domain.ConnectDB;
import com.waitingpiri.domain.Funcionario;

public class FuncionarioViewModel implements ABM {
	
	static final String PATH_FOTOS_FUNCIONARIOS = Sessions.getCurrent().getWebApp().getRealPath("fotos")
			+ "/";
	
	private String filterID = "";
	private String filterNA = "";
	private String filterAP = "";
	private String filterCI = "";
	
	private List<Funcionario> funcionariosNuevos = new ArrayList<Funcionario>();
	
	private Funcionario selectedFuncionario;
	
	private boolean modoEdicion = false;
	private boolean editando = true;

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
	
	@Listen("onUpload=#fotoUpload")
	public void subirFoto(UploadEvent event) throws Exception {
		this.subirImagen(event);
		Executions.sendRedirect("/waitingpiri/funcionarios.zul");
	}
	
	/**
	 * upload de la imagen..
	 */
	private void subirImagen(UploadEvent event) throws IOException {
		String fileName = "funcionario";
		String folder = PATH_FOTOS_FUNCIONARIOS;
		this.uploadFile(folder, fileName, event);
		Clients.showNotification("Imagen correctamente subida..");
	}
	
	/**
	 * Este método sirve para cuando queremos subir archivos al servidor lo que
	 * hace es recibir como parametro el evento tipo upload la ruta del
	 * directorio y el nombre del archivo y tambien el tipo de archivo para
	 * controlar si se quieren subir imagenes o docs..
	 * */
	private void uploadFile(String folder, String fileName,
			UploadEvent event) throws IOException {

		String format = event.getMedia().getFormat().toLowerCase();
		InputStream file = event.getMedia().getStreamData();
		String destino = folder + fileName + "." + format;
		
		this.copiarArchivo(file, destino);
	}
	
	/**
	 * Recibe un archivo y lo copia a un directorio destino..
	 */
	private void copiarArchivo(InputStream file, String destino)
			throws IOException {
		File dst = new File(destino);
		Files.copy(dst, file);
	}
	
	@Command
	@NotifyChange({ "modoEdicion", "selectedFuncionario" })
	public void nuevo() {
		this.modoEdicion = true;
		this.selectedFuncionario = new Funcionario(0, "", "", "", "", "", new Cargo(0, ""));
	}

	@Command
	@NotifyChange({ "modoEdicion", "selectedFuncionario" })
	public void editar() {
		this.modoEdicion = !this.modoEdicion;	
		if (this.modoEdicion) {
			this.editando = true;
		} else {
			this.selectedFuncionario = null;
			this.editando = false;
		}		
	}

	@Command
	@NotifyChange({ "modoEdicion", "funcionariosNuevos", "selectedFuncionario", "funcionarios" })
	public void guardar() {
		if (!this.validarDatos()) {
			Messagebox.show("Error de Datos, verifique..", "Validación de Datos..", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		if (!this.editando) {
			ConnectDB conn = ConnectDB.getInstance();
			try {
				conn.insertFuncionario(this.selectedFuncionario);
			} catch (Exception e) {
				Clients.showNotification("No se pudo guardar, hubo un error..", Clients.NOTIFICATION_TYPE_ERROR, null,
						null, 0);
			}			
		}		
		if(this.editando){
			ConnectDB conn = ConnectDB.getInstance();
			try {
				conn.updateFuncionario(this.selectedFuncionario);
			} catch (Exception e) {
				Clients.showNotification("No se pudo guardar, hubo un error..", Clients.NOTIFICATION_TYPE_ERROR, null,
						null, 0);
			}
		}
		this.selectedFuncionario = null;
		this.modoEdicion = false;
		this.editando = false;
		Clients.showNotification("Registro Guardado..");
	}

	@Override
	@Command
	@NotifyChange({ "selectedFuncionario", "funcionarios" })
	public void eliminar() {
		if (Messagebox.show("Desea eliminar el registro..", "Eliminar registro..", Messagebox.OK | Messagebox.CANCEL,
				Messagebox.QUESTION) == Messagebox.OK) {
			ConnectDB conn = ConnectDB.getInstance();
			try {
				conn.deleteFuncionario(this.selectedFuncionario);
				this.selectedFuncionario = null;
				Clients.showNotification("Registro eliminado..");
			} catch (Exception e) {
				Clients.showNotification("No se pudo guardar, hubo un error..", Clients.NOTIFICATION_TYPE_ERROR, null,
						null, 0);
			}
		}
	}
	
	@Override
	public int getLastId() {
		return 100;
	}
	
	@Override
	public boolean validarDatos() {
		boolean out = true;

		// campos obligatorios..
		if (this.selectedFuncionario.getNombre().trim().isEmpty() || this.selectedFuncionario.getCedula().trim().isEmpty()
				|| this.selectedFuncionario.getTelefono().trim().isEmpty()) {
			out = false;
		}

		return out;
	}
	
	/**
	 * GET / SET
	 */		
	@DependsOn({ "filterID", "filterNA", "filterAP", "filterCI" })
	public List<Funcionario> getFuncionarios() {
		ConnectDB conn = ConnectDB.getInstance();
		return conn.getFuncionarios(this.filterID, this.filterNA, this.filterAP, this.filterCI);
	}	
	
	@Override
	@DependsOn("modoEdicion")
	public boolean isGuardarEnabled() {		
		return this.isModoEdicion();
	}

	@Override
	@DependsOn("selectedFuncionario")
	public boolean isEditarEnabled() {
		return this.selectedFuncionario != null;
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
	@DependsOn({ "selectedFuncionario", "modoEdicion" })
	public boolean isEliminarEnabled() {
		return this.selectedFuncionario != null && !this.isModoEdicion();
	}
	
	/**
	 * @return los cargos..
	 */
	
	
	public List<Cargo>  getCargo() {
		ConnectDB conn = ConnectDB.getInstance();
		return conn.getCargos(filterAP,filterNA);
	}

	public String getFilterID() {
		return filterID;
	}

	public void setFilterID(String filterID) {
		this.filterID = filterID;
	}

	public String getFilterNA() {
		return filterNA;
	}

	public void setFilterNA(String filterNA) {
		this.filterNA = filterNA;
	}

	public String getFilterCI() {
		return filterCI;
	}

	public void setFilterCI(String filterCI) {
		this.filterCI = filterCI;
	}

	public Funcionario getSelectedFuncionario() {
		return selectedFuncionario;
	}

	public void setSelectedFuncionario(Funcionario selectedFuncionario) {
		this.selectedFuncionario = selectedFuncionario;
	}

	public boolean isModoEdicion() {
		return modoEdicion;
	}

	public void setModoEdicion(boolean soloLectura) {
		this.modoEdicion = soloLectura;
	}

	public List<Funcionario> getFuncionariosNuevos() {
		return funcionariosNuevos;
	}

	public void setFuncionariosNuevos(List<Funcionario> funcionariosNuevos) {
		this.funcionariosNuevos = funcionariosNuevos;
	}

	public String getFilterAP() {
		return filterAP;
	}

	public void setFilterAP(String filterAP) {
		this.filterAP = filterAP;
	}
}
