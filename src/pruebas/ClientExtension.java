package pruebas;

import es.upm.grise.subscription.Client;
import es.upm.grise.subscription.Message;

public class ClientExtension implements Client{

	
	public ClientExtension() {
		
	}
	
	@Override
	public void receiveMessage(Message message) {
		
		
	}

	@Override
	public boolean hasEmail() {
		
		return false;
	}
	

}
