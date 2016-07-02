package com.waitingpiri.gestion;

public interface ABM {
	
	/**
	 * nuevo registro..
	 */
	//public void nuevo();
	
	/**
	 * modificacion del registro..
	 */
	public void editar();
	
	/**
	 * guardar el registro..
	 */
	public void guardar();
	
	/**
	 * eliminar el registro..
	 */
	public void eliminar();
	
	/**
	 * @return true si nuevo esta habilitado..
	 */
	public boolean isNuevoEnabled();
	
	/**
	 * @return true si editar esta habilitado..
	 */
	public boolean isEditarEnabled();
	
	/**
	 * @return true si guardar esta habilitado..
	 */
	public boolean isGuardarEnabled();
	
	/**
	 * @return true si eliminar esta habilitado..
	 */
	public boolean isEliminarEnabled();
	
	/**
	 * @return el ultimo id..
	 */
	public int getLastId();
	
	/**
	 * @return true si son datos validos..
	 */
	public boolean validarDatos();
}
