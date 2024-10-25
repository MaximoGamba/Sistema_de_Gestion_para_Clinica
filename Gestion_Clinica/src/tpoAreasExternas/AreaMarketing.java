package tpoAreasExternas;
import java.util.ArrayList;
import java.util.List;
public class AreaMarketing implements Observer{
	 private List<String> registros = new ArrayList<>();

	    @Override
	    public void actualizar(String message) {
	        System.out.println("Marketing: " + message);
	        registros.add(message);
	    }

	    @Override
	    public String toString() {
	    	System.out.println("REGISTROS AREA MARKETING: ");
	        return String.join("\n", registros);
	    }

}
