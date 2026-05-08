package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import entidades.Articulo;
import entidades.Doctor;
import entidades.Doctorando;
import java.util.List;
import java.util.ArrayList;


public class ArticuloDAO implements DAOI <Articulo>{

	@Override
	public void create(Articulo articulo) {
		
		
		if (articulo != null) {
			
			Connection conexion = null;
			
			String sql1 = "INSERT INTO Articulo (Titulo, Num_Paginas, Inv_ID) "
					+ "	VALUES (?, ?, ?)";

        	String sql2 = "INSERT INTO Colabora (Inv_ID, Articulo_ID) "
        			+ "VALUES (?, ?)";
        	
			try {
				conexion = Conexion.conectar();
				conexion.setAutoCommit(false);
				
				PreparedStatement sentencia1 = conexion.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);				
				sentencia1.setString(1, articulo.getTitulo());
                sentencia1.setInt(2, articulo.getNumPaginas());
                sentencia1.setInt(3, articulo.getAutor().getInvID());
                
                sentencia1.executeUpdate();
                
             // Obtener el ID generado automáticamente
	            ResultSet keys = sentencia1.getGeneratedKeys();
	            
	            if (keys.next()) {
	            	int idGenerado = keys.getInt(1);
	            	
	            	if (articulo.getColaboradores() != null) {
	                    for (Doctorando colaborador : articulo.getColaboradores()) {
	                    	
	                        PreparedStatement sentencia2 = conexion.prepareStatement(sql2);
	                        
	                        sentencia2.setInt(1, colaborador.getInvID());
	                        sentencia2.setInt(2, idGenerado);
	                        
	                        sentencia2.executeUpdate();
	                    }
	                }
	            }
	            conexion.commit();
	            conexion.close();
	            
			} catch (SQLException ex) {
            	try { 
            		conexion.rollback(); 
            		} catch (SQLException e) { 
            			e.printStackTrace();
            		}
                System.out.println("Error al insertar el Articulo.");
			}
			
		}
		
	}

	@Override
	public Articulo read(int id) {
		
		Articulo articulo = null;
		
		String sql1 = "SELECT * FROM Articulo a "
		        + "JOIN Investigador i ON a.Inv_ID = i.Inv_ID "
		        + "WHERE a.Articulo_ID = ?";
		try {
			Connection conexion = Conexion.conectar();
			
			PreparedStatement sentencia1 = conexion.prepareStatement(sql1);
			
			sentencia1.setInt(1, id);
			
			ResultSet rs = sentencia1.executeQuery();
			
			if (rs.next()) { 
                int Articulo_ID = rs.getInt("Articulo_ID");
                String Titulo = rs.getString("Titulo");
                int Num_Paginas = rs.getInt("Num_Paginas");
                
                DoctorDAO doctorDAO = new DoctorDAO();
                Doctor autor = doctorDAO.read(rs.getInt("Inv_ID"));
                
                String sql2 = "SELECT * FROM Colabora c "
                        + "JOIN Doctorando d ON c.Inv_ID = d.Inv_ID "
                        + "WHERE c.Articulo_ID = ?";
                
                PreparedStatement sentencia2 = conexion.prepareStatement(sql2);
                
                sentencia2.setInt(1, Articulo_ID);
                
                ResultSet rs2 = sentencia2.executeQuery();
                
                List<Doctorando> colaboradores = new ArrayList<>();
                DoctorandoDAO doctorandoDAO = new DoctorandoDAO();
                
                while (rs2.next()) {
                	Doctorando colaborador = doctorandoDAO.read(rs2.getInt("Inv_ID"));
                    colaboradores.add(colaborador);
				}
                
                Doctorando[] arrayColaboradores = colaboradores.toArray(new Doctorando[0]);
                
                articulo = new Articulo(Articulo_ID, Titulo, Num_Paginas, autor, arrayColaboradores);
                conexion.close();
            }			
		} catch (SQLException ex) {
		    System.out.println("Error al consultar articulo.");
		}
		return articulo;
	}

	@Override
	public void update(Articulo articulo) {
		if (articulo != null) {
			
			Connection conexion = null;
			
			String sql1 = "UPDATE Articulo "
					+ "SET Titulo=?, Num_Paginas=?, Inv_ID=? "
					+ "WHERE Articulo_ID=?";
			
			String sql2 = "DELETE FROM Colabora "
					+ "WHERE Articulo_ID=?";
			
			String sql3 = "INSERT INTO Colabora (Inv_ID, Articulo_ID) "
					+ "VALUES (?, ?)";
			
			try {
				conexion = Conexion.conectar();
				conexion.setAutoCommit(false);
				
				PreparedStatement sentencia1 = conexion.prepareStatement(sql1);				
				sentencia1.setString(1, articulo.getTitulo());
                sentencia1.setInt(2, articulo.getNumPaginas());
                sentencia1.setInt(3, articulo.getAutor().getInvID());
                sentencia1.setInt(4, articulo.getArticuloID());
                
                sentencia1.executeUpdate();
                
                PreparedStatement sentencia2 = conexion.prepareStatement(sql2);
                sentencia2.setInt(1, articulo.getArticuloID());
                sentencia2.executeUpdate();
                
	            
	            if (articulo.getColaboradores() != null) {
	            	for (Doctorando colaborador : articulo.getColaboradores()) {
	                    	
	            		PreparedStatement sentencia3 = conexion.prepareStatement(sql3);
	                        
	            		sentencia3.setInt(1, colaborador.getInvID());
	            		sentencia3.setInt(2, articulo.getArticuloID());
	            		sentencia3.executeUpdate();
	                }
	            }
	            
	            conexion.commit();
	            conexion.close();
	            
			} catch (SQLException ex) {
            	try { 
            		conexion.rollback(); 
            		} catch (SQLException e) { 
            			e.printStackTrace();
            		}
                System.out.println("Error al actualizar el Articulo.");
			}
		}
	}

	@Override
	public void delete(int id) {
		
		String sql1 = "DELETE FROM Colabora "
                + " WHERE Articulo_ID=?";
		String sql2 = "DELETE FROM Articulo  "
				+ " WHERE Articulo_ID=?";
		
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
            System.out.println("Error al eliminar el Articulo.");
        }
		
	}

}
