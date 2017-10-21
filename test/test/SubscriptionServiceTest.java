package test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.never;

import es.upm.grise.subscription.Client;
import es.upm.grise.subscription.ExistingClientException;
import es.upm.grise.subscription.Message;
import es.upm.grise.subscription.NonExistingClientException;
import es.upm.grise.subscription.NullClientException;
import es.upm.grise.subscription.SubscriptionExtension;
import es.upm.grise.subscription.SubscriptionService;

public class SubscriptionServiceTest {

	SubscriptionService service;
	SubscriptionExtension serviceEx;

	Client client1WithEmail;
	Client client2NOEmail;
	Client client3WithEmail;
	Message message1;
	Message message2;

	@Before
	public void setUp() throws NullClientException, ExistingClientException {
		client1WithEmail = mock(Client.class);
		client2NOEmail = mock(Client.class);
		client3WithEmail = mock(Client.class);
		
		message1 = mock(Message.class);
		message2 = mock(Message.class);

		when(client1WithEmail.hasEmail()).thenReturn(true);
		when(client2NOEmail.hasEmail()).thenReturn(false);
		when(client3WithEmail.hasEmail()).thenReturn(true);

		service = new SubscriptionService();
		serviceEx = new SubscriptionExtension();
		
	}
	
	
	/*****************************
	 ******STATE TEST*************
	 ****************************/
	

	/**
	 * No se puede a�adir un Client null a la lista subscribers.
	 **/
	@Test(expected = NullClientException.class)
	public void testNullClient() throws NullClientException, ExistingClientException {

		service.addSubscriber(null);

	}

	/**
	 * Al a�adir un Client mediante addSubscriber(), �ste Client se almacena en
	 * la lista subscribers.
	 **/
	@Test
	public void testStoreClient() throws NullClientException, ExistingClientException, NonExistingClientException {

		service.addSubscriber(client1WithEmail);
		service.removeSubscriber(client1WithEmail);

	}

	/**
	 * No se puede a�adir dos veces el mismo Client mediante addSubscriber() en
	 * la lista subscribers. Al hacerlo, se lanza la excepci�n
	 * ExistingClientException.
	 **/
	@Test(expected = ExistingClientException.class)
	public void test2Client() throws NullClientException, ExistingClientException, NonExistingClientException {

		service.addSubscriber(client1WithEmail);
		service.addSubscriber(client1WithEmail);

	}

	/**
	 * Al a�adir varios Client mediante addSubscriber(), todos los Client se
	 * almacenan en la lista subscribers.
	 **/
	@Test
	public void testExistsClients() throws NullClientException, ExistingClientException, NonExistingClientException {

		serviceEx.addSubscriber(client1WithEmail);
		serviceEx.addSubscriber(client2NOEmail);
		assertEquals(true, serviceEx.containsSub(client1WithEmail));
		assertEquals(true, serviceEx.containsSub(client2NOEmail));
	}

	/**
	 * No se puede eliminar (usando removeSubscriber()) un Client null de la
	 * lista subscribers. Al hacerlo, se lanza la excepci�n NullClientException.
	 * 
	 * @throws NullClientException
	 **/
	@Test(expected = NullClientException.class)
	public void testRemoveNullClients() throws NonExistingClientException, NullClientException {

		service.removeSubscriber(null);
	}

	/**
	 * No se puede eliminar (usando removeSubscriber()) un Client que no est�
	 * almacenado en la lista subscribers. Al hacerlo, se lanza la excepci�n
	 * NonExistingClientException.
	 * 
	 * @throws NullClientException
	 **/

	@Test(expected = NonExistingClientException.class)
	public void testRemoveNonExistingClient() throws NonExistingClientException, NullClientException {

		service.removeSubscriber(client1WithEmail);
	}

	/**
	 * Se puede eliminar correctamente (usando removeSubscriber()) un Client
	 * almacenado en la lista subscribers.
	 * 
	 * @throws NullClientException
	 * @throws ExistingClientException
	 **/

	@Test
	public void testRemoveCorrectly() throws NonExistingClientException, NullClientException, ExistingClientException {
		service.addSubscriber(client1WithEmail);
		service.removeSubscriber(client1WithEmail);
	}

	/**
	 * No se puede eliminar (usando removeSubscriber()) dos veces el mismo
	 * Client de la lista subscribers. Al hacerlo, se lanza la excepci�n
	 * NonExistingClientException.
	 * 
	 * @throws NullClientException
	 * @throws ExistingClientException
	 **/

	@Test(expected = NonExistingClientException.class)
	public void testRemove2Correctly() throws NonExistingClientException, NullClientException, ExistingClientException {
		service.addSubscriber(client1WithEmail);
		service.removeSubscriber(client1WithEmail);
		service.removeSubscriber(client1WithEmail);
	}

	/**
	 * Se pueden eliminar correctamente (usando removeSubscriber()) varios
	 * Client almacenados en la lista subscribers.
	 * 
	 * @throws NullClientException
	 * @throws ExistingClientException
	 **/

	@Test
	public void testRemoveCorrectlySeveralClients()
			throws NonExistingClientException, NullClientException, ExistingClientException {
		service.addSubscriber(client1WithEmail);
		service.addSubscriber(client2NOEmail);
		service.removeSubscriber(client1WithEmail);
		service.removeSubscriber(client2NOEmail);
	}

	/**
	 * Se pueden eliminar correctamente (usando removeSubscriber()) todos los
	 * Client almacenados en la lista subscribers.
	 * 
	 * @throws NullClientException
	 * @throws ExistingClientException
	 **/

	@Test
	public void testRemoveCorrectlyAllClients()
			throws NonExistingClientException, NullClientException, ExistingClientException {
		service.addSubscriber(client1WithEmail);
		service.addSubscriber(client2NOEmail);
		service.addSubscriber(client3WithEmail);
		service.removeSubscriber(client1WithEmail);
		service.removeSubscriber(client2NOEmail);
		service.removeSubscriber(client3WithEmail);
	}

	///////////////////////////////////////////////////////////////////////////////
	/*****************************
	 ******INTERACTION TEST*******
	 ****************************/

	///////////////////////////////////////////////////////////////////////////////
	/**
	 * Un Client suscrito recibe mensajes (m�todo receiveMessage()) si tiene
	 * email (m�todo hasEmail() == true).
	 * 
	 * @throws NullClientException
	 * @throws ExistingClientException
	 **/

	@Test
	public void testClientRecievesMessages() throws NullClientException, ExistingClientException{

		service.addSubscriber(client1WithEmail);
		service.sendMessage(message1);

		verify(client1WithEmail).receiveMessage(message1);
	}

	/**
	 * Un Client suscrito no recibe mensajes (m�todo receiveMessage()) si tiene //
	 * email (m�todo hasEmail() == false).
	 * 
	 * @throws NullClientException
	 * @throws ExistingClientException
	 **/

	@Test
	public void testClientDontRecievesMessages() throws NullClientException, ExistingClientException {

		service.addSubscriber(client2NOEmail);
		service.sendMessage(message1);

		verify(client1WithEmail,never()).receiveMessage(null);
	}


	/**
	 *  Varios Client suscritos reciben mensajes (m�todo receiveMessage()) si
	 *  tienen email (m�todo hasEmail() == true).
	 * 
	 * @throws NullClientException
	 * @throws ExistingClientException
	 **/

	@Test
	public void testClientsRecievesMessages() throws NullClientException, ExistingClientException{

		service.addSubscriber(client1WithEmail);
		service.addSubscriber(client3WithEmail);
		service.sendMessage(message1);

		verify(client1WithEmail).receiveMessage(message1);
		verify(client1WithEmail).receiveMessage(message1);
	}
	
	
	/**
	 * Al des-suscribir un Client, �ste no recibe mensajes (m�todo
	receiveMessage()).
	 * 
	 * @throws NullClientException
	 * @throws ExistingClientException
	 * @throws NonExistingClientException 
	 **/

	@Test
	public void testClientsDontRecievesMessages() throws NullClientException, ExistingClientException, NonExistingClientException {

		service.addSubscriber(client1WithEmail);
		service.removeSubscriber(client1WithEmail);
		service.sendMessage(message1);

		verify(client1WithEmail,never()).receiveMessage(null);
	}

	
	// Al des-suscribir un Client, �ste no recibe mensajes (m�todo
	// receiveMessage()).

}
