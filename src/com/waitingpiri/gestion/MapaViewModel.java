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

import com.waitingpiri.domain.Colectivo;
import com.waitingpiri.domain.ConnectDB;

public class MapaViewModel {
	
	static final int IDA = 1;
	static final int VUELTA = 2;
	
	static final Object[] MAPA_IDA = new Object[] { IDA, " DE ASUNCION A PIRIBEBUY" };
	static final Object[] MAPA_VUELTA = new Object[] { VUELTA, " DE PIRIBEBUY A ASUNCION" };
	
	private Object[] selectedMapa = MAPA_IDA;
	
	@Init
	public void init() {
	}
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireEventListeners(view, this);
	}
	
	@Command
	@NotifyChange({ "localizaciones" })
	public void test() {
	}
	
	/**
	 * @return los colectivos..
	 */
	public List<Colectivo> getColectivos() {
		ConnectDB conn = ConnectDB.getInstance();
		return conn.getColectivos("", "", "");
	}
	
	@DependsOn("selectedMapa")
	public List<Localizacion> getLocalizaciones() {
		return this.selectedMapa.equals(MAPA_IDA)? this.getLocalizacionesIda() : this.getLocalizacionesVuelta();
	}
	
	/**
	 * @return las localizaciones de los colectivos..
	 */
	public List<Localizacion> getLocalizacionesIda() {
		ConnectDB conn = ConnectDB.getInstance();
		List<Localizacion> out = new ArrayList<Localizacion>();
		for (Colectivo colectivo : this.getColectivos()) {
			List<Localizacion> locs = conn.getLocalizaciones(colectivo, 0);
			if (locs.size() > 0) {
				out.add(locs.get(0));
			}			
		}
		return out;
	}
	
	/**
	 * @return las localizaciones de los colectivos..
	 */
	public List<Localizacion> getLocalizacionesVuelta() {
		ConnectDB conn = ConnectDB.getInstance();
		List<Localizacion> out = new ArrayList<Localizacion>();
		for (Colectivo colectivo : this.getColectivos()) {
			List<Localizacion> locs = conn.getLocalizaciones(colectivo, 1);
			if (locs.size() > 0) {
				out.add(locs.get(0));
			}			
		}
		return out;
	}
	
	/**
	 * @return los destinos..
	 */
	public List<Object[]> getMapas() {
		List<Object[]> out = new ArrayList<Object[]>();
		out.add(MAPA_IDA);
		out.add(MAPA_VUELTA);
		return out;
	}

	public Object[] getSelectedMapa() {
		return selectedMapa;
	}

	public void setSelectedMapa(Object[] selectedMapa) {
		this.selectedMapa = selectedMapa;
	}
}
