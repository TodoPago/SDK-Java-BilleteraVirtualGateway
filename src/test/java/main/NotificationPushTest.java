package main;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

import java.util.Map;

import org.junit.Test;

import ar.com.todopago.api.TodoPagoConector;
import ar.com.todopago.api.exceptions.ConnectionException;
import ar.com.todopago.api.exceptions.EmptyFieldPassException;
import ar.com.todopago.api.exceptions.InvalidFieldException;
import ar.com.todopago.api.exceptions.ResponseException;
import ar.com.todopago.api.model.NotificationPushBSA;
import mock.NotificationPushMock;

public class NotificationPushTest {

	private NotificationPushBSA notificationPushParametersOK=NotificationPushMock.getNotificationPushOKParameters();
	private NotificationPushBSA notificationPushParametersError=NotificationPushMock.getNotificationPushErrorParameters();

	private NotificationPushBSA notificationPushOKResponse=NotificationPushMock.getOkResponse();
	private NotificationPushBSA notificationPushErroresponse=NotificationPushMock.getErrorResponse();	
	
	@Test
	public void notificationOKTest() throws EmptyFieldPassException, ConnectionException, ResponseException, InvalidFieldException {
		
		TodoPagoConector tpc=mock(TodoPagoConector.class);
		
		when(tpc.notificationPush(notificationPushParametersOK)).thenReturn(notificationPushOKResponse);
			
		NotificationPushBSA notification=tpc.notificationPush(notificationPushParametersOK);
		
		assertEquals("-1",notification.getStatusCode());
		assertEquals("OK",notification.getStatusMessage());
	}
	
	@Test
	public void notificationErrorTest() throws EmptyFieldPassException, ConnectionException, ResponseException, InvalidFieldException {
			
		TodoPagoConector tpc=mock(TodoPagoConector.class);
		
		when(tpc.notificationPush(notificationPushParametersError)).thenReturn(notificationPushErroresponse);
			
		NotificationPushBSA notification=tpc.notificationPush(notificationPushParametersError);

		assertEquals("1014",notification.getStatusCode());
		assertEquals("Completá este campo.",notification.getStatusMessage());
	}
}