package com.waitingpiri.util;

import java.util.List;

import com.waitingpiri.domain.ConnectDB;
import com.waitingpiri.domain.Funcionario;

public class DBUtil {
	
	static final String CREATE_TABLE_FUNCIONARIO = "CREATE TABLE FUNCIONARIO (" 
            + "ID INT(64) NOT NULL AUTO_INCREMENT,"  
            + "NOMBRE VARCHAR(200),"
            + "APELLIDO VARCHAR(200),"
            + "CEDULA VARCHAR(10)," 
            + "DIRECCION VARCHAR(200),"
            + "TELEFONO VARCHAR(10),"
            + "CARGO INT(2), "
            + "PRIMARY KEY(ID))";

	/**
	 * pobla la base de datos..
	 */
	public static void poblarDB(List<Funcionario> funcionarios) {
		try {
			
			ConnectDB conn = ConnectDB.getInstance();
			conn.executeUpdate(CREATE_TABLE_FUNCIONARIO);
			System.out.println("Tabla [funcionario] creada..");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		DBUtil.poblarDB(null);
	}
}
