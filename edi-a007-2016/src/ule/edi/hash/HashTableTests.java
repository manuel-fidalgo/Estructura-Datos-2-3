package ule.edi.hash;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import ule.edi.hash.HashTableImpl.HashFunction;

public class HashTableTests {

	private HashTableImpl<String, String> TS;
	
	//	Mala función de hash, pero útil para los tests
	private static final HashFunction<String> hLength =  new HashFunction<String>() {

		@Override
		public int apply(int n, String g) {

			return (g.length() % n);
		}
	};

	@Before
	public void setupTables() {
		
		TS = new HashTableImpl<String, String>(hLength, 3, 3);
	}
	
	private String key(int n) {
		
		return String.format("K%03d", n);
	}
	
	private String value(int n) {
		
		return String.format("V%03d", n);
	}
	
	private void put(HashTableImpl<String, String> t, int n) {
		
		t.put(key(n), value(n));
	}
	
	@Test
	public void testPrinting() {
		
		TS.put("A","0");
		TS.put("A", "1");
		TS.put("B", "2");
		TS.put("D", "3");
		TS.put("G", "4");
		TS.put("J", "5");
		System.out.println(TS);
	}
	@Test
	public void test00(){
		TS = new HashTableImpl<String,String>();
		//TS.contains("Hola");
		//TS.get("Hola");
	}

}
















