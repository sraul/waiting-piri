package com.waitingpiri.gestion;

import java.util.EventListener;

import org.zkoss.bind.annotation.Command;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.event.Event;

public class MainMenuViewModel {
	
	@Command
	public void salir() {
		Messagebox.show("Esta seguro que desea Salir del Menu Principal?", "Question", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION);
		new EventListener(){
			@SuppressWarnings("unused")
			public void onEvent(Event e){
				if("onOK".equals(e.getName())){
					
				}else if("onCancel".equals(e.getName())){
					
				}
			}
		
		};	
	}
}
