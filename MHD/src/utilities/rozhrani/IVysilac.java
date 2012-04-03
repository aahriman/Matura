package utilities.rozhrani;

import java.util.ArrayList;
import java.util.List;

public interface IVysilac<T> {
	//== VEŘEJNÉ KONSTANTY =========================================================
	//== DEKLAROVANÉ METODY ========================================================
	/**
	 * přidá posluchače a vrátí true pokud se povedlo přidat
	 */
	public boolean pridej(IPosluchac<T> p);
	
	/**
	 * odebere posluchače a vrátí true pokud se povedlo odebrat
	 */
	public boolean odeber(IPosluchac<T> p);
	
	/**
	 * Obvolá všechny posluchače
	 */
	public void obvolej();
	//== ZDĚDĚNÉ METODY ============================================================
	//== INTERNÍ DATOVÉ TYPY =======================================================
	
	static class Vysilac<T> implements IVysilac<T>{
		final List<IPosluchac<T>> LIST_POSLUCHACU=new ArrayList<IPosluchac<T>>();
		final T VYSILAC; 
		public Vysilac(T vysilac){
			VYSILAC=vysilac;
		}

		@Override
		public void obvolej() {
			for(IPosluchac<T> t:LIST_POSLUCHACU){
				t.zmena(VYSILAC);
			}
		}

		@Override
		public boolean odeber(IPosluchac<T> p) {
			return LIST_POSLUCHACU.remove(p);
		}

		@Override
		public boolean pridej(IPosluchac<T> p) {
			p.zmena(VYSILAC);
			return LIST_POSLUCHACU.add(p);
		}
		
		
	}
}
