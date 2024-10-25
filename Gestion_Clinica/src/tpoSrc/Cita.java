package tpoSrc;
import java.time.LocalDateTime;
import java.util.List;
import tpoEnums.EspecMedica;
import tpoEnums.Estado;
import tpoEnums.MotivoCita;

//NEUROLOGIA DURA 25 MIN
//PEDIATRIA DURA 30
//CARDIOLOGIA DURA 35
//TRAUMATOLOGIA 30


public class Cita {
	private int id;
	private LocalDateTime hora_inicio;
	private LocalDateTime hora_finalizacion; // this.hora_finalizacion = hora_inicio.PlusHours(duracion)
	private EspecMedica especMedica;
	private Paciente paciente;
	private Medico medico;
	private MotivoCita motivoCita;
	private Estado estado;
	private Factura factura;
	private String complejidad;
    private List<String> serviciosAdicionales;
	
	private static int next_id = 0;

	public static int getNext_id() {
		return next_id;
	}

	public void setId() {
		this.id = next_id;
		next_id++;
	}

	public void setHorario(LocalDateTime hora_inicio, EspecMedica especMedica) {
		this.hora_inicio = hora_inicio;
		setHora_finalizacion(hora_inicio, especMedica); // Ajustar la hora de finalización según la duración de la cita
    }
	

	public void setHora_finalizacion(LocalDateTime hora_inicio, EspecMedica especMedica) {
		int duracion = obtenerDuracionCita(especMedica);
        this.hora_finalizacion = hora_inicio.plusMinutes(duracion);
	}
	 private int obtenerDuracionCita(EspecMedica especMedica) {//Nunca se pasa por parametro
		 switch (especMedica) {
	            case NEUROLOGIA:
	                return 25;
	            case PEDIATRIA:
	                return 30;
	            case CARDIOLOGIA:
	                return 35;
	            case TRAUMATOLOGIA:
	                return 30;
	            default:
	                return 30; // Duración por defecto
	        }
	    }
	 

	
	public void setEspecMedica(EspecMedica especMedica) {
		this.especMedica= especMedica;
	}
	
	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public void setMotivoCita(MotivoCita motivoCita) {
		this.motivoCita = motivoCita;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	
	public void setFactura(Factura factura) {
		this.factura = factura;
	}

	public static void setNext_id(int next_id) {
		Cita.next_id = next_id;
	}
	
	public EspecMedica getEspecMedica() {
		return especMedica;
	}

	public MotivoCita getMotivoCita() {
		return motivoCita;
	}
	
	public double getMontoTotal() {
		return factura.getMonto();
	}
	 public Paciente getPaciente() {
	        return paciente;
	    }
	 
	 public Factura getFactura() {
		 return this.factura;
	 }
	 
	 public boolean isPagada() {
		 return this.factura.isPagada();
	 }

	public int getPacienteDNI () {
		return this.paciente.getDni();
	}
	
	public int getMedicoDNI () {
		return this.medico.getDni();
	}
	
	public int getCitaId() {
		return id;
	}
	
	public LocalDateTime getHora_inicio() {
		return hora_inicio;
	}

	public LocalDateTime getHora_finalizacion() {
		return hora_finalizacion;
	}
	
	public void setPagada() {
		this.factura.setPagada();
	}

	@Override
	public String toString() {
		return  ("Area medica: " + especMedica + ", Paciente: " + paciente.getApellido() + ", Medico asignado: " + medico.getApellido() + ", Tipo Cita: "
				+ motivoCita + ", Estado: " + estado + ", Hora inicio: " + hora_inicio + ", Hora finalizacion: " + hora_finalizacion +",  factura=" + factura);
	}

	public List<String> getServiciosAdicionales() {
		return serviciosAdicionales;
	}

	public void setServiciosAdicionales(List<String> serviciosAdicionales) {
		this.serviciosAdicionales = serviciosAdicionales;
	}

	public String getComplejidad() {
		return this.complejidad;
	}
	
	

	public int getId() {
		return id;
	}

	public Medico getMedico() {
		return medico;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setComplejidad(String complejidad) {
		this.complejidad = complejidad;
	}


		
	


	
}
