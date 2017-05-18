package alberto.boedo.wirtz.DetectorColores;

import java.util.ArrayList;
import java.util.List;

import com.alberto.boedo.modelo.Persona;
import com.alberto.boedo.modelo.PersonaDAOImpl;

public class PersonaDaoMokTest {
	
	public static void main(String[] args) {
		
		PersonaDAOImpl personaDAO = new PersonaDAOImpl();
		
		List<String> rutas = new ArrayList<String>();
		rutas.add("src/jjaj.png");
		rutas.add("src/jpa.png");
	
		Persona p = new Persona();
		p.setNombre("Alberto");
		p.setApellidos("Boedo Galan");
		p.setEmail("a@b.com");
		p.setPassword("ijsioa");
		p.setFotos(rutas);
		p.setTelefono("981124073");
		
		personaDAO.addPersona(p);
		
	}
	


}
