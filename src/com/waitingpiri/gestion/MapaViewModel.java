package com.waitingpiri.gestion;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;

import com.waitingpiri.domain.Colectivo;
import com.waitingpiri.domain.ConnectDB;

public class MapaViewModel {
	
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
	
	/**
	 * @return las localizaciones de los colectivos..
	 */
	public List<Localizacion> getLocalizaciones() {
		ConnectDB conn = ConnectDB.getInstance();
		List<Localizacion> out = new ArrayList<Localizacion>();
		for (Colectivo colectivo : this.getColectivos()) {
			List<Localizacion> locs = conn.getLocalizaciones(colectivo);
			if (locs.size() > 0) {
				out.add(locs.get(0));
			}			
		}
		return out;
	}
}
