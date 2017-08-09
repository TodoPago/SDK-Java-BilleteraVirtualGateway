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
import ar.com.todopago.api.model.TransactionBSA;
import mock.BSAParametersMock;
import mock.TransactionBSAMock;

public class TransactionTest {

	private TransactionBSA transactionOKParameters=TransactionBSAMock.getTransactionParameters("1",BSAParametersMock.security);
	private TransactionBSA transactionErrorParameters=TransactionBSAMock.getTransactionParameters("",BSAParametersMock.security);
	private TransactionBSA transactionError702Parameters=TransactionBSAMock.getTransactionParameters("1","");
	
	private Map<String,Object> transactionOKResponse=TransactionBSAMock.getOKResponse();
	private Map<String,Object> transactionErrorResponse=TransactionBSAMock.getErrorResponse();
	private Map<String,Object> transactionError702Response=TransactionBSAMock.getError702Response();
	
	@Test
	public void transactionOKTest() throws EmptyFieldPassException, ConnectionException, ResponseException, InvalidFieldException {
		TodoPagoConector tpc=mock(TodoPagoConector.class);
		
		TransactionBSA transaction=new TransactionBSA();
		
		transaction.setTransactionResponse(transactionOKResponse);
		
		when(tpc.transaction(transactionOKParameters)).thenReturn(transaction);
		
		Map<String,Object> response=tpc.transaction(transactionOKParameters).getTransactionResponse();
		
		assertEquals(37581,response.get("merchantId"));
		assertEquals("411d188b-2c6d-4d39-977c-b6d9de119c80",response.get("publicRequestKey"));
		assertEquals(11,response.get("channel"));	
	}
	
	@Test
	public void transactionErrorTest() throws EmptyFieldPassException, ConnectionException, ResponseException, InvalidFieldException {
		TodoPagoConector tpc=mock(TodoPagoConector.class);
		
		TransactionBSA transaction=new TransactionBSA();
		
		transaction.setTransactionResponse(transactionErrorResponse);
		
		when(tpc.transaction(transactionErrorParameters)).thenReturn(transaction);
		
		Map<String,Object> response=tpc.transaction(transactionErrorParameters).getTransactionResponse();
		
		assertEquals(1014,response.get("errorCode"));
		assertEquals("Completá este campo.",response.get("errorMessage"));
		assertEquals(11,response.get("channel"));	
	}
	
	@Test
	public void transactionError702Test() throws EmptyFieldPassException, ConnectionException, ResponseException, InvalidFieldException {
		TodoPagoConector tpc=mock(TodoPagoConector.class);
		
		TransactionBSA transaction=new TransactionBSA();
		
		transaction.setTransactionResponse(transactionError702Response);
		
		when(tpc.transaction(transactionError702Parameters)).thenReturn(transaction);
		
		Map<String,Object> response=tpc.transaction(transactionError702Parameters).getTransactionResponse();
		
		assertEquals(702,response.get("errorCode"));
		assertEquals("Cuenta de vendedor invalida",response.get("errorMessage"));
		assertEquals(11,response.get("channel"));	
	}
}