package es.upm.grise.subscription;

import org.junit.experimental.theories.Theories;

public class SubscriptionExtension extends SubscriptionService {
	
	
	public SubscriptionExtension() {
		// TODO Auto-generated constructor stub
	}
	
	public boolean containsSub(Client client){
		return this.subscribers.contains(client);
	}

}
