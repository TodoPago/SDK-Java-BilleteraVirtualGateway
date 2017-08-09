package main;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import ar.com.todopago.api.TodoPagoConector;
import ar.com.todopago.api.exceptions.ConnectionException;
import ar.com.todopago.api.exceptions.ResponseException;
import mock.DiscoverPaymentMethodMock;

public class DiscoverPaymentMethodTest {

	@Test
	public void discoverOKResponse() throws ConnectionException, ResponseException{
		
		TodoPagoConector tpc=mock(TodoPagoConector.class);
		
		when(tpc.discoverPaymentMethodBSA()).thenReturn(DiscoverPaymentMethodMock.getOKResponse());
		
		List<Map<String, Object>> methodsList=tpc.discoverPaymentMethodBSA().getPaymentMethodsBSAList();
		
		Map<String,Object> elem=methodsList.get(0);
	
		assertEquals(1,elem.get("idMedioPago"));
		assertEquals("AMEX",elem.get("nombre"));
		assertEquals("Crédito",elem.get("tipoMedioPago"));
		assertEquals(1,elem.get("idBanco"));
		assertEquals("Provincia",elem.get("nombreBanco"));
	}
	
	@Test
	public void discoverErrorResponse() throws ConnectionException, ResponseException{
				
		TodoPagoConector tpc=mock(TodoPagoConector.class);
		
		when(tpc.discoverPaymentMethodBSA()).thenReturn(DiscoverPaymentMethodMock.getErrorResponse());
		
		List<Map<String, Object>> methodsList=tpc.discoverPaymentMethodBSA().getPaymentMethodsBSAList();
		List<Map<String, Object>> methodsListAux=new ArrayList<Map<String,Object>>();
		
		assertEquals(methodsListAux,methodsList);	
	}
}