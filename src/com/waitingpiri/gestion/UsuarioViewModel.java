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
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;

import com.waitingpiri.domain.ConnectDB;
import com.waitingpiri.domain.Usuario;
public class UsuarioViewModel implements ABM {
	private String filterID="";
	private String filterNICK="";
	
	private List<Usuario>usuariosNuevos=new ArrayList<Usuario>();
	
	private Usuario selectedUsuario;
	
	private boolean modoEdicion=false;
	private boolean editando=false;
	
	@Init
	public void init(){
		
	}
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW)Component view){
		
	}
	
	@Command
	@NotifyChange({"modoEdicion", "selectedUsuario"})
	public void nuevo(){
		this.modoEdicion=true;
		this.selectedUsuario=new Usuario(0,"","");
	}
	
	@Command
	@NotifyChange("modoEdicion")
	public void editar(){
		this.modoEdicion=!this.modoEdicion;
		this.editando=true;
	}
	@Command
	@NotifyChange({"modoEdicion","usuariosNuevos","selectedUsuario","usuario"})
	public void guardar(){
		if(!this.validarDatos()){
			Messagebox.show("Error de datos, Verifique..","Validación de datos..",Messagebox.OK,Messagebox.ERROR);
		return;
				}
		if (!this.editando){
			ConnectDB conn=ConnectDB.getInstance();
			try{
				conn.insertUsuario(this.selectedUsuario);
			} catch (Exception e){
				Clients.showNotification("No se pudo guardar,hubo un error..",Clients.NOTIFICATION_TYPE_ERROR,null,null,0);
			}
		}
		this.selectedUsuario=null;
		this.modoEdicion=false;
		this.editando=false;
		Clients.showNotification("Registro Agregado..");
	}
	
	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getLastId() {
		// TODO Auto-generated method stub
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
	@DependsOn({"filterID","filterNICK"})
	public List<Usuario> getUsuario(){
		ConnectDB conn = ConnectDB.getInstance();
		return conn.getUsuarios(this.filterID,this.filterNICK);
		
		}
	@Override
	@DependsOn("modoEdicion")
	public boolean isGuardarEnabled() {
		return this.isModoEdicion();
	}
	@Override
	@DependsOn("selectedUsuario")
	public boolean isEditarEnabled() {

		return this.selectedUsuario !=null;
	}
	@Override
	@DependsOn("modoEdicion")
	public boolean isNuevoEnabled() {
		boolean out= true;
			if(this.isModoEdicion()){
				out=false;
			}
		return out;
	}
	
	@Override
	@DependsOn({"selectedUsuario","modoEdicion"})
	public boolean isEliminarEnabled() {
		
		return this.selectedUsuario !=null && !this.isModoEdicion();
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
		
	}
	


