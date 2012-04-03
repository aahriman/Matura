package utilities.struct;

import java.util.Comparator;



public class Heap<E> {
	private static final int DEFAULT_SIZE = 10;
	
	@SuppressWarnings("unchecked")
	private final static Comparator DEFAULT_COMPARATOR = new Comparator(){
		@Override
		public boolean equals(Object obj){
			return this.equals(obj);
		}
		
		@Override
		public int compare(Object o1,Object o2){				
			return o1.hashCode() - o2.hashCode();
		}
	};
	
	private E [] elements;
	private int actualSize = 0;
	private final Comparator<E> ACTUAL_COMPARATOR;
	
	public Heap(){
		this(DEFAULT_SIZE);
	}
	

	@SuppressWarnings("unchecked")
	public Heap(int size){
		this(size,DEFAULT_COMPARATOR);
	}
	
	@SuppressWarnings("unchecked")
	public Heap(int size,Comparator<E> comparator){
		E[] array = (E[])new Object[size+1];
		elements = array;
		ACTUAL_COMPARATOR = comparator;
	}
	
	//@SuppressWarnings("unchecked")
	@SuppressWarnings("unchecked")
	public void add(E element){
		actualSize++;
		if(elements.length <= actualSize){
			E[] pom = (E[])new Object[actualSize*2];
			for(int i = 0; i < elements.length; i++){
				pom[i] = elements[i];
			}
			
			elements = pom;
		}
		elements[actualSize] = element;
		up();
	}
	
	public E remove(){
		E top = elements[0];
		E botton = elements[actualSize --];
		elements[0]=botton;
		down();

		
		return top;
	}
	public E top(){
		return elements[0];
	}
	
	
	private void down(){
		int i = 0;
		int j = 2;
		
		E comparitingElement = elements[i];
		
		while(i < actualSize){
			E pom = null;
			int k = j;
			if(0<ACTUAL_COMPARATOR.compare(elements[j], elements[j+1])){
				pom = elements[k];
				k--;
			}
			
			if(0<ACTUAL_COMPARATOR.compare(comparitingElement, pom)){
				elements[i] = pom;
				i = k;
				j *= 2;
			}else{
				elements[i] = comparitingElement;
				i = actualSize; // END
			}
		}
	}
	
	private void up(){
		int i = actualSize;
		
		E comparitingElement = elements[i];
		
		while(i > 1){
			if(0>ACTUAL_COMPARATOR.compare(comparitingElement, elements[i/2])){
				elements[i] = elements[i/2];
				i/=2;
			}else{
				elements[i] = comparitingElement;
				i = -1; // END
			}
		}
		
		if(i == 1){
			elements[1] = comparitingElement;
		}
	}
	
	@Override
	public String toString(){
		String s = new String("[");
		for(int i = 1; i <= actualSize ; i++){
			s+= elements[i].toString();
			i++;
			for(; i <= actualSize; i++){
				s+= ", ";
				s+= elements[i].toString();
			}
			s+= "]";
		}
		
		
		return s;
	}
		
	public static void main(String[] args) {
		Heap<Integer> h = new Heap<Integer>(2);
		
		h.add(10);
		h.add(20);
		h.add(14);
		h.add(1);
		h.add(2);
		h.add(3);
		h.add(4);
		h.add(5);
		h.add(7);
		
		
		
		
		System.out.println(h);
	}
}
