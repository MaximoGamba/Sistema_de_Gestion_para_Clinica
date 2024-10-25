package tpoSrc;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import tpoEnums.EspecMedica;
import tpoEnums.MotivoCita;
import tpoEnums.ObraSocial;



//use SINGLETON para tener siempre una instancia para verificar que tipo de sesion se esta corriendo.
public class App {

	public static void main(String[] args) {
		
//LOGIN
		Sistema sistema= new Sistema();
	    // Simulación de autenticación
		  
			sistema.iniciarSesion();
        	
            
//AGREGAR PACIENTES 
		
			Paciente messi= sistema.agregarPaciente(44788598,"lionel", "messi",LocalDate.of(2003,04,03),"Artigas","1122926211",ObraSocial.OSDE,"gian@gmail","");
//    		sistema.agregarPaciente("Cristiano", "Ronaldo");
            //System.out.println(sistema.pacientes.toString());
		
//AGREGAR MEDICO
			
			Medico medico = new Medico("Juan","Perez", EspecMedica.CARDIOLOGIA,11111111);
        	
//SECCION PROGRAMAR CITAS 
		
			Cita cita= sistema.programarCita(EspecMedica.CARDIOLOGIA, LocalDateTime.of(2024, 6, 4, 10, 15, 30), messi, medico, MotivoCita.PROCEDIMIENTO);
			//Cita cita2= sistema.programarCita(EspecMedica.CARDIOLOGIA, LocalDateTime.of(2024, 6, 4, 10, 15, 30), messi, medico, MotivoCita.PROCEDIMIENTO); //Demostracion de que no se pueden superponer citas de un mismo paciente
		    
		   	Medico medico1 = new Medico("Dr. García","Estandar", EspecMedica.CARDIOLOGIA,44444444);
	        Paciente paciente = new Paciente(87654321, "Ana", "Martínez", LocalDate.of(1985, 10, 20), "Av. Principal 456", "555-987654", ObraSocial.OSDE, "ana.martinez@example.com");
			
			Cita cita1 = sistema.programarCita(EspecMedica.CARDIOLOGIA, LocalDateTime.of(2021, 2, 8, 11, 2), paciente, medico, MotivoCita.CONSULTA );
	        Cita cita2 = sistema.programarCita(EspecMedica.CARDIOLOGIA, LocalDateTime.of(2022, 7, 3, 15, 30), paciente, medico, MotivoCita.CONSULTA);
	        Cita cita3 = sistema.programarCita( EspecMedica.CARDIOLOGIA,LocalDateTime.of(2023, 4, 9, 6, 1), paciente, medico, MotivoCita.CONSULTA);
			 
	        List<Cita> citasFiltradas = sistema.filtrarPorMedicoAsignado(paciente, medico1);
			
	        System.out.println(citasFiltradas.size());
			//messi.agregarRegistroHistorialMedico("El paciente es Asmatico");
			//System.out.println(messi.getHistorial_medico());
			
			//System.out.println(sistema.getCitasPaciente(messi));
			
			sistema.filtrarPorMedicoAsignado(messi, medico);
			//sistema.filtrarPorEspecialidad(messi, EspecMedica.CARDIOLOGIA);
			//sistema.filtrarPorFecha(messi, LocalDateTime.of(2024, 6, 4, 10, 15, 30) );
			
			//System.out.println("****************************");
			
			//System.out.println(sistema.citasMedicas.toString()); //Muestro como se van guardando las citas
			
			//sistema.cancelarCita(cita); //El sitema no soporta logica de modificacion de citas. Si se debe reagendar, se procede a cancelar y reagendar.
			
//			sistema.abonarFactura(cita);
//			
			//Registro de todos los movimientos notificados a area contable y de marketing.
//			System.out.println(sistema.areaContable.toString());
//			System.out.println(sistema.areaMarketing.toString());
        

	}
}    
		

	
