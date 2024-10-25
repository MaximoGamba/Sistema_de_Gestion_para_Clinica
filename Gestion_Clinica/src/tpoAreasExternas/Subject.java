package tpoAreasExternas;
import java.util.ArrayList;
import java.util.List;
public class Subject {
	private List<Observer> observers = new ArrayList<>(); //SUBJECT/OBSERVABLE SERIAN LAS CITAS o el SISTEMA?? a cada cambio de cita/pago notificar al area contable

    public void agregarObservador(Observer observer) {
        observers.add(observer);
    }

    public void notificarObservadores(String mensaje) {
        for (Observer observer : observers) {
            observer.actualizar(mensaje);
        }
    }

}
