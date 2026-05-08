package entidades;

import java.sql.Date;
import java.sql.Time;
import java.util.Objects;

public class SeImparteEn {
	Curso curso;
	Aula aula;
	Time hora;
	Date fecha;

	protected SeImparteEn(Curso curso, Aula aula, Time hora, Date fecha) {
		super();
		this.curso = curso;
		this.aula = aula;
		this.hora = hora;
		this.fecha = fecha;
	}

	@Override
	public String toString() {
		return "SeImparteEn [curso=" + curso + ", aula=" + aula + ", hora=" + hora + ", fecha=" + fecha + "]";
	}

	
	public Curso getCurso() {
		return curso;
	}

	
	public Aula getAula() {
		return aula;
	}

	
	public Time getHora() {
		return hora;
	}

	
	public Date getFecha() {
		return fecha;
	}

	@Override
	public int hashCode() {
		return Objects.hash(aula, curso, fecha, hora);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof SeImparteEn)) {
			return false;
		}
		SeImparteEn other = (SeImparteEn) obj;
		return Objects.equals(aula, other.aula) && Objects.equals(curso, other.curso)
				&& Objects.equals(fecha, other.fecha) && Objects.equals(hora, other.hora);
	}

	
	
}
