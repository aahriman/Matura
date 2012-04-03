/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */

package utilities.pomocneProgramy.pomocne;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utilities.pomocneProgramy.PraceSRetezci;

/*******************************************************************************
 * Instance výčtového typu {@code MojeTagy} představují ...
 *
 * @author    Vojtěch Sejkora
 * @version   1.00.000
 */
public enum MojeTagy
{
//== HODNOTY VÝČTOVÉHO TYPU ====================================================

////=====  NÁPOVĚDA: KONSTRUKTOR BEZ PARAMETRŮ  ==============
    TABULKA("{table","{/table}",new VykreslovacTabulka()), 
    TEXT("{text}","{/text}",new VykreslovacTextu()),
    DEFAULT("","",new VykreslovacTextu());
//
////=====  NÁPOVĚDA: KONSTRUKTOR S PARAMETRY  ================
//     JARO(par), LETO(par), PODZIM(par), ZIMA(par);


//== KONSTANTNÍ ATRIBUTY TŘÍDY =================================================
    private static final List<MojeTagy> MOZNE_TAGY=new ArrayList<MojeTagy>();
    private static final Map<String,MojeTagy> MAPA_TAGU_DLE_ZACATKU=new HashMap<String,MojeTagy>();
//== PROMĚNNÉ ATRIBUTY TŘÍDY ===================================================
//== STATICKÝ INICIALIZAČNÍ BLOK - STATICKÝ KONSTRUKTOR ========================
    static{
    	MojeTagy.MOZNE_TAGY.add(MojeTagy.TABULKA);
    	MojeTagy.MOZNE_TAGY.add(MojeTagy.TEXT);
    	MojeTagy.MOZNE_TAGY.add(MojeTagy.DEFAULT);
    	MojeTagy.MAPA_TAGU_DLE_ZACATKU.put(TABULKA.ZACATEK,TABULKA);
    	MojeTagy.MAPA_TAGU_DLE_ZACATKU.put(TEXT.ZACATEK,TEXT);
    	MojeTagy.MAPA_TAGU_DLE_ZACATKU.put(DEFAULT.ZACATEK,DEFAULT);
    }
//== KONSTANTNÍ ATRIBUTY INSTANCÍ ==============================================
    private final String ZACATEK;
    private final String KONEC;
    private final AVyreslovatelTagu VYKRESLOVAC;
//== PROMĚNNÉ ATRIBUTY INSTANCÍ ================================================
//== PŘÍSTUPOVÉ METODY VLASTNOSTÍ TŘÍDY ========================================
//== OSTATNÍ NESOUKROMÉ METODY TŘÍDY ===========================================

//##############################################################################
//== KONSTRUKTORY A TOVÁRNÍ METODY =============================================

    /***************************************************************************
     *
     */
    private MojeTagy(String zacatek,String konec,AVyreslovatelTagu vykreslovac)
    {
    	this.ZACATEK=zacatek;
    	this.KONEC=konec;
    	this.VYKRESLOVAC=vykreslovac;
    }


//== ABSTRAKTNÍ METODY =========================================================
//== PŘÍSTUPOVÉ METODY VLASTNOSTÍ INSTANCÍ =====================================
    
    public String getZACATEK() {
		return ZACATEK;
	}


	public String getKONEC() {
		return KONEC;
	}
	
	public AVyreslovatelTagu getVYKRESLOVAC() {
		return VYKRESLOVAC;
	}


	public static MojeTagy getPrvniTag(int od,String text){
    	int i=text.indexOf(TABULKA.ZACATEK);
    	
    	if(od-i==0){
    		return TABULKA;
    	}
    	
    	i=text.indexOf(TEXT.ZACATEK);
    	if(od-i==0){
    		return TEXT;
    	}
    		return DEFAULT;
    }
    
    public static String orizniDleTagu(String text, MojeTagy zpusobKresleni,int od){
    	if(zpusobKresleni.equals(DEFAULT)){
    		String [] oddelovac=new String [MojeTagy.MOZNE_TAGY.size()-1];
    		int j=0;
    		for(int i=0;i<MojeTagy.MOZNE_TAGY.size();i++){
    			if(MojeTagy.MOZNE_TAGY.get(i)==DEFAULT)
    				continue;
    			else{
    				oddelovac[j]=MojeTagy.MOZNE_TAGY.get(i).ZACATEK;
    				j++;
    			}
    		}
    		
    		
    		int nejblizsi=PraceSRetezci.najdiNejmensiIndexMoznychOddelovacu(text, od, oddelovac);
    		if(nejblizsi==od){
    			
    			j=text.length();
    			
    			for(int i=0;i<oddelovac.length;i++){
    				int k=PraceSRetezci.najdiNasledujiciIndexZaPodretezcem(text, od,oddelovac[i]);
    				if(k<j && k!=-1)
    					j=i;
    			}
    			String podretezec=MojeTagy.MAPA_TAGU_DLE_ZACATKU.get(oddelovac[j]).KONEC;
    			System.out.print(podretezec);
    			return orizniDleTagu(text, zpusobKresleni, PraceSRetezci.najdiNasledujiciIndexZaPodretezcem(text, od, podretezec));
    		}
    		if(nejblizsi==-1)
    			nejblizsi=text.length();
    		
    		return text.substring(od,nejblizsi);
    	}else{
    		int po=PraceSRetezci.najdiNasledujiciIndexZaPodretezcem(text, od, zpusobKresleni.KONEC);
    		return text.substring(text.indexOf(zpusobKresleni.ZACATEK), (po!=-1)? po:text.length());
    	}
    }
//== OSTATNÍ NESOUKROMÉ METODY INSTANCÍ ========================================
//== SOUKROMÉ A POMOCNÉ METODY TŘÍDY ===========================================
//== SOUKROMÉ A POMOCNÉ METODY INSTANCÍ ========================================
//== INTERNÍ DATOVÉ TYPY =======================================================
//== TESTOVACÍ METODY A TŘÍDY ==================================================
//
     /***************************************************************************
      * Testovací metoda.
      */
     public static void test()
     {
    	 System.out.println(MojeTagy.orizniDleTagu("text", MojeTagy.DEFAULT, 0));
    	 System.out.println(MojeTagy.orizniDleTagu("{table name=Tabulka zastrašení rows=charisma columns=bojovnost}\n"+
    			 "{title}\n"+
    				"{column}jednaaaaaaaaaaaaaaaa	DVĚ	3	4	5	6	7	8	9	10	11	12	13	14	15	16	17	18{/column}\n"+
    				"{row}2	3	4	5	6	7	8	9	10	11{/row}\n"+
    			"{/title}\n"+
    			"{body}\n"+
    			"100	85	70	55	40	25	10	-5	-20	-35	-50	-65	-80	-95	-80	-65	-50	-35\n"+
    			"90	75	60	45	30	15	0	-15	-30	-45	-60	-75	-90	-105	-90	-75	-60	-45\n"+
    			"80	65	50	35	20	5	-10	-25	-40	-55	-70	-85	-100	-115	-100	-85	-70	-55\n"+
    			"70	55	40	25	10	-5	-20	-35	-50	-65	-80	-95	-110	-125	-110	-95	-80	-65\n"+
    			"60	45	30	15	0	-15	-30	-45	-60	-75	-90	-105	-120	-135	-120	-105	-90	-75\n"+
    			"50	35	20	5	-10	-25	-40	-55	-70	-85	-100	-115	-130	-145	-130	-115	-100	-85\n"+
    			"40	25	10	-5	-20	-35	-50	-65	-80	-95	-110	-125	-140	-155	-140	-125	-110	-95\n"+
    			"30	15	0	-15	-30	-45	-60	-75	-90	-105	-120	-135	-150	-165	-150	-135	-120	-105\n"+
    			"20	5	-10	-25	-40	-55	-70	-85	-100	-115	-130	-145	-160	-175	-160	-145	-130	-115\n"+
    			"10	-5	-20	-35	-50	-65	-80	-95	-110	-125	-140	-155	-170	-185	-170	-155	-140	-125\n"+
    			"{/body}\n"+
    			"{/table}\n"+
    			 "{color(255,0,0)}Válečník může zastrašovat skupinu, jen proti které družina bouje jako celek. Tedy si nemůže vybrat jen jednu oběť.{/color(255,0,0)}{/b}\n"+
"Pokud zastrašuje skupinu, pak se pravděpodobnost z tabulky {b}dělí počtem protivníků v t0 skupině{/b}.\n"+
"Zastrašování se musí oznámit PJ. Zastrašování se bere jako útok.\n", MojeTagy.DEFAULT, 0));
     }
     /** @param args Parametry příkazového řádku - nepoužívané. */
     public static void main(String[] args) { test(); }
}
