package com.waitingpiri.util;

import java.util.List;

import com.waitingpiri.domain.ConnectDB;
import com.waitingpiri.domain.Funcionario;
import com.waitingpiri.domain.Usuario;

public class DBUtil {

	static final String CREATE_TABLE_FUNCIONARIO = "CREATE TABLE FUNCIONARIO (" + "ID INT(64) NOT NULL AUTO_INCREMENT,"
			+ "NOMBRE VARCHAR(200)," + "APELLIDO VARCHAR(200)," + "CEDULA VARCHAR(10)," + "DIRECCION VARCHAR(200),"
			+ "TELEFONO VARCHAR(10)," + "CARGO INT(2), " + "PRIMARY KEY(ID))";
	
	//Tarea: sql crear tabla usuario
	static final String CREATE_TABLE_USUARIO = "";

	static final String INSERT_FUNCIONARIO = "INSERT INTO FUNCIONARIO (NOMBRE, APELLIDO, CEDULA, DIRECCION, TELEFONO, CARGO) values (";
	
	//Tarea: sql insertar usuarios
	static final String INSERT_USUARIO = "";

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
				String insert = INSERT_FUNCIONARIO + "'" + func.getNombre() + "', '" + func.getApellido() + "', '"
						+ func.getCedula() + "', '" + func.getDireccion() + "', '" + func.getTelefono() + "', "
						+ func.getCargo() + " )";
				conn.executeUpdate(insert);
				System.out.println("Funcionario insertado..");
			}
			
			// Tarea: aqui insertar usuarios

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		DBUtil.poblarDB(DataUtil.getFuncionariosData(), DataUtil.getUsuariosData());
	}
}
