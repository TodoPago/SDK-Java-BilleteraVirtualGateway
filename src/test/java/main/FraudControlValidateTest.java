package main;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.com.todopago.api.operations.FraudControlValidate;

public class FraudControlValidateTest {

	public static void main(String[] args) throws MalformedURLException {
		Map<String, String> parameters = new HashMap<String, String> ();
		FraudControlValidate FCV = new FraudControlValidate();
		parameters = FCV.validate(getFraudControlParametersTestroto());
		printMap(parameters, "");
	}

	private static void printMap(Map<String, String> pr, String tab) {

		List<String> keys = new ArrayList<String>();
		keys.addAll(pr.keySet());

		for (int i = 0; i < keys.size(); i++) {
			Map<String, String> aux = new HashMap<String, String>();
			Object tmp = pr.get(keys.get(i));

			if (tmp != null && tmp.getClass().isInstance(aux)) {
				System.out.println(tab + "- " + keys.get(i));
				aux = (Map<String, String>) (tmp);
				printMap(aux, tab + "  ");

			} else {
				System.out.println(tab + "- " + keys.get(i) + " : " + pr.get(keys.get(i)));
			}
		}
	}

	private static Map<String, String> getFraudControlParametersTest() {

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("CSBTCITY", "Villa General Belgrano"); // MANDATORIO.
		parameters.put("CSBTCOUNTRY", "AR");// MANDATORIO. Código ISO.
		parameters.put("CSBTEMAIL", "some@someurl.com"); // MANDATORIO.
		parameters.put("CSBTFIRSTNAME", "Juan");// MANDATORIO.
		parameters.put("CSBTLASTNAME", "Perez");// MANDATORIO.
		parameters.put("CSBTPHONENUMBER", "541160913988");// MANDATORIO.
		parameters.put("CSBTPOSTALCODE", "1010");// MANDATORIO.
		parameters.put("CSBTSTATE", "B");// MANDATORIO
		parameters.put("CSBTSTREET1", "Some Street 2153");// MANDATORIO.
		parameters.put("CSBTSTREET2", "test");// NO MANDATORIO

		parameters.put("CSBTCUSTOMERID", "453458"); // MANDATORIO.
		parameters.put("CSBTIPADDRESS", "192.0.0.4"); // MANDATORIO.
		parameters.put("CSPTCURRENCY", "ARS");// MANDATORIO.
		parameters.put("CSPTGRANDTOTALAMOUNT", "50.01");// MANDATORIO.

		parameters.put("CSMDD6", "");// NO MANDATORIO.
		parameters.put("CSMDD7", "");// NO MANDATORIO.
		parameters.put("CSMDD8", ""); // NO MANDATORIO.
		parameters.put("CSMDD9", "");// NO MANDATORIO.
		parameters.put("CSMDD10", "");// NO MANDATORIO.
		parameters.put("CSMDD11", "");// NO MANDATORIO.

		parameters.put("CSSTCITY", "Villa General Belgrano"); // MANDATORIO.
		parameters.put("CSSTCOUNTRY", "AR");// MANDATORIO. Código ISO.
		parameters.put("CSSTEMAIL", "some@someurl.com"); // MANDATORIO.
		parameters.put("CSSTFIRSTNAME", "Juan");// MANDATORIO.
		parameters.put("CSSTLASTNAME", "Perez");// MANDATORIO.
		parameters.put("CSSTPHONENUMBER", "541160913988");// MANDATORIO.
		parameters.put("CSSTPOSTALCODE", " 1010");// MANDATORIO.
		parameters.put("CSSTSTATE", "B");// MANDATORIO
		parameters.put("CSSTSTREET1", "Some Street 2153");// MANDATORIO.
		parameters.put("CSSTSTREET2", "test");// NO MANDATORIO.

		parameters.put("CSITPRODUCTCODE", "electronic_good");// CONDICIONAL
		parameters.put("CSITPRODUCTDESCRIPTION", "Test Prd Description");// CONDICIONAL.
		parameters.put("CSITPRODUCTNAME", "TestPrd");// CONDICIONAL.
		parameters.put("CSITPRODUCTSKU", "SKU1234");// CONDICIONAL.
		parameters.put("CSITTOTALAMOUNT", "10.01");// CONDICIONAL.
		parameters.put("CSITQUANTITY", "1");// CONDICIONAL.
		parameters.put("CSITUNITPRICE", "10.01");

		parameters.put("CSMDD12", "");// NO MADATORIO.
		parameters.put("CSMDD13", "");// NO MANDATORIO.
		parameters.put("CSMDD14", "");// NO MANDATORIO.
		parameters.put("CSMDD15", "");// NO MANDATORIO.
		parameters.put("CSMDD16", "");// NO MANDATORIO.

		return parameters;
	}

	private static Map<String, String> getFraudControlParametersTestroto() {

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("CSBTCITY", "Villa General BelgranoAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA!$%&/()?"); // MANDATORIO.
		parameters.put("CSBTCOUNTRY", "ARAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA!$%&/()?");// MANDATORIO.
																														// Código
																														// ISO.
		parameters.put("CSBTEMAIL", "some@someurl.com"); // MANDATORIO.
		parameters.put("CSBTFIRSTNAME", "JuanAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA!$%&/()?");// MANDATORIO.
		parameters.put("CSBTLASTNAME", "PerezAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA!$%&/()?");// MANDATORIO.
		parameters.put("CSBTPHONENUMBER", "5411609-13r988");// MANDATORIO.
		parameters.put("CSBTPOSTALCODE", "");// MANDATORIO.
		parameters.put("CSBTSTATE", "B");// MANDATORIO
		parameters.put("CSBTSTREET1",
				"Some Street 2153AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA!$%&/()?");// MANDATORIO.
		parameters.put("CSBTSTREET2", "yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy");// NO MANDATORIO

		parameters.put("CSBTCUSTOMERID", ""); // MANDATORIO.
		parameters.put("CSBTIPADDRESS", "190.111.246.12"); // MANDATORIO.
		parameters.put("CSPTCURRENCY", "ARS");// MANDATORIO.
		parameters.put("CSPTGRANDTOTALAMOUNT", "50.01");// MANDATORIO.

		parameters.put("CSMDD6", "");// NO MANDATORIO.
		parameters.put("CSMDD7", "");// NO MANDATORIO.
		parameters.put("CSMDD8", ""); // NO MANDATORIO.
		parameters.put("CSMDD9", "");// NO MANDATORIO.
		parameters.put("CSMDD10", "");// NO MANDATORIO.
		parameters.put("CSMDD11", "");// NO MANDATORIO.

		parameters.put("CSSTCITY",
				"Villa General BelgranoPerezAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA!$%&/()?"); // MANDATORIO.
		parameters.put("CSSTCOUNTRY", "ARPerezAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA!$%&/()?");// MANDATORIO.
																														// Código
																														// ISO.
		parameters.put("CSSTEMAIL", "some@someurl.com"); // MANDATORIO.
		parameters.put("CSSTFIRSTNAME",
				"JuanPerezAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA!$%&/()?");// MANDATORIO.
		parameters.put("CSSTLASTNAME",
				"PerezPerezAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA!$%&/()?");// MANDATORIO.
		parameters.put("CSSTPHONENUMBER", "541160913988");// MANDATORIO.
		parameters.put("CSSTPOSTALCODE", " 1010");// MANDATORIO.
		parameters.put("CSSTSTATE", "B");// MANDATORIO
		parameters.put("CSSTSTREET1",
				"Some Street 2153PerezAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA!$%&/()?");// MANDATORIO.
		// parameters.put("CSSTSTREET2", "t");// NO MANDATORIO.

		parameters.put("CSITPRODUCTCODE",
				"electronic_good#aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa#bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb#ccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc");// CONDICIONAL
		parameters.put("CSITPRODUCTDESCRIPTION",
				"Test Prd Description#aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa#bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb#ccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc");// CONDICIONAL.
		parameters.put("CSITPRODUCTNAME",
				"TestPrd#aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa#bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb#ccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc");// CONDICIONAL.
		parameters.put("CSITPRODUCTSKU",
				"SKU1234#aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa#bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb#ccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc");// CONDICIONAL.
		parameters.put("CSITTOTALAMOUNT", "10.01#10.01#10.01#10.01");// CONDICIONAL.
		parameters.put("CSITQUANTITY", "1#1#1#1");// CONDICIONAL.
		parameters.put("CSITUNITPRICE", "10.01#10.01#10.01#10.01");

		parameters.put("CSMDD12", "");// NO MADATORIO.
		parameters.put("CSMDD13", "");// NO MANDATORIO.
		parameters.put("CSMDD14", "Car");// NO MANDATORIO.
		parameters.put("CSMDD15", "");// NO MANDATORIO.
		parameters.put("CSMDD16", "");// NO MANDATORIO.

		return parameters;
	}

}
