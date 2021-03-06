package ule.edi.tree;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

import ule.edi.EmptyCollectionException;

public class BinarySearchTreeADTImplTests {

	/*
	 * ∅
	 */
	private BinarySearchTreeADTImpl<Integer> TE = null;
	/*por la izquierda, por la derecha*/
	/*
	 * 1
	 * |  ∅
	 * |  2
	 * |  |  ∅
	 * |  |  3
	 * |  |  |  ∅
	 * |  |  |  4
	 * |  |  |  |  ∅
	 * |  |  |  |  ∅
	 */	
	private BinarySearchTreeADTImpl<Integer> T1234 = null;

	/*
	 * 4
	 * |  3
	 * |  |  2
	 * |  |  |  1
	 * |  |  |  |  ∅
	 * |  |  |  |  ∅
	 * |  |  |  ∅
	 * |  |  ∅
	 * |  ∅
	 */	
	private BinarySearchTreeADTImpl<Integer> T4321 = null;

	/*
	 * 5
	 * |  2
	 * |  |  1
	 * |  |  |  ∅
	 * |  |  |  ∅
	 * |  |  3
	 * |  |  |  ∅
	 * |  |  |  ∅
	 * |  8
	 * |  |  7
	 * |  |  |  ∅
	 * |  |  |  ∅
	 * |  |  9
	 * |  |  |  ∅
	 * |  |  |  ∅
	 */	
	private BinarySearchTreeADTImpl<Integer> TC3 = null;

	/*
	 * 10
	 * |  5
	 * |  |  ∅
	 * |  |  ∅
	 * |  20
	 * |  |  ∅
	 * |  |  30
	 * |  |  |  ∅
	 * |  |  |  ∅
	 */
	private BinarySearchTreeADTImpl<Integer> TEx = null;

	private LinkedList<String> lS = null;

	@Before
	public void setupBSTs() {

		TE = new BinarySearchTreeADTImpl<Integer>();

		T1234 = new BinarySearchTreeADTImpl<Integer>();
		T1234.insert(1,2,3,4);

		T4321 = new BinarySearchTreeADTImpl<Integer>();
		T4321.insert(4, 3, 2, 1);

		TC3 = new BinarySearchTreeADTImpl<Integer>();
		TC3.insert(5, 2, 8, 1, 3, 7, 9);

		TEx = new BinarySearchTreeADTImpl<Integer>();
		TEx.insert(10, 20, 30, 5);

		lS = new LinkedList<String>();
	}

	@Test
	public void test00(){
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 3; i++) {
			sb.append("1");
			assertEquals(T1234.getContentWithPath(sb.toString()),new Integer(i+2));
		}
	}
	@Test (expected = IllegalArgumentException.class)
	public void test01(){
		T1234.getContentWithPath("Hola wapo");
	}
	@Test (expected = NoSuchElementException.class)
	public void test02(){
		T1234.getContentWithPath("0000000000");
	}
	@Test
	public void test03(){
		assertEquals(this.T1234.countOddLevelElements(),2);
		assertEquals(this.T4321.countOddLevelElements(),2);
		assertEquals(this.TC3.countOddLevelElements(),5);
	}
	@Test
	public void test04(){
		//LS es el buffer que ha declarado ella como atributo de clase
		T1234.parentChildPairs(lS);
		assertEquals("[(1,2), (2,3), (3,4)]",lS.toString());
		lS = new LinkedList<String>();
		T4321.parentChildPairs(lS);
		assertEquals("[(4,3), (3,2), (2,1)]",lS.toString());
	}

	@Test (expected = NoSuchElementException.class)
	public void test05(){
		T1234.getContentWithPath("0");
	}

	@Test
	public void test06(){
		assertEquals(TE.render(),TE.render());
		assertEquals(TE.countEmpty(),1);
		TE.insert(1,2,3,4,5,6,7,8,9,7,6,5);
		assertEquals(TE.render(),TE.render());
		assertEquals(TE.countEmpty(),10);
	}
	@Test
	public void test07(){
		ArrayList<Integer> lis = new ArrayList<Integer>();
		for (int i = 0; i < 30; i++) {
			lis.add(i);
		}
		try{

			TE.insert(lis);
		}catch(Exception e){

		}
		try{
			lis.toArray()[8]=null;
		}catch(Exception e){

		}
		try{
			TE.insert(lis);
		}catch(Exception e){

		}
		try{
			TE.insert(null,0,1);
		}catch(Exception e){

		}
		try{
			Integer g=null;
			TE.insert(g);
		}catch(Exception e){

		}
		ArrayList<Integer> fucking_lista = new ArrayList<Integer>();
		for (int i = 0; i < 30; i++) {
			fucking_lista.add(i);
		}
		TE.insert(fucking_lista);
		TE.withdraw(fucking_lista);
		try{
			TE.withdraw(29845);
		}catch(NoSuchElementException e){

		}
		try {
			TE.findMax();
		} catch (EmptyCollectionException e) {
		}
		TE = new BinarySearchTreeADTImpl<>();
		try {
			TE.findMax();
		} catch (EmptyCollectionException e) {
		}

	}
	@Test
	public void testcountEmpty(){
		assertEquals(5, TEx.countEmpty());
		System.out.println("=");
		assertEquals(8, TC3.countEmpty());
		System.out.println("=");
		assertEquals(5, T4321.countEmpty());
		System.out.println("=");
		assertEquals(5, T1234.countEmpty());
	}
	@Test 
	public void testOddLevelElements(){
		assertEquals(5, TC3.countOddLevelElements());
		assertEquals(2, T1234.countOddLevelElements());
		assertEquals(2, T4321.countOddLevelElements());
		assertEquals(2, TEx.countOddLevelElements());
		assertEquals(0,TE.countOddLevelElements());
	}

	@Test
	public void testParentChildPair(){
		List<String> buffer = new ArrayList<String>();
		TEx.insert(2);
		TEx.parentChildPairs(buffer);

		assertEquals("[(10,5), (5,2), (10,20), (20,30)]",buffer.toString());
		/*buffer=new ArrayList<String>();
		TC3.parentChildPairs(buffer);
		assertEquals("[(5,2), (2,1), ()]");*/
	}

	@Test
	public void testGetContentWithPath(){
		Integer numero=3;
		assertEquals(numero, TC3.getContentWithPath("01"));

		numero=4;
		assertEquals(numero, T4321.getContentWithPath(""));

		numero=3;
		assertEquals(numero, T4321.getContentWithPath("0"));

		numero=1;
		assertEquals(numero, T4321.getContentWithPath("000"));

		numero=7;
		assertEquals(numero, TC3.getContentWithPath("10"));

	}

	@Test (expected = IllegalArgumentException.class)
	public void testgetContentWithPathException_1() throws IllegalArgumentException{
		TEx.getContentWithPath("A53");
	}
	@Test
	public void testFinal(){
		TE.insert(1,2,3,4,5,6,7,8,6);
		for (int i = 0; i < 7; i++) {
			try {
				TE.withdraw(TE.findMax());
			} catch (EmptyCollectionException e) {
				e.printStackTrace();
			}
		}
		try{
		TE.withdraw(1,2,3,4,5,6,7,6);
		Integer g = null;
			TE.withdraw(g);
		}catch(Exception e){

		}
		TE = new BinarySearchTreeADTImpl<>();
		List<String> b = new LinkedList<String>();
		TE.parentChildPairs(b);
		System.out.println(b);

	}


}
