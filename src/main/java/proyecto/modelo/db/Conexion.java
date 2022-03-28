package proyecto.modelo.db;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class Conexion {
	
	private static String url;
	private static String usuario;
	private static String password;
	
	public static Connection getConnection() {
		Connection conn = null;
		try {
			readProperties();
			System.out.println("Conectando a la Base de datos...");
			conn = DriverManager.getConnection(url,usuario,password);
			System.out.println("Conexion exitosa!");
		}catch(Exception ex) {
			System.err.println("Ocurrio un error conectando a la base de datos");
			ex.printStackTrace();
		}
		return conn;
	}
	
	public static void closeConnection(Connection conn) {
		try {
			System.out.println("Cerrando una conexion");
			conn.close();
			System.out.println("Se cerro exitosamente!");
		}catch(Exception e) {
			System.err.println("Ocurrio un error cerrando la conexion");
		}
	}
	
	public static boolean thereIsConnection() {
		Connection conn = getConnection();
		if(conn == null) {
			return false;
		} else {
			closeConnection(conn);
			return true;
		}
	}

	public static void readProperties(){
		try {
			String path = Conexion.class.getResource("/config.properties").getPath();
			InputStream input = new FileInputStream(path);
			Properties prop = new Properties();
			prop.load(input);
			usuario = prop.getProperty("db.user");
			System.out.println(usuario);
			password = prop.getProperty("db.password");
			System.out.println(password);
			String test = prop.getProperty("db.test");
			if(test.equals("true")){
				url = "jdbc:mysql://localhost/tienda_de_videojuegos_test";
			}else{
				url = "jdbc:mysql://localhost/tienda_de_videojuegos";
			}
			System.out.println(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
