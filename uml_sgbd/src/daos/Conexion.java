package daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
	
	public static Connection conectar(){
        Connection con = null;

        String url = "jdbc:mysql://localhost/Instituto";
        try {
            con = DriverManager.getConnection(url, "mateo_prog", "Mateito1107.");
        } catch (SQLException ex) {
            System.out.println("Error al conectar al SGBD.");
            ex.printStackTrace();
        }

        return con;
    }
}
