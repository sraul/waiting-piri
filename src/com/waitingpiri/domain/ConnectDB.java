package com.waitingpiri.domain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.waitingpiri.util.DBUtil;

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
	 * @return los cargos..
	 */
	public List<Cargo> getCargos(String id, String descripcion){
		List<Cargo> out = new ArrayList<Cargo>();
		String sql = "SELECT * FROM CARGO WHERE CAST(ID AS CHAR(10))  LIKE '%" + id + "%' AND"
				+ " DESCRIPCION LIKE UPPER('%" + descripcion.toUpperCase() + "%')";
		
		try{
			Statement statement = connection.createStatement();
			ResultSet result=statement.executeQuery(sql);
		 while (result.next()){
			 int id_= result.getInt("ID");
			 String desc=result.getString("descripcion");
			 Cargo car =new Cargo (id_, desc);
			 out.add(car);
		 }
		}
		catch (Exception e){
			 e.printStackTrace();
		 }
		 return out;
		 
		
	}
	
	/**
	 * @return el cargo segun el id..
	 */
	public Cargo getCargo(int id) {
		List<Cargo> out = new ArrayList<Cargo>();
		String sql = "SELECT * FROM CARGO WHERE ID = " + id;
		
		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			while (result.next()) {
				int id_= result.getInt("ID");
				String desc = result.getString("DESCRIPCION");
				Cargo cargo = new Cargo(id_, desc);
				out.add(cargo);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return out.size() > 0 ? out.get(0) : null;
	} 
	
	/**
	 * @return los usuarios..
	 */
	public List<Usuario> getUsuarios(String id, String nick){
		List<Usuario> out =new ArrayList<Usuario>();
		String sql ="SELECT * FROM USUARIO WHERE CAST(ID AS CHAR(10))  LIKE '%" + id + "%' AND"
				+ " NICK LIKE UPPER('%" + nick.toUpperCase() + "%')";
		try{
			Statement statement = connection.createStatement();
			ResultSet result= statement.executeQuery(sql);
			while(result.next()){
				int idUsu= result.getInt("ID");
				String nickUsu =result.getString("NICK");
				String passUsu = result.getString("PASSWORD");
				Usuario usu=new Usuario(idUsu,nickUsu,passUsu);
				out.add(usu);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return out;
	}
	
	
	/**
	 * @return los funcionarios..
	 */
	public List<Funcionario> getFuncionarios(String id, String nombre, String apellido, String cedula) {
		List<Funcionario> out = new ArrayList<Funcionario>();
		String sql = "SELECT * FROM FUNCIONARIO WHERE CAST(ID AS CHAR(10)) LIKE '%" + id + "%' AND"
				+ " NOMBRE LIKE UPPER('%" + nombre.toUpperCase() + "%')"
				+ " AND APELLIDO LIKE UPPER('%" + apellido.toUpperCase() + "%')"
				+ " AND CEDULA LIKE ('%" + cedula + "%')" ;
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
				int idCargo = result.getInt("IDCARGO");
				Cargo cargo = this.getCargo(idCargo);
				Funcionario func = new Funcionario(id_, nombre_, apellido_, cedula_, direccion, telefono, cargo);
				out.add(func);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return out;
	}
	/**
	 * @return los colectivos..
	 */
	public List<Colectivo> getColectivos(String id, String nroColec, String nroChapa) {
		List<Colectivo> out = new ArrayList<Colectivo>();
		String sql = "SELECT * FROM COLECTIVO WHERE CAST(ID AS CHAR(10)) LIKE '%" + id + "%' AND"
				+ " NROCOLEC LIKE UPPER('%" + nroColec.toUpperCase() + "%')"
				+ " AND NROCHAPA LIKE UPPER('%" + nroChapa.toUpperCase() + "%')";
		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			while (result.next()) {
				int idCol = result.getInt("ID");
				String nroCol = result.getString("NROCOLEC");
				String nroChas = result.getString("NROCHASIS");
				String nroChap = result.getString("NROCHAPA");
				Colectivo col = new Colectivo(idCol,nroCol,nroChas,nroChap);
				out.add(col);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return out;
		}
	
	
	/**
	 * inserta un nuevo funcionario..
	 */
	public void insertFuncionario(Funcionario func) throws Exception {
		String insert = DBUtil.INSERT_FUNCIONARIO + "'" + func.getNombre()
		+ "', '" + func.getApellido() + "', '"
		+ func.getCedula() + "', '" + func.getDireccion()
		+ "', '" + func.getTelefono() + "', " + func.getCargo().getId() + ")";
		this.executeUpdate(insert);
	}
	
	/**
	 * update funcionario..
	 */
	public void updateFuncionario(Funcionario func) throws Exception {
		String update = DBUtil.UPDATE_FUNCIONARIO + "NOMBRE = '" + func.getNombre() 
		+ "', APELLIDO = '" + func.getApellido() + "', CEDULA = '" + func.getCedula() + "', "
		+ "DIRECCION = '" + func.getDireccion() + "', TELEFONO = '" + func.getTelefono() 
		+ "', IDCARGO = " + func.getCargo().getId() + " WHERE ID = " + func.getId();
		this.executeUpdate(update);
	}
	
	/**
	 * elimina un funcionario..
	 */
	public void deleteFuncionario(Funcionario func) throws Exception {
		String delete = DBUtil.DELETE_FUNCIONARIO + func.getId();
		this.executeUpdate(delete);
	}

	
	/**
	 * inserta un nuevo usuario..
	 */
	public void insertUsuario(Usuario usu) throws Exception{
		String insert=DBUtil.INSERT_USUARIO+"'"+usu.getNick()+"','"+usu.getPassword()+"')";
		this.executeUpdate(insert);
		
	}
	/** 
	 * elimina un usuario
	 */
	public void deleteUsuario(Usuario usu)throws Exception{
		String delete=DBUtil.DELETE_USUARIO+usu.getId();
		this.executeUpdate(delete);
	}
	
	/**
	 * inserta un nuevo colectivo..
	 */
	public void insertColectivo(Colectivo col)throws Exception{
		String insert=DBUtil.INSERT_COLECTIVO+"'"+col.getNroColec()+"','"+col.getNroChasis()+"','"
		+col.getNroChapa()+"')";
		this.executeUpdate(insert);
		
	}
	/** 
	 * elimina un colectivo
	 */
	public void deleteColectivo(Colectivo col)  throws  Exception{
		String delete=DBUtil.DELETE_COLECTIVO + col.getId();
			this.executeUpdate(delete);
		}
	
	/**
	 * update colectivo
	 */
	
	public void updateColectivo(Colectivo col) throws Exception{
		String update=DBUtil.UPDATE_COLECTIVO + "NROCOLEC = '"+col.getNroColec()+"', NROCHASIS = '"
		+col.getNroChasis()+"', NROCHAPA ='"+col.getNroChapa()+"'"+ "WHERE ID= "+col.getId();
		this.executeUpdate(update);
	}
	/**
	 * inserta un nuevo cargo..
	 */

  public void insertCargo(Cargo car ) throws Exception{
	  String insert= DBUtil.INSERT_CARGO+ "'"+car.getDescripcion()+"')";
	  this.executeUpdate(insert);
	  
  }
  /** 
	 * elimina un cargo
	 */
  public void deleteCargo(Cargo car)  throws  Exception{
		String delete=DBUtil.DELETE_CARGO + car.getId();
			this.executeUpdate(delete);
		}
}