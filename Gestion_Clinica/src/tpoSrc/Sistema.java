package tpoSrc;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import tpoAreasExternas.AreaContable;
import tpoAreasExternas.AreaMarketing;
import tpoAreasExternas.Observer;
import tpoAreasExternas.Subject;
import tpoConcreteBuilders.*;
import tpoEnums.EspecMedica;
import tpoEnums.FormatoEnvioFactura;
import tpoEnums.MotivoCita;
import tpoEnums.ObraSocial;
import tpoEnums.UserType;
import tpoInterfaces.ICitaBuilder;

public class Sistema {
	AuthService authService;
	UserSession userSession;
	DirectorCitaMedica directorCitaMedica;
	CitaCardiologicaBuilder citaCardiologicaBuilder;
	CitaNeurologicaBuilder citaNeurologicaBuilder;
	CitaPediatricaBuilder citaPediatricaBuilder;
	CitaTraumatologicaBuilder citaTraumatologicaBuilder;
	List<Paciente> pacientes;
	List<Cita> citasMedicas;
	List<Medico> medicos;
	List<Observer> observes;
	Subject sistemaNotificacion; 
    AreaContable areaContable; 
    AreaMarketing areaMarketing; 

	
	public Sistema(){
		Scanner scanner = new Scanner(System.in);
		pacientes = new ArrayList<Paciente>();
		citasMedicas = new ArrayList<Cita>();
		medicos = new ArrayList<Medico>();
		observes = new ArrayList<Observer>();
		sistemaNotificacion = new Subject();
	    areaContable = new AreaContable();
	    areaMarketing = new AreaMarketing();
	    
	    sistemaNotificacion.agregarObservador(areaContable);
	    sistemaNotificacion.agregarObservador(areaMarketing);

	}
	
	public void iniciarSesion() {
		boolean isAuthenticated = false;
        while (!isAuthenticated) {
            // Solicitar credenciales al usuario
        	Scanner scanner = new Scanner(System.in);
            System.out.print("Ingrese el usuario: ");
            String username = scanner.nextLine();
            System.out.print("Ingrese la contraseña: ");
            String password = scanner.nextLine();
          
            // Intentar iniciar sesión
            isAuthenticated = AuthService.login(username, password);
            
            if (isAuthenticated) {
                AuthService.displayUserInfo();
            } else {
                System.out.println("Credenciales incorrectas, intente nuevamente.");
            }
        }
	}
	
	public Paciente agregarPaciente(
			int dni,String nombre,String apellido,LocalDate fecha_nac,String direccion,String telefono,ObraSocial obrasocial,String email,String historial_medico){
		if(UserSession.getInstance().getUserType().equals(UserType.ADMIN)) {
				Paciente paciente = new Paciente(dni,nombre,apellido,fecha_nac,direccion,telefono,obrasocial,email);
				pacientes.add(paciente);
				return paciente;
	}else{
		System.out.println("No tienes permisos para agregar un paciente");}
		return null;
		}
		
	public void editarHistorialPaciente(int dni,String entrada) {
		// Tanto procedimientos como tratamientos realizados van a parar al historial del paciente
		if(UserSession.getInstance().getUserType().equals(UserType.ADMIN)) { //Valido sesion
			Paciente paciente = Paciente.buscarPacientePorDni(pacientes, dni);
			paciente.agregarEntrada(entrada);
		}
		
	}
	
	public Cita programarCita(EspecMedica especMedica, LocalDateTime horaInicio, Paciente paciente, Medico medico, MotivoCita motivoCita) {
	    Cita cita = null;
	    if (UserSession.getInstance().getUserType().equals(UserType.ADMIN)) {
	        DirectorCitaMedica directorCitaMedica = new DirectorCitaMedica();
	        ICitaBuilder citaBuilder;
	        switch (especMedica) {
	            case CARDIOLOGIA:
	                citaBuilder = new CitaCardiologicaBuilder();
	                break;
	            case NEUROLOGIA:
	                citaBuilder = new CitaNeurologicaBuilder();
	                break;
	            case PEDIATRIA:
	                citaBuilder = new CitaPediatricaBuilder();
	                break;
	            case TRAUMATOLOGIA:
	                citaBuilder = new CitaTraumatologicaBuilder();
	                break;
	            default:
	                throw new IllegalArgumentException("Especialidad médica no soportada");
	        }

	        directorCitaMedica.setCitaBuilder(citaBuilder);
	        directorCitaMedica.construirCita(horaInicio, paciente, medico, motivoCita);
	        cita = directorCitaMedica.getCita();

	        // Establecer complejidad y servicios adicionales
	        cita.setComplejidad("Media");
	        cita.setServiciosAdicionales(List.of("Análisis de sangre", "Rayos X"));
	        cita.setFactura(new Factura(cita)); // Crear la factura
	        
	        
	        if (validarCita(paciente, especMedica, horaInicio, medico, motivoCita)) { //Valida que no haya citas superpuestas, que la especialidad del medico asignado sea la correcta, etc...
	            citasMedicas.add(cita);
	            recibirNotificaciones(paciente); //Notifica al paciente via mail sobre su nueva cita 
	            paciente.setHistorialMedico(cita); //SE AGREGA EL PROCEDIMIENTO AL HISTORIAL MEDICO DEL PACIENTE
	            System.out.println("La cita fue programada con éxito!");
	            System.out.println("*********************************");
	            System.out.println("Costo Total: " + cita.getMontoTotal());

	            // Verificar si la factura está pagada y enviar recordatorio
	            Factura factura = cita.getFactura();
	            
	            enviarRecordatorio(paciente.getNombre(), factura);
	            enviarFactura(obtenerFormatoEnvioFactura(), cita);
	                }
	                
	        else {
	            System.out.println("No se pudo programar la cita. Verifique los detalles.");
	        }

	        
	    }
	    return cita;
	}
	
	public void abonarFactura(Cita cita) {
        cita.setPagada();
        Factura factura = cita.getFactura();
        String mensaje = "La factura " + factura.getId() + " ha sido pagada.";
        sistemaNotificacion.notificarObservadores(mensaje);
	}
	
	public static void enviarRecordatorio(String paciente, Factura factura) {
        System.out.println("Sr/a " + paciente + " se le hara envio de la factura " + factura.getId() + " correspondiente a la cita medica recientemente agendada, por un monto de " + factura.getMonto());
    }
	
	public boolean validarCita(Paciente paciente, EspecMedica especMedica,LocalDateTime horaInicio, Medico medico, MotivoCita motivoCita) {
		List<Cita> citasMedicasPaciente = getCitasPaciente(paciente); //Validar que no tenga citas en el horario solicitado
		List<Cita> citasMedicasMedico = getCitasMedico(medico);
		boolean valido= true;
		for (Cita cita : citasMedicasPaciente) { 
			if(cita.getHora_inicio().isAfter(cita.getHora_inicio().minusHours(1)) || cita.getHora_inicio().isBefore(cita.getHora_inicio().plusHours(1))) { //Como hago para validar que no tenga citas en un rango mas amplio??
				System.out.println("El paciente ya tiene citas programadas para esa fecha, no se agendo la cita");
				valido= false;
			}
		}
		for (Cita cita: citasMedicasMedico) {
			if(cita.getHora_inicio().isAfter(cita.getHora_inicio().minusHours(1)) || cita.getHora_inicio().isBefore(cita.getHora_inicio().plusHours(1))) { //Como hago para validar que no tenga citas en un rango mas amplio??
				System.out.println("El medico ya tiene citas programadas para esa fecha, no se agendo la cita");
				valido= false;
			}
			}
		//Valido que el medico seleccionado sea de la especialidad correcta
		if (medico.getEspecialidad()!= especMedica) {
			valido=false;
			System.out.println("La especialidad del medico seleccionado no es la indicada, no se agendo la cita");
		}
		return valido;
	}

	
	public void recibirNotificaciones(Paciente paciente) { //Devuelve las citas programadas del paciente
		int count = 0;
		for(Cita cita : citasMedicas) {
			if (paciente.getDni()== cita.getPacienteDNI()) {
				count ++;
				System.out.println("Cita nro "+ count +": "+ cita.toString());
			}
		}
	}
	
	
	public void cancelarCita(Cita citaAEliminar) { 
		String mensaje = ("Se ha eliminado la cita de ID "+ citaAEliminar.getCitaId());
		Paciente paciente= citaAEliminar.getPaciente();
		notificarCancelacion(paciente, citaAEliminar); //Se notifica al paciente
		for(Cita cita : citasMedicas)
			if(cita.getCitaId()== citaAEliminar.getCitaId()){
				citasMedicas.remove(citaAEliminar);
				System.out.println("Cita Eliminada con exito");
				sistemaNotificacion.notificarObservadores(mensaje); 
				break;
			}
		
	}
	
	public void notificarCancelacion(Paciente paciente, Cita cita) { //Devuelve las citas programadas del paciente
		System.out.println("Se notifica al paciente: " + paciente.getApellido()+ " de Mail: " + paciente.getMail() +" que su cita: " + cita.toString() + " se ha cancelado exitosamente");
	}
	
	
	public List<Cita> filtrarPorMedicoAsignado(Paciente paciente, Medico medico) { //Mostrar las citas de X medico con X paciente
		List<Cita> citasFiltradas = new ArrayList<>();
		for (Cita cita: citasMedicas) {
			if(cita.getPacienteDNI()== paciente.getDni() && cita.getMedicoDNI()==medico.getDni()) {
				citasFiltradas.add(cita);
			}
		}System.out.println(citasFiltradas.toString());
		return citasFiltradas;
	}
	
	public List<Cita> filtrarPorEspecialidad(Paciente paciente, EspecMedica especMedica) { //Mostrar las citas de X paciente para la especialidad indicada
		List<Cita> citasFiltradas = new ArrayList<>();
		for (Cita cita: citasMedicas) {
			if(cita.getPacienteDNI()== paciente.getDni() && cita.getEspecMedica()==especMedica) {
				citasFiltradas.add(cita);
			}
		}System.out.println(citasFiltradas.toString());
		return citasFiltradas;
	}
	
	public List<Cita> filtrarPorFecha(Paciente paciente, LocalDateTime fecha) { //Mostrar las citas de X paciente en un dia determinado
		List<Cita> citasFiltradas = new ArrayList<>();
		for (Cita cita: citasMedicas) {
			if(cita.getPacienteDNI()== paciente.getDni() && cita.getHora_inicio().getDayOfYear()==fecha.getDayOfYear()) {
				citasFiltradas.add(cita);
			}
		}System.out.println(citasFiltradas.toString());
		return citasFiltradas;
	}
	
	
	
	public void verHistorialMedico(int dni) {
		Paciente paciente = Paciente.buscarPacientePorDni(pacientes, dni);
		paciente.getHistorial_medico();
	}
	
	public List<Cita> getCitasPaciente(Paciente paciente){ //Devuelve las citas medicas programadas de un paciente
		List<Cita> citasMedicasPaciente = new ArrayList<>();
		for (Cita cita : citasMedicas) {
			if(paciente.getDni()==cita.getPacienteDNI()) {
				citasMedicasPaciente.add(cita);
			}
		}
		return citasMedicasPaciente;
		
	}

	public List<Cita> getCitasMedico(Medico medico){ //Devuelve las citas medicas programadas de un paciente
		List<Cita> citasMedicasMedico = new ArrayList<>();
		for (Cita cita : citasMedicas) {
			if(medico.getDni()==cita.getMedicoDNI()) {
				citasMedicasMedico.add(cita);
			}
		}
		return citasMedicasMedico;
	}
	
	public FormatoEnvioFactura obtenerFormatoEnvioFactura() {
		FormatoEnvioFactura formato;
		int numero = 0;
		Scanner scanner = new Scanner(System.in);
		
        while (numero != 1 && numero != 2) {
            System.out.print("Seleccione un formato para envio de factura. 1 para SMS, 2 para Mail");
            if (scanner.hasNextInt()) {
                numero = scanner.nextInt();
                if (numero != 1 && numero != 2) {
                    System.out.println("Número inválido. Por favor, ingresa 1 para SMS o 2 para Mail.");
                }
            }
        }
        if (numero == 1) return FormatoEnvioFactura.SMS;
        else return FormatoEnvioFactura.EMAIL;
	}
	
	public void enviarFactura(FormatoEnvioFactura formato,Cita cita) {
		Paciente paciente = cita.getPaciente();
		Factura factura = cita.getFactura();
		
		if (formato == FormatoEnvioFactura.EMAIL) {		
			System.out.println("Se envia la factura via Mail: " + factura.toString() + "Al paciente " + paciente.getApellido() + " " + paciente.getNombre() + " de mail: " + paciente.getMail());
					
		}
		else {
			System.out.println("Se envia la factura via SMS: " + factura.toString() + "Al paciente " + paciente.getApellido() + " " + paciente.getNombre() + " de numero: " + paciente.getNumero());
		}
	}
	
	

	public List<Paciente> getPacientes() {
		return pacientes;
	}


	public List<Medico> getMedicos() {
		return medicos;
	}

	@Override
	public String toString() {
		return "Sistema [pacientes=" + pacientes + "]";
	}





	
	
	
}
