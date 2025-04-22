package es.upm.sos.cliente;

import es.upm.sos.cliente.controller.Controller;
import es.upm.sos.cliente.view.View;

public class ClienteApplication {

	public static void main(String[] args) {
		//SpringApplication.run(ClienteApplication.class, args);
		Controller controller = new Controller();
		View view = new View();
		view.setController(controller);
		view.desplegarMenuPrincipal();
	}

}
