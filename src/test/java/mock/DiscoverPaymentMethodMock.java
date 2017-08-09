package mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.com.todopago.api.model.PaymentMethodsBSA;

public class DiscoverPaymentMethodMock {

	public static PaymentMethodsBSA getOKResponse(){
		PaymentMethodsBSA pmBSA=new PaymentMethodsBSA();
		
		List<Map<String, Object>> pmsList=new ArrayList<Map<String,Object>>();
		Map<String,Object> pm0=new HashMap<String, Object>();
		
		pm0.put("idMedioPago",1);
		pm0.put("nombre","AMEX");
		pm0.put("tipoMedioPago","Crédito");
		pm0.put("idBanco",1);
		pm0.put("nombreBanco","Provincia");
		
		pmsList.add(pm0);
		pmBSA.setPaymentMethodsBSAList(pmsList);
		
		return pmBSA;
	}

	public static PaymentMethodsBSA getErrorResponse() {
		PaymentMethodsBSA pmBSA=new PaymentMethodsBSA();
		List<Map<String, Object>> pmsList=new ArrayList<Map<String,Object>>();
		
		pmBSA.setPaymentMethodsBSAList(pmsList);
		
		return pmBSA;
	}
}