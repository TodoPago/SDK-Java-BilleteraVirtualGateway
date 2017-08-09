package ar.com.todopago.api.model;

import java.util.List;
import java.util.Map;

public class PaymentMethodsBSA {
	
    private List<Map<String, Object>> paymentMethodsBSAList;

    public List<Map<String, Object>> getPaymentMethodsBSAList(){
        return this.paymentMethodsBSAList;
    }

    public void setPaymentMethodsBSAList(List<Map<String, Object>> paymentMethodsBSAList){
        this.paymentMethodsBSAList = paymentMethodsBSAList;
    }
	

}
