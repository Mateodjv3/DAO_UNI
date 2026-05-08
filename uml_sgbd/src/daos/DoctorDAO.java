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
import entidades.Departamento;
import entidades.Doctor;

public class DoctorDAO implements DAOI <Doctor>{

		@Override
		public void create(Doctor doctor) {
		    if (doctor != null) {	    	
		        	        
		        String sql1 = "INSERT INTO Investigador (Nombre, Apellidos, Telefono, Correo, Dept_ID) " +
		                      "VALUES (?, ?, ?, ?, ?)";
		     
                String sql2 = "INSERT INTO Doctor (Inv_ID, Ano_tesis, Titulo_tesis, Calificacion_tesis) " +
                              "VALUES (?, ?, ?, ?)";
                
		        Connection conexion = null;
		        
		        try {
		        	conexion = Conexion.conectar();
		        	conexion.setAutoCommit(false);
		        	
		            // 1º INSERT en Investigador
		            PreparedStatement sentencia1 = conexion.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);
		            sentencia1.setString(1, doctor.getNombre());
		            sentencia1.setString(2, doctor.getApellidos());
		            sentencia1.setString(3, doctor.getTelefono());
		            sentencia1.setString(4, doctor.getCorreo());
		            sentencia1.setInt(5, doctor.getDepartamento().getDeptID());
		            
		            sentencia1.executeUpdate();
	
		            // Obtener el ID generado automáticamente
		            ResultSet keys = sentencia1.getGeneratedKeys();
		            
		            if (keys.next()) {
		                int idGenerado = keys.getInt(1);
		                
		                // 2º INSERT en Doctor
		                
		                PreparedStatement sentencia2 = conexion.prepareStatement(sql2);
		                sentencia2.setInt(1, idGenerado);
		                sentencia2.setDate(2, (Date) doctor.getAnyoTesis());
		                sentencia2.setString(3, doctor.getTituloTesis());
		                sentencia2.setString(4, doctor.getCalificacionTesis());
		                
		                sentencia2.executeUpdate();
		                
		            }
		            
		            conexion.commit();
		            conexion.close(); // cerrar al final del todo
	
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
		public Doctor read(int id) {
			
			Doctor doctor = null;
			
	        String sql1 = "SELECT * FROM Investigador i \r\n"
	        		+ "JOIN Doctor d ON i.Inv_ID = d.Inv_ID \r\n"
	        		+ "WHERE i.Inv_ID = ?";
	        
	        String sql2 = "SELECT * FROM Articulo "
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
	                Date Ano_tesis = rs1.getDate("Ano_tesis");
	                String Titulo_tesis = rs1.getString("Titulo_tesis");
	                String Calificacion_tesis = rs1.getString("Calificacion_tesis");
	                
	                DepartamentoDAO deptDAO = new DepartamentoDAO();
	                Departamento dept_id = deptDAO.read(rs1.getInt("Dept_ID"));
	                
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
	                
	                doctor = new Doctor(Inv_ID, Nombre, Apellidos, Telefono, Correo, dept_id, Ano_tesis, Titulo_tesis, Calificacion_tesis, arrayArticulos);
	                conexion.close();
	            }
	        } catch (SQLException ex) {
	            System.out.println("Error al consultar un Doctor.");
	        }

	        return doctor; 
		}
		
		
		@Override
		public void update(Doctor doctor) {
			
			if (doctor !=  null) {
				
				String sql1 = "UPDATE Investigador "
	                    + "SET Nombre=?, Apellidos=?, Telefono=?, Correo=?, Dept_ID=? "
	                    + "WHERE Inv_ID=?";
				
				String sql2 = "UPDATE Doctor "
	                    + "SET Ano_tesis=?, Titulo_tesis=?, Calificacion_tesis=? "
	                    + "WHERE Inv_ID=?";
				
				Connection conexion = null;
				try {
					conexion = Conexion.conectar();
				    // 1. Iniciar transacción manual
					conexion.setAutoCommit(false);
					
					PreparedStatement sentencia1 = conexion.prepareStatement(sql1);
										
		            sentencia1.setString(1, doctor.getNombre());
		            sentencia1.setString(2, doctor.getApellidos());
		            sentencia1.setString(3, doctor.getTelefono());
		            sentencia1.setString(4, doctor.getCorreo());
		            sentencia1.setInt(5, doctor.getDepartamento().getDeptID());
		            sentencia1.setInt(6, doctor.getInvID());
		            
	                sentencia1.executeUpdate();
	                
	                PreparedStatement sentencia2 = conexion.prepareStatement(sql2);
	                               
	                sentencia2.setDate(1, (Date) doctor.getAnyoTesis());
	                sentencia2.setString(2, doctor.getTituloTesis());
	                sentencia2.setString(3, doctor.getCalificacionTesis());
	                sentencia2.setInt(4, doctor.getInvID());
	                
	                sentencia2.executeUpdate();
	                               
	                conexion.commit();
	                
	                conexion.close();
	                	                	                
	            } catch (SQLException ex) {
	            	try { 
	            		conexion.rollback(); 
	            		} catch (SQLException e) { 
	            			e.printStackTrace();
	            		}
	                System.out.println("Error al actualizar el Doctor.");
	            }			
			}			
		}
			
		@Override
		public void delete(int id) {
			
			//se elmina en orden contrario al ingreso porque Doctor tiene clave foranea de Investigador
			
			String sql1 = "DELETE FROM Doctor "
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
                System.out.println("Error al eliminar el Doctor.");
            }
			
		}
}
