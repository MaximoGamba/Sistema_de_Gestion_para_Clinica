package tpoSrc;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import tpoEnums.ObraSocial;

public class Paciente {
	private int dni; 
	private String nombre;
	private String apellido;
	private LocalDate fecha_nac;
	private String direccion;
	private String telefono;
	private ObraSocial obrasocial;
	private String email;
	private List<String> historial_medico;
	
	
	public Paciente( int dni, String nombre, String apellido,LocalDate fecha_nac,String direccion,String telefono,ObraSocial obrasocial,String email) {
		super();
		this.dni = dni;
		this.nombre = nombre;
		this.apellido = apellido;
		this.fecha_nac= fecha_nac;
		this.direccion= direccion;
		this.telefono= telefono;
		this.obrasocial= obrasocial;
		this.email=email;
		this.historial_medico = new ArrayList<>();
		
		
	}
	

	public int getDni() {
		return dni;
	}

	public LocalDate getFecha_nac() {
		return fecha_nac;
	}


	public void setFecha_nac(LocalDate fecha_nac) {
		this.fecha_nac = fecha_nac;
	}


	public String getDireccion() {
		return direccion;
	}


	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}


	public String getTelefono() {
		return telefono;
	}


	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}


	public ObraSocial getObraSocial() {
		return obrasocial;
	}


	public void setObrasocial(ObraSocial obrasocial) {
		this.obrasocial = obrasocial;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public void setDni(int dni) {
		this.dni = dni;
	}


	public void setHistorial_medico(List<String> historial_medico) {
		this.historial_medico = historial_medico;
	}


	public String getNombre() {
		return nombre;
	}

	public String getApellido() {
		return apellido;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	
	
	public boolean esJubilado() {
		return Period.between(this.fecha_nac, LocalDate.now()).getYears() >= 65;
	    }
	

	public boolean tieneSeguroMedico() {
		return this.obrasocial != null;
	    }
	
	public String getMail() {
		return this.email;
	}
	
	public String getNumero() {
		return this.telefono;
	}

	public static Paciente buscarPacientePorDni(List<Paciente> pacientes, int dni) {
        for (Paciente paciente : pacientes) {
            if (paciente.getDni()==dni) {
                return paciente;
            }
        }
        return null; // Retorna null si no se encuentra el paciente
    }
	
	public void setHistorialMedico(Cita cita) {
		this.historial_medico.add(cita.toString());
	} //Agrega un tratamiento
	
	public void agregarRegistroHistorialMedico(String registro) {
		this.historial_medico.add(registro);
	}

	public List<String> getHistorial_medico() {
		return historial_medico;
	}



	public void agregarEntrada(String entrada) {
		this.historial_medico.add(entrada);
	}








	



	

	
	


	
	
}
