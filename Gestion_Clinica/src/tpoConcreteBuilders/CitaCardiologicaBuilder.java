package tpoConcreteBuilders;

import java.time.LocalDateTime;
import java.util.List;

import tpoEnums.EspecMedica;
import tpoEnums.Estado;
import tpoEnums.MotivoCita;
import tpoInterfaces.ICitaBuilder;
import tpoSrc.Cita;
import tpoSrc.Factura;
import tpoSrc.Medico;
import tpoSrc.Paciente;

//NEUROLOGIA DURA 25 MIN
//PEDIATRIA DURA 30
//CARDIOLOGIA DURA 35
//TRAUMATOLOGIA 30

public class CitaCardiologicaBuilder implements ICitaBuilder{
	private Cita cita = new Cita();
	
	
	@Override
	public void buildId() {
		cita.setId();
	}

	@Override
	public void buildHorario(LocalDateTime hora) {
		cita.setHorario(hora, EspecMedica.CARDIOLOGIA);
        // La duración específica para cardiología ya se maneja dentro de la clase Cita
		
		
	}
	
	@Override
	public void buildEspecMedica() {
		cita.setEspecMedica(EspecMedica.CARDIOLOGIA);
		
	}

	@Override
	public void buildPaciente(Paciente paciente) {
		cita.setPaciente(paciente);
	}

	@Override
	public void buildDoctor(Medico medico) {
		cita.setMedico(medico);
	}

	@Override
	public void buildMotivoCita(MotivoCita motivoCita) {
		cita.setMotivoCita(motivoCita);
	}

	@Override
	public void buildEstado() {
		cita.setEstado(Estado.PROGRAMADA);
	}
	
	public void buildFactura() {
		cita.setFactura(new Factura(this.cita));
	}
	@Override
    public void buildComplejidad(String complejidad) {
        cita.setComplejidad(complejidad);
    }

    @Override
    public void buildServiciosAdicionales(List<String> serviciosAdicionales) {
        cita.setServiciosAdicionales(serviciosAdicionales);
    }
	

	@Override
	public Cita getCita() {
		return this.cita;
	}


}
