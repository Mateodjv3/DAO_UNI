package entidades;

import java.util.Arrays;
import java.util.Objects;

public class Articulo {

	int articuloID;
	String titulo;
	int numPaginas;
	Doctor autor;
	Doctorando [] colaboradores;
	
	public Articulo(int articuloID, String titulo, int numPaginas, Doctor autor, Doctorando[] colaboradores) {
		super();
		this.articuloID = articuloID;
		this.titulo = titulo;
		this.numPaginas = numPaginas;
		this.autor = autor;
		this.colaboradores = colaboradores;
	}
	
	@Override
	public String toString() {
		return "Articulo [articuloID=" + articuloID + ", titulo=" + titulo + ", numPaginas=" + numPaginas + ", autor="
				+ autor + ", colaboradores=" + Arrays.toString(colaboradores) + ", toString()=" + super.toString()
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(colaboradores);
		result = prime * result + Objects.hash(articuloID, autor, numPaginas, titulo);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Articulo)) {
			return false;
		}
		Articulo other = (Articulo) obj;
		return articuloID == other.articuloID && Objects.equals(autor, other.autor)
				&& Arrays.equals(colaboradores, other.colaboradores) && numPaginas == other.numPaginas
				&& Objects.equals(titulo, other.titulo);
	}

	public int getArticuloID() {
		return articuloID;
	}

	public void setArticuloID(int articuloID) {
		this.articuloID = articuloID;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public int getNumPaginas() {
		return numPaginas;
	}

	public void setNumPaginas(int numPaginas) {
		this.numPaginas = numPaginas;
	}

	public Doctor getAutor() {
		return autor;
	}

	public void setAutor(Doctor autor) {
		this.autor = autor;
	}

	public Doctorando[] getColaboradores() {
		return colaboradores;
	}

	public void setColaboradores(Doctorando[] colaboradores) {
		this.colaboradores = colaboradores;
	}

	
	
}
