package com.waitingpiri.util;

import java.util.ArrayList;
import java.util.List;

import com.waitingpiri.domain.Cargo;
import com.waitingpiri.domain.Colectivo;
import com.waitingpiri.domain.Funcionario;
import com.waitingpiri.domain.Usuario;
import com.waitingpiri.domain.Horario;
import com.waitingpiri.domain.Tarifa;
import com.waitingpiri.domain.Sugerencia;

public class DataUtil {
	
	public static final String ROL_ADMINISTRADOR = "ADMINISTRADOR";
	public static final String ROL_OPERADOR = "OPERADOR";
	public static final String ROL_CONSULTA = "CONSULTA";
	
	public static final String PERFIL_ADM_FUNCIONARIOS = "ADMINISTRAR FUNCIONARIOS";
	public static final String PERFIL_ADM_USUARIOS = "ADMINISTRAR USUARIOS";
	public static final String PERFIL_ADM_COLECTIVOS = "ADMINISTRAR COLECTIVOS";
	public static final String PERFIL_ADM_TARIFAS = "ADMINISTRAR TARIFAS";
	public static final String PERFIL_ADM_HORARIOS = "ADMINISTRAR HORARIOS";
	public static final String PERFIL_ADM_CARGOS = "ADMINISTRAR CARGOS";
	public static final String PERFIL_ADM_INFORMES = "ADMINISTRAR INFORMES";
	public static final String PERFIL_ADM_MONITOREO = "MONITOREAR COLECTIVOS";
	
	/**
	 * @return los roles..
	 */
	public static List<String> getRoles() {
		List<String> out = new ArrayList<String>();
		out.add(ROL_ADMINISTRADOR);
		out.add(ROL_OPERADOR);
		out.add(ROL_CONSULTA);
		return out;
	}
	
	/**
	 * @return los roles..
	 */
	public static List<String> getPerfiles() {
		List<String> out = new ArrayList<String>();
		out.add(PERFIL_ADM_FUNCIONARIOS);
		out.add(PERFIL_ADM_USUARIOS);
		out.add(PERFIL_ADM_COLECTIVOS);
		out.add(PERFIL_ADM_TARIFAS);
		out.add(PERFIL_ADM_HORARIOS);
		out.add(PERFIL_ADM_CARGOS);
		out.add(PERFIL_ADM_INFORMES);
		out.add(PERFIL_ADM_MONITOREO);
		return out;
	}
	
	/**
	 * @return los datos de cargos..
	 */
	public static List<Cargo> getCargosData() {
		List<Cargo> out = new ArrayList<Cargo>();
		String[] descripcion = new String[] { "GERENTE", "AUXILIAR ADMINISTRATIVO", "CHOFER"};
		for (int i = 0; i < descripcion.length; i++) {
			Cargo car = new Cargo(i + 1, descripcion[i]);
			out.add(car);
		}
		return out;
	}

	/**
	 * @return datos de funcionarios..
	 */
	public static List<Funcionario> getFuncionariosData() {
		List<Funcionario> out = new ArrayList<Funcionario>();
		List<Cargo> cargos = DataUtil.getCargosData();

		String[] nombres = new String[] { "Juan", "Luis", "Lida", "Geronimo",
				"Hipolito", "Violeta", "Damian", "Fabian", "Alberto",
				"Dario" };

		String[] apellidos = new String[] { "Perez", "Gimenez", "Herrera",
				"Rojas", "Juarez", "Ruiz", "Espinola", "Caceres", "Hernandez",
				"Lezcano" };

		String[] cedulas = new String[] { "3.500.200", "132.456", "1.369.874",
				"9.513.574", "6.314.785", "9.874.563", "789.562", "856.321",
				"6.321.457", "456.782" };

		String[] direcciones = new String[] { "Direccion1", "Direccion2",
				"Direccion3", "Direccion4", "Direccion5", "Direccion6",
				"Direccion7", "Direccion8", "Direccion9", "Direccion10" };

		String[] telefonos = new String[] { "Telefono1", "Telefono2",
				"Telefono3", "Telefono4", "Telefono5", "Telefono6",
				"Telefono7", "Telefono8", "Telefono9", "Telefono10" };
	
		Cargo[] cargos_ = new Cargo[] { cargos.get(0), cargos.get(1), cargos.get(2), cargos.get(0), cargos.get(1),
				cargos.get(2), cargos.get(0), cargos.get(1), cargos.get(2), cargos.get(0) };

		for (int i = 0; i < 10; i++) {
			Funcionario func = new Funcionario(i + 1, nombres[i], apellidos[i],
					cedulas[i], direcciones[i], telefonos[i], cargos_[i]);
			out.add(func);
		}

		return out;
	}

	/**
	 * @return los datos de usuarios..
	 */
	public static List<Usuario> getUsuariosData() {
		List<Usuario> out = new ArrayList<Usuario>();

		String[] nick = new String[] { "jesrazal", "Lizilla", "Gabybu",
				"MarE", "SergioA", "DanielV", "cesarR", "DianaG", "AnaA",
				"RoqueA" };

		String[] password = new String[] { Util.encriptar("13254"), Util.encriptar("987541"), Util.encriptar("65495"),
				Util.encriptar("46584"), Util.encriptar("65485"), Util.encriptar("3219"), Util.encriptar("984325"),
				Util.encriptar("98435"), Util.encriptar("6314"), Util.encriptar("51889") };
	
		String[] roles = new String[] { ROL_ADMINISTRADOR, ROL_OPERADOR, ROL_CONSULTA, ROL_ADMINISTRADOR,
				ROL_ADMINISTRADOR, ROL_OPERADOR, ROL_CONSULTA, ROL_ADMINISTRADOR, ROL_OPERADOR, ROL_CONSULTA };
		
		String[] perfiles = new String[] { PERFIL_ADM_FUNCIONARIOS, PERFIL_ADM_USUARIOS, PERFIL_ADM_COLECTIVOS,
				PERFIL_ADM_FUNCIONARIOS, PERFIL_ADM_FUNCIONARIOS, PERFIL_ADM_USUARIOS, PERFIL_ADM_FUNCIONARIOS,
				PERFIL_ADM_USUARIOS, PERFIL_ADM_FUNCIONARIOS, PERFIL_ADM_USUARIOS };

		for (int i = 0; i < 10; i++) {
			Usuario usu = new Usuario(i + 1, nick[i], password[i], roles[i], perfiles[i]);
			out.add(usu);
		}

		return out;
	}

	/**
	 * @return los datos de colectivos..
	 */
	public static List<Colectivo> getColectivosData() {
		List<Colectivo> out = new ArrayList<Colectivo>();
		String[] nroColec = new String[] { "001", "025", "032", "040", "090",
				"067", "070", "125", "090", "110" };
		String[] nroChasis = new String[] { "D2262662", "142GE4AA",
				"79652F4S2", "9654F124", "19844RF6515", "96F4856E",
				"98AS4DF651", "98DSF974", "646ADS654", "DSGDF988651" };
		String[] nroChapa = new String[] { "AKA 047", "BOB 963", "KAU 020",
				"BOX 425", "APF 965", "AAX 789", "BBF 456", "ASD 456",
				"BBP 654", "ABZ 987" };
		String[] imei=new String[]{"12365478941236587","12589633255","2326595230","789456123","1548310311","7894002232",
				"4512369874","78456311222","554557412","458255221"};
		
		for (int i = 0; i < 10; i++) {
			Colectivo col = new Colectivo(i + 1, nroColec[i], nroChasis[i],
					nroChapa[i],imei[i]);
			out.add(col);
		}
		return out;
	}
	/**
	 * @return los datos de horarios 
	 */
	public static List<Horario> getHorarioData(){
		List<Horario> out = new ArrayList<Horario>();
		String[] salida=new String[]{"03.00","03.30","04.00"};
		String[] llegada=new String[] {"07.00","07.30","08.00"};
		
		for(int i=0; i<3;i++){
			Horario hora = new Horario(i + 1, salida[i], llegada[i]);
			out.add(hora);
		}
		return out;
	}
	
	/**
	 * @return los datos de Tarifas 
	 */
	public static List<Tarifa> getTarifaData(){
		List<Tarifa> out = new ArrayList<Tarifa>();
		String[] descripcion=new String[]{"Piribebuy-Asuncion","Piribebuy-Fernando de la Mora","Piribebuy-San Lorenzo"};
		Double[] Precio=new Double[] {7000.00,6200.00,5700.00};
		
		for(int i=0; i<3;i++){
			Tarifa tarifa =new Tarifa(i + 1,descripcion[i], Precio[i]);
			out.add(tarifa);
		}
		return out;
	}
	
	/**
	 * @return los datos de Sugerencia
	 */
	
	public static List<Sugerencia> getSugerenciaData(){
		List<Sugerencia> out = new ArrayList<Sugerencia>();
		String[] nombre=new String[]{"Liz","Jesus","Gabriela"};
		String[] mail=new String[]{"lizilla@gmail.com","jesusR@hotmail.com","gabyB@gmail.com"};
		String[] sugerencia =new String[]{"Sugerencia1","Sugerencia2", "sugerencia3"};
		
		for(int i=0; i<3;i++){
			Sugerencia sugerencias =new Sugerencia(i + 1 ,nombre[i],mail[i],sugerencia[i]);
			out.add(sugerencias);
		}
		return out;
		
	}
}
