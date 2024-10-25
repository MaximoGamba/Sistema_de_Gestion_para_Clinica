package tpoSrc;
import java.util.List;
import tpoEnums.EspecMedica;

public class Factura {
	private int id;
	private boolean pagada;
	private double monto;
	private static int next_id = 0;

	
	
	public Factura(Cita cita) {
		super();
		this.pagada = false;
		this.monto = calcularTotal(cita);
		this.id = next_id;
		
		next_id++;
	}
	
	
    public void registrarPago(Cita cita) {
        cita.setPagada();
    }

    public boolean estaPagada() {
        return this.pagada;
    }

	public double calcularTotal(Cita cita) {
		double costoBase = 0;
	    EspecMedica especialidad = cita.getEspecMedica();
	    
	 // Lógica para determinar el costo base según la especialidad médica
        switch (especialidad) {
            case CARDIOLOGIA:
                costoBase = 100;
                break;
            case NEUROLOGIA:
                costoBase = 120;
                break;
            case PEDIATRIA:
                costoBase = 70;
                break;
            case TRAUMATOLOGIA:
                costoBase = 90;
                break;
            default:
                break;
        }

        // Ajustar el costo según la complejidad del procedimiento
        switch (cita.getComplejidad().toString().toUpperCase()) {
            case "ALTA":
                costoBase *= 1.5;
                break;
            case "MEDIA":
                costoBase *= 1.2;
                break;
            case "BAJA":
                costoBase *= 1.0;
                break;
            default:
                break;
        }

        // Agregar costo de servicios adicionales
        List<String> serviciosAdicionales = cita.getServiciosAdicionales();
        for (String servicio : serviciosAdicionales) {
            switch (servicio.toUpperCase()) {
                case "ANÁLISIS DE SANGRE":
                    costoBase += 20;
                    break;
                case "RAYOS X":
                    costoBase += 50;
                    break;
                default:
                    break;
            }
        }

        // Aplicar descuentos especiales
        if (cita.getPaciente().esJubilado()) {
            costoBase *= 0.8; // 20% de descuento para jubilados
        } else if (cita.getPaciente().tieneSeguroMedico()) {
            costoBase *= 0.9; // 10% de descuento para pacientes con seguro médico
        }

        return costoBase;
    }

    public double getMonto() {
        return monto;
    }

    public boolean isPagada() {
        return pagada;
    }
    
	public int getId(){
		return id;
	}
	
	public void setPagada() {
		this.pagada = true;
	}
    
    
	@Override
	public String toString() {
		return "Datos de la factura: Estado: " + pagada + ", Monto: " + monto + " ";
	}
}
	    
	    
	

