package com.waitingpiri.util;

import java.util.List;

import com.waitingpiri.domain.ConnectDB;
import com.waitingpiri.domain.Funcionario;

public class DBUtil {
	
	static final String CREATE_TABLE_FUNCIONARIO = "CREATE TABLE FUNCIONARIO (" 
            + "ID INT(64) NOT NULL AUTO_INCREMENT,"  
            + "NOMBRE VARCHAR(2)," 
            + "CEDULA VARCHAR(2)," 
            + "DIRECCION VARCHAR(2),"
            + "TELEFONO VARCHAR(2),"
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
}
