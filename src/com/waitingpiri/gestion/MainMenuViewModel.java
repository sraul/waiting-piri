package com.waitingpiri.gestion;

import org.zkoss.bind.annotation.Command;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;

import com.waitingpiri.util.DataUtil;

public class MainMenuViewModel {
	
	@Command
	public void salir() {
		if (Messagebox.show("Esta seguro que desea Salir del Menu Principal?", "Question",
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == Messagebox.OK) {
			Executions.sendRedirect("/");
		}		
	}
	
	/**
	 * @return false si esta habilitado el perfil..
	 */
	public boolean isFuncionariosDisabled() {
		return InicioViewModel.perfiles.get(DataUtil.PERFIL_ADM_FUNCIONARIOS) == null;
	}
	
	/**
	 * @return false si esta habilitado el perfil..
	 */
	public boolean isUsuariosDisabled() {
		return InicioViewModel.perfiles.get(DataUtil.PERFIL_ADM_USUARIOS) == null;
	}
	
	/**
	 * @return false si esta habilitado el perfil..
	 */
	public boolean isColectivosDisabled() {
		return InicioViewModel.perfiles.get(DataUtil.PERFIL_ADM_COLECTIVOS) == null;
	}
	
	/**
	 * @return false si esta habilitado el perfil..
	 */
	public boolean isTarifasDisabled() {
		return InicioViewModel.perfiles.get(DataUtil.PERFIL_ADM_TARIFAS) == null;
	}
	
	/**
	 * @return false si esta habilitado el perfil..
	 */
	public boolean isHorariosDisabled() {
		return InicioViewModel.perfiles.get(DataUtil.PERFIL_ADM_HORARIOS) == null;
	}
	
	/**
	 * @return false si esta habilitado el perfil..
	 */
	public boolean isCargosDisabled() {
		return InicioViewModel.perfiles.get(DataUtil.PERFIL_ADM_CARGOS) == null;
	}
	
	/**
	 * @return false si esta habilitado el perfil..
	 */
	public boolean isInformesDisabled() {
		return InicioViewModel.perfiles.get(DataUtil.PERFIL_ADM_INFORMES) == null;
	}
	
	/**
	 * @return false si esta habilitado el perfil..
	 */
	public boolean isMonitorearDisabled() {
		return InicioViewModel.perfiles.get(DataUtil.PERFIL_ADM_MONITOREO) == null;
	}
}
