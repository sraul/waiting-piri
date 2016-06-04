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

import com.waitingpiri.domain.Funcionario;

public class FuncionarioViewModel implements ABM {
	
	static final String PATH_FOTOS_FUNCIONARIOS = Sessions.getCurrent().getWebApp().getRealPath("fotos")
			+ "/";
	
	private String filterID = "";
	private String filterNA = "";
	private String filterCI = "";
	
	private List<Funcionario> funcionariosNuevos = new ArrayList<Funcionario>();
	
	private Funcionario selectedFuncionario;
	
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
		this.selectedFuncionario = new Funcionario(this.getLastId(), "", "", "", "", Funcionario.ID_CARGO_AUXILIAR);
	}

	@Command
	@NotifyChange("modoEdicion")
	public void editar() {
		this.modoEdicion = !this.modoEdicion;	
		this.editando = true;
	}

	@Command
	@NotifyChange({ "modoEdicion", "funcionariosNuevos", "selectedFuncionario", "funcionarios_" })
	public void guardar() {
		if (!this.validarDatos()) {
			Messagebox.show("Error de Datos, verifique..", "Validación de Datos..", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		if (!this.editando) {
			this.getFuncionariosNuevos().add(this.selectedFuncionario);
		}		
		this.selectedFuncionario = null;
		this.modoEdicion = false;
		this.editando = false;
		Clients.showNotification("Registro Agregado..");
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int getLastId() {
		return 100;
	}
	
	@Override
	public boolean validarDatos() {
		boolean out = true;

		// campos obligatorios..
		if (this.selectedFuncionario.getNombreApellido().trim().isEmpty() || this.selectedFuncionario.getCedula().trim().isEmpty()
				|| this.selectedFuncionario.getTelefono().trim().isEmpty()) {
			out = false;
		}

		return out;
	}
	
	/**
	 * GET / SET
	 */	
	public List<Funcionario> getFuncionarios() {
		List<Funcionario> out = new ArrayList<Funcionario>();
		out.addAll(FuncionarioData.getFuncionariosData());
		out.addAll(this.funcionariosNuevos);
		return out;
	}
	
	@DependsOn({ "filterID", "filterNA", "filterCI" })
	public List<Funcionario> getFuncionarios_() {
		List<Funcionario> out = new ArrayList<Funcionario>();
		

		if (this.filterCI.isEmpty() && this.filterID.isEmpty() && this.filterNA.isEmpty()) {
			return this.getFuncionarios();
		}

		for (Funcionario func : this.getFuncionarios()) {

			// na y ci no estan vacio
			if ((!func.getNombreApellido().isEmpty()
					&& func.getNombreApellido().toLowerCase().indexOf(this.filterNA.toLowerCase()) >= 0)
					&& (!func.getCedula().isEmpty()
							&& func.getCedula().toLowerCase().indexOf(this.filterCI.toLowerCase()) >= 0)) {
				out.add(func);

				// ci vacio
			} else if ((!func.getNombreApellido().isEmpty()
					&& func.getNombreApellido().toLowerCase().indexOf(this.filterNA.toLowerCase()) >= 0)
					&& (func.getCedula().isEmpty())) {
				out.add(func);

				// na vacio
			} else if ((!func.getCedula().isEmpty()
					&& func.getCedula().toLowerCase().indexOf(this.filterCI.toLowerCase()) >= 0)
					&& (func.getNombreApellido().isEmpty())) {
				out.add(func);
			}
		}
		return out;
	}
	
	/**
	 * @return el cargo segun el id..
	 */
	public String getCargo(int idCargo) {
		return Funcionario.getCargos().get(idCargo);
	}
	
	/**
	 * @return el cargo segun el id..
	 */
	public String getIconCargo(String idCargo) {
		return "/images/cargo_" + idCargo + ".png";
	}
	
	/**
	 * @return los cargos..
	 */
	public List<String[]> getCargos() {
		List<String[]> out = new ArrayList<String[]>();
		for (Integer key : Funcionario.getCargos().keySet()) {
			String[] cargo = new String[] { key.toString(), this.getCargo(key) };
			out.add(cargo);
		}
		return out;
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
}

/**
 * Datos de prueba de funcionarios..
 */
class FuncionarioData {
	
	/**
	 * @return los funcionarios de prueba..
	 */
	public static List<Funcionario> getFuncionariosData() {
		List<Funcionario> out = new ArrayList<Funcionario>();

		String[] nombres = new String[] { "Juan Perez", "Luis Gimenez", "Lida Herrera", "Geronimo Rojas",
				"Hipolito Juarez", "Violeta Ruiz", "Damian Espinola", "Fabian Caceres", "Kike Hernandez",
				"Dario Lezcano" };

		String[] cedulas = new String[] { "3.500.200", "132.456", "1.369.874", "9.513.574", "6.314.785", "9.874.563",
				"789.562", "856.321", "6.321.457", "456.782" };

		String[] direcciones = new String[] { "Direccion1", "Direccion2", "Direccion3", "Direccion4", "Direccion5",
				"Direccion6", "Direccion7", "Direccion8", "Direccion9", "Direccion10" };

		String[] telefonos = new String[] { "Telefono1", "Telefono2", "Telefono3", "Telefono4", "Telefono5",
				"Telefono6", "Telefono7", "Telefono8", "Telefono9", "Telefono10" };
		Integer[] cargos= new Integer[]{Funcionario.ID_CARGO_GERENTE, Funcionario.ID_CARGO_CHOFER, Funcionario.ID_CARGO_AUXILIAR, Funcionario.ID_CARGO_CHOFER,Funcionario.ID_CARGO_CHOFER,
				Funcionario.ID_CARGO_CHOFER,Funcionario.ID_CARGO_AUXILIAR, Funcionario.ID_CARGO_CHOFER, Funcionario.ID_CARGO_CHOFER, Funcionario.ID_CARGO_CHOFER,Funcionario.ID_CARGO_CHOFER};
		
		for (int i = 0; i < 10; i++) {
			Funcionario func = new Funcionario(i + 1, nombres[i], cedulas[i], direcciones[i], telefonos[i], cargos[i]);
			out.add(func);
		}

		return out;
	}
	
}
