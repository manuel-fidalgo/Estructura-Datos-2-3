package ule.edi.list;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ule.edi.EmptyCollectionException;

public class UnorderedArrayListTests {

	private UnorderedArrayList<Number> lN1;
	
	@Before
	public void setupLists() {
		this.lN1 = new UnorderedArrayList<Number>();
	}
	
	//	JUnit no executa los tests anotados con @Ignore
	
	@Test
	public void testDistinct() {
	
		lN1.addToRear(1);
		lN1.addToRear(2);
		lN1.addToRear(1);
		lN1.addToRear(2);
		lN1.addToRear(1);
		
		Assert.assertEquals(5L, lN1.size());
		
		//	distinct es un método estático de 'utilidad'
		UnorderedListADT<Number> dlN1 = UnorderedArrayList.distinct(lN1);
		
		Assert.assertEquals(2L, dlN1.size());
		Assert.assertTrue(dlN1.contains(1));
		Assert.assertTrue(dlN1.contains(2));
	}
	@Test
	public void testRangedIterator() {
	
		UnorderedListADT<Number> lN2;
		
		for (int i = 0; i < 32; i++) {
			lN1.addToRear(Integer.valueOf(i));
		}
		/**/
		//	listWith es un método estático
		lN2 = UnorderedArrayList.listWith(lN1.rangedIterator(1, 16, 4));
		Assert.assertEquals("[0, 4, 8, 12]", lN2.toString());		
		
	}
	
	@Test
	public void test01(){
		lN1.addToRear(1);
		lN1.addToRear(2);
		lN1.addToRear(1);
		lN1.addToRear(2);
		lN1.addToRear(1);
		Assert.assertEquals(lN1.toString(),lN1.toString());
	}
	@Test
	public void test02(){
		for (int i = 0; i < 230; i++) {
			lN1.addToFront(i);
			try {
				Assert.assertEquals(lN1.getFirst(),i);
			} catch (EmptyCollectionException e) {
				Assert.assertEquals(e.getMessage(),e.getMessage());
			}
		}
		for (int i = 0; i < 230; i++) {
			lN1.removeElementAt(1);
			try {
				Assert.assertEquals(lN1.getLast(),0);
			} catch (EmptyCollectionException e) {
				Assert.assertEquals(e.getMessage(),e.getMessage());
			}
		}
	}
	@Test
	public void test03(){
		for (int i = 0; i < 230; i++) {
			lN1.addToFront(i);
			try {
				Assert.assertEquals(lN1.getFirst(),i);
			} catch (EmptyCollectionException e) {
				Assert.assertEquals(e.getMessage(),e.getMessage());
			}
		}
		int size=230;
		for (int i = 0; i < 230; i++) {
			try {
				Number e = lN1.remove(i);
				size--;
				Assert.assertEquals(size,lN1.size());
				Assert.assertEquals(e,i);
			} catch (EmptyCollectionException e) {
				if(lN1.size()==0){
					Assert.assertEquals(e.getMessage(),e.getMessage());
				}else{
					Assert.assertTrue(false);
				}
			}
		}
	}
	@Test(expected = NoSuchElementException.class)
	public void test04() throws NoSuchElementException, EmptyCollectionException{
		lN1.addToRear(1);
		lN1.remove(110);
	}
	@Test(expected = EmptyCollectionException.class)
	public void test05() throws NoSuchElementException, EmptyCollectionException{
		lN1.remove(110);
	}
	@Test(expected = EmptyCollectionException.class)
	public void test06() throws EmptyCollectionException{
		lN1.getFirst();
	}
	@Test(expected = EmptyCollectionException.class)
	public void test07() throws EmptyCollectionException{
		lN1.getLast();
	}
	@Test
	public void test08() throws EmptyCollectionException{
		lN1.addToFront(0);
		lN1.addToRear(1);
		lN1.removeFirst();
		lN1.removeLast();
		Assert.assertEquals(lN1.size(),0);
	}
	@Test
	public void test09(){
		for (int i = 0; i < 10; i++) {
			lN1.addToRear(i);
		}
		for (int i = 0; i < 10; i++) {
			Assert.assertEquals(lN1.getIndex(i),i);
		}
		Assert.assertEquals(lN1.getIndex(20),lN1.INVALID_INDEX);
	}
	@Test (expected = EmptyCollectionException.class)
	public void test10() throws EmptyCollectionException{
		lN1.addToFront(0);
		lN1.addToRear(1);
		lN1.removeFirst();
		lN1.removeLast();
		Assert.assertEquals(lN1.size(),0);
		lN1.removeFirst();
		lN1.removeLast();
	}
	@Test
	public void test11(){
		Number[] ar = {1,2,3,4,3,2,3,4,5,6};
		Number[] arr = {6,5,4,3,2,3,4,3,2,1};
		Number[] noDupls = {1,2,3,4,5,6};
		
		UnorderedArrayList<Number> lista = new UnorderedArrayList<>(ar);
		Assert.assertEquals(new UnorderedArrayList<Number>(arr).toString(),UnorderedArrayList.reverse(lista).toString());
		Assert.assertEquals(new UnorderedArrayList<Number>(noDupls).toString(),UnorderedArrayList.distinct(lista).toString());
	}
	@Test
	public void test12(){
		for (int i = 0; i < 10; i++) {
			lN1.addToRear(i);
		}
		for (int i = 0; i < 10; i++) {
			lN1.replaceElementAt(i+1,100);
			Assert.assertEquals(lN1.getElementAt(i+1),100);
		}		
	}
	@Test
	public void test13(){
		for (int i = 0; i < 10; i++)
			lN1.addToRear(i);
		
		Iterator<Number> iterador = lN1.rangedIterator(3, 9, 3);
		int i=1;
		for(;iterador.hasNext();i++){
			Number next = iterador.next();
			switch(i){
			case 1:
				Assert.assertEquals(2, next);
				break;
			case 2:
				Assert.assertEquals(5, next);
				break;
			case 3:
				Assert.assertEquals(8, next);
				break;
			}
		}
	}
	@Test (expected = EmptyCollectionException.class)
	public void test14() throws EmptyCollectionException{
		lN1.moveUp(23);
		lN1.removeLast();
	}
	@Test (expected = IndexOutOfBoundsException.class)
	public void test15() throws EmptyCollectionException{
		lN1.indexCorrecto(0);
	}
	@Test 
	public void test16(){
		int i;
		for (i = 0; i < 10; i++) 
			lN1.addToRear(i);
		
		Iterator<Number> iter = lN1.iterator();
		for (i = 0; iter.hasNext(); i++) {
			Assert.assertEquals(i,iter.next());
		}
		Iterator<Number> skipping_iter = lN1.skippingIterator();
		Assert.assertEquals(0, skipping_iter.next());
		Assert.assertEquals(2, skipping_iter.next());
		Assert.assertEquals(4, skipping_iter.next());
		Assert.assertEquals(6, skipping_iter.next());
		Assert.assertEquals(8, skipping_iter.next());
		
	}
	@Test
	public void test17(){
		lN1.addToRear(1);
		lN1.addToRear(2);
		lN1.addToRear(3);
		lN1.addToRear(4);
		Assert.assertEquals("[1, 2, 3, 4]",lN1.toString());
	}
	@Test
	public void test18() throws EmptyCollectionException{
		lN1.addToRear(1);
		lN1.addToRear(2);
		lN1.addToRear(3);
		lN1.addToRear(4);
		for (int i = 0; i < 4; i++) {
			lN1.removeFirst();
		}
		Assert.assertEquals(lN1.size(),0);
	}
	@Test (expected = EmptyCollectionException.class)
	public void test19() throws EmptyCollectionException{
		lN1.removeFirst();
	}
	@Test
	public void test20(){
		for(int i=0; i<100; i++){
			lN1.addToRear(i+1);
		}
		Iterator<Number> iter = lN1.iterator();
		for(int j=0;j<100;j++){
			if(iter.hasNext()){
				Assert.assertTrue(true);
			}else{
				Assert.assertTrue(false);
			}
			Assert.assertEquals(j+1,iter.next());
		}
	}
	@Test(expected = UnsupportedOperationException.class)
	public void test21(){
		Iterator<Number> iter = lN1.iterator();
		iter.remove();
	}
	@Test (expected = UnsupportedOperationException.class)
	public void test22(){
		Iterator<Number> iter = lN1.rangedIterator(0, 1, 2);
		iter.remove();
	}
	@Test (expected = UnsupportedOperationException.class)
	public void test23(){
		Iterator<Number> iter = lN1.skippingIterator();
		iter.remove();
	}
}
