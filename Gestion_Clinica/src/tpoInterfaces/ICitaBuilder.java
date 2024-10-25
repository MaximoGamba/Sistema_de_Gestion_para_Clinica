package tpoInterfaces;
import tpoEnums.*;

import java.time.LocalDateTime;
import java.util.List;

import tpoEnums.MotivoCita;
import tpoSrc.Cita;
import tpoSrc.Medico;
import tpoSrc.Paciente;

public interface ICitaBuilder {
	void buildId();

    void buildHorario(LocalDateTime hora);

    void buildEspecMedica();
    
    void buildPaciente(Paciente paciente); 

    void buildDoctor(Medico medico);
    
    void buildMotivoCita(MotivoCita motivoCita);
    
    void buildEstado();
    
    void buildFactura();
    
    void buildComplejidad(String complejidad);
    
    void buildServiciosAdicionales(List<String> serviciosAdicionales);

    Cita getCita();
}
