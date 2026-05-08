package entidades;

import java.sql.Date;
import java.util.Arrays;
import java.util.Objects;

public class Doctorando extends Investigador {
	
	Date fechaInicioDoctorando;
	Articulo [] articulosEscritos;
	
	public Doctorando(int invID, String nombre, String apellidos, String telefono, String correo,
			Date fechaInicioDoctorando, Articulo[] articulosEscritos) {
		super(invID, nombre, apellidos, telefono, correo);
		this.fechaInicioDoctorando = fechaInicioDoctorando;
		this.articulosEscritos = articulosEscritos;
	}

	@Override
	public String toString() {
		return "Doctorando [fechaInicioDoctorando=" + fechaInicioDoctorando + ", articulosEscritos="
				+ Arrays.toString(articulosEscritos) + ", toString()=" + super.toString() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Arrays.hashCode(articulosEscritos);
		result = prime * result + Objects.hash(fechaInicioDoctorando);
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
		if (!(obj instanceof Doctorando)) {
			return false;
		}
		Doctorando other = (Doctorando) obj;
		return Arrays.equals(articulosEscritos, other.articulosEscritos)
				&& Objects.equals(fechaInicioDoctorando, other.fechaInicioDoctorando);
	}

	public Date getFechaInicioDoctorando() {
		return fechaInicioDoctorando;
	}

	public void setFechaInicioDoctorando(Date fechaInicioDoctorando) {
		this.fechaInicioDoctorando = fechaInicioDoctorando;
	}

	public Articulo[] getArticulosEscritos() {
		return articulosEscritos;
	}

	public void setArticulosEscritos(Articulo[] articulosEscritos) {
		this.articulosEscritos = articulosEscritos;
	}

}
