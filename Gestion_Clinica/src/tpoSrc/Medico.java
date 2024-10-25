package tpoSrc;

import tpoEnums.EspecMedica;

public class Medico {
	private String nombre;
	private String apellido;
	private EspecMedica especialidad;
	private int dni;
	
	public Medico(String nombre, String apellido, EspecMedica especialidad, int dni) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.especialidad = especialidad;
		this.dni= dni;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public EspecMedica getEspecialidad() {
		return especialidad;
	}
	
	public int getDni() {
		return dni;
	}
	
	
	
	
	
	
	
}
