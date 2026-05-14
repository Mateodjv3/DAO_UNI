package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entidades.Curso;
import entidades.Doctor;
import entidades.Doctorando;

public class CursoDAO implements DAOI<Curso>{

	@Override
	public void create(Curso curso) {
		
		
		if (curso != null) {
		
			Connection conexion = null;
			
			String sql1 = "INSERT INTO Curso (Descripcion, Num_Horas, Inv_ID) "
					+ "VALUES (?, ?, ?)";

        	String sql2 = "INSERT INTO Cursa (Inv_ID, Curso_ID) "
        			+ "VALUES (?, ?)";
			
        	try {
				
        		conexion = Conexion.conectar();
				conexion.setAutoCommit(false);
				
				PreparedStatement sentencia1 = conexion.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);				
				sentencia1.setString(1, curso.getDescripcion());
                sentencia1.setInt(2, curso.getNumHoras());
                sentencia1.setInt (3, curso.getDocente().getInvID());
                
                sentencia1.executeUpdate();
        		
                ResultSet keys = sentencia1.getGeneratedKeys();
                
                if (keys.next()) {
	            	int idGenerado = keys.getInt(1);
	            	
	            	if (curso.getAlumnos() != null) {
	                    for (Doctorando estudiante : curso.getAlumnos()) {
	                    	
	                        PreparedStatement sentencia2 = conexion.prepareStatement(sql2);
	                        
	                        sentencia2.setInt(1, estudiante.getInvID());
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
                System.out.println("Error al insertar el Curso.");
			}
		}
	}

	@Override
	public Curso read(int id) {
		Curso curso = null;
		
		String sql1 = "SELECT * FROM Curso c "
				+ "JOIN Investigador i ON c.Inv_ID = i.Inv_ID "
				+ "WHERE c.Curso_ID = ?";
		try {
			Connection conexion = Conexion.conectar();
			
			PreparedStatement sentencia1 = conexion.prepareStatement(sql1);
			
			sentencia1.setInt(1, id);
			
			ResultSet rs = sentencia1.executeQuery();
			
			if (rs.next()) { 
                int Curso_ID = rs.getInt("Curso_ID");
                String Descripcion = rs.getString("Descripcion");
                int Horas = rs.getInt("Num_Horas");
                
                DoctorDAO doctorDAO = new DoctorDAO();
                Doctor profesor = doctorDAO.read(rs.getInt("Inv_ID"));
                
                String sql2 = "SELECT * FROM Cursa WHERE Curso_ID = ?";
                
                PreparedStatement sentencia2 = conexion.prepareStatement(sql2);
                
                sentencia2.setInt(1, Curso_ID);
                
                ResultSet rs2 = sentencia2.executeQuery();
                
                List<Doctorando> alumnos = new ArrayList<>();
                DoctorandoDAO doctorandoDAO = new DoctorandoDAO();
                
                while (rs2.next()) {
                	Doctorando alumno = doctorandoDAO.read(rs2.getInt("Inv_ID"));
                    alumnos.add(alumno);
				}
                
                Doctorando[] arrayAlumnos = alumnos.toArray(new Doctorando[0]);
                
                curso = new Curso(Curso_ID, Descripcion, Horas, profesor, arrayAlumnos);
                conexion.close();
            }			
		} catch (SQLException ex) {
		    System.out.println("Error al consultar curso.");
		}
		return curso;
	}

	@Override
	public void update(Curso curso) {
		if (curso != null) {

			Connection conexion = null;

			String sql1 = "UPDATE Curso " +
					"SET Descripcion=?, Num_Horas=?, " +
					"Inv_ID=? WHERE Curso_ID=?";

			String sql2 = "DELETE FROM Cursa " +
					"WHERE Curso_ID=?";

			String sql3 = "INSERT INTO Cursa (Inv_ID, Curso_ID) " +
					"VALUES (?, ?)";

			try {
				conexion = Conexion.conectar();
				conexion.setAutoCommit(false);

				PreparedStatement sentencia1 = conexion.prepareStatement(sql1);
				sentencia1.setString(1, curso.getDescripcion());
				sentencia1.setInt(2, curso.getNumHoras());
				sentencia1.setInt (3, curso.getDocente().getInvID());
				sentencia1.setInt(4, curso.getCursoID());

				sentencia1.executeUpdate();

				PreparedStatement sentencia2 = conexion.prepareStatement(sql2);
				sentencia2.setInt(1, curso.getCursoID());
				sentencia2.executeUpdate();


				if (curso.getAlumnos() != null) {
					for (Doctorando alumno : curso.getAlumnos()) {

						PreparedStatement sentencia3 = conexion.prepareStatement(sql3);

						sentencia3.setInt(1, alumno.getInvID());
						sentencia3.setInt(2, curso.getCursoID());
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
				System.out.println("Error al actualizar el Curso.");
			}
		}
	}

	@Override
	public void delete(int id) {

		String sql1 = "DELETE FROM Cursa " +
				"WHERE Curso_ID=?";
		String sql2 = "DELETE FROM Curso " +
				"WHERE Curso_ID=?";

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
			System.out.println("Error al eliminar el Curso.");
		}

	}





}
