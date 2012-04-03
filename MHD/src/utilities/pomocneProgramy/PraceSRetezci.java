package utilities.pomocneProgramy;

import java.util.ArrayList;


final public class PraceSRetezci {
	private PraceSRetezci(){}

	/**
     * najde nejmenší index v řetězci vCemHledat daných oddělovačů
     * 
     * @param vCemHleda  v jakém textu má vyhledávat
     * @param od  od kterého indexu má vyhledávat
     * @param oddelovac  oddělovače, které má hledat
     * 
     * @return číslo nejmenšího indexu
     * <ul><li>pokud nic nenašel vrátí <b>-1</b></li>
     * <li>jinak vrátí nejmenší vzdálenost <b>od</od> k nějakému <b>oddelovac</b></li></ul>
     */
    public static int najdiNejmensiIndexMoznychOddelovacu(String vCemHleda,int od,String... oddelovac){
    	int k=-1;
    	int pomK;
    	 
    	    for(int i=0;i<oddelovac.length;i++){
    	    	pomK=vCemHleda.indexOf(oddelovac[i],od);
    	    	if(pomK>-1)
    	    		if(k==-1 || k>pomK)
    	    			k=pomK;
    	    }
    	
    	return k;
    }
    
    public static int najdiNejmensiIndexMoznychOddelovacu(String vCemHleda,String... oddelovac){
    	return PraceSRetezci.najdiNejmensiIndexMoznychOddelovacu(vCemHleda, 0, oddelovac);
    }
    
    /**
     * najde nejmenší index v řetězci vCemHledat daného řetězce a vrátí následující index
     * <p><i>pokud budete hledat podřetězec ahoj v řetězci <b>ahoj kam jdeš</b> , tak vám vrátí index až té mezery před k</i></p>
     * 
     * @param vCemHleda  v jakém textu má vyhledávat
     * @param od  od kterého indexu má vyhledávat
     * @param podretezec  podretezec, který má hledat
     * 
     * @return číslo indexu
     * <ul><li>pokud nic nenašel vrátí <b>-1</b></li>
     * <li>jinak vrátí následující index za tímto slovem</li></ul>
     */
    public static int najdiNasledujiciIndexZaPodretezcem(String vCemHleda,int od,String podretezec){
    	int k=-1;
    	int pomK=vCemHleda.indexOf(podretezec,od);
    	    	if(pomK>-1)
    	    		k=pomK+podretezec.length();
    	return k;
    }
    
    /**
     * najde nejmenší index v řetězci vCemHledat daného řetězce a vrátí následující index
     * <p><i>pokud budete hledat podřetězec ahoj v řetězci <b>ahoj kam jdeš</b> , tak vám vrátí index až té mezery před k</i></p>
     * 
     * @param vCemHleda  v jakém textu má vyhledávat
     * @param podretezec  podretezec, který má hledat
     * 
     * @return číslo indexu
     * <ul><li>pokud nic nenašel vrátí <b>-1</b></li>
     * <li>jinak vrátí následující index za tímto slovem</li></ul>
     */
    public static int najdiNasledujiciIndexZaPodretezcem(String vCemHleda,String podretezec){
    	return PraceSRetezci.najdiNasledujiciIndexZaPodretezcem(vCemHleda, 0, podretezec);
    }
    
    public static int najdiZacatekSlova(String text,String hledanyVyraz){
    	return najdiZacatekSlova(text, hledanyVyraz, 0);
    }
    
    public static int najdiZacatekSlova(String text,String hledanyVyraz, int od){
    	return najdiZacatekSlova(text,new HledajiciAutomat(hledanyVyraz), od);
    }
    
    public static int najdiZacatekSlova(String text,HledajiciAutomat hledanyVyraz){
    	return najdiZacatekSlova(text, hledanyVyraz, 0);
    }
    
    public static int najdiZacatekSlova(String text,HledajiciAutomat hledanyVyraz, int od){
    	return hledanyVyraz.hledaniZacatek(text, od);
    }
    
    public static int[] najdiVsechnyZacatkySlova(String text,HledajiciAutomat hledanyVyraz){
    	return hledanyVyraz.hledaniZacateku(text,0);
    }
    
    public static int najdiKonecSlova(String text,String hledanyVyraz){
    	return najdiKonecSlova(text,hledanyVyraz,0);
    }
    
    public static int najdiKonecSlova(String text,String hledanyVyraz,int od){
    	return najdiKonecSlova(text,new HledajiciAutomat(hledanyVyraz),od);
    }
    
    public static int najdiKonecSlova(String text,HledajiciAutomat hledanyVyraz){
    	return najdiKonecSlova(text, hledanyVyraz, 0);
    }
    
    public static int najdiKonecSlova(String text,HledajiciAutomat hledanyVyraz, int od){
    	return hledanyVyraz.hledaniKonec(text, od);
    }
    
    public static int[] najdiVsechnyKonceSlova(String text,HledajiciAutomat hledanyVyraz){
    	return hledanyVyraz.hledaniKonecu(text, 0);
    }
    
    public static class HledajiciAutomat{
    	final char[] HLEDANY_VYRAZ;
    	final int  [] F;
    	
    	public HledajiciAutomat(String hledanyText){
    		HLEDANY_VYRAZ = hledanyText.toCharArray();
    		F = new int[hledanyText.length()];
    		konstrukce();
    	}
    	
    	private int krok(int i,char c){
    	if (i < HLEDANY_VYRAZ.length && HLEDANY_VYRAZ[i+1] == c) 
  	      return i + 1;
  	    else if (i > 0)
  	      return krok(F[i], c);
  	    else
  	      return 0;
    	}
    	
    	private void konstrukce(){
    		F[1] = 0;
    		F[0] = 0;
    		for( int i = 2; i < HLEDANY_VYRAZ.length; i++)
    			F[i] = krok(F[i-1],HLEDANY_VYRAZ[i]);
    	}
    	
    	private int hledaniKonec(String seno, int od){
    		int R= 0;
    		for (int i = od ; i < seno.length() ; i++){
    			R = krok(R, seno.charAt(i));
    			if( R == HLEDANY_VYRAZ.length -1)  
    				return i;
    		}
    		return -1;
    	}
    	
    	private int[] hledaniKonecu(String seno,int od){
    		ArrayList<Integer> konec = new ArrayList<Integer>();
    		int R = 0;
    		for (int i = od ; i < seno.length() ; i++){
    			R = krok(R, seno.charAt(i));
    			if( R == HLEDANY_VYRAZ.length -1){  
    				konec.add(i);
    				R = 0;
    			}
    		}
    		
    		int[] vratit = new int[konec.size()];
    		for(int i = 0; i < vratit.length; i++)
    			vratit[i] = konec.get(i);
    		return vratit;
    	}
    	
    	private int hledaniZacatek(String seno,int od){
    		int k = hledaniKonec(seno, od);
    		return k == -1? -1 : k - HLEDANY_VYRAZ.length + 1; 
    	}
    	
    	private int[] hledaniZacateku(String seno,int od){
    		int[] r = hledaniKonecu(seno, od);
    		for(int i = 0; i < r.length; i++){
    			r[i] = r[i] - HLEDANY_VYRAZ.length + 1;
    		}
    		return r; 
    	}
    }
}
