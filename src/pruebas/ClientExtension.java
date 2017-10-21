package pruebas;

import es.upm.grise.subscription.Client;
import es.upm.grise.subscription.Message;

public class ClientExtension implements Client{

	
	public ClientExtension() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void receiveMessage(Message message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean hasEmail() {
		// TODO Auto-generated method stub
		return false;
	}
	

}
