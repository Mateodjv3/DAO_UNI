package daos;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entidades.Articulo;
import entidades.Doctorando;



public class DoctorandoDAO implements DAOI <Doctorando> {

	@Override
	public void create(Doctorando doctorando) {
		if (doctorando != null) {
			
			String sql1 = "INSERT INTO Investigador (Nombre, Apellidos, Telefono, Correo, Dept_ID) "
                    + "             VALUES ( ?, ?, ?, ?, ?)";

        	String sql2 = "INSERT INTO Doctorando (Inv_ID, Fecha_inicio_doctorado) "
        			+ "VALUES (?, ?)";
        	
			Connection conexion = null;
					
			try {
				conexion = Conexion.conectar();
				conexion.setAutoCommit(false);
				
                PreparedStatement sentencia1 = conexion.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);
                sentencia1.setString(1, doctorando.getNombre());
                sentencia1.setString(2, doctorando.getApellidos());
                sentencia1.setString(3, doctorando.getTelefono());
                sentencia1.setString(4, doctorando.getCorreo());
                sentencia1.setInt(5, doctorando.getDepartamento().getDeptID());

                sentencia1.executeUpdate();
                
                ResultSet keys = sentencia1.getGeneratedKeys();
                
                if (keys.next()) {
                	int idGenerado = keys.getInt(1);
                	
                    PreparedStatement sentencia2 = conexion.prepareStatement(sql2);
                    sentencia2.setInt(1, idGenerado);
                    sentencia2.setDate(2, (Date) doctorando.getFechaInicioDoctorando());

                    sentencia2.executeUpdate();
                }
                
                conexion.commit();
                conexion.close();
                
            } catch (SQLException ex) {
            	try { 
            		conexion.rollback(); 
            		} catch (SQLException e) { 
            			e.printStackTrace();
            		}
                System.out.println("Error al insrtar el Doctor.");
            }
		}
	}

	@Override
	public Doctorando read(int id) {
		
		Doctorando doctorando = null;
		
        String sql1 = "SELECT * FROM Investigador i "
        		+ "JOIN Doctorando d ON i.Inv_ID = d.Inv_ID "
        		+ "WHERE i.Inv_ID = ?";
        
        String sql2 = "SELECT * FROM Colabora "
        		+ "WHERE Inv_ID = ?";
		
        try {
        	Connection conexion = Conexion.conectar();

            PreparedStatement sentencia1 = conexion.prepareStatement(sql1);

            sentencia1.setInt(1, id); 

            ResultSet rs1 = sentencia1.executeQuery();

            if (rs1.next()) { 
                int Inv_ID = rs1.getInt("Inv_ID");
                String Nombre = rs1.getString("Nombre");
                String Apellidos = rs1.getString("Apellidos");
                String Telefono = rs1.getString("Telefono");
                String Correo = rs1.getString("Correo");
                Date Fecha_inicio_doctorado = rs1.getDate("Fecha_inicio_doctorado");
                
                ArticuloDAO articuloDAO = new ArticuloDAO();
                
                PreparedStatement sentencia2 = conexion.prepareStatement(sql2);
                
                sentencia2.setInt(1, Inv_ID);
                ResultSet rs2 = sentencia2.executeQuery();

                List<Articulo> articulos = new ArrayList<>();
                
                while (rs2.next()) {
                    Articulo articulo = articuloDAO.read(rs2.getInt("Articulo_ID"));
                    articulos.add(articulo);
                }

                Articulo[] arrayArticulos = articulos.toArray(new Articulo[0]);
                
                doctorando = new Doctorando(Inv_ID, Nombre, Apellidos, Telefono, Correo, Fecha_inicio_doctorado, arrayArticulos);
                conexion.close();
            }
        } catch (SQLException ex) {
            System.out.println("Error al consultar un Doctorando.");
        }
        return doctorando;
	}

	@Override
	public void update(Doctorando doctorando) {
		if (doctorando !=  null) {
			
			String sql1 = "UPDATE Investigador "
					+ "SET Nombre=?, Apellidos=?, Telefono=?, Correo=? "
					+ "WHERE Inv_ID=?";
			
			String sql2 = "UPDATE Doctorando "
					+ "SET Fecha_inicio_doctorado=? "
					+ "WHERE Inv_ID=?";
			
			Connection conexion = null;
			try {
				conexion = Conexion.conectar();
			    // 1. Iniciar transacción manual
				conexion.setAutoCommit(false);
				
				PreparedStatement sentencia1 = conexion.prepareStatement(sql1);
									
	            sentencia1.setString(1, doctorando.getNombre());
	            sentencia1.setString(2, doctorando.getApellidos());
	            sentencia1.setString(3, doctorando.getTelefono());
	            sentencia1.setString(4, doctorando.getCorreo());
	            sentencia1.setInt(5, doctorando.getInvID());
	            
                sentencia1.executeUpdate();
                
                PreparedStatement sentencia2 = conexion.prepareStatement(sql2);                               
                
                sentencia2.setDate(1, (Date) doctorando.getFechaInicioDoctorando());
                sentencia2.setInt(2, doctorando.getInvID());
                
                sentencia2.executeUpdate();
                               
                conexion.commit();                
                conexion.close();
                	                	                
            } catch (SQLException ex) {
            	try { 
            		conexion.rollback(); 
            		} catch (SQLException e) { 
            			e.printStackTrace();
            		}
                System.out.println("Error al actualizar el Doctorando.");
            }			
		}
	}

	@Override
	public void delete(int id) {
		
		String sql1 = "DELETE FROM Doctorando "
                + "WHERE Inv_ID = ?";
		String sql2 = "DELETE FROM Investigador "
				+ " WHERE Inv_ID = ?";
		
		Connection conexion = null;
		
		try {
			conexion = Conexion.conectar();
			conexion.setAutoCommit(false);
			
			PreparedStatement sentencia1 = conexion.prepareStatement(sql1);
			
			sentencia1.setInt(1, id); 

            sentencia1.executeUpdate();
            
            PreparedStatement sentencia2 = conexion.prepareStatement(sql2);
			
			sentencia2.setInt(1, id); 

            sentencia2.executeUpdate();
            
            conexion.commit();
            conexion.close();
		} catch (SQLException ex) {
        	try { 
        		conexion.rollback(); 
        		} catch (SQLException e) { 
        			e.printStackTrace();
        		}
            System.out.println("Error al eliminar el Doctorando.");
        }
		
	}

}
