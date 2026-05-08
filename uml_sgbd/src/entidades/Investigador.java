package entidades;

import java.util.Objects;

public abstract class Investigador {

	int invID;
	String nombre;
	String apellidos;
	String telefono;
	String correo;
	Departamento departamento;
	
	public Investigador(int invID, String nombre, String apellidos, String telefono, String correo) {

		this.invID = invID;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.telefono = telefono;
		this.correo = correo;
	}

	public Investigador(int invID, String nombre, String apellidos, String telefono, String correo,
			Departamento departamento) {
		this.invID = invID;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.telefono = telefono;
		this.correo = correo;
		this.departamento = departamento;
	}

	@Override
	public String toString() {
		return "Investigador [invID=" + invID + ", nombre=" + nombre + ", apellidos=" + apellidos + ", telefono="
				+ telefono + ", correo=" + correo + ", departamento=" + departamento + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(apellidos, correo, departamento, invID, nombre, telefono);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Investigador)) {
			return false;
		}
		Investigador other = (Investigador) obj;
		return Objects.equals(apellidos, other.apellidos) && Objects.equals(correo, other.correo)
				&& Objects.equals(departamento, other.departamento) && invID == other.invID
				&& Objects.equals(nombre, other.nombre) && Objects.equals(telefono, other.telefono);
	}

	public int getInvID() {
		return invID;
	}

	public void setInvID(int invID) {
		this.invID = invID;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	
}
