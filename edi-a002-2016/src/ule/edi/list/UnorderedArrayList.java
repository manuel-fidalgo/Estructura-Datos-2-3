package ule.edi.list;

import java.util.Iterator;
import java.util.NoSuchElementException;

import ule.edi.EmptyCollectionException;

public class UnorderedArrayList<T> implements UnorderedListADT<T> {

	private T[] storage;

	private int nElements;

	private final static String CLASS_NAME = "UnorderedArrayList<T>";

	protected int INVALID_INDEX = -1;

	int INITIAL_CAPACITY = 5;


	@SuppressWarnings("unchecked")
	public UnorderedArrayList() {	
		this.storage = (T[]) new Object [INITIAL_CAPACITY];
		this.nElements = 0;		
	}

	@SuppressWarnings("unchecked")
	public UnorderedArrayList(T ... v) {
		this.storage = (T[]) new Object [INITIAL_CAPACITY];
		this.nElements = 0;		
		for (T x : v) {
			addToRear(x);
		}
	}


	@Override
	public void addToFront(T element) {
		/*Desplazamos todos los elementos hacia abajo desde la ultima posicion para asi dejar la primera libre*/
		if(this.isEmpty()){
			this.storage[0] = element;
		}else{
			if(nElements >= this.storage.length) expandCapacity();
			for (int i = this.nElements-1; i >= 0; i--) {
				this.storage[i+1]=this.storage[i];
			}
			this.storage[0]=element;
		}
		this.nElements++;

	}
	private void expandCapacity() {
		@SuppressWarnings("unchecked")
		T[] aux = (T[]) new Object [this.storage.length*2];
		for (int i = 0; i < this.storage.length; i++) {
			aux[i] = this.storage[i];
		}
		this.storage = aux;
	}

	@Override
	public void addToRear(T element) {
		if(nElements >= this.storage.length)expandCapacity();
		this.storage[nElements] = element;
		nElements++;
	}


	@Override
	public T removeFirst() throws EmptyCollectionException {
		T aux;
		if(nElements==0) throw new EmptyCollectionException(CLASS_NAME);
		else{
			aux = this.storage[0];
			this.storage[0]=null;
			moveUp(0); //Desde el principio
			nElements--;
			return aux;
		}
	}
	/**
	 * @param a indice que se ha quedado en null y que se tiene que rellenar
	 */
	public void moveUp(int a) {
		for (int i = a; i < nElements; i++) {
			try{
				if(this.storage[i+1]!=null) this.storage[i] = this.storage[i+1];
			}catch(IndexOutOfBoundsException ex){
				//No hacer nada, simplemente para que no pete;
			}
		}
	}
	@Override
	public T removeLast() throws EmptyCollectionException {
		T aux;
		if(nElements==0) throw new EmptyCollectionException(CLASS_NAME);
		else{
			aux = this.storage[nElements-1];
			this.storage[nElements-1] = null;
			nElements--;
			return aux;
		}
	}
	@Override
	public T remove(T element) throws EmptyCollectionException,NoSuchElementException {

		T aux;
		int index;
		listaVacia();
		if(!this.contains(element)) throw new NoSuchElementException();
		index = getIndex(element);
		aux = this.storage[index];
		this.storage[index]=null;
		moveUp(index);
		nElements--;
		return aux;
	}
	/**
	 * @param element
	 * @return the index of the element,
	 * if the element is not in the list return -1
	 */
	public int getIndex(T element) {
		for (int i = 0; i < nElements; i++) {
			if(this.storage[i].equals(element)) return i;
		}
		return INVALID_INDEX;
	}

	@Override
	public T getFirst() throws EmptyCollectionException {
		listaVacia();
		return this.storage[0];
	}

	@Override
	public T getLast() throws EmptyCollectionException {
		listaVacia();
		return this.storage[nElements-1];
	}
	/**
	 * Lanza una escepcion si la lista esta vacia
	 * @throws EmptyCollectionException
	 */
	private void listaVacia() throws EmptyCollectionException {
		if(nElements==0) throw new EmptyCollectionException(CLASS_NAME);
	}

	@Override
	public boolean contains(T target) {
		for (int i = 0; i < nElements; i++){
			if(this.storage[i].equals(target))  return true;
		}
		return false;
	}

	@Override
	public boolean isEmpty() {
		return nElements==0;
	}

	@Override
	public int size() {
		return this.nElements;
	}

	/*LOS INDICES DE 1...N (Numero elementos)*/
	@Override
	public T getElementAt(int i) throws IndexOutOfBoundsException {
		indexCorrecto(i);
		return this.storage[i-1];
	}

	@Override
	public T replaceElementAt(int i, T value) throws IndexOutOfBoundsException {
		indexCorrecto(i);
		T aux = this.storage[i-1];
		this.storage[i-1] = value;
		return aux;
	}

	@Override
	public T removeElementAt(int i) throws IndexOutOfBoundsException {
		indexCorrecto(i);
		T aux;
		aux = this.storage[i-1];
		this.storage[i-1]=null;
		moveUp(i-1);
		nElements--;
		return aux;
	}

	public void indexCorrecto(int i) throws IndexOutOfBoundsException {
		if(i > nElements || i<1) throw new IndexOutOfBoundsException(Integer.toString(i));
	}


	@Override
	public Iterator<T> iterator() {

		return new DefaultIteratorImpl();
	}

	/**
	 * Clase para el iterador.
	 * 
	 * Como clase interior (anidada no estática) tiene acceso a los atributos
	 * de la clase que la contiene. También al parámetro 'T' del tipo genérico.
	 * 
	 * Si fuera anidada y estática, no tendría acceso.
	 * 
	 * @author profesor
	 */
	private class DefaultIteratorImpl implements Iterator<T> {
		int nextPosition;
		@Override
		public boolean hasNext() {
			try{
				return storage[nextPosition]!=null;
			}catch(IndexOutOfBoundsException e){
				return false;
			}
		}

		@Override
		public T next() {
			if(!hasNext())throw new java.util.NoSuchElementException();
			return  storage[nextPosition++];
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		private DefaultIteratorImpl(){
			this.nextPosition=0;
		}
	};

	private class SkippingIteratorImpl implements Iterator<T> {
		private int nextPosition;

		@Override
		public boolean hasNext() {
			try{
				return storage[nextPosition]!=null;
			}catch(IndexOutOfBoundsException e){
				return false;
			}
		}

		@Override
		public T next() {
			if(!hasNext())throw new java.util.NoSuchElementException();
			T aux =  storage[nextPosition];
			nextPosition = nextPosition+2;
			return aux;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		private SkippingIteratorImpl(){
			this.nextPosition=0;
		}
	};

	@Override
	public Iterator<T> skippingIterator() {
		return new SkippingIteratorImpl();
	}

	private class RangedIteratorImpl implements Iterator<T> {
		int from,to,step,current;

		private RangedIteratorImpl(int from, int to, int step) {
			this.to = to-1;
			this.step = step;
			this.from = from-1;
			this.current = this.from;
		}
		@Override
		public boolean hasNext() {
			try{
				return storage[current] != null && current <= to;
			}catch(IndexOutOfBoundsException e){
				return false;
			}
		}

		@Override
		public T next() {
			if(!hasNext())throw new java.util.NoSuchElementException();
			T aux = storage[current];
			current  = current+step;
			return aux;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}		
	};

	@Override
	public Iterator<T> rangedIterator(int from, int to, int step) {
		return new RangedIteratorImpl(from,to,step);
	}

	/**
	 * Construye y devuelve una lista con los elementos dados por un iterador.
	 * 
	 * Por ejemplo, si el iterador devuelve sucesivamente los caracteres
	 * 'a', 'z', 'b', 'y' al invocar a next(), la lista resultado sería
	 * 
	 * 	['a', 'z', 'b', 'y']
	 * 
	 * @param contents iterador de los elementos a añadir en la nueva lista.
	 * @return una lista con los elementos que va entregando el iterador.
	 */
	public static <E> UnorderedListADT<E> listWith(Iterator<E> contents) {
		UnorderedArrayList<E> list = new UnorderedArrayList<>();
		for (Iterator<E> iterator = contents ; iterator.hasNext();) 
			list.addToRear(iterator.next());
		return list;
	}

	/**
	 * Elimina duplicados y devuelve el resultado como otra lista.
	 * 
	 * Si T1 es vacía, devuelve una lista vacía.
	 * 
	 * Únicamente se dispone de las operaciones del TAD y del iterador por defecto.
	 * 
	 * Por ejemplo, con una lista x de números [1, 2, 3, 1, 2, 3, 4, 5, 3] se tendría:
	 * 
	 * 	UnorderedArrayList.distinct(x) es [1, 2, 3, 4, 5]
	 * 
	 * @param T1 una lista de elementos
	 * @return una lista con los elementos de T1 sin duplicados
	 */
	public static <E> UnorderedListADT<E> distinct(UnorderedListADT<E> T1) {
		UnorderedListADT<E> aux = new UnorderedArrayList<E>();
		E aux_element;
		boolean is;
		for (int i = 0; i < T1.size(); i++) {
			aux_element = T1.getElementAt(i+1);
			is = false;
			for (int j = 0; j < aux.size(); j++) {
				if(aux_element.equals(aux.getElementAt(j+1))) is=true;
			}
			if(!is) aux.addToRear(aux_element);
		}
		
		return aux;
	}

	/**
	 * Devuelve una lista en orden inverso.
	 * 
	 * Únicamente se dispone de las operaciones del TAD y del iterador por defecto.
	 * 
	 * Si T1 es vacía, devuelve una lista vacía.
	 * 
	 * Por ejemplo, sea x una lista de caracteres ['A', 'B', 'C', 'C', 'D'], se tiene:
	 * 
	 *  UnorderedArrayList.reverse(x) es ['D', 'C', 'C', 'B', 'A']
	 *  
	 * @param T1 una lista de elementos. 
	 * @return otra lista con los mismos elementos en orden inverso.
	 */
	public static <E> UnorderedListADT<E> reverse(UnorderedListADT<E> T1) {
		UnorderedListADT<E> list = new UnorderedArrayList<>();
		int i;
		for(i=0; i<T1.size();i++)
			list.addToFront(T1.getElementAt(i+1));
		return list;
	}


	@Override
	public String toString() {

		//	Construye y devuelve con el formato adecuado
		StringBuffer rx = new StringBuffer();

		rx.append("[");

		for (int i = 0; i < nElements; i++) {
			rx.append(storage[i]);
			rx.append(", ");
		}
		//	Elimina ", " de más
		if (! isEmpty()) {
			rx.delete(rx.length() - 2, rx.length());
		}

		rx.append("]");

		return rx.toString();
	}

}
