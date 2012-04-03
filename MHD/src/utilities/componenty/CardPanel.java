/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kĂłdovĂˇnĂ­: PĹ™Ă­liĹˇ ĹľluĹĄouÄŤkĂ˝ kĹŻĹ� ĂşpÄ›l ÄŹĂˇbelskĂ© Ăłdy. */

package utilities.componenty;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.Panel;
import java.awt.ScrollPane;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import utilities.componenty.pomocne.OknoProTesty;

/*******************************************************************************
 * Instance tĹ™Ă­dy {@code CardPanel} pĹ™edstavujĂ­ conteiner, do kterĂ©ho se vklĂˇdajĂ­
 * jednotlivĂ© componenty, ale kaĹľdĂˇ pod novou zĂˇloĹľku
 *
 * @author    VojtÄ›ch Sejkora
 * @version   1.01.000
 */
public class CardPanel 
{
//== KONSTANTNĂŤ ATRIBUTY TĹ�ĂŤDY =================================================
	static final int VYSKA_ZALOZKY=20;
//== PROMÄšNNĂ‰ ATRIBUTY TĹ�ĂŤDY ===================================================
//== STATICKĂť INICIALIZAÄŚNĂŤ BLOK - STATICKĂť KONSTRUKTOR ========================
//== KONSTANTNĂŤ ATRIBUTY INSTANCĂŤ ==============================================
	final private Panel cele=new Panel(new BorderLayout());
	final private Panel HLAVICKA=new Panel(new BorderLayout());
	final private java.util.List<Zalozka> ZALOZKY=new ArrayList<Zalozka>();
//== PROMÄšNNĂ‰ ATRIBUTY INSTANCĂŤ ================================================
	private Zalozka vybranaZalozka;
	private Component vlozeny;
	private final ScrollPane sp=new ScrollPane();
	private int sirkaVsechZalozek=0;
	
	private boolean nicVlozeno=true;
//== PĹ�ĂŤSTUPOVĂ‰ METODY VLASTNOSTĂŤ TĹ�ĂŤDY ========================================
//== OSTATNĂŤ NESOUKROMĂ‰ METODY TĹ�ĂŤDY ===========================================

//##############################################################################
//== KONSTRUKTORY A TOVĂ�RNĂŤ METODY =============================================

    /***************************************************************************
     *
     */
    public CardPanel()
    {
    	super();	
		
    	sp.add(HLAVICKA);
    	sp.setSize(25, 45);
    	cele.add(sp,BorderLayout.NORTH);
    	
    	pridejListeneraVelikosti();
    }    
//== ABSTRAKTNĂŤ METODY =========================================================
//== PĹ�ĂŤSTUPOVĂ‰ METODY VLASTNOSTĂŤ INSTANCĂŤ =====================================
    public Component getAsComponent(){
    	return cele;
    }
    
    public Component[] getComponents(){
    	final java.util.List<Component> l=new ArrayList<Component>();
    	for(final Zalozka z:ZALOZKY)
    		l.add(z.SCHOVANEC);
    	
    	return l.toArray(new Component[0]);
    }
    
//    public void select(String nazev){
//
//    	Zalozka z=null;
//    	int i=-1;
//    	for(Iterator<Zalozka> it=ZALOZKY.iterator();it.hasNext();){
//    		z=it.next();
//    		i++;
//    		if(nazev.equals(z.NAZEV)){
//    			break;
//    		}
//    	}
//    	
//    	select(i);
//    }
//    
//    public void select(int index){
//    	if(index>=ZALOZKY.size() || index<0)
//    		return;
//    
//    	ZALOZKY.get(index).reakceNaZmacknul();
//    }
//== OSTATNĂŤ NESOUKROMĂ‰ METODY INSTANCĂŤ ========================================
    public void add(final Component coVkladate,final String jakSeToMaJmenovat){
    	Zalozka z;
    	
    	if(nicVlozeno){	
    		z=new Zalozka(jakSeToMaJmenovat,true,coVkladate);
    		vybranaZalozka=z;
    		vlozeny=coVkladate;
    		cele.add(vlozeny,BorderLayout.CENTER);
    		cele.validate();
    		nicVlozeno=false;
    	}else
    		z=new Zalozka(jakSeToMaJmenovat,coVkladate);
    	
    	pridejDoHlavicky(z);
    	
    	sirkaVsechZalozek+=z.getWidth();
    }   
//== SOUKROMĂ‰ A POMOCNĂ‰ METODY TĹ�ĂŤDY ===========================================
//== SOUKROMĂ‰ A POMOCNĂ‰ METODY INSTANCĂŤ ========================================
    private void zmenObrazVPanelu(){
    	if(vlozeny!=null){
    		cele.remove(vlozeny);
    	}
    	
    	vlozeny=this.vybranaZalozka.getSchovane();

    	cele.add(vlozeny,BorderLayout.CENTER);
    	cele.validate();
    	Dimension celeP=cele.getSize();
    	final Dimension vlozeneP=vlozeny.getPreferredSize();
    	
    	if(celeP.width<vlozeneP.width){
    		cele.setSize(vlozeneP.width, celeP.height);
    	}
    	
    	celeP=cele.getSize();
    	
    	if(celeP.height<vlozeneP.height){
    		cele.setSize(celeP.width,vlozeneP.height);
    	}
    	cele.setPreferredSize(cele.getSize());    	
    }
    
    private void zmenaAktivniZalozky(final Zalozka z){
    	this.vybranaZalozka.setStav(false);
    	this.vybranaZalozka.repaint();
    	this.vybranaZalozka=z;
    	this.vybranaZalozka.setStav(true);
    	this.vybranaZalozka.repaint();
    }
    
    private void pridejDoHlavicky(final Zalozka z){
    	final Component [] c=HLAVICKA.getComponents();
    	if(c.length==0){
    		HLAVICKA.add(z,BorderLayout.WEST);
    		return;
    	}else if(c.length==1){
    		final Panel pom= new Panel(new BorderLayout());
    		pom.add(c[0],BorderLayout.WEST);
    		pom.add(z,BorderLayout.EAST);
    		HLAVICKA.add(pom,BorderLayout.WEST);
    		return;
    	}
    	
    	final Panel [] p=new Panel[c.length-1];
    	p[0].add(c[0],BorderLayout.WEST);
    	p[0].add(c[1],BorderLayout.EAST);
    	
    	for(int i=1;i<p.length;i++){
    		p[i].add(p[i-1],BorderLayout.WEST);
    		p[i].add(c[i+1],BorderLayout.EAST);
    	}
    	final Panel pom= new Panel(new BorderLayout());
    	
    	pom.add(p[p.length-1],BorderLayout.WEST);
    	pom.add(z,BorderLayout.EAST);
    	
    	HLAVICKA.add(pom,BorderLayout.WEST);
    }

    private void pridejListeneraVelikosti(){
    	sp.addComponentListener(new ComponentAdapter(){
    		@Override
    		public void componentResized(final ComponentEvent e){
    			if(cele.getWidth()<sirkaVsechZalozek)
    	    		sp.setSize(cele.getWidth(), VYSKA_ZALOZKY+21);
    	    	else
    	    		sp.setSize(cele.getWidth(), VYSKA_ZALOZKY+3);
    		}
    	});
    }
    
    @Override
    public String toString(){
    	return ZALOZKY.toString();
    }
    
//== INTERNĂŤ DATOVĂ‰ TYPY =======================================================
    
    class Zalozka extends Canvas{
    	/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		
		static final int PRESAH_PRES_TEXT=5;
    	static final int VELIKOST_PISMA=15;
    	static final int SIKMA_HRANA=15;
    	
    	private final String NAZEV;
    	private final Font VYBRAN_FONT=new Font("Dialog",Font.ITALIC+Font.BOLD,VELIKOST_PISMA);
    	private final Font NEVYBRAN_FONT=new Font("Dialog",Font.PLAIN,VELIKOST_PISMA);
    	private final Color BARVA_TEXTU=Color.BLACK;
    	
    	
    	private int sirkaTextu;
		private Color barvaZalozky;
		private boolean isSelect=false;
    	private Font aktivniFont;
    	
    	private boolean chybiPoslochac=true;
    	// schovance poklĂˇdejte za konstantu
    	private Component SCHOVANEC;
    	public Zalozka(final String nazev) {
    		this.NAZEV=nazev;

    		nastavDleStavu();
    		this.setFont(aktivniFont);
    		final FontMetrics fm=this.getFontMetrics(aktivniFont);
    		
    		sirkaTextu=fm.stringWidth(NAZEV);;
    		ZALOZKY.add(this);
    		this.setSize(sirkaTextu+PRESAH_PRES_TEXT+SIKMA_HRANA,VYSKA_ZALOZKY);
    		this.repaint();
		}
    	
    	public Zalozka(final String nazev, final boolean jeVybran) {
    		this(nazev);
    		this.isSelect=jeVybran;
		}
    	
    	public Zalozka(final String nazev,final Component schovanec) {
    		this(nazev);
    		this.SCHOVANEC=schovanec;
		}
    	
    	public Zalozka(final String nazev, final boolean jeVybran,final Component schovanec) {
    		this(nazev,jeVybran);
    		this.SCHOVANEC=schovanec;
    	}
    	
    	
    	@Override
    	public void paint(final Graphics g){
    		nastavDleStavu();
    		g.setColor(barvaZalozky);
    		g.setFont(aktivniFont);
    		final FontMetrics fm=g.getFontMetrics();
    		
    		sirkaTextu=fm.stringWidth(NAZEV);
    		
    		paintTvarZalozky(g);
    		
    		g.setColor(BARVA_TEXTU);
    		g.drawString(NAZEV, PRESAH_PRES_TEXT/2, fm.getAscent());
    		
    		this.setSize(sirkaTextu+PRESAH_PRES_TEXT+SIKMA_HRANA,VYSKA_ZALOZKY);
    		if(chybiPoslochac){
    			this.nastavPosluchace();
    			chybiPoslochac=false;
    		}
    	}

//    	@Override
//    	public String toString(){
//    		return "ZALOZKA:" + this.NAZEV +" Schovanec:\n"+ getSchovane()+"\n stav="+isSelect;
//    	}
    	public void setStav(final boolean isSelect){
    		this.isSelect=isSelect;
    	}
    	
    	public Component getSchovane(){
    		if(SCHOVANEC==null)
    			return new Container();
    		else
    			return SCHOVANEC;
    	}
    	
    	// sopukrom0 a pomocn0 metody instance
    	private void nastavDleStavu(){
    		if(isSelect){	
    			barvaZalozky=Color.WHITE;
    			aktivniFont=VYBRAN_FONT;
    		}else{
    			barvaZalozky=Color.LIGHT_GRAY;
    			aktivniFont=NEVYBRAN_FONT;
    		}
    	}
    	
    	public void reakceNaZmacknul(){
    		zmenaAktivniZalozky(this);
    		zmenObrazVPanelu();
    	}
    	
    	private void nastavPosluchace(){
        	this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        	this.addMouseListener(new MouseAdapter(){
        		@Override
				public void mouseClicked(final MouseEvent arg0) {
					reakceNaZmacknul();
				}
        	});
        }
    	
    	private void paintTvarZalozky(final Graphics g){
    		
    		final int [] x=new int[]{      0      , 0 , sirkaTextu+PRESAH_PRES_TEXT, sirkaTextu+PRESAH_PRES_TEXT+SIKMA_HRANA};
    		final int [] y=new int[]{VYSKA_ZALOZKY, 0 ,            0               , VYSKA_ZALOZKY};
    		g.fillPolygon(x,y,4);
    		g.setColor(Color.BLACK);
    		g.drawPolygon(x,y,4);
    	}
    }
//== TESTOVACĂŤ METODY A TĹ�ĂŤDY ==================================================

    private static void pridej(final CardPanel instance,final int kolik){
    	for(int i=1;i<=kolik;i++){
    	     final Panel p=new Panel(new FlowLayout());
	         p.add(new Label("TEST " + i));
	         p.setPreferredSize(new Dimension(100,100));
	         instance.add(p,"TEST " +i);
    	}
    }
     /***************************************************************************
      * TestovacĂ­ metoda.
      */
     public static void test()
     {
    	 final OknoProTesty o=new OknoProTesty();
    	 final CardPanel instance = new CardPanel();
         final CardPanel instance1 = new CardPanel();
         final CardPanel instance2 = new CardPanel();
         
         instance.add(instance1.getAsComponent(),"1");
         instance.add(instance2.getAsComponent(),"2");
         pridej(instance1,10);
         pridej(instance2,2);
         
//         instance1.select("TEST 9");
         o.add(instance.getAsComponent());
         
         o.setVisible(true);
     }
     /** @param args Parametry pĹ™Ă­kazovĂ©ho Ĺ™Ăˇdku - nepouĹľĂ­vanĂ©. */
     public static void main(final String[] args)  {  test();  }
}

