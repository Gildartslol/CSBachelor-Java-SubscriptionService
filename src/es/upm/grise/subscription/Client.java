package es.upm.grise.subscription;

public interface Client {

	void receiveMessage(Message message);

	boolean hasEmail();

}
