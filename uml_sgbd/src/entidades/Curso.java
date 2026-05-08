package entidades;

import java.util.Arrays;
import java.util.Objects;

public class Curso {

	int cursoID;
	String descripcion;
	int numHoras;
	Doctor docente;
	Doctorando [] alumnos;
	
	public Curso(int cursoID, String descripcion, int numHoras, Doctor docente, Doctorando[] alumnos) {
		super();
		this.cursoID = cursoID;
		this.descripcion = descripcion;
		this.numHoras = numHoras;
		this.docente = docente;
		this.alumnos = alumnos;
	}

	@Override
	public String toString() {
		return "Curso [cursoID=" + cursoID + ", descripcion=" + descripcion + ", numHoras=" + numHoras + ", docente="
				+ docente + ", alumnos=" + Arrays.toString(alumnos) + ", toString()=" + super.toString() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(alumnos);
		result = prime * result + Objects.hash(cursoID, descripcion, docente, numHoras);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Curso)) {
			return false;
		}
		Curso other = (Curso) obj;
		return Arrays.equals(alumnos, other.alumnos) && cursoID == other.cursoID
				&& Objects.equals(descripcion, other.descripcion) && Objects.equals(docente, other.docente)
				&& numHoras == other.numHoras;
	}

	public int getCursoID() {
		return cursoID;
	}

	public void setCursoID(int cursoID) {
		this.cursoID = cursoID;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getNumHoras() {
		return numHoras;
	}

	public void setNumHoras(int numHoras) {
		this.numHoras = numHoras;
	}

	public Doctor getDocente() {
		return docente;
	}

	public void setDocente(Doctor docente) {
		this.docente = docente;
	}

	public Doctorando[] getAlumnos() {
		return alumnos;
	}

	public void setAlumnos(Doctorando[] alumnos) {
		this.alumnos = alumnos;
	}
	
}
