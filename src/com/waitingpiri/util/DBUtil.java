package com.waitingpiri.util;

import java.util.List;

import com.waitingpiri.domain.ConnectDB;
import com.waitingpiri.domain.Funcionario;
import com.waitingpiri.domain.Usuario;

public class DBUtil {

	static final String CREATE_TABLE_FUNCIONARIO = "CREATE TABLE FUNCIONARIO ("
			+ "ID INT(64) NOT NULL AUTO_INCREMENT," + "NOMBRE VARCHAR(200),"
			+ "APELLIDO VARCHAR(200)," + "CEDULA VARCHAR(10),"
			+ "DIRECCION VARCHAR(200)," + "TELEFONO VARCHAR(10),"
			+ "CARGO INT(2), " + "PRIMARY KEY(ID))";

	static final String CREATE_TABLE_USUARIO = "CREATE TABLE USUARIO ("
			+ "ID INT(64) NOT NULL AUTO_INCREMENT," + "NICK VARCHAR(200),"
			+ "PASSWORD VARCHAR(200)," + "PRIMARY KEY(ID))";

	static final String INSERT_FUNCIONARIO = "INSERT INTO FUNCIONARIO (NOMBRE, APELLIDO, CEDULA, DIRECCION, TELEFONO, CARGO) values (";

	// Tarea: sql insertar usuarios
	static final String INSERT_USUARIO = "INSERT INTO USUARIO (NICK, PASSWORD) values (";

	/**
	 * pobla la base de datos..
	 */
	public static void poblarDB(List<Funcionario> funcionarios, List<Usuario> usuarios) {
		try {

			ConnectDB conn = ConnectDB.getInstance();
			conn.executeUpdate(CREATE_TABLE_FUNCIONARIO);
			System.out.println("Tabla [funcionario] creada..");
			
			conn.executeUpdate(CREATE_TABLE_USUARIO);
			System.out.println("Tabla [usuario] creada..");

			for (Funcionario func : funcionarios) {
				String insert = INSERT_FUNCIONARIO + "'" + func.getNombre()
						+ "', '" + func.getApellido() + "', '"
						+ func.getCedula() + "', '" + func.getDireccion()
						+ "', '" + func.getTelefono() + "', " + func.getCargo()
						+ " )";
				conn.executeUpdate(insert);
				System.out.println("Funcionario insertado..");
			}
			
					
			for (Usuario usu :  usuarios) {
				String insert = INSERT_USUARIO + "'" + usu.getNick() + "','" + usu.getPassword() + "')";
				conn.executeUpdate(insert);
				System.out.println("Usuario insertado..");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		DBUtil.poblarDB(DataUtil.getFuncionariosData(),
				DataUtil.getUsuariosData());
	}
}
