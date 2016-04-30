package com.waitingpiri.gestion;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.io.Files;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.util.Clients;

public class FuncionarioViewModel {
	
	static final String PATH_FOTOS_FUNCIONARIOS = Sessions.getCurrent().getWebApp().getRealPath("fotos")
			+ "/";

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
		
		System.out.println("-------------- DESTINO -----------------");
		System.out.println(destino);
		
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
	
}
