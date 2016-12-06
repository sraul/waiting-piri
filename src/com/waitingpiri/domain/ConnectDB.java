package com.waitingpiri.domain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.waitingpiri.gestion.Localizacion;
import com.waitingpiri.util.DBUtil;
import com.waitingpiri.util.Util;

public class ConnectDB {
	
	/**
	 * API de coneccion a la bd..(es un Singleton)
	 */

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
		String sql = "SELECT * FROM USUARIO WHERE NICK = '" + nick + "' AND PASSWORD = '" + Util.encriptar(password) + "'";

		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			while (result.next()) {
				int id = result.getInt("ID");
				String rol = result.getString("ROL");
				String perfil = result.getString("PERFIL");
				Usuario user = new Usuario(id, nick, password, rol, perfil);
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
				String rol = result.getString("ROL");
				String perfil = result.getString("PERFIL");
				Usuario usu = new Usuario(idUsu, nickUsu, passUsu, rol, perfil);
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
				+ " NROCOLEC LIKE UPPER('%" + nroColec.toUpperCase() + "%')" + " AND NROCHAPA LIKE UPPER('%"
				+ nroChapa.toUpperCase() + "%')";
		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			while (result.next()) {
				int idCol = result.getInt("ID");
				String nroCol = result.getString("NROCOLEC");
				String nroChas = result.getString("NROCHASIS");
				String nroChap = result.getString("NROCHAPA");
				String imei = result.getString("IMEI");
				Colectivo col = new Colectivo(idCol, nroCol, nroChas, nroChap, imei);
				out.add(col);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return out;
	}
	/**
	 * @return los horarios..
	 */
	public List<Horario> getHorarios(String id, String salida,String llegada) {
		List<Horario> out = new ArrayList<Horario>();
		String sql = "SELECT * FROM HORARIO WHERE CAST(ID AS CHAR(10)) LIKE '%" + id + "%' AND"
				+ " SALIDA LIKE UPPER('%" + salida.toUpperCase() + "%')"+ "AND LLEGADA LIKE UPPER('%" + llegada.toUpperCase() + "%')";
		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			while (result.next()) {
				int idCol = result.getInt("ID");
				String salida_ = result.getString("SALIDA");
				String llegada_=result.getString("LLEGADA");
				Horario horario = new Horario(idCol, salida_,llegada_);
				out.add(horario);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return out;
	}
	
	/**
	 * @return las localizaciones..
	 */
	public List<Localizacion> getLocalizaciones(Colectivo colectivo, int flag) {
		List<Localizacion> out = new ArrayList<Localizacion>();
		String sql = "SELECT * FROM LOCALIZACIONES WHERE NROCHASIS = '"
				+ colectivo.getNroColec() + "' AND FLAG = " + flag + "  ORDER BY FECHA DESC";
		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			while (result.next()) {
				Localizacion loc = new Localizacion();
				loc.setLatitud(result.getDouble("LATITUD"));
				loc.setLongitud(result.getDouble("LONGITUD"));
				loc.setColectivo(colectivo);
				out.add(loc);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return out;
	}
	
	
	
	/**
	 * @return las tarifas..
	 */
	public List<Tarifa> getTarifas(String id, String descripcion) {
		List<Tarifa> out = new ArrayList<Tarifa>();
		String sql = "SELECT * FROM TARIFA WHERE CAST(ID AS CHAR(10))  LIKE '%" + id + "%' AND"
				+ " DESCRIPCION LIKE UPPER('%" + descripcion.toUpperCase() + "%')";
		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			while (result.next()) {
				int idCol = result.getInt("ID");
				String descripcion_ = result.getString("DESCRIPCION");
				Double precio = result.getDouble("PRECIO");
				Tarifa tarifa = new Tarifa(idCol,descripcion_, precio);
				out.add(tarifa);
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
		String insert=DBUtil.INSERT_USUARIO +"'" +usu.getNick() + "','" + usu.getPasswordEncriptado() + "','" + usu.getRol() + "','" + usu.getPerfil() + "')";
		this.executeUpdate(insert);
		
	}
	/**
	 * update usuario..
	 */
	public void updateUsuario(Usuario usu) throws Exception {
		String update = DBUtil.UPDATE_USUARIO + "NICK = '" + usu.getNick()  + "', ROL = '" + usu.getRol() + "', PERFIL = '" + usu.getPerfil() + "'"
				+ "WHERE ID= " + usu.getId();
		this.executeUpdate(update);
	}
	/**
	 * update usuario..
	 */
	public void updateUsuarioAndPassword(Usuario usu) throws Exception {
		String update = DBUtil.UPDATE_USUARIO + "NICK = '" + usu.getNick() + "', PASSWORD = '" + usu.getPasswordEncriptado() + "', ROL = '" + usu.getRol() + "', PERFIL = '" + usu.getPerfil() + "'"
				+ "WHERE ID= " + usu.getId();
		this.executeUpdate(update);
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
		+col.getNroChapa()+"','"+col.getImei()+"')";
		this.executeUpdate(insert);	
	}
	
	/**
	 * elimina un colectivo
	 */
	public void deleteColectivo(Colectivo col) throws Exception {
		String delete = DBUtil.DELETE_COLECTIVO + col.getId();
		this.executeUpdate(delete);
	}
	
	/**
	 * elimina un horario
	 */
	public void deleteHorario(Horario hor) throws Exception {
		String delete = DBUtil.DELETE_HORARIO + hor.getId();
		this.executeUpdate(delete);
	}
	
	/**
	 * elimina una tarifa
	 */
	public void deleteTarifa(Tarifa tarifa) throws Exception {
		String delete = DBUtil.DELETE_TARIFA + tarifa.getId();
		this.executeUpdate(delete);
	}
	
	/**
	 * update colectivo
	 */	
	public void updateColectivo(Colectivo col) throws Exception{
		String update=DBUtil.UPDATE_COLECTIVO + "NROCOLEC = '"+col.getNroColec()+"', NROCHASIS = '"
		+col.getNroChasis()+"', NROCHAPA ='"+col.getNroChapa()+"', IMEI ='"+col.getImei()+"'"+"WHERE ID= "+col.getId();
		this.executeUpdate(update);
	}

	/**
	 * inserta un nuevo cargo..
	 */
	public void insertCargo(Cargo car) throws Exception {
		String insert = DBUtil.INSERT_CARGO + "'" + car.getDescripcion() + "')";
		this.executeUpdate(insert);

	}
  	
	/**
	 * inserta un nuevo horario..
	 */
	public void insertHorario(Horario hor) throws Exception {
		String insert = DBUtil.INSERT_HORARIO + "'" + hor.getSalida() +"','" + hor.getLlegada()+ "')";
		this.executeUpdate(insert);
	}
	
	/**
	 * inserta una nueva tarifa..
	 */
	public void insertTarifa(Tarifa tarifa) throws Exception {
		String insert = DBUtil.INSERT_TARIFA + " '" + tarifa.getDescripcion()+ "'," + tarifa.getPrecio() + ")";
		this.executeUpdate(insert);
	}
	
	/**
	 * update de un horario..
	 */
	public void updateHorario(Horario hor) throws Exception{
		String update = DBUtil.UPDATE_HORARIO + "SALIDA = '" + hor.getSalida() + "'," + "LLEGADA = '" + hor.getLlegada() + "'"+ "WHERE ID= " + hor.getId();
		this.executeUpdate(update);
	}
	
	/**
	 * update de una tarifa..
	 */
	public void updateTarifa(Tarifa tarifa) throws Exception {
		String update = DBUtil.UPDATE_TARIFA + "DESCRIPCION = '" + tarifa.getDescripcion()+ "', PRECIO = " +
				tarifa.getPrecio() + " WHERE ID= " + tarifa.getId();
		this.executeUpdate(update);
	}

	/**
	 * update cargo..
	 */
	public void updateCargo(Cargo car) throws Exception {
		String update = DBUtil.UPDATE_CARGO + "DESCRIPCION = '" + car.getDescripcion() + "'" + "WHERE ID= "
				+ car.getId();
		this.executeUpdate(update);
	}

	/**
	 * elimina un cargo
	 */
	public void deleteCargo(Cargo car) throws Exception {
		String delete = DBUtil.DELETE_CARGO + car.getId();
		this.executeUpdate(delete);
	}
}
  