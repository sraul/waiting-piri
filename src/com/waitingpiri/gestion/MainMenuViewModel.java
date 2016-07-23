package com.waitingpiri.gestion;

import org.zkoss.bind.annotation.Command;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;

public class MainMenuViewModel {
	
	@Command
	public void salir() {
		if (Messagebox.show("Esta seguro que desea Salir del Menu Principal?", "Question",
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION) == Messagebox.OK) {
			Executions.sendRedirect("/");
		}		
	}
}
