package ec.edu.ups.p58.pw.demoappdocker;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/hola")
public class HolaMundoService {

	@GET
	@Produces("application/json")
	public String getSaludo() {
		return "{ \"mensaje\": \"Hola mundo\" }";
	}
}
