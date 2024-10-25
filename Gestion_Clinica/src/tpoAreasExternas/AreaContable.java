package tpoAreasExternas;
import java.util.ArrayList;
import java.util.List;
public class AreaContable implements Observer{
	private List<String> registros = new ArrayList<>();

    @Override
    public void actualizar(String message) {
        //System.out.println("Area contable: " + message);
        registros.add(message);
    }

    @Override
    public String toString() {
    	System.out.println("REGISTROS AREA CONTABLE: ");
        return String.join("\n", registros);
    }
	

}
