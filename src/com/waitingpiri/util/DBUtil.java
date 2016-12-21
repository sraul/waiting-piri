package com.waitingpiri.util;

import java.util.List;

import com.waitingpiri.domain.Cargo;
import com.waitingpiri.domain.Colectivo;
import com.waitingpiri.domain.ConnectDB;
import com.waitingpiri.domain.Funcionario;
import com.waitingpiri.domain.Horario;
import com.waitingpiri.domain.Sugerencia;
import com.waitingpiri.domain.Tarifa;
import com.waitingpiri.domain.Usuario;

public class DBUtil {

	/**
	 * Clase para poblar la base de datos..
	 */
	
	static final String CREATE_TABLE_CARGO = "CREATE TABLE CARGO (" + "ID INT (64) NOT NULL AUTO_INCREMENT," 
						+ "DESCRIPCION VARCHAR(200),"+ "PRIMARY KEY(ID))";
	
	static final String CREATE_TABLE_FUNCIONARIO = "CREATE TABLE FUNCIONARIO ("
			+ "ID INT(64) NOT NULL AUTO_INCREMENT," + "NOMBRE VARCHAR(200),"
			+ "APELLIDO VARCHAR(200)," + "CEDULA VARCHAR(50),"
			+ "DIRECCION VARCHAR(200)," + "TELEFONO VARCHAR(200),"
			+ "IDCARGO INT(10), PRIMARY KEY(ID))";

	static final String CREATE_TABLE_USUARIO = "CREATE TABLE USUARIO ("
			+ "ID INT(64) NOT NULL AUTO_INCREMENT," + "NICK VARCHAR(200),"
			+ "PASSWORD VARCHAR(200), ROL VARCHAR(200), PERFIL VARCHAR(200)," + "PRIMARY KEY(ID))";
	
	static final String CREATE_TABLE_COLECTIVO = "CREATE TABLE COLECTIVO ("
			+"ID INT(64) NOT NULL AUTO_INCREMENT," + "NROCOLEC VARCHAR(200)," 
			+ "NROCHASIS VARCHAR(200)," + "NROCHAPA VARCHAR(200)," + "PRIMARY KEY(ID))";
	
	static final String CREATE_TABLE_HORARIO = "CREATE TABLE HORARIO (" + "ID INT (64) NOT NULL AUTO_INCREMENT," 
			+ "SALIDA VARCHAR(6),"+ " LLEGADA VARCHAR(6),"+ "PRIMARY KEY(ID))";
	
	static final String CREATE_TABLE_TARIFA = "CREATE TABLE TARIFA ("
			+"ID INT(64) NOT NULL AUTO_INCREMENT," + "DESCRIPCION VARCHAR(200)," + "PRECIO DOUBLE," + "PRIMARY KEY(ID))";
	
	static final String CREATE_TABLE_SUGERENCIA="CREATE TABLE SUGERENCIA ("
			+ "ID INT(64) NOT NULL AUTO_INCREMENT," + "NOMBRE VARCHAR(200)," + "MAIL VARCHAR(200),"
			+ "SUGERENCIA VARCHAR (300)," + "PRIMARY KEY(ID))";
	
	
	public static final String INSERT_CARGO = "INSERT INTO CARGO (DESCRIPCION) values (";

	public static final String INSERT_FUNCIONARIO = "INSERT INTO FUNCIONARIO (NOMBRE, APELLIDO, CEDULA, DIRECCION, TELEFONO, IDCARGO) values (";

	public static final String INSERT_USUARIO = "INSERT INTO USUARIO (NICK, PASSWORD, ROL, PERFIL) values (";
	
	public static final String INSERT_COLECTIVO = "INSERT INTO COLECTIVO (NROCOLEC, NROCHASIS , NROCHAPA) values (";
	
	public static final String INSERT_HORARIO = "INSERT INTO HORARIO (SALIDA, LLEGADA) values (";
	
	public static final String INSERT_TARIFA = "INSERT INTO TARIFA (DESCRIPCION, PRECIO) values (";
	
	public static final String INSERT_SUGERENCIA = "INSERT INTO SUGERENCIA (NOMBRE, MAIL, SUGERENCIA) values (";
	
	public static final String DELETE_FUNCIONARIO = "DELETE FROM FUNCIONARIO WHERE ID = ";

	public static final String DELETE_COLECTIVO ="DELETE FROM COLECTIVO WHERE ID =";
	
	public static final String DELETE_USUARIO="DELETE FROM USUARIO WHERE ID =";
	
	public static final String DELETE_CARGO= "DELETE FROM CARGO WHERE ID =";
	
	public static final String DELETE_HORARIO ="DELETE FROM HORARIO WHERE ID =";
	
	public static final String DELETE_TARIFA ="DELETE FROM TARIFA WHERE ID =";
	
	public static final String DELETE_SUGERENCIA ="DELETE FROM SUGERENCIA WHERE ID =";
	
	public static final String UPDATE_CARGO= "UPDATE CARGO SET ";
	
	public static final String UPDATE_FUNCIONARIO = "UPDATE FUNCIONARIO SET ";
	
	public static final String UPDATE_USUARIO = "UPDATE USUARIO SET ";
	
	public static final String UPDATE_COLECTIVO = "UPDATE COLECTIVO SET ";
	
	public static final String UPDATE_HORARIO = "UPDATE HORARIO SET ";
	
	public static final String UPDATE_TARIFA = "UPDATE TARIFA SET ";
	

	public static final String UPDATE_SUGERENCIA = "UPDATE SUGERENCIA SET ";
	/**
	 * pobla la base de datos..
	 */
	public static void poblarDB(List<Cargo> cargos, List<Funcionario> funcionarios, List<Usuario> usuarios,
			List<Colectivo> colectivos, List<Horario> horarios, List<Tarifa> tarifas, List<Sugerencia> sugerencias ){
		try {

			ConnectDB conn = ConnectDB.getInstance();
			
			conn.executeUpdate(CREATE_TABLE_CARGO);
			System.out.println("Tabla [cargo] creada..");
			
			conn.executeUpdate(CREATE_TABLE_FUNCIONARIO);
			System.out.println("Tabla [funcionario] creada..");
			
			conn.executeUpdate(CREATE_TABLE_USUARIO);
			System.out.println("Tabla [usuario] creada..");
			
			conn.executeUpdate(CREATE_TABLE_COLECTIVO);
			System.out.println("Tabla [colectivo] creada..");
			
			conn.executeUpdate(CREATE_TABLE_HORARIO);
			System.out.println("Tabla [horario] creada..");
			
			conn.executeUpdate(CREATE_TABLE_TARIFA);
			System.out.println("Tabla [tarifa] creada..");
			
			conn.executeUpdate(CREATE_TABLE_SUGERENCIA);
			System.out.println("Tabla [sugerencia] creada..");

			for (Cargo car : cargos){
				String insert = INSERT_CARGO + "'" + car.getDescripcion() + "')";
				conn.executeUpdate(insert);
				System.out.println("Cargo insertado..");
			}
			
			for (Funcionario func : funcionarios) {
				String insert = INSERT_FUNCIONARIO + "'" + func.getNombre()
						+ "', '" + func.getApellido() + "', '"
						+ func.getCedula() + "', '" + func.getDireccion()
						+ "', '" + func.getTelefono() + "', " + func.getCargo().getId() + ")";
				conn.executeUpdate(insert);
				System.out.println("Funcionario insertado..");
			}			
					
			for (Usuario usu :  usuarios) {
				String insert = INSERT_USUARIO + "'" + usu.getNick() + "','" + usu.getPassword()+ "','" + usu.getRol() + "','" + usu.getPerfil() + "')";
				conn.executeUpdate(insert);
				System.out.println("Usuario insertado..");
			}
			
			for (Colectivo col : colectivos){
				String insert = INSERT_COLECTIVO + "'" + col.getNroColec() + "','" + col.getNroChasis() + "','" + col.getNroChapa() + "')";
				conn.executeUpdate(insert);
				System.out.println("Colectivo insertado..");
			}
			
			for (Horario horario : horarios) {
				String insert = INSERT_HORARIO + "'" + horario.getSalida()+ "','" + horario.getLlegada() + "')";
				conn.executeUpdate(insert);
				System.out.println("Horario insertado..");
			}
			
			for (Tarifa tarifa : tarifas) {
				String insert = INSERT_TARIFA + "'" + tarifa.getDescripcion()  + "', " + tarifa.getPrecio() + ")";
				conn.executeUpdate(insert);
				System.out.println("Tarifa insertada..");
			}
			
			for (Sugerencia sugerencia : sugerencias) {
				String insert = INSERT_SUGERENCIA + "'" + sugerencia.getNombre() + "',' "
			+ sugerencia.getMail()+ "','" +sugerencia.getSugerencia()+ "')";
				conn.executeUpdate(insert);
				System.out.println("Sugerencia insertada..");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		DBUtil.poblarDB(DataUtil.getCargosData(), DataUtil.getFuncionariosData(), DataUtil.getUsuariosData(),
				DataUtil.getColectivosData(), DataUtil.getHorarioData(),DataUtil.getTarifaData(),DataUtil.getSugerenciaData());
	}
	
}
