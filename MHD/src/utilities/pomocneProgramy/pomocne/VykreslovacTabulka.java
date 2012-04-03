/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */

package utilities.pomocneProgramy.pomocne;



import java.awt.Canvas;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

import utilities.componenty.pomocne.OknoProTesty;
import utilities.pomocneProgramy.PraceSRetezci;

/*******************************************************************************
 * Instance třídy {@code VykreslovacTabulka} představují ...
 *
 * @author    Vojtěch Sejkora
 * @version   1.00.000
 */
public class VykreslovacTabulka extends AVyreslovatelTagu
{
//== KONSTANTNÍ ATRIBUTY TŘÍDY =================================================
	/**	úvodník před nadpisem */
	//podle tohoto řetězce se bude hledat jak se jmenuje tabulka
	private static final String OPPENING_NAME="name=";
	
	/**	úvodník před řádkem */
	//podle tohoto řetězce se bude hledat jak se mají popsat jednotlivé buňky v řádku
	private static final String OPPENING_ROW="rows=";
	
	/**	úvodník před sloupce */
	//podle tohoto řetězce se bude hledat jak se mají popsat jednotlivé buňky ve sloupci
	private static final String OPPENING_COLUMN="columns=";
	
	/**	úvodník před sloupce */
	//podle tohoto řetězce se bude hledat jak se mají popsat jednotlivé buňky ve sloupci
	private static final String OPPENING_ALIGN="align=";
	
	/**	úvodník před hodnotami sloupců */
	//podle tohoto řetězce se bude hledat jakou mají hodtotu jednotlivé sloupce
	private static final String VALUE_COLUMNS_START="{column}";
	/**	závěrník za hodnotami sloupců */
	//podle tohoto řetězce se bude hledat jakou mají hodtotu jednotlivé sloupce
	private static final String VALUE_COLUMNS_END="{/column}";
	
	/**	úvodník před hodnotami řádků */
	//podle tohoto řetězce se bude hledat jakou mají hodtotu jednotlivé řádky
	private static final String VALUE_ROWS_START="{row}";
	/**	závěrník za hodnotami řádků */
	//podle tohoto řetězce se bude hledat jakou mají hodtotu jednotlivé řádky
	private static final String VALUE_ROWS_END="{/row}";
	
	/**	úvodník před tělem */
	//podle tohoto řetězce se bude hledat jakou mají hodtotu jednotlivé řádky
	private static final String VALUE_BODY_START="{body}";
	/**	závěrník za tělem */
	//podle tohoto řetězce se bude hledat jakou mají hodtotu jednotlivé řádky
	private static final String VALUE_BODY_END="{/body}";
	
	private static final Font fontHlavicky=new Font("Serif",Font.BOLD+Font.ITALIC,20);
	private static final Font fontPopisku=new Font("Serif",Font.BOLD+Font.ITALIC,15);
	private static final Font fontTela=new Font("Serif",Font.PLAIN,15);
//== PROMĚNNÉ ATRIBUTY TŘÍDY ===================================================
//== STATICKÝ INICIALIZAČNÍ BLOK - STATICKÝ KONSTRUKTOR ========================
//== KONSTANTNÍ ATRIBUTY INSTANCÍ ==============================================
//== PROMĚNNÉ ATRIBUTY INSTANCÍ ================================================
	private String nadpis;
	private String popisSloupcu;
	private String popisRadku;
	private String [] radky;
	private String [] sloupce;
	private String [][] telo;
	
	private int sirkaTabulky;
	private int pocetSloupcuTela;
	
	private Zarovnej zpusobZarovnani=new VykresliDoprava();
//== PŘÍSTUPOVÉ METODY VLASTNOSTÍ TŘÍDY ========================================
//== OSTATNÍ NESOUKROMÉ METODY TŘÍDY ===========================================

//##############################################################################
//== KONSTRUKTORY A TOVÁRNÍ METODY =============================================

    /***************************************************************************
     *
     */
    public VykreslovacTabulka()
    {
    }



//== ABSTRAKTNÍ METODY =========================================================
//== PŘÍSTUPOVÉ METODY VLASTNOSTÍ INSTANCÍ =====================================
    
    public int getSirka(){
    	return sirkaTabulky;
    }
//== OSTATNÍ NESOUKROMÉ METODY INSTANCÍ ========================================
    public int vykresli(String text,Graphics g, int zacniKreslitOd){
    	int zkoncilJsemKreslitNa=zacniKreslitOd;
    	
    	zjistiPopisky(text.substring(0,text.indexOf("}")+1));
    	if(text.indexOf("{title}")!=-1)
    		zjistiHodnotySloupceARadky(text.substring(PraceSRetezci.najdiNasledujiciIndexZaPodretezcem(text, "{title}"),text.indexOf("{/title}")));
    	zjistiHodnoty(text.substring(PraceSRetezci.najdiNasledujiciIndexZaPodretezcem(text,VykreslovacTabulka.VALUE_BODY_START ),text.indexOf(VALUE_BODY_END)));
    	
    	int vykresliTeloOdVrchu=0;
    	int vykreslitTeloZleva=0;
    
    	if(!this.nadpis.equals(""))
    		vykresliTeloOdVrchu+=g.getFontMetrics(VykreslovacTabulka.fontHlavicky).getHeight()+5;
    	if(!this.popisSloupcu.equals("")){		
    		vykresliTeloOdVrchu+=g.getFontMetrics(VykreslovacTabulka.fontPopisku).getHeight();
    	}
    	
    		vykreslitTeloZleva=vykreslLevouCastTabulky(g, vykresliTeloOdVrchu+zacniKreslitOd, 0);
    		vykresliTelo(g, vykresliTeloOdVrchu+zacniKreslitOd, vykreslitTeloZleva);
    		
    		vykreslVrchniCastTabulky(g, zacniKreslitOd, vykreslitTeloZleva);
    	
    		zkoncilJsemKreslitNa=zacniKreslitOd+vykresliTeloOdVrchu+g.getFontMetrics(VykreslovacTabulka.fontTela).getHeight()*(this.telo.length+((this.sloupce.length>0)?1:0));
    		g.drawLine(sirkaTabulky, zacniKreslitOd, sirkaTabulky, zkoncilJsemKreslitNa);
    		g.drawLine(sirkaTabulky, zkoncilJsemKreslitNa, 0, zkoncilJsemKreslitNa);
    	return zkoncilJsemKreslitNa;
    }
//== SOUKROMÉ A POMOCNÉ METODY TŘÍDY ===========================================
    /**
     * Vrátí řetězec, který se vyřízne z textu
     * 
     * @param text    Text, z kterého se má vybírat daný podřetězec
     * @param zacatek   číslo od kud se má začít vyřezávat
     * @param konec    číslo kam až se má vyžíznout
     *  
     * @return vzátí podřetězec <ul>
     * <li><b>zacatek=-1</b>, pak vrátí ""</li>
     * <li><b>zacatek>=konec</b>, pak vrátí ""</li>
     * <li><b>konec>zacatek</b>, vrátí podžetězec  [od,do) </li>
     * </ul>
     */
    private static String retezec(String text,int zacatek,int konec){
    	if(zacatek==-1)
    		return "";
    	else if(konec<=zacatek)
    		return "";
    	else
    		return text.substring(zacatek,konec);   		
    }
//== SOUKROMÉ A POMOCNÉ METODY INSTANCÍ =======================================
    /**
     * zjístí <ul><li><b>nadpis</b> což je název tabulky</li>
     * 				<li><b>popisek sloupcu</b></li>
     * 				<li><b>popisek řádků</b></li></ul>
     * a dá je do globálních proměnných
     * 
     * @param text   v tomto parametru hledá víše zmíněné věci, pokud něco nenajde nastaví to na <b>""</b>
     */
    private void zjistiPopisky(String text){
    	String [] oddelovace=new String[]{"\t"," name="," columns="," rows=","}","align="};
    	
    	int zacatekHlavicky=PraceSRetezci.najdiNasledujiciIndexZaPodretezcem(text, VykreslovacTabulka.OPPENING_NAME);
    	int konecHlavicky=PraceSRetezci.najdiNejmensiIndexMoznychOddelovacu(text, zacatekHlavicky,oddelovace);
    	this.nadpis=VykreslovacTabulka.retezec(text,zacatekHlavicky,konecHlavicky);
    		
    	int zacatekPopiskuSloupcu=PraceSRetezci.najdiNasledujiciIndexZaPodretezcem(text, VykreslovacTabulka.OPPENING_COLUMN);
    	int konecPopiskuSloupcu=PraceSRetezci.najdiNejmensiIndexMoznychOddelovacu(text, zacatekPopiskuSloupcu,oddelovace);
    	this.popisSloupcu=VykreslovacTabulka.retezec(text,zacatekPopiskuSloupcu,konecPopiskuSloupcu);
    	
    	int zacatekPopiskuRadku=PraceSRetezci.najdiNasledujiciIndexZaPodretezcem(text, VykreslovacTabulka.OPPENING_ROW);
    	int konecPopiskuRadku=PraceSRetezci.najdiNejmensiIndexMoznychOddelovacu(text, zacatekPopiskuRadku,oddelovace);
    	this.popisRadku=VykreslovacTabulka.retezec(text,zacatekPopiskuRadku,konecPopiskuRadku);
    	
    	int zacatekZarovnani=PraceSRetezci.najdiNasledujiciIndexZaPodretezcem(text, VykreslovacTabulka.OPPENING_ALIGN);
    	int konecZarovnani=PraceSRetezci.najdiNejmensiIndexMoznychOddelovacu(text, zacatekZarovnani,oddelovace);
    	String align=VykreslovacTabulka.retezec(text,zacatekZarovnani,konecZarovnani);
    	
    	if(align.equals("centre") || align.equals("na střed")){
    		this.zpusobZarovnani=new VykresliNaStred();
    	}else
    		this.zpusobZarovnani=new VykresliDoprava();
    }

    private void zjistiHodnotySloupceARadky(String text){
    	int zacatekHodnotSloupcu=PraceSRetezci.najdiNasledujiciIndexZaPodretezcem(text, VykreslovacTabulka.VALUE_COLUMNS_START);
    	int konecHodnotSloupcu=text.indexOf(VykreslovacTabulka.VALUE_COLUMNS_END);
    	String hodnotySloupcu=VykreslovacTabulka.retezec(text,zacatekHodnotSloupcu,konecHodnotSloupcu);
    	this.sloupce=hodnotySloupcu.split("\t");
    	

    	int zacatekHodnotRadku=PraceSRetezci.najdiNasledujiciIndexZaPodretezcem(text, VykreslovacTabulka.VALUE_ROWS_START);
    	int konecHodnotRadku=text.indexOf( VykreslovacTabulka.VALUE_ROWS_END);
    	String hodnotyRadku=VykreslovacTabulka.retezec(text,zacatekHodnotRadku,konecHodnotRadku);
    	this.radky=hodnotyRadku.split("\t");
    }
    
    private void zjistiHodnoty(String text){
    	String [] radky;
    	
    	if(this.sloupce==null || this.radky==null){
    		radky=text.split("\n");
    		
    		this.telo=new String[radky.length][];
    		for(int i=0;i<radky.length;i++){
    			this.telo[i]=radky[i].split("\t");
    			if(pocetSloupcuTela>this.telo[i].length)
        			pocetSloupcuTela=this.telo[i].length;
    		}
    		
    		return;
    	}
    	
    	radky=text.split("\t|\n");
    	
    	if(!this.sloupce[0].equals("")){
    		this.telo=new String[radky.length/this.sloupce.length][this.sloupce.length];
    		this.pocetSloupcuTela=this.sloupce.length;
    		int j=0;
    		
    		for(int i=1;i<radky.length;){
    			for(int k=0;k<this.sloupce.length;k++){
    				this.telo[j][k]=radky[i];
    				
    				i++;
    				if(i==radky.length)
    					return;
    			}
    			j++;
    		}
    		return;		
    	}
    	
    	
    		this.telo=new String[this.radky.length][radky.length/this.radky.length];
    		this.pocetSloupcuTela=radky.length/this.radky.length;
    		int j=0;
    		
    		for(int i=1;i<radky.length;){
    			for(int k=0;k<this.telo[j].length;k++){
    				this.telo[j][k]=radky[i];
    				
    				i++;
    				if(i==radky.length)
    					return;
    			}
    			j++;
    		}    	
    }
    
    /**
     * Zjistí šířku sloupce v těle s daným indexem
     * 
     * @param i index slupce v těle
     * @param fm  FontMetrics grafického kontextu
     * @return vrátí šířku v pixelech
     */
    private int zjistiSirkuSloupce(int i, FontMetrics fm){
    	int sirka=0;
    	
    	try{
    		sirka=fm.stringWidth(sloupce[i]);
    	}catch(ArrayIndexOutOfBoundsException e){}
    	catch(NullPointerException e){}
    		
    	for(int j=0;j<telo.length;j++){
    		try{
    		int pomSirka=fm.stringWidth(telo[j][i]);
    		if(sirka<pomSirka)
    			sirka=pomSirka;
    		}catch(NullPointerException e){
    			System.out.println("TABULKA na souřednicích ["+j+"]["+i+"] je prázdná");
    		}
    	}
    	return sirka;	
    }
    
    /**
     * Zjistí největší šířku a zadaných slovech a vrátí ji
     * 
     * @param fm  FontMetrics grafického kontextu
     * @param vCemChceteNajitNejvetsiSirku
     * 
     * @return vrátí šířku v pixelech
     * @throws ArrayIndexOutOfBoundsException
     */
    private int zjistiMaxSirku(FontMetrics fm,String ... vCemChceteNajitNejvetsiSirku) throws ArrayIndexOutOfBoundsException{
    	int sirka=0;
    	
    	for(int j=0;j<vCemChceteNajitNejvetsiSirku.length;j++){
    		
    		int pomSirka=fm.stringWidth(vCemChceteNajitNejvetsiSirku[j]);
    		if(sirka<pomSirka)
    			sirka=pomSirka;
    	}
    	return sirka;	
    }
    
    private void vykresliTelo(Graphics g,int zhora,int zleva){
    	g.setFont(VykreslovacTabulka.fontTela);
    	FontMetrics fm=g.getFontMetrics();
    	int vyskaTextu=fm.getHeight();
    	int vykreslovaciVyskaTextu=fm.getAscent()+fm.getDescent();
    	int sirkaTela=0;
    	
    	for(int i=0;i<pocetSloupcuTela;i++){
    		int sirkaSloupce=zjistiSirkuSloupce(i, g.getFontMetrics())+10;
    		int pridatKVysce=0;    		
    		if(sloupce.length>i){
    			this.zpusobZarovnani.vykresli(g, zleva+sirkaTela, zhora, sloupce[i], sirkaSloupce);
    			g.drawLine(zleva+sirkaTela, zhora+vykreslovaciVyskaTextu, zleva+sirkaTela+sirkaSloupce, zhora+vykreslovaciVyskaTextu);
    			g.drawLine(zleva+sirkaTela, zhora+vykreslovaciVyskaTextu+1, zleva+sirkaTela+sirkaSloupce, zhora+vykreslovaciVyskaTextu+1);
    			pridatKVysce=vyskaTextu;
    		}
    		
    		for(int j=0;j<telo.length;j++){
    			try{
    				this.zpusobZarovnani.vykresli(g, zleva+sirkaTela, zhora+j*vyskaTextu+pridatKVysce, telo[j][i], sirkaSloupce);
    				g.drawLine(zleva+sirkaTela, zhora+(j+1)*vyskaTextu+pridatKVysce, zleva+sirkaTela+sirkaSloupce, zhora+(j+1)*vyskaTextu+pridatKVysce);
    			}catch(NullPointerException e){
    				System.out.println("telo["+j+"]["+i+"] je prázdné");
    			}
    		}
    		sirkaTela+=sirkaSloupce;
    	}
    	
    	this.sirkaTabulky=sirkaTela+zleva+10;
    }
    
    /**
     * 
     * @param g
     * @param zhora   
     * @param zleva   tato proměná se použíje pokud se má vypsat popisek sloupců
     */
    private void vykreslVrchniCastTabulky(Graphics g,int zhora,int zleva){
    	if(!this.nadpis.equals("")){
    	g.drawLine(0, zhora, sirkaTabulky, zhora);
    	zhora+=2;
    	g.drawLine(0, zhora, sirkaTabulky, zhora);
    	zhora+=1;
    	
    	// START vykreslení názvu
    	g.setFont(VykreslovacTabulka.fontHlavicky);
    	AVyreslovatelTagu.vykresliNaStred(g, 0, zhora, this.nadpis, sirkaTabulky);
    	zhora+=g.getFontMetrics().getHeight();
    	// KONEC vykreslení názvu
    	
    	g.drawLine(0, zhora, sirkaTabulky, zhora);
    	zhora+=2;
    	g.drawLine(0, zhora, sirkaTabulky, zhora);
    	}
    	
    	if(!this.popisSloupcu.equals("")){
        	g.setFont(VykreslovacTabulka.fontPopisku);
        	AVyreslovatelTagu.vykresliNaStred(g, zleva, zhora, this.popisSloupcu, sirkaTabulky-zleva);
        	}
    }
    
    private int vykreslLevouCastTabulky(Graphics g,int zhora,int zleva){
    	int odsazeniTela=0;
    	if(!this.popisRadku.equals("")){
	    	// START vykreslení popisku
	    	((Graphics2D) g).rotate(-Math.PI/2);
	    	g.setFont(VykreslovacTabulka.fontPopisku);
	    	AVyreslovatelTagu.vykresliNaStred(g, -g.getFontMetrics().stringWidth(this.popisRadku),0 , this.popisRadku, -g.getFontMetrics(VykreslovacTabulka.fontTela).getHeight()*telo.length);
	    	((Graphics2D) g).rotate(Math.PI/2);
	    	// KONEC vykreslení popisku
	    	odsazeniTela+=g.getFontMetrics().getHeight();
    	}
    	
    	
    	// START vykreslení řádků
    	if(!this.radky[0].equals("")){
	    	g.setFont(VykreslovacTabulka.fontTela);
	    	FontMetrics fm=g.getFontMetrics();
	    	int vyskaTextu=fm.getHeight();
	    	int sirkaSloupce=zjistiMaxSirku(fm, this.radky);
	    	int pridatKVysce=0;
	    	if(!this.sloupce[0].equals(""))
	    		pridatKVysce=vyskaTextu;
	    	
	    	for(int i=0;i<this.radky.length;i++){
	    		AVyreslovatelTagu.vykresliDoprava(g, zleva+odsazeniTela, zhora+i*vyskaTextu+pridatKVysce, this.radky[i], sirkaSloupce);
	    	}
	    	odsazeniTela+=sirkaSloupce;
	    	
	    	g.drawLine(zleva+odsazeniTela, zhora+pridatKVysce, zleva+odsazeniTela, zhora+this.radky.length*vyskaTextu+pridatKVysce);
			g.drawLine(zleva+odsazeniTela+1, zhora+pridatKVysce, zleva+odsazeniTela+1, zhora+this.radky.length*vyskaTextu+pridatKVysce);
    	}
    	// KONEC vykreslení řádků
    	
    	return odsazeniTela;
    }
//== INTERNÍ DATOVÉ TYPY =======================================================
    abstract class Zarovnej{
    	abstract void vykresli(Graphics g,int sirka,int vyska,String text,int sirkaVykreslovaciOblasti);
    }
    
    class VykresliNaStred extends Zarovnej{
		@Override
		void vykresli(Graphics g, int sirka, int vyska, String text,
				int sirkaVykreslovaciOblasti) {
			AVyreslovatelTagu.vykresliNaStred(g, sirka, vyska, text, sirkaVykreslovaciOblasti);
			
		}
    }
    
    class VykresliDoprava extends Zarovnej{
		@Override
		void vykresli(Graphics g, int sirka, int vyska, String text,
				int sirkaVykreslovaciOblasti) {
			AVyreslovatelTagu.vykresliDoprava(g, sirka, vyska, text, sirkaVykreslovaciOblasti);
			
		}
    }
//== TESTOVACÍ METODY A TŘÍDY ==================================================
//
     /***************************************************************************
      * Testovací metoda.
      */
     public static void test()
     {
        final VykreslovacTabulka instance = new VykreslovacTabulka();
         Canvas c=new Canvas(){
        	 /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
        	 public void paint(Graphics g){
        		 String text="{table name=Tabulka zastrašení rows=charisma columns=bojovnost}\n"+
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
"{/table}";
        		 int kresli= instance.vykresli(text, g, 0)+20;
            	instance.vykresli(text, g, kresli);
        	 }
         };
         
         OknoProTesty o=new OknoProTesty();
         o.add(c);
         o.pack();
         o.setSize(100,100);
         o.setVisible(true);
         
     }
     /** @param args Parametry příkazového řádku - nepoužívané. */
     public static void main(String[] args)  {  test();  }
}


