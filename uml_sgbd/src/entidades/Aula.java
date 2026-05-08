package entidades;

import java.util.Objects;

public class Aula {
	
	int aulaID;
	String numero;
	int capacidad;

	public Aula(int aulaID, String numero, int capacidad) {
		super();
		this.aulaID = aulaID;
		this.numero = numero;
		this.capacidad = capacidad;
	}

	@Override
	public String toString() {
		return "Aula [aulaID=" + aulaID + ", numero=" + numero + ", capacidad=" + capacidad + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(aulaID, capacidad, numero);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Aula other = (Aula) obj;
		return aulaID == other.aulaID && capacidad == other.capacidad && Objects.equals(numero, other.numero);
	}

	public int getAulaID() {
		return aulaID;
	}

	public void setAulaID(int aulaID) {
		this.aulaID = aulaID;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	
}