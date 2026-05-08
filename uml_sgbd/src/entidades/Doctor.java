package entidades;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

public class Doctor extends Investigador {
	
	Date anyoTesis;
	String tituloTesis;
	String calificacionTesis;
	Articulo [] articulosEscritos;
	
	public Doctor(int invID, String nombre, String apellidos, String telefono, String correo, Departamento departamento , Date anyoTesis,
			String tituloTesis, String calificacionTesis) {
		super(invID, nombre, apellidos, telefono, correo);
		this.anyoTesis = anyoTesis;
		this.tituloTesis = tituloTesis;
		this.calificacionTesis = calificacionTesis;
	}
	
	public Doctor(int invID, String nombre, String apellidos, String telefono, String correo, Departamento departamento , Date anyoTesis,
			String tituloTesis, String calificacionTesis, Articulo[] articulosEscritos) {
		super(invID, nombre, apellidos, telefono, correo);
		this.anyoTesis = anyoTesis;
		this.tituloTesis = tituloTesis;
		this.calificacionTesis = calificacionTesis;
		this.articulosEscritos = articulosEscritos;
	}
	/*
	public Doctor(int invID, String nombre, String apellidos, String telefono, String correo, Date anyoTesis,
			String tituloTesis, String calificacionTesis, Articulo[] articulosEscritos) {
		super(invID, nombre, apellidos, telefono, correo);
		this.anyoTesis = anyoTesis;
		this.tituloTesis = tituloTesis;
		this.calificacionTesis = calificacionTesis;
		this.articulosEscritos = articulosEscritos;
	}
	*/
	@Override
	public String toString() {
		return "Doctor [anyoTesis=" + anyoTesis + ", tituloTesis=" + tituloTesis + ", calificacionTesis="
				+ calificacionTesis + ", articulosEscritos=" + Arrays.toString(articulosEscritos) + ", toString()="
				+ super.toString() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Arrays.hashCode(articulosEscritos);
		result = prime * result + Objects.hash(anyoTesis, calificacionTesis, tituloTesis);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof Doctor)) {
			return false;
		}
		Doctor other = (Doctor) obj;
		return Objects.equals(anyoTesis, other.anyoTesis) && Arrays.equals(articulosEscritos, other.articulosEscritos)
				&& Objects.equals(calificacionTesis, other.calificacionTesis)
				&& Objects.equals(tituloTesis, other.tituloTesis);
	}

	public Date getAnyoTesis() {
		return anyoTesis;
	}

	public void setAnyoTesis(Date anyoTesis) {
		this.anyoTesis = anyoTesis;
	}

	public String getTituloTesis() {
		return tituloTesis;
	}

	public void setTituloTesis(String tituloTesis) {
		this.tituloTesis = tituloTesis;
	}

	public String getCalificacionTesis() {
		return calificacionTesis;
	}

	public void setCalificacionTesis(String calificacionTesis) {
		this.calificacionTesis = calificacionTesis;
	}

	public Articulo[] getArticulosEscritos() {
		return articulosEscritos;
	}

	public void setArticulosEscritos(Articulo[] articulosEscritos) {
		this.articulosEscritos = articulosEscritos;
	}	
}