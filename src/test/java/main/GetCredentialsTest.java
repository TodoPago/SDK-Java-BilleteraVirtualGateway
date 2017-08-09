package main;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.net.MalformedURLException;

import org.junit.Test;

import ar.com.todopago.api.TodoPagoConector;
import ar.com.todopago.api.exceptions.ConnectionException;
import ar.com.todopago.api.exceptions.EmptyFieldException;
import ar.com.todopago.api.exceptions.EmptyFieldPassException;
import ar.com.todopago.api.exceptions.EmptyFieldUserException;
import ar.com.todopago.api.exceptions.ResponseException;
import ar.com.todopago.api.model.User;
import mock.GetCredentiasMockParameters;

public class GetCredentialsTest {

	User mockUser=GetCredentiasMockParameters.getUser();
	String name="Edmonton";
	
	@Test(expected=EmptyFieldUserException.class)
	public void nullUserExceptionTest() throws MalformedURLException, EmptyFieldException, ResponseException, ConnectionException{
		User user=getUserInstance();
		TodoPagoConector tpc=getConectorInstance();
		
		user.setUser(null);
	
		tpc.getCredentials(user);
	}
	
	@Test(expected=EmptyFieldUserException.class)
	public void emptyUserExceptionTest() throws MalformedURLException, EmptyFieldException, ResponseException, ConnectionException{
		User user=getUserInstance();
		TodoPagoConector tpc=getConectorInstance();
		
		user.setUser("");
	
		tpc.getCredentials(user);
	}
	
	@Test(expected=EmptyFieldPassException.class)
	public void nullPasswordExceptionTest() throws MalformedURLException, EmptyFieldException, ResponseException, ConnectionException{
		User user=getUserInstance();
		TodoPagoConector tpc=getConectorInstance();
		
		user.setUser(name);
		user.setPassword(null);
	
		tpc.getCredentials(user);
	}
	
	@Test(expected=EmptyFieldPassException.class)
	public void emptyPasswordExceptionTest() throws MalformedURLException, EmptyFieldException, ResponseException, ConnectionException{
		User user=getUserInstance();
		TodoPagoConector tpc=getConectorInstance();
		
		user.setUser(name);
		user.setPassword(null);
	
		tpc.getCredentials(user);
	}
	
	@Test()
	public void getCredentialsOKTest() throws MalformedURLException, EmptyFieldException, ResponseException, ConnectionException{
		TodoPagoConector tpc=mock(TodoPagoConector.class);
		User user=mock(User.class);
		
		when(user.getUser()).thenReturn("user");
		when(user.getPassword()).thenReturn("Password");
		when(tpc.getCredentials(getUserInstance())).thenReturn(mockUser);

		assertEquals("test@test.com.ar",mockUser.getUser());
		assertEquals("test1234",mockUser.getPassword());
		
	}
	
	/*---- Auxiliary methods ----*/
	private User getUserInstance(){
		return new User();
	}
	
	private TodoPagoConector getConectorInstance() throws MalformedURLException{
		return new TodoPagoConector(111);
	}
}