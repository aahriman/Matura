/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */

package utilities.componenty;

/**************************************************************
 * Instance třídy {@code Tabulka} představují tabulku, do která se dají vkládat 
 * componenty, a tato tabulka se vytváří v závislosti na počtu komponent
 * 
 * může být vkládána jen do okna (Frame)
 *
 * @author    Vojtěch Sejkora
 * @version   2.01.000
 */
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.MouseInfo;
import java.awt.Panel;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import utilities.componenty.pomocne.OknoProTesty;

public class Tabulka{
	//== KONSTANTNÍ ATRIBUTY TŘÍDY =================================================
	//== PROMĚNNÉ ATRIBUTY TŘÍDY ===================================================
	//== STATICKÝ INICIALIZAČNÍ BLOK - STATICKÝ KONSTRUKTOR ========================
	//== KONSTANTNÍ ATRIBUTY INSTANCÍ ==============================================
	private final Panel cele=new Panel();
	//== PROMĚNNÉ ATRIBUTY INSTANCÍ ================================================
	/** Seznam,který v sobě má odkazy componentz v 1.sloupci */
	private ArrayList<Component> componentyVeSloupci = new ArrayList<Component>();

	/** Seznam komponent, současného řádku */
	private ArrayList<Component> componentyVRadku = new ArrayList<Component>();

	/** Předchozí vložená komponenta */
	private Component predchoziVlozena;

	/** GridBagConstraints panelu */
	private GridBagConstraints gbc = new GridBagConstraints();

	/** Layout aktualnihoPanelu*/
	private GridBagLayout gbl = new GridBagLayout();

	/** GridBagConstraints tabulky */
	private GridBagConstraints gbcTAB = new GridBagConstraints();

	/** Layout tabulky*/
	private GridBagLayout gblTAB = new GridBagLayout();;

	/**Panel do kterého se zrovna vkládá*/
	private Panel aktualniPanel;

	//== PŘÍSTUPOVÉ METODY VLASTNOSTÍ TŘÍDY ========================================
	//== OSTATNÍ NESOUKROMÉ METODY TŘÍDY ===========================================

	//##############################################################################
	//== KONSTRUKTORY A TOVÁRNÍ METODY =============================================

	/***************************************************************************
	 * Konstruktor vytvoří panel, do kterého se daj vkládat komponenty 
	 * a jejich velikost se dá pomocí přepážek měnit 
	 * 
	 * @param okno-Frame    okno, do kterého je tato tabulka přidávána    
	 */
	public Tabulka() {
		super();

		aktualniPanel = new Panel(gbl);
		predchoziVlozena = null;
		cele.setLayout(gblTAB);
		gbcTAB.fill = GridBagConstraints.BOTH;
		gbc.fill = GridBagConstraints.BOTH;
		// chci aby se 1. radek roztahoval
		gbc.weighty = 1.0;
		gbcTAB.weighty = 1.0;
		gbcTAB.gridy = 1;
		gblTAB.setConstraints(aktualniPanel, gbcTAB);
		cele.add(aktualniPanel);
	}

	//== ABSTRAKTNÍ METODY =========================================================
	//== PŘÍSTUPOVÉ METODY VLASTNOSTÍ INSTANCÍ =====================================
	public Component getAsComponent(){
		return cele;
	}
	
	//== OSTATNÍ NESOUKROMÉ METODY INSTANCÍ ========================================
	public void validete(){
		cele.validate();
	}
	
	public void ukonci(){	
		Prepazka p=new Prepazka(EOrientace.SIRKA, aktualniPanel);
		p.setBackground(Color.WHITE);
		pridejPrepazku(p);
	}
	
	/**
	 * přidá komponentu vedle naposledy přidané
	 * pokud ještě žádná komponenta v tabulce není tak ji dá na první místo
	 * 
	 * @param c-Component    přídávaná komponenta
	 */
	public void pridejVedle(Component c) {

		if (predchoziVlozena == null) {
			componentyVeSloupci.add(aktualniPanel);
		} else {
			// pokud to je již 2. komponenta, musím přidat přepášku
			pridejPrepazkuVedle(c);
		}
		nastavProVedle();

		gbl.setConstraints(c, gbc);
		pridejDoPanelu(c);
		predchoziVlozena = c;
	}
	
	public void pridejVedle(Component c,boolean sPrepazkou) {
		if(sPrepazkou){
			pridejVedle(c);
			return;
		}
		
		nastavProVedle();

		gbl.setConstraints(c, gbc);
		pridejDoPanelu(c);
		predchoziVlozena = c;
	}

	/**
	 * přidá komponentu na další řádek
	 * a zařídí aby se příští komponenta dávala vedle této
	 * nastaví i aby se tyto komponenty na tomto řádku mohli roztahovat
	 * 
	 * @param c-Component    přídávaná komponenta
	 */
	public void pridejNaDalsiRadek(Component c) {
		// protože další řádek je 2. musím přidat přepášku
		pridejPrepazkuPod();
		// pak vynuluji vše co mi zahltil minulý řádek
		predchoziVlozena = null;
		componentyVRadku.removeAll(componentyVRadku);
		
		pridejVedle(c);
	}
	
	/**
	 * přidá komponentu na další řádek
	 * a zařídí aby se příští komponenta dávala vedle této
	 * a dle výběru nastaví přidání přepášky a tedy i roztahování dané komponenty
	 * 
	 * @param c        - Component    přídávaná komponenta
	 * @param prepazka - boolean      přidání přepíšku: true  - přidá
	 * 													false - nepřidá 
	 */
	public void pridejNaDalsiRadek(Component c,boolean prepazka) {
		if(prepazka)
			pridejNaDalsiRadek(c);
		else{
			// tento panel je pro přepášku
			pridejPanel();
			gbc.gridx = 1;
			gbc.weightx=1.0;
			// u této komponenty je nežádoucí aby se rozšiřovala dolů
			gbc.weighty = 0.0;
			// pak vynuluji vše co mi zahltil minulý řádek
			predchoziVlozena = null;
			componentyVRadku.removeAll(componentyVRadku);
			pridejDoPanelu(c);
		}
	}
	//== SOUKROMÉ A POMOCNÉ METODY TŘÍDY ===========================================
	//== SOUKROMÉ A POMOCNÉ METODY INSTANCÍ ========================================
	
	/**
	 * tuto metodu nepoužívat místo nich používat: pridejVedle
	 *                                             pridejPosledni
	 * 
	 */

	private Component add(Component c) {
		componentyVRadku.add(c);
		return cele.add(c);
	}
	
	/**
	 * přidá přepášku, vedle komponenty
	 * 
	 * @param c-Component     componenta, před kterou se dá přepáška
	 */
	private void pridejPrepazkuVedle(Component c) {
		nastavProVedle();
		final Prepazka p = new Prepazka(c);

		gbl.setConstraints(p, gbc);
		new ZmenaVelikosti(c, predchoziVlozena, p);
		
		pridejDoPanelu(p);
	}

	/**
	 * přidá přepášku, na nový řádek
	 */
	private void pridejPrepazkuPod() {
		Prepazka p = new Prepazka(EOrientace.SIRKA, aktualniPanel);
		
		// po přidání nového panelu bude už tento panel starý
		Panel predchoziPanel=aktualniPanel;
		
		pridejPrepazku(p);
		
		pridejPanel();
		new ZmenaVelikosti(aktualniPanel,predchoziPanel, p);
		
	}
	
	private void pridejPrepazku(Prepazka p){
		// tento panel je pro přepášku
		pridejPanel();
		gbc.gridx = 1;
		// u této přepášky je nežádoucí aby se rozšiřovala dolů
		gbc.weighty = 0.0;

		pridejDoPanelu(p);
		// ale komponenty na dalším řádku se do velikosti panelu musí roztahovat
		gbc.weighty = 1.0;
	}

	/**
	 * nastaví gbc pro přidání vedle
	 */
	private void nastavProVedle() {
		if (predchoziVlozena == null) {
			// první komponeta v řádku se rozhatuje
			gbc.weightx = 1.0;
			gbc.gridx = 1;
		} else {
			// jen první komponenta na řádku má tu výsadu, že se může roztahovat dostrany
			gbc.weightx = 0.0;
			gbc.gridx++;
		}
	}

	/**
	 * přidá componentu do panelu
	 * 
	 * @param c        Component,kterou chcete přidat do panelu
	 */
	private void pridejDoPanelu(Component c) {
		gbl.setConstraints(c, gbc);
		aktualniPanel.add(c);
	}

	/**
	 * dá nový panel na další řádek a naastaví ho jako aktuální
	 */
	private void pridejPanel() {
		gbcTAB.weighty = 0.0;
		gbcTAB.weightx = 1.0;
		gbcTAB.gridy++;
		aktualniPanel = new Panel(gbl);
		gblTAB.setConstraints(aktualniPanel, gbcTAB);
		this.add(aktualniPanel);
	}
	
	//== INTERNÍ DATOVÉ TYPY =======================================================
	/** Přidává vlastnost přepážce měnit velikost component pomocí tažení */
	private class ZmenaVelikosti{
		/**	Prepážka, která je nynější */
		private final Prepazka SOUCASNA_PREPAZKA;
		
		private final Component soucasna;
		private final Component predchozi;
		/** Pomocná, kde byla komponenta Prepážka, když začal táhnout */
		private int zacalNaX;
		
		/** Pomocná, kde byla komponenta Prepážka, když začal táhnout */
		private int zacalNaY;
		
		/** Pozice, na kterém místě byla přepážka, když začal s posunováním*/
		Point poziceSoucasne;
		
		private int ipadxSoucasnaStart;
		private int ipadxPredchoziStart;

		private int ipadySoucasnaStart;
		private int ipadyPredchoziStart;

		private long pocCasTazeni;
		ZmenaVelikosti(Component c, Component predchozi,
				final Prepazka p) {
			
			this.soucasna = c;
			this.predchozi = predchozi;
			this.SOUCASNA_PREPAZKA=p;
			
			
			
			if(p.o==EOrientace.VYSKA)
				p.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
			else
				p.setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
			
			aktivovaniListeneru(p);
			//nastavNove();
		}
		
		private void aktivovaniListeneru(Prepazka p) {
			p.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					poziceSoucasne = SOUCASNA_PREPAZKA.getLocation();
                    Point mistoMisi = MouseInfo.getPointerInfo().getLocation();
                    zacalNaX = mistoMisi.x;
                    zacalNaY = mistoMisi.y;
                    GridBagConstraints gbc = gbl.getConstraints(soucasna);
                    ipadxSoucasnaStart = gbc.ipadx;
                    gbc = gbl.getConstraints(predchozi);
                    ipadxPredchoziStart = gbc.ipadx;
                    gbc = gblTAB.getConstraints(soucasna);
                    ipadySoucasnaStart = gbc.ipady;
                    gbc = gblTAB.getConstraints(predchozi);
                    ipadyPredchoziStart = gbc.ipady;		
				}
			});
			
			

			p.addMouseMotionListener(new MouseMotionAdapter() {
				@Override
				public void mouseDragged(MouseEvent e) {
					if(pocCasTazeni+80>System.currentTimeMillis())
						return;
					
					if(SOUCASNA_PREPAZKA.o == EOrientace.VYSKA){
                        int xSoucasne = zacalNaX - MouseInfo.getPointerInfo().getLocation().x;
                        posunutiSoucasne(xSoucasne, 0);
                        nastavVelikosti(xSoucasne, 0);
                    } else{
                        int ySoucasne = zacalNaY - MouseInfo.getPointerInfo().getLocation().y;
                        posunutiSoucasne(0, ySoucasne);
                        nastavVelikosti(0, ySoucasne);
                    }
						pocCasTazeni=System.currentTimeMillis();
				}
			});
		}

		private void nastavVelikosti(int ox, int oy)
        {
//			if(nove){
//				nastavVelikostiNove(ox, oy);
//			}else
				if(ox != 0)
            {
                GridBagConstraints gbc = gbl.getConstraints(predchozi);
                gbc.ipadx = ipadxPredchoziStart - ox;
                gbl.setConstraints(predchozi, gbc);
                gbc = gbl.getConstraints(soucasna);
                gbc.ipadx = ipadxSoucasnaStart + ox;
                gbl.setConstraints(soucasna, gbc);
            } else
            {
                Tabulka.this.gbc = gblTAB.getConstraints(predchozi);
                Tabulka.this.gbc.ipady = ipadyPredchoziStart - oy;
                gblTAB.setConstraints(predchozi, Tabulka.this.gbc);
                Tabulka.this.gbc = gblTAB.getConstraints(soucasna);
                Tabulka.this.gbc.ipady = ipadySoucasnaStart + oy;
                gblTAB.setConstraints(soucasna, Tabulka.this.gbc);
            }
            cele.validate();
        }

		/**
		 * posouvám přepážku v závislosti na tažení myši
		 * 
		 * @param o      číslo, o kolik se posunula přepážka doleva pro kladné o do prava pro záporné o
		 */
		private void posunutiSoucasne(int ox,int oy) {
			SOUCASNA_PREPAZKA.setLocation(poziceSoucasne.x-ox,poziceSoucasne.y-oy);
		}
	}
	
	enum EOrientace {
		VYSKA, SIRKA
	}

	class Prepazka extends Canvas {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		/**Defaultní šířka*/
		private static final int sirkaDEF = 5;
		/**Defaultní výška*/
		private static final int vyskaDEF = 5;

		/** Componenta, která je vedle*/
		// pokud je orientace na šířku, tak se musí vložit všechny komponenty
		private final Component[] c;

		/**Orientace přepášky*/
		private final EOrientace o;

		/** sirka a vyska prepazky*/
		private int sirka = Prepazka.sirkaDEF, vyska = Prepazka.vyskaDEF;

		protected Prepazka(Component c) {
			this(EOrientace.VYSKA, c);
		}

		protected Prepazka(EOrientace o, Component... c) {
			this.o = o;
			this.c = c;
			this.setBackground(Color.BLACK);
		}

		/** Zjisti sirky vsech komponent */
		private int getSirky() {
			int sirka = 0;

			for (int i = 0; i < c.length; i++) {
				Dimension d = c[i].getSize();
				sirka += d.getWidth();
			}

			return sirka;
		}

		public void nastaveniRozmeru() {
			if (o == EOrientace.VYSKA) {
				vyska = (int) c[0].getSize().getHeight();
			} else {
				sirka = getSirky();
			}

		}

		@Override
		public Dimension getPreferredSize() {
			if (sirka < this.getMinimumSize().getWidth()
					|| vyska < this.getMinimumSize().getHeight())
				return this.getMinimumSize();
			else
				return new Dimension(sirka, vyska);
		}

		@Override
		public Dimension getMinimumSize() {
			if (o == EOrientace.VYSKA)
				return new Dimension(Prepazka.sirkaDEF, (int) c[0]
						.getPreferredSize().getHeight());
			else
				return new Dimension(this.getSirky(), vyskaDEF);
		}

		@Override
		public String toString() {
			return o + " velikost [" + getPreferredSize().getWidth() + ";"
					+ getPreferredSize().getHeight() + "]";
		}
	}

	//== TESTOVACÍ METODY A TŘÍDY ==================================================
	//
	/***************************************************************************
	 * Testovací metoda.
	 */

	public static void test() {

		OknoProTesty o = new OknoProTesty();

		Tabulka instance = new Tabulka();
		instance.pridejVedle(new java.awt.Label("1"));
		instance.pridejNaDalsiRadek(new Label("konec radku"));
		instance.pridejVedle(new Label("3"));
		instance.pridejVedle(new Label("4"));
		instance.pridejVedle(new Label("1"));
		instance.pridejVedle(new Label("2"));
		instance.pridejVedle(new Label("3"));
		instance.pridejVedle(new Label("konec radku"));

		o.add(instance.getAsComponent());
		o.setVisible(true);
	}
	      /** @param args Parametry příkazového řádku - nepoužívané. */
	      public static void main(String[] args)  {  
	    	  test();
	     
	      }
}
