package com.waitingpiri.util;

import java.util.List;

import com.waitingpiri.domain.Cargo;
import com.waitingpiri.domain.Colectivo;
import com.waitingpiri.domain.ConnectDB;
import com.waitingpiri.domain.Funcionario;
import com.waitingpiri.domain.Sugerencia;
import com.waitingpiri.domain.Usuario;

public class DBUtil {

	static final String CREATE_TABLE_CARGO = "CREATE TABLE CARGO (" + "ID INT (64) NOT NULL AUTO_INCREMENT," 
						+ "DESCRIPCION VARCHAR(200),"+ "PRIMARY KEY(ID))";
	
	static final String CREATE_TABLE_FUNCIONARIO = "CREATE TABLE FUNCIONARIO ("
			+ "ID INT(64) NOT NULL AUTO_INCREMENT," + "NOMBRE VARCHAR(200),"
			+ "APELLIDO VARCHAR(200)," + "CEDULA VARCHAR(50),"
			+ "DIRECCION VARCHAR(200)," + "TELEFONO VARCHAR(200),"
			+ "IDCARGO INT(10), PRIMARY KEY(ID))";

	static final String CREATE_TABLE_USUARIO = "CREATE TABLE USUARIO ("
			+ "ID INT(64) NOT NULL AUTO_INCREMENT," + "NICK VARCHAR(200),"
			+ "PASSWORD VARCHAR(200)," + "PRIMARY KEY(ID))";
	
	static final String CREATE_TABLE_COLECTIVO = "CREATE TABLE COLECTIVO ("
			+"ID INT(64) NOT NULL AUTO_INCREMENT," + "NROCOLEC VARCHAR(200)," 
			+ "NROCHASIS VARCHAR(200)," + "NROCHAPA VARCHAR(200),"+"IMEI VARCHAR(25)," + "PRIMARY KEY(ID))";
	
	static final String CREATE_TABLE_HORARIO = "CREATE TABLE HORARIO (" + "ID INT (64) NOT NULL AUTO_INCREMENT," 
			+ "DESCRIPCION VARCHAR(200),"+ "PRIMARY KEY(ID))";
	
	public static final String INSERT_CARGO = "INSERT INTO CARGO (DESCRIPCION) values (";

	public static final String INSERT_FUNCIONARIO = "INSERT INTO FUNCIONARIO (NOMBRE, APELLIDO, CEDULA, DIRECCION, TELEFONO, IDCARGO) values (";

	public static final String INSERT_USUARIO = "INSERT INTO USUARIO (NICK, PASSWORD) values (";
	
	public static final String INSERT_COLECTIVO = "INSERT INTO COLECTIVO (NROCOLEC, NROCHASIS , NROCHAPA, IMEI) values (";
	
	public static final String INSERT_HORARIO = "INSERT INTO SUGERENCIA (FECHA, DESCRIPCION) values (";
	
	public static final String DELETE_FUNCIONARIO = "DELETE FROM FUNCIONARIO WHERE ID = ";

	public static final String DELETE_COLECTIVO ="DELETE FROM COLECTIVO WHERE ID=";
	
	public static final String DELETE_USUARIO="DELETE FROM USUARIO WHERE ID=";
	
	public static final String DELETE_CARGO= "DELETE FROM CARGO WHERE ID=";
	
	public static final String UPDATE_CARGO= "UPDATE CARGO SET ";
	
	public static final String UPDATE_FUNCIONARIO = "UPDATE FUNCIONARIO SET ";
	
	public static final String UPDATE_USUARIO = "UPDATE USUARIO SET ";
	
	public static final String UPDATE_COLECTIVO = "UPDATE COLECTIVO SET ";
	
	
	
	/**
	 * pobla la base de datos..
	 */
	public static void poblarDB(List<Cargo> cargos, List<Funcionario> funcionarios, List<Usuario> usuarios,
			List<Colectivo> colectivos, List<Sugerencia> sugerencias) {
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
				String insert = INSERT_USUARIO + "'" + usu.getNick() + "','" + usu.getPassword() + "')";
				conn.executeUpdate(insert);
				System.out.println("Usuario insertado..");
			}
			
			for (Colectivo col : colectivos){
				String insert = INSERT_COLECTIVO + "'" + col.getNroColec() + "','" + col.getNroChasis() + "','" + col.getNroChapa() + "','"+col.getImei()+"')";
				conn.executeUpdate(insert);
				System.out.println("Colectivo insertado..");
			}
			
			for (Sugerencia sug : sugerencias) {
				String insert = INSERT_HORARIO + "'" + sug.getFecha() + "','" + sug.getDescripcion() + "')";
				conn.executeUpdate(insert);
				System.out.println("Sugerencia insertado..");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * test usuario..
	 */
	public static void testUsuario(String nick, String password) {
		ConnectDB conn = ConnectDB.getInstance();
		Usuario user = conn.getUsuario(nick, password);
		if (user == null) {
			System.out.println("No se encontro un usuario con esos datos..");
		} else {
			System.out.println("Usuario encontrado.. ID: " + user.getId() + " NICK: " + user.getNick() + " PASS: "
					+ user.getPassword());
		}
	}

	public static void main(String[] args) {
		DBUtil.poblarDB(DataUtil.getCargosData(),DataUtil.getFuncionariosData(),
				DataUtil.getUsuariosData(),DataUtil.getColectivosData(), DataUtil.getSugerenciasData());
	}
	
}
