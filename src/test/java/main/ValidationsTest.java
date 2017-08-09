package main;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import ar.com.todopago.api.operations.Validations;

public class ValidationsTest {

	@Test
	public void isNumericTest() {
		String email1="12222";
		String email2="345";
		String email3="mkyong@.com.my";
		String email4="2233op";
		
		assertEquals(true,Validations.isNumeric(email1));
		assertEquals(true,Validations.isNumeric(email2));
		assertNotEquals(true,Validations.isNumeric(email3));
		assertNotEquals(true,Validations.isNumeric(email4));
	}
	
	@Test
	public void isIPV4Test() {
		String ip1="1.1.1.1";
		String ip2="255.255.255.255";
		String ip3="10.10.10";
		String ip4="10.10";
		
		assertEquals(true,Validations.isIPV4(ip1));
		assertEquals(true,Validations.isIPV4(ip2));
		assertEquals(false,Validations.isIPV4(ip3));
		assertEquals(false,Validations.isIPV4(ip4));

	}
	
	@Test
	public void isDateTimeTest() {
		String dt1="20160704085736";
		String dt2="20150228231100";
		String dt3="201502282311";
		String dt4="201502282311009999";
		
		assertEquals(true,Validations.isDateTime(dt1));
		assertEquals(true,Validations.isDateTime(dt2));
		assertEquals(false,Validations.isDateTime(dt3));
		assertEquals(false,Validations.isDateTime(dt4));

	}
	
	@Test
	public void isSpecialCharacterTest() {
		String dt1="TodoPago";
		String dt2="No tiene caracteres especiales";
		String dt3="tiene & caracteres especiales";
		String dt4="p?";
		
		assertEquals(false,Validations.isSpecialCharacter(dt1));
		assertEquals(false,Validations.isSpecialCharacter(dt2));
 		assertEquals(true,Validations.isSpecialCharacter(dt3));
 		assertEquals(true,Validations.isSpecialCharacter(dt4));
	}
	
	@Test
	public void isIntegerArrayTest() {
		
		String elem1="elemento1";
		String elem2="2";
		String elem3="345";
		List<String> list1=new ArrayList<String>();
		List<String> list2=new ArrayList<String>();
		
		list1.add(elem2);
		list1.add(elem3);
		list2.add(elem1);
		list2.add(elem2);
		
		assertEquals(true,Validations.isNumericArray(list1));
 		assertEquals(false,Validations.isNumericArray(list2));
	}
	
	@Test
	public void isDecimalPatternTest() {
		
		String num1="23,11";
		String num2="24,09";
		String num3="82,0";
		String num4="94,0";
		
		assertEquals(true,Validations.isDecimalPattern(num1));
		assertEquals(true,Validations.isDecimalPattern(num2));
		assertEquals(false,Validations.isDecimalPattern(num3));
		assertEquals(false,Validations.isDecimalPattern(num4));
	}
}