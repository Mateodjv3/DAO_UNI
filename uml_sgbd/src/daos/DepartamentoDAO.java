package daos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import entidades.Departamento;
import entidades.Doctor;
import entidades.Investigador;

public class DepartamentoDAO implements DAOI<Departamento> {

	@Override
	public void create(Departamento departamento) {
		if (departamento != null) {
			
			String sql = "INSERT INTO Departamento (Descripcion, Num_Profesores) "
					+ "VALUES (?, ?)";
		
			try {	
				Connection conexion = Conexion.conectar();
				
				PreparedStatement sentencia = conexion.prepareStatement(sql);
				
				sentencia.setString(1, departamento.getDescripcion());
                sentencia.setInt(2, departamento.getNumProfesores());
            
                sentencia.executeUpdate();
                conexion.close();
			} catch (SQLException ex) {
                System.out.println("Error al insertar Departamento.");
                ex.printStackTrace();
            }
		}
	}

	@Override
	public Departamento read(int id) {
		Departamento departamento = null;
        String sql1 = "SELECT * FROM Departamento "
        		+ "WHERE Dept_ID = ?";
    	String sql2 = "SELECT i.* FROM Investigador i "
                + "JOIN Doctor d ON i.Inv_ID = d.Inv_ID "
                + "WHERE i.Dept_ID = ?";

        try {
        	Connection conexion = Conexion.conectar();
            PreparedStatement sentencia1 = conexion.prepareStatement(sql1);
            
            sentencia1.setInt(1, id); 
            
            ResultSet rs1 = sentencia1.executeQuery();

            if (rs1.next()) { 
            	int deptID = rs1.getInt("Dept_ID");
            	String descripcion = rs1.getString("Descripcion");
            	int numProfesores = rs1.getInt("Num_Profesores");
                
            	DoctorDAO doctorDAO = new DoctorDAO();
            	
                PreparedStatement sentencia2 = conexion.prepareStatement(sql2);
                sentencia2.setInt(1, deptID);
                
                ResultSet rs2 = sentencia2.executeQuery();
                List<Investigador> investigadores = new ArrayList<>();
                
                while (rs2.next()) {
                    Doctor doctor = doctorDAO.read(rs2.getInt("Inv_ID"));
                    investigadores.add(doctor);
                }

                Investigador[] arrayInvestigadores = investigadores.toArray(new Investigador[0]);
            	
                departamento = new Departamento(deptID, descripcion, numProfesores, arrayInvestigadores);
                conexion.close();
            }
        } catch (SQLException ex) {
            System.out.println("Error al consultar el departamento.");
        }

        return departamento;
	}

	@Override
	public void update(Departamento departamento) {
		
		if (departamento !=  null) {
			
			String sql = "UPDATE Departamento "
                    + "SET Descripcion=?, Num_Profesores=? "
                    + "WHERE Dept_ID=?";
			
			try {
				Connection conexion = Conexion.conectar();
				PreparedStatement sentencia = conexion.prepareStatement(sql);
				
				sentencia.setString(1, departamento.getDescripcion());
                sentencia.setInt(2, departamento.getNumProfesores());
                sentencia.setInt(3, departamento.getDeptID());
            
                sentencia.executeUpdate();
                conexion.close();
                
            } catch (SQLException ex) {
                System.out.println("Error al actualizar un departamento.");
            }
		}
		
	}

	@Override
	public void delete(int id) {
		
		String sql = "DELETE FROM Departamento "
                + "WHERE Dept_ID = ?";
		
		try {
			Connection conexion = Conexion.conectar();
			PreparedStatement sentencia = conexion.prepareStatement(sql);
			
			sentencia.setInt(1, id); 

            sentencia.executeUpdate();
            conexion.close();
		} catch (SQLException ex) {
			System.out.println("Error al eliminar un departamento.");
		}
	}

}
