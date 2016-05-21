package com.waitingpiri.gestion;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.DependsOn;
import org.zkoss.bind.annotation.Init;
import org.zkoss.io.Files;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.util.Clients;

import com.waitingpiri.domain.Funcionario;

public class FuncionarioViewModel {
	
	static final String PATH_FOTOS_FUNCIONARIOS = Sessions.getCurrent().getWebApp().getRealPath("fotos")
			+ "/";
	
	private String filterID = "";
	private String filterNA = "";
	private String filterCI = "";
	
	private Funcionario selectedFuncionario;

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
	
	/**
	 * GET / SET
	 */
	
	public List<Funcionario> getFuncionarios() {
		List<Funcionario> out = new ArrayList<Funcionario>();
		out.addAll(FuncionarioData.getFuncionariosData());
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
		
		String[] cedulas = new String[] {"3.500.200", "132.456", "1.369.874", "9.513.574", "6.314.785", "9.874.563", "789.562", "856.321","6.321.457","456.782"};

		String[] direcciones = new String[]{};
		
		String[] telefonos = new String[]{};
 		
		for (int i = 0; i < 10; i++) {
			Funcionario func = new Funcionario(i + 1, nombres[i], cedulas[i], direcciones[i], telefonos[i]);
			out.add(func);
		}

		return out;
	}
	
}
