package com.waitingpiri.gestion;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkex.zul.Jasperreport;
import org.zkoss.zul.Button;
import org.zkoss.zul.Timer;
import org.zkoss.zul.Window;

import com.waitingpiri.domain.Colectivo;
import com.waitingpiri.domain.ConnectDB;
import com.waitingpiri.domain.Funcionario;
import com.waitingpiri.domain.Usuario;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class InformesViewModel {
	
	@Init
	public void init() {
	}
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
	}
	
	@Wire
	private Jasperreport report;
	
	@Wire
	private Window win;
	
	@Wire
	private Button verInfo;
	
	static final int FUNCIONARIOS = 1;
	static final int USUARIOS = 2;
	static final int COLECTIVOS = 3;
	
	static final String JASPER_FUNCIONARIOS = "/reportes/jasper/Funcionarios.jasper";
	static final String JASPER_USUARIOS = "/reportes/jasper/Usuarios.jasper";
	static final String JASPER_COLECTIVOS = "/reportes/jasper/Colectivos.jasper";
	
	static List<Object[]> reportes = new ArrayList<Object[]>();
	static List<Object[]> formatos = new ArrayList<Object[]>();
	
	static String DD_MM_YYYY = "dd-MM-yyyy";
	static String DD_MM_YY = "dd-MM-yy";
	static final NumberFormat FORMATTER = new DecimalFormat("###,###,##0");
	
	static {
		reportes.add(new Object[] { FUNCIONARIOS, "Funcionarios", true, false });
		reportes.add(new Object[] { USUARIOS, "Usuarios", true, true });
		reportes.add(new Object[] { COLECTIVOS, "Colectivos", true, true });
		
		formatos.add(new Object[] { "PDF", "pdf" });
		formatos.add(new Object[] { "HTML", "html" });
		formatos.add(new Object[] { "Word (RTF)", "rtf" });
		formatos.add(new Object[] { "Excel", "xls" });
		formatos.add(new Object[] { "Excel (JXL)", "jxl" });
		formatos.add(new Object[] { "CSV", "csv" });
		formatos.add(new Object[] { "OpenOffice (ODT)", "odt" });
	}
	
	private Object[] selectedReporte;
	private Object[] selectedFormato;
	
	private ReportConfig reportConfig = null;

	@Command("showReport")
	public void showReport() {
		
		this.verInfo.setDisabled(true);
		Clients.showBusy(this.report, "Procesando Información..");
		
		String source = null;
		JRDataSource dataSource = null;
		Map<String, Object> params = new HashMap<String, Object>();
		int reporte = (int) this.selectedReporte[0];

		switch (reporte) {
		
		case FUNCIONARIOS:
			source = JASPER_FUNCIONARIOS;
			dataSource = new FuncionariosDataSource();
			params.put("Empresa", "Empresa de Transporte Piribebuy S.A.");
			break;
		
		case USUARIOS:
			source = JASPER_USUARIOS;
			dataSource = new UsuariosDataSource();
			params.put("Empresa", "Empresa de Transporte Piribebuy S.A.");
			break;
			
		case COLECTIVOS:
			source = JASPER_COLECTIVOS;
			dataSource = new ColectivosDataSource();
			params.put("Empresa", "Empresa de Transporte Piribebuy S.A.");
			break;
		}
		reportConfig = new ReportConfig();
		reportConfig.setSource(source);
		reportConfig.setParameters(params);
		reportConfig.setDataSource(dataSource);
		
		Events.echoEvent("onLater", this.report, null);
	}	
	
	/**
	 * Cierra la ventana de progreso..
	 */
	@Command
	public void clearProgress() {
		Timer timer = new Timer();
		timer.setDelay(1000);
		timer.setRepeats(false);

		timer.addEventListener(Events.ON_TIMER, new EventListener() {
			@Override
			public void onEvent(Event evt) throws Exception {
				Clients.clearBusy(report);
				verInfo.setDisabled(false);
			}
		});
		timer.setParent(this.win);
	}
	
	/**
	 * DataSource de Funcionarios..
	 */
	class FuncionariosDataSource implements JRDataSource {
		
		List<Funcionario> funcionarios = new ArrayList<Funcionario>();
		
		public FuncionariosDataSource() {
			try {
				this.obtenerValores();
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }
		
		/**
		 * obtiene los datos de la bd..
		 */		
		private void obtenerValores() throws Exception {
			ConnectDB conn = ConnectDB.getInstance();
			this.funcionarios = conn.getFuncionarios("", "", "", "");
		}
		
		private int index = -1;

		@Override
		public Object getFieldValue(JRField field) throws JRException {
	        Object value = null;
	        String fieldName = field.getName();
	        Funcionario funcionario = this.funcionarios.get(index);
	        
	        if("Cedula".equals(fieldName)) {
	            value = funcionario.getCedula();
	        } else if("Nombre".equals(fieldName)) {
	            value = funcionario.getNombre();
	        } else if ("Apellido".equals(fieldName)) {
				value = funcionario.getApellido();
			} else if ("Telefono".equals(fieldName)) {
				value = funcionario.getTelefono();
			} else if ("Direccion".equals(fieldName)) {
					value = funcionario.getDireccion();}
	        return value;
	        
	    }

		@Override
		public boolean next() throws JRException {
			if (index < funcionarios.size() - 1) {
				index ++;
				return true;
			}
			return false;
		}		
	}
	
	/**
	 * DataSource de Usuarios..
	 */
	class UsuariosDataSource implements JRDataSource {
		
		List<Usuario> usuarios = new ArrayList<Usuario>();
		
		public UsuariosDataSource() {
			try {
				this.obtenerValores();
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }
		
		/**
		 * obtiene los datos de la bd..
		 */		
		private void obtenerValores() throws Exception {
			ConnectDB conn = ConnectDB.getInstance();
			this.usuarios = conn.getUsuarios("", "");
		}
		
		private int index = -1;

		@Override
		public Object getFieldValue(JRField field) throws JRException {
	        Object value = null;
	        String fieldName = field.getName();
	        Usuario usuario = this.usuarios.get(index);
	         
	        if ("Nick".equals(fieldName)) {
	            value = usuario.getNick();
	        }         
	        return value;
	    }

		@Override
		public boolean next() throws JRException {
			if (index < usuarios.size() - 1) {
				index ++;
				return true;
			}
			return false;
		}		
	}
	
	/**
	 * DataSource de Colectivos..
	 */
	class ColectivosDataSource implements JRDataSource {
		
		List<Colectivo> colectivos = new ArrayList<Colectivo>();
		
		public ColectivosDataSource() {
			try {
				this.obtenerValores();
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }
		
		/**
		 * obtiene los datos de la bd..
		 */		
		private void obtenerValores() throws Exception {
			ConnectDB conn = ConnectDB.getInstance();
			this.colectivos = conn.getColectivos("", "", "");
		}
		
		private int index = -1;

		@Override
		public Object getFieldValue(JRField field) throws JRException {
	        Object value = null;
	        String fieldName = field.getName();
	        Colectivo colectivo = this.colectivos.get(index);
	        
	        if ("Numero".equals(fieldName)) {
	            value = colectivo.getNroColec();
	        } else if ("Chapa".equals(fieldName)) {
				value = colectivo.getNroChapa();
			}  else if ("Chasis".equals(fieldName)) {
				value = colectivo.getNroChasis();
			}  
	        return value;
	    }

		@Override
		public boolean next() throws JRException {
			if (index < colectivos.size() - 1) {
				index ++;
				return true;
			}
			return false;
		}		
	}

	public Object[] getSelectedReporte() {
		return selectedReporte;
	}

	public void setSelectedReporte(Object[] selectedReporte) {
		this.selectedReporte = selectedReporte;
	}

	public Object[] getSelectedFormato() {
		return selectedFormato;
	}

	public void setSelectedFormato(Object[] selectedFormato) {
		this.selectedFormato = selectedFormato;
	}

	public List<Object[]> getReportes() {
		return reportes;
	}

	public static void setReportes(List<Object[]> reportes) {
		InformesViewModel.reportes = reportes;
	}

	public List<Object[]> getFormatos() {
		return formatos;
	}

	public static void setFormatos(List<Object[]> formatos) {
		InformesViewModel.formatos = formatos;
	}

	public ReportConfig getReportConfig() {
		return reportConfig;
	}

	public void setReportConfig(ReportConfig reportConfig) {
		this.reportConfig = reportConfig;
	}
}
