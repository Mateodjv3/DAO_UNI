package entidades;

import java.util.Arrays;
import java.util.Objects;

public class Departamento {

	int deptID;
	String descripcion;
	int numProfesores;
	Investigador [] investigadores;
	
	public Departamento(int deptID, String descripcion, int numProfesores, Investigador[] investigadores) {
		super();
		this.deptID = deptID;
		this.descripcion = descripcion;
		this.numProfesores = numProfesores;
		this.investigadores = investigadores;
	}

	@Override
	public String toString() {
		return "Departamento [deptID=" + deptID + ", descripcion=" + descripcion + ", numProfesores=" + numProfesores
				+ ", investigadores=" + Arrays.toString(investigadores) + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(investigadores);
		result = prime * result + Objects.hash(deptID, descripcion, numProfesores);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Departamento)) {
			return false;
		}
		Departamento other = (Departamento) obj;
		return deptID == other.deptID && Objects.equals(descripcion, other.descripcion)
				&& Arrays.equals(investigadores, other.investigadores) && numProfesores == other.numProfesores;
	}

	public int getDeptID() {
		return deptID;
	}

	public void setDeptID(int deptID) {
		this.deptID = deptID;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getNumProfesores() {
		return numProfesores;
	}

	public void setNumProfesores(int numProfesores) {
		this.numProfesores = numProfesores;
	}

	public Investigador[] getInvestigadores() {
		return investigadores;
	}

	public void setInvestigadores(Investigador[] investigadores) {
		this.investigadores = investigadores;
	}
}
