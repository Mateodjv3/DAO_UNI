package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import entidades.Aula;

public class AulaDAO implements DAOI<Aula>{

	@Override
	public void create(Aula aula) {
		if (aula != null) {
			String sql = "INSERT INTO Aula (Numero, Capacidad) "
					+ "VALUES (?, ?)";
		
			try {	
				Connection conexion = Conexion.conectar();
				
				PreparedStatement sentencia = conexion.prepareStatement(sql);
				
				sentencia.setString(1, aula.getNumero());
                sentencia.setInt(2, aula.getCapacidad());
            
                sentencia.executeUpdate();
                conexion.close();
                
			} catch (SQLException ex) {
                System.out.println("Error al insertar aula.");
                ex.printStackTrace();
            }
		}
		
	}

	@Override
	public Aula read(int id) {
		
		Aula aula = null;
		
		String sql = "SELECT * FROM Aula "
				+ "WHERE Aula_ID = ?";
		
		try {
        	Connection conexion = Conexion.conectar();
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, id); 
            ResultSet rs = sentencia.executeQuery();

            if (rs.next()) { 
            	int aulaID = rs.getInt("Aula_ID");
            	String numero = rs.getString("Numero");
            	int capacidad = rs.getInt("Capacidad");
                
                aula = new Aula(aulaID, numero, capacidad);
                conexion.close();
            }
        } catch (SQLException ex) {
            System.out.println("Error al consultar el Aula.");
        }
		
		return aula;
	}

	@Override
	public void update(Aula aula) {

		if (aula !=  null) {
			
			String sql = "UPDATE Aula "
					+ "SET Numero=?, Capacidad=? "
					+ "WHERE Aula_ID=?";
			
			try {
				Connection conexion = Conexion.conectar();
				PreparedStatement sentencia = conexion.prepareStatement(sql);
				
				sentencia.setString(1, aula.getNumero());
                sentencia.setInt(2, aula.getCapacidad());
                sentencia.setInt(3, aula.getAulaID());
            
                sentencia.executeUpdate();
                conexion.close();
                
            } catch (SQLException ex) {
                System.out.println("Error al actualizar el Aula.");
            }
		}
		
	}

	@Override
	public void delete(int id) {

		String sql = "DELETE FROM Aula "
				+ "WHERE Aula_ID=?";
		
		try {
			Connection conexion = Conexion.conectar();
			PreparedStatement sentencia = conexion.prepareStatement(sql);
			
			sentencia.setInt(1, id); 

            sentencia.executeUpdate();
            conexion.close();
		} catch (SQLException ex) {
			System.out.println("Error al eliminar un Aula.");
		}
		
	}
	

}
