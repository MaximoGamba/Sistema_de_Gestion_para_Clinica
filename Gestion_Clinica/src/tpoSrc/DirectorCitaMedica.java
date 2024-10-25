package tpoSrc;

import java.time.LocalDateTime;

import tpoEnums.MotivoCita;
import tpoInterfaces.ICitaBuilder;

public class DirectorCitaMedica {
	private ICitaBuilder citaBuilder;
	
	public void setCitaBuilder(ICitaBuilder citaBuilder) {
		this.citaBuilder= citaBuilder;
	}
	
	public void construirCita(LocalDateTime horaInicio,Paciente paciente, Medico medico, MotivoCita motivoCita) {
		citaBuilder.buildId();
		citaBuilder.buildHorario(horaInicio);
		citaBuilder.buildEspecMedica();
		citaBuilder.buildPaciente(paciente);
		citaBuilder.buildDoctor(medico);
		citaBuilder.buildMotivoCita(motivoCita);
		citaBuilder.buildEstado();
		//citaBuilder.buildFactura();
		
	}
	
	public Cita getCita() {
		return citaBuilder.getCita();
	}
	
}
