package com.waitingpiri.domain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ConnectDB {

	static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/waitingpiri";
	static final String DB_USER = "admin";
	static final String DB_PASS = "admin";
	
	private static ConnectDB instance = new ConnectDB();
	private static Connection connection = null;
	
	public synchronized static ConnectDB getInstance() {
		if (instance == null || connection == null) {
			instance = new ConnectDB();
		}
		return instance;
	}
	
	/**
	 * Al instanciar se conecta a la BD..
	 */
	private ConnectDB() {		
		try {
			Class.forName(DB_DRIVER);

		} catch (ClassNotFoundException e) {
			System.out.println("Driver no registrado..");
			e.printStackTrace();
			return;
		}

		try {
			connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

		} catch (SQLException e) {
			System.err.println("Fallo la conexion..");
			e.printStackTrace();
			return;
		}
		System.out.println("Conexion exitosa..!!");
	}
	
	/**
	 * execute update..
	 */
	public void executeUpdate(String sql) throws Exception {
		Statement statement = connection.createStatement();
		statement.executeUpdate(sql);
	}
	
	/**
	 * @return Usuario segun nick y password..
	 */
	public Usuario getUsuario(String nick, String password) {
		List<Usuario> out = new ArrayList<Usuario>();
		String sql = "SELECT * FROM USUARIO WHERE NICK = '" + nick + "' AND PASSWORD = '" + password + "'";

		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			while (result.next()) {
				int id = result.getInt("ID");
				Usuario user = new Usuario(id, nick, password);
				out.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return out.size() > 0 ? out.get(0) : null;
	}
	
	/**
	 * @return los funcionarios..
	 */
	public List<Funcionario> getFuncionarios(int id, String nombre, String apellido, String cedula) {
		List<Funcionario> out = new ArrayList<Funcionario>();
		String sql = "SELECT * FROM FUNCIONARIO WHERE NOMBRE LIKE UPPER('%" + nombre.toUpperCase() + "%')"
				+ " AND APELLIDO LIKE UPPER('%" + apellido.toUpperCase() + "%')"+ "AND CEDULA LIKE ('%"+cedula+"%')" ;
		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			while (result.next()) {
				int id_ = result.getInt("ID");
				String nombre_ = result.getString("NOMBRE");
				String apellido_ = result.getString("APELLIDO");
				String cedula_ = result.getString("CEDULA");
				String direccion = result.getString("DIRECCION");
				String telefono = result.getString("TELEFONO");
				Funcionario func = new Funcionario(id_, nombre_, apellido_, cedula_, direccion, telefono, 1);
				out.add(func);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return out;
	}
}
