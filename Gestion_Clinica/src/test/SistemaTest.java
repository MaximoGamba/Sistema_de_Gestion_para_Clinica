package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import tpoEnums.*;
import tpoSrc.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class SistemaTest {

    private Sistema sistema;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUp() {
        sistema = new Sistema(); // Inicializa tu Sistema antes de cada prueba si es necesario
        System.setOut(new PrintStream(outContent));
    }
    
    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testAgregarPaciente_Administrador() {
        // Configura el entorno necesario, como iniciar sesión como administrador
        // Puedes usar mocks o configuraciones simuladas para UserSession y AuthService.

        UserSession.getInstance().setUserType(UserType.ADMIN); // Simula usuario tipo administrador

        // Define datos de prueba para el paciente
        int dni = 12345678;
        String nombre = "Juan";
        String apellido = "Pérez";
        LocalDate fechaNac = LocalDate.of(1990, 5, 15);
        String direccion = "Calle Principal 123";
        String telefono = "555-123456";
        ObraSocial obraSocial = ObraSocial.OSDE;
        String email = "juan.perez@example.com";
        String historialMedico = "Historial médico de Juan Pérez.";

        // Ejecuta el método que quieres probar
        Paciente paciente = sistema.agregarPaciente(dni, nombre, apellido, fechaNac, direccion, telefono, obraSocial, email, historialMedico);

        // Verifica que el paciente se haya agregado correctamente a la lista de pacientes
        assertNotNull(paciente); // Verifica que el paciente no sea nulo
        assertEquals(dni, paciente.getDni()); // Verifica que el DNI sea el esperado
        assertEquals(nombre, paciente.getNombre()); // Verifica que el nombre sea el esperado
        assertEquals(apellido, paciente.getApellido()); // Verifica que el apellido sea el esperado
        assertEquals(fechaNac, paciente.getFecha_nac()); // Verifica que la fecha de nacimiento sea la esperada
        assertEquals(direccion, paciente.getDireccion()); // Verifica que la dirección sea la esperada
        assertEquals(telefono, paciente.getTelefono()); // Verifica que el teléfono sea el esperado
        assertEquals(obraSocial, paciente.getObraSocial()); // Verifica que la obra social sea la esperada
        assertEquals(email, paciente.getEmail()); // Verifica que el email sea el esperado

        // Puedes seguir agregando más aserciones según sea necesario para otros campos o comportamientos esperados.
    }

    @Test
    public void testAgregarPaciente_NoAdmin() {
        // Configura el entorno necesario para simular un usuario no administrador
        UserSession.getInstance().setUserType(UserType.USER); // Simula usuario tipo usuario normal

        // Intenta agregar un paciente como usuario no administrador
        Paciente paciente = sistema.agregarPaciente(12345678, "Juan", "Pérez", LocalDate.of(1990, 5, 15), "Calle Principal 123", "555-123456", ObraSocial.OSDE, "juan.perez@example.com", "Historial médico de Juan Pérez.");

        // Verifica que no se haya agregado ningún paciente (debería devolver null según tu implementación)
        assertNull(paciente); // Verifica que el paciente sea nulo
    }
    
    @Test
    public void testEditarHistorialPaciente_Administrador() {
        // Configura el entorno necesario, como iniciar sesión como administrador
        // Puedes usar mocks o configuraciones simuladas para UserSession y AuthService.

        UserSession.getInstance().setUserType(UserType.ADMIN); // Simula usuario tipo administrador

        // Agrega un paciente para probar la edición del historial médico
        Paciente paciente = new Paciente(12345678, "Juan", "Pérez", LocalDate.of(1990, 5, 15), "Calle Principal 123", "555-123456", ObraSocial.OSDE, "juan.perez@example.com");
        sistema.getPacientes().add(paciente); // Agrega el paciente a la lista de pacientes

        // Agrega una entrada al historial médico del paciente
        String entrada = "Nuevo procedimiento realizado.";
        sistema.editarHistorialPaciente(12345678, entrada);

        // Verifica que la entrada se haya agregado correctamente al historial médico del paciente
        assertEquals(1, paciente.getHistorial_medico().size()); // Verifica que se haya agregado una entrada al historial médico
        assertEquals(entrada, paciente.getHistorial_medico().get(0)); // Verifica que la entrada agregada sea la esperada
    }
    
    
    @Test
    public void testProgramarCita_Administrador_Correcta() {
        // Configura el entorno necesario, como iniciar sesión como administrador
        // Puedes usar mocks o configuraciones simuladas para UserSession y AuthService.

        UserSession.getInstance().setUserType(UserType.ADMIN); // Simula usuario tipo administrador

        // Define datos de prueba para la cita
        LocalDateTime horaInicio = LocalDateTime.now().plusDays(1); // Fecha y hora de inicio de la cita (ejemplo: mañana)
        Paciente paciente = new Paciente(12345678, "Juan", "Pérez", LocalDate.of(1990, 5, 15), "Calle Principal 123", "555-123456", ObraSocial.OSDE, "juan.perez@example.com");
        Medico medico = new Medico("Dr. García","Estandar", EspecMedica.CARDIOLOGIA,44444444);
        MotivoCita motivoCita = MotivoCita.CONSULTA;

        // Ejecuta el método que quieres probar
        Cita cita = sistema.programarCita(EspecMedica.CARDIOLOGIA, horaInicio, paciente, medico, motivoCita);

        // Verifica que la cita se haya programado correctamente
        assertNotNull(cita); // Verifica que la cita no sea nula
        assertEquals(EspecMedica.CARDIOLOGIA, cita.getEspecMedica()); // Verifica que la especialidad médica sea la esperada
        assertEquals(horaInicio, cita.getHora_inicio()); // Verifica que la hora de inicio sea la esperada
        assertEquals(paciente, cita.getPaciente()); // Verifica que el paciente sea el esperado
        assertEquals(medico, cita.getMedico()); // Verifica que el médico sea el esperado
        assertEquals(motivoCita, cita.getMotivoCita()); // Verifica que el motivo de la cita sea el esperado

  
    }

    @Test
    public void testProgramarCita_NoAdmin() {
        // Configura el entorno necesario para simular un usuario no administrador
        UserSession.getInstance().setUserType(UserType.USER); // Simula usuario tipo usuario normal

        // Intenta programar una cita como usuario no administrador
        Cita cita = sistema.programarCita(EspecMedica.CARDIOLOGIA, LocalDateTime.now(), new Paciente(0, null, null, null, null, null, null, null), new Medico(null, null, null, 0), MotivoCita.CONSULTA);

        // Verifica que no se haya programado ninguna cita (debería devolver null según tu implementación)
        assertNull(cita); // Verifica que la cita sea nula
}
    
    @Test
    public void testFiltrarPorMedicoAsignado_CitasEncontradas() {
        // Configura el entorno necesario y agrega citas de ejemplo al sistema
    	Medico medico = new Medico("Dr. García","Estandar", EspecMedica.CARDIOLOGIA,44444444);
        Paciente paciente = sistema.agregarPaciente(44788598,"lionel", "messi",LocalDate.of(2003,04,03),"Artigas","1122926211",ObraSocial.OSDE,"gian@gmail","");

        // Crea algunas citas para el paciente y médico específicos
        
        Cita cita1 = sistema.programarCita(EspecMedica.CARDIOLOGIA, LocalDateTime.of(2024, 2, 1, 10, 0), paciente, medico, MotivoCita.CONSULTA );
//


        // Ejecuta el método que quieres probar
        List<Cita> citasFiltradas = sistema.filtrarPorMedicoAsignado(paciente, medico);

        // Verifica que las citas filtradas sean las esperada
        assertEquals(1, citasFiltradas.size()); // Verifica que se hayan encontrado todas las citas

        // Puedes agregar más aserciones según lo esperado en la salida de este método.
    }

    @Test
    public void testFiltrarPorMedicoAsignado_NingunaCitaEncontrada() {
        // Configura el entorno necesario y agrega citas de ejemplo al sistema
        Medico medico = new Medico("Dr. García","Smith", EspecMedica.CARDIOLOGIA,12345678);
        Paciente paciente = new Paciente(87654321, "Ana", "Martínez", LocalDate.of(1985, 10, 20), "Av. Principal 456", "555-987654", ObraSocial.OSDE, "ana.martinez@example.com");

        // Agrega citas diferentes o ninguna cita para este paciente y médico específicos

        // Ejecuta el método que quieres probar
        List<Cita> citasFiltradas = sistema.filtrarPorMedicoAsignado(paciente, medico);

        // Verifica que no se haya encontrado ninguna cita
        assertEquals(0, citasFiltradas.size()); // Verifica que no se hayan encontrado citas
}
    @Test
    public void testFiltrarPorFecha_CitasEncontradas() {
        // Configura el entorno necesario y agrega citas de ejemplo al sistema
        Paciente paciente = new Paciente(87654321, "Ana", "Martínez", LocalDate.of(1985, 10, 20), "Av. Principal 456", "555-987654", ObraSocial.OSDE, "ana.martinez@example.com");

        // Crea algunas citas para el paciente en una fecha específica
        LocalDateTime fecha = LocalDateTime.of(2024, 7, 1, 0, 0); // Fecha a filtrar
        
        Cita cita1 = new Cita(1, LocalDateTime.of(2024, 7, 1, 10, 0), paciente, new Medico(), MotivoCita.CONSULTA_GENERAL, EspecMedica.CARDIOLOGIA);
        Cita cita2 = new Cita(2, LocalDateTime.of(2024, 7, 1, 15, 30), paciente, new Medico(), MotivoCita.CONTROL_ANUAL, EspecMedica.CARDIOLOGIA);
        Cita cita3 = new Cita(3, LocalDateTime.of(2024, 7, 2, 9, 0), paciente, new Medico(), MotivoCita.CONSULTA_GENERAL, EspecMedica.NEUROLOGIA);

        sistema.getCitasMedicas().add(cita1);
        sistema.getCitasMedicas().add(cita2);
        sistema.getCitasMedicas().add(cita3);

        // Ejecuta el método que quieres probar
        sistema.filtrarPorFecha(paciente, fecha);

        // Verifica que las citas filtradas sean las esperadas
        // (En este caso, deberían ser cita1 y cita2)
        List<Cita> citasFiltradas = sistema.getCitasFiltradas();
        assertEquals(2, citasFiltradas.size()); // Verifica que se hayan encontrado las citas esperadas

        // Puedes agregar más aserciones según lo esperado en la salida de este método.
    }
    
    @Test
    public void testFiltrarPorFecha_NingunaCitaEncontrada() {
        // Configura el entorno necesario y agrega citas de ejemplo al sistema
        Paciente paciente = new Paciente(87654321, "Ana", "Martínez", LocalDate.of(1985, 10, 20), "Av. Principal 456", "555-987654", ObraSocial.OSDE, "ana.martinez@example.com");

        // Crea algunas citas para el paciente en una fecha diferente
        LocalDateTime fecha = LocalDateTime.of(2024, 7, 1, 0, 0); // Fecha a filtrar
        
        Cita cita1 = new Cita(1, LocalDateTime.of(2024, 7, 2, 10, 0), paciente, new Medico(), MotivoCita.CONSULTA_GENERAL, EspecMedica.CARDIOLOGIA);
        Cita cita2 = new Cita(2, LocalDateTime.of(2024, 7, 3, 15, 30), paciente, new Medico(), MotivoCita.CONSULTA, EspecMedica.CARDIOLOGIA);
        Cita cita3 = new Cita(3, LocalDateTime.of(2024, 7, 4, 9, 0), paciente, new Medico(), MotivoCita.CONSULTA, EspecMedica.NEUROLOGIA);

        sistema.getCitasMedicas().add(cita1);
        sistema.getCitasMedicas().add(cita2);
        sistema.getCitasMedicas().add(cita3);

        // Ejecuta el método que quieres probar
        sistema.filtrarPorFecha(paciente, fecha);

        // Verifica que no se haya encontrado ninguna cita
        List<Cita> citasFiltradas = sistema.getCitasFiltradas();
        assertEquals(0, citasFiltradas.size()); // Verifica que no se hayan encontrado citas

        // Puedes agregar más aserciones según el comportamiento esperado cuando no se encuentran citas.
    }
    
    @Test
    public void testEnviarFactura_Email() {
        Paciente paciente = new Paciente(87654321, "Ana", "Martínez", 
                                         LocalDate.of(1985, 10, 20), 
                                         "Av. Principal 456", "555-987654", 
                                         ObraSocial.OSDE, "ana.martinez@example.com");
        Factura factura = new Factura();
        Cita cita = new Cita(1, LocalDateTime.of(2024, 7, 1, 10, 0), 
                             paciente, new Medico(12345678, "Dr. Smith", EspecMedica.CARDIOLOGIA), 
                             MotivoCita.CONSULTA_GENERAL, EspecMedica.CARDIOLOGIA);
        cita.setFactura(factura);

        sistema.enviarFactura(Sistema.FormatoEnvioFactura.EMAIL, cita);

        String expectedOutput = "Se envia la factura via Mail: " + factura.toString() + 
                                " al paciente Martínez Ana de mail: ana.martinez@example.com\n";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void testEnviarFactura_SMS() {
        Paciente paciente = new Paciente(87654321, "Ana", "Martínez", 
                                         LocalDate.of(1985, 10, 20), 
                                         "Av. Principal 456", "555-987654", 
                                         ObraSocial.OSDE, "ana.martinez@example.com");
        Factura factura = new Factura();
        Cita cita = new Cita(1, LocalDateTime.of(2024, 7, 1, 10, 0), 
                             paciente, new Medico(12345678, "Dr. Smith", EspecMedica.CARDIOLOGIA), 
                             MotivoCita., EspecMedica.CARDIOLOGIA);
        cita.setFactura(factura);

        sistema.enviarFactura(FormatoEnvioFactura.SMS, cita);

        String expectedOutput = "Se envia la factura via SMS: " + factura.toString() + 
                                " al paciente Martínez Ana de numero: 555-987654\n";
        assertEquals(expectedOutput, outContent.toString());
    }
    
    @Test
    public void testValidarCita_SinConflictos() {
        LocalDateTime horaInicio = LocalDateTime.of(2024, 7, 1, 10, 0);
        boolean resultado = sistema.validarCita(paciente, EspecMedica.CARDIOLOGIA, horaInicio, medico, MotivoCita.CONSULTA_GENERAL);
        assertTrue(resultado);
    }

    @Test
    public void testValidarCita_ConflictoConPaciente() {
        LocalDateTime horaInicio = LocalDateTime.of(2024, 7, 1, 10, 0);
        Cita citaExistente = new Cita(1, LocalDateTime.of(2024, 7, 1, 9, 30), paciente, medico, MotivoCita.CONSULTA_GENERAL, EspecMedica.CARDIOLOGIA);
        sistema.getCitasMedicas().add(citaExistente);

        boolean resultado = sistema.validarCita(paciente, EspecMedica.CARDIOLOGIA, horaInicio, medico, MotivoCita.CONSULTA_GENERAL);
        assertFalse(resultado);
    }

    @Test
    public void testValidarCita_ConflictoConMedico() {
        LocalDateTime horaInicio = LocalDateTime.of(2024, 7, 1, 10, 0);
        Cita citaExistente = new Cita(1, LocalDateTime.of(2024, 7, 1, 9, 30), paciente, medico, MotivoCita.CONSULTA_GENERAL, EspecMedica.CARDIOLOGIA);
        sistema.getCitasMedicas().add(citaExistente);

        boolean resultado = sistema.validarCita(paciente, EspecMedica.CARDIOLOGIA, horaInicio, medico, MotivoCita.CONSULTA_GENERAL);
        assertFalse(resultado);
    }

    @Test
    public void testValidarCita_EspecialidadIncorrecta() {
        LocalDateTime horaInicio = LocalDateTime.of(2024, 7, 1, 10, 0);
        Medico otroMedico = new Medico(12345679, "Dr. Jones", EspecMedica.NEUROLOGIA);

        boolean resultado = sistema.validarCita(paciente, EspecMedica.CARDIOLOGIA, horaInicio, otroMedico, MotivoCita.CONSULTA_GENERAL);
        assertFalse(resultado);
    }


}
    
    
    
    

    
    
    
    