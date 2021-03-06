package ule.edi.tree;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import ule.edi.EmptyCollectionException;

/**
 * Ã�rbol binario de bÃºsqueda (binary search tree, BST).
 * 
 * @author profesor
 * 
 * @param <T>
 *            tipo de la informaciÃ³n en cada nodo, comparable.
 */
public class BinarySearchTreeADTImpl<T extends Comparable<? super T>> extends
AbstractBinaryTreeADT<T> {

	/**
	 * Devuelve el Ã¡rbol binario de bÃºsqueda izquierdo.
	 * 
	 * @return
	 */
	protected BinarySearchTreeADTImpl<T> getLeftBST() {
		return (BinarySearchTreeADTImpl<T>) leftSubtree;
	}

	private void setLeftBST(BinarySearchTreeADTImpl<T> left) {
		this.leftSubtree = left;
	}

	/**
	 * Devuelve el Ã¡rbol binario de bÃºsqueda derecho.
	 * 
	 * @return
	 */
	protected BinarySearchTreeADTImpl<T> getRightBST() {
		return (BinarySearchTreeADTImpl<T>) rightSubtree;
	}

	private void setRightBST(BinarySearchTreeADTImpl<T> right) {
		this.rightSubtree = right;
	}

	/**
	 * Ã�rbol BST vacÃ­o
	 */
	public BinarySearchTreeADTImpl() {

		setContent(null);

		setLeftBST(null);
		setRightBST(null);
	}

	private BinarySearchTreeADTImpl<T> emptyBST() {
		return new BinarySearchTreeADTImpl<T>();
	}

	/**
	 * Inserta todos los elementos de una colecciÃ³n en el Ã¡rbol.
	 * 
	 * @param elements
	 *            valores a insertar.
	 */
	public void insert(Collection<T> elements) {

		//	O todos o ninguno; si alguno es 'null', ni siquiera se comienza a insertar
		for (T i : elements) {
			if (i == null) {
				throw new IllegalArgumentException("No se aceptan elementos nulos");
			}
		}

		for (T j : elements) {
			insert(j);
		}
	}

	/**
	 * Inserta todos los elementos de un array en el Ã¡rbol.
	 * 
	 * @param elements elementos a insertar.
	 */
	@SuppressWarnings("unchecked")
	public void insert(T ... elements) {

		//	O todos o ninguno; si alguno es 'null', ni siquiera se comienza a insertar
		for (T i : elements) {
			if (i == null) {
				throw new IllegalArgumentException("No se aceptan elementos nulos");
			}
		}

		for (T j : elements) {
			insert(j);
		}
	}

	/**
	 * Inserta (como hoja) un nuevo elemento en el Ã¡rbol de bÃºsqueda. Si el
	 * elemento ya existe en el Ã¡rbol NO lo inserta.
	 * 
	 * @param element
	 *            valor a insertar.
	 */
	public void insert(T element) {

		if (element == null) {
			throw new IllegalArgumentException("No se aceptan elementos nulos");
		}

		if (isEmpty()) {

			// Ya no es vacÃ­o, ahora es hoja
			setContent(element);

			//	Sus hijos son vacÃ­os
			setLeftBST(emptyBST());
			setRightBST(emptyBST());
		} else {

			// Compara con el valor de la raÃ­z
			int diff = element.compareTo(content);

			if (diff == 0) {
				// Ya estÃ¡ en el Ã¡rbol
				return;
			}

			if (diff < 0) {
				// Es menor, irÃ¡ en el sub-Ã¡rbol izquierdo
				getLeftBST().insert(element);
			}

			if (diff > 0) {
				// Es mayor, irÃ¡ al sub-Ã¡rbol derecho
				getRightBST().insert(element);
			}
		}
	}

	/**
	 * Elimina los elementos de la colecciÃ³n del Ã¡rbol.
	 * 
	 * @param elements
	 *            elementos a eliminar.
	 */
	public void withdraw(Collection<T> elements) {
		for (T e : elements) {
			withdraw(e);
		}
	}

	/**
	 * Elimina los valores en un array del Ã¡rbol.
	 * 
	 * @param elements valores a eliminar.
	 */
	@SuppressWarnings("unchecked")
	public void withdraw(T ... elements) {
		for (T e : elements) {
			withdraw(e);
		}
	}

	/**
	 * Devuelve el mÃ¡ximo en el Ã¡rbol.
	 * 
	 * @return mÃ¡ximo valor en el Ã¡rbol.
	 * @throws EmptyCollectionException 
	 */
	public T findMax() throws EmptyCollectionException {
		//	Inspeccionar con recursividad, bajando por la derecha
		if (!isEmpty()) {
			if (getRightBST().isEmpty()) {
				return getContent();
			} else {
				return getRightBST().findMax();
			}
		} else {
			throw new EmptyCollectionException("No se puede encontrar el mÃ¡ximo de un Ã¡rbol vacÃ­o");
		}
	}

	/**
	 * Elimina un elemento del Ã¡rbol.
	 * 
	 * @param element
	 *            elemento a eliminar.
	 * @throws NoSuchElementException si el elemento a eliminar no estÃ¡ en el Ã¡rbol           
	 */
	public void withdraw(T element) {

		if (element == null) {
			// Eliminado (no estaba)
			return;
		}

		if (!isEmpty()) {
			// Compara con la informaciÃ³n en la raÃ­z
			int diff = element.compareTo(getContent());

			if (diff == 0) {

				// Eliminar este nodo

				// Si es una hoja
				if (getLeftBST().isEmpty() && getRightBST().isEmpty()) {
					setContent(null);
					setLeftBST(null);
					setRightBST(null);
				}
				// Si tiene sub-Ã¡rbol izquierdo pero no derecho
				else if (!getLeftBST().isEmpty() && getRightBST().isEmpty()) {
					setContent(getLeftBST().getContent());

					setRightBST(getLeftBST().getRightBST());
					setLeftBST(getLeftBST().getLeftBST());
				}
				// Si tiene sub-Ã¡rbol derecho pero no izquierdo
				else if (getLeftBST().isEmpty() && !getRightBST().isEmpty()) {
					setContent(getRightBST().getContent());
					setLeftBST(getRightBST().getLeftBST());
					setRightBST(getRightBST().getRightBST());

				}
				// // Si tiene sub-Ã¡rbol izquierdo y derecho
				else if (!getLeftBST().isEmpty() && !getRightBST().isEmpty()) {

					try {
						T searchMax = getLeftBST().findMax(); // busca el mayor
						// de los
						// menores
						// sustituye el elemento que quiere eliminar por el
						// mayor de los menores
						content = searchMax;
						// elimina el mayor de los menores que no tendrÃ¡ hijo
						// derecho y por tanto termina sin recursividad
						getLeftBST().withdraw(searchMax);
					} catch (EmptyCollectionException e) {
						// No deberÃ­a ser posible
						throw new IllegalStateException(e);
					}
				}

			}
			// Si no lo encuentra sigue buscando por la rama adecuada
			else {
				if (diff > 0)
					getRightBST().withdraw(element);

				else
					getLeftBST().withdraw(element);
			}
		}
		// si el elemento no estÃ¡ en el Ã¡rbol
		else
			throw new NoSuchElementException();
	}					

	/**
	 * Devuelve el nÃºmero de Ã¡rboles vacÃ­os en este Ã¡rbol.
	 * 
	 * Por ejemplo, sea un Ã¡rbol "A" {10, {5, âˆ…, âˆ…}, {20, âˆ…, {30, âˆ…, âˆ…}}}:
	 * 
	 * 10
	 * |  5
	 * |  |  âˆ…
	 * |  |  âˆ…
	 * |  20
	 * |  |  âˆ…
	 * |  |  30
	 * |  |  |  âˆ…
	 * |  |  |  âˆ…
	 * 
	 * el resultado serÃ­a 5.
	 * 
	 * @return nÃºmero de Ã¡rboles vacÃ­os.
	 */
	
	public long countEmpty() {
		AbstractBinaryTreeADT<T> tree = (AbstractBinaryTreeADT<T>)this;
		int[] acum = new int[1]; acum[0] = 0;
		
		countEmptyRec(acum, tree);
		
		return acum[0];
	}

	public void countEmptyRec(int[] acum, AbstractBinaryTreeADT<T> tree){
		if(tree.isEmpty()){
			acum[0]++;
		}else{
		//	if(tree.leftSubtree!=null)
				countEmptyRec(acum, tree.leftSubtree);
		//	if(tree.rightSubtree!=null)
				countEmptyRec(acum, tree.rightSubtree);
		}
	}

	/**
	 * Indica el nÃºmero de elementos en niveles impares.
	 * 
	 * La raÃ­z de Ã©ste Ã¡rbol estÃ¡ en nivel 1.
	 * 
	 * Por ejemplo, sea un Ã¡rbol "A" {10, {5, âˆ…, âˆ…}, {20, âˆ…, {30, âˆ…, âˆ…}}}:
	 * 
	 * 10
	 * |  5
	 * |  |  âˆ…
	 * |  |  âˆ…
	 * |  20
	 * |  |  âˆ…
	 * |  |  30
	 * |  |  |  âˆ…
	 * |  |  |  âˆ…
	 * 
	 * el resultado serÃ­a 2.
	 * 
	 * @return nÃºmero de elementos en niveles impares.
	 */

	public long countOddLevelElements() {

		return countOddLevelsRec(1);
	}

	private long countOddLevelsRec(int currentlevel) {

		if(this.content==null){
			return 0;
		}
		if(currentlevel%2!=0){
			currentlevel++;
			return 1 + this.getLeftBST().countOddLevelsRec(currentlevel)+this.getRightBST().countOddLevelsRec(currentlevel);
		}else{
			currentlevel++;
			return this.getLeftBST().countOddLevelsRec(currentlevel)+this.getRightBST().countOddLevelsRec(currentlevel);
		}

	}

	/**
	 * Acumula en pre-orden, una lista con los pares 'padre-hijo' en este Ã¡rbol.
	 * 
	 * Por ejemplo, sea un Ã¡rbol "A":
	 * 
	 * {10, {5, {2, âˆ…, âˆ…}, âˆ…}, {20, âˆ…, {30, âˆ…, âˆ…}}}
	 * 
	 * 10
	 * |  5
	 * |  |  2
	 * |  |  |  âˆ…
	 * |  |  |  âˆ…
	 * |  |  âˆ…
	 * |  20
	 * |  |  âˆ…
	 * |  |  30
	 * |  |  |  âˆ…
	 * |  |  |  âˆ…
	 * 
	 * el resultado serÃ­a una lista de cadenas:
	 * 
	 * 	[(10,5), (5,2), (10,20), (20,30)]
	 * 
	 * @param buffer lista con el resultado.
	 */

	public void parentChildPairs(List<String> buffer) {
		
		if(this.content==null){
			return;
		}
		if(this.getLeftBST().content != null){
			buffer.add(createString(this, this.getLeftBST()));
			this.getLeftBST().parentChildPairs(buffer);//LLamada por la izquierda
		}

		if(this.getRightBST().content!=null){
			buffer.add(createString(this, this.getRightBST()));
			this.getRightBST().parentChildPairs(buffer); //Llamada por la derecha
		}
	}
	public String createString(BinarySearchTreeADTImpl<T> a, BinarySearchTreeADTImpl<T> b){

		return "("+a.content.toString()+","+b.content.toString()+")";
	}

	/**
	 * Devuelve el contenido del nodo alcanzado desde la raÃ­z
	 * de Ã©ste Ã¡rbol, con el camino dado.
	 * 
	 * Si se codifica "bajar por la izquierda" como "0" y
	 * "bajar por la derecha" como "1", el camino desde un 
	 * nodo N hasta un nodo M (en uno de sus sub-Ã¡rboles) serÃ¡ la
	 * cadena de 0s y 1s que indica cÃ³mo llegar desde N hasta M.
	 *
	 * Se define tambiÃ©n el camino vacÃ­o desde un nodo N hasta
	 * Ã©l mismo, como cadena vacÃ­a.

	 * Por ejemplo, sea un Ã¡rbol "A" {10, {5, âˆ…, âˆ…}, {20, âˆ…, {30, âˆ…, âˆ…}}}:
	 * 
	 * 10
	 * |  5
	 * |  |  âˆ…
	 * |  |  âˆ…
	 * |  20
	 * |  |  âˆ…
	 * |  |  30
	 * |  |  |  âˆ…
	 * |  |  |  âˆ…
	 * 
	 * Entonces se tiene que A.getContentWithPath("11") es 30 y
	 * que A.getContentWithPath("") es 10.
	 * 
	 * @param path camino a seguir desde la raÃ­z.
	 * @return contenido del nodo alcanzado.
	 * @throws NoSuchElementException si el camino no alcanza un nodo en el Ã¡rbol
	 * @throws IllegalArgumentException si el camino no contiene sÃ³lamente 0s y 1s
	 */

	public T getContentWithPath(String path) {
		return getContentRec(new StringBuffer(path));
	}

	private T getContentRec(StringBuffer sb) throws NoSuchElementException, IllegalArgumentException {

		char us;

		if(sb.length()==0){
			if(this.content==null) throw new NoSuchElementException();
			return this.content;
		}else{
			us = sb.charAt(0);
			sb.deleteCharAt(0);
				if(us == '0'){
					if(this.getLeftBST()==null) 
						throw new NoSuchElementException();
					else
						return this.getLeftBST().getContentRec(sb);
				}else if(us == '1'){
					if(this.getRightBST()==null) 
						throw new NoSuchElementException();
					else
						return this.getRightBST().getContentRec(sb);
				}else{
					throw new IllegalArgumentException();
				}
		}
	}	
}
