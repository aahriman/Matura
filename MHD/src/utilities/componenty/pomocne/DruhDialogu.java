/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */

package utilities.componenty.pomocne;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.Serializable;





/*******************************************************************************
 * Instance výčtového typu {@code DruhDialogu} představují ...
 *
 * @author    Vojtěch Sejkora
 * @version   1.01.000
 */
public enum DruhDialogu implements Serializable
{
//== HODNOTY VÝČTOVÉHO TYPU ====================================================

////=====  NÁPOVĚDA: KONSTRUKTOR S PARAMETRY  ================
     WARNING(){
    	public void nakresli(Graphics g,int x,int y){
    		
    		final int VYKRESLI_OD_KRAJE=VELIKOST_OBRAZKU/4;
    		final int VELIKOST_TECKY=VELIKOST_OBRAZKU/6;
    		final int X_TECKY=VELIKOST_OBRAZKU/2-VELIKOST_TECKY/2;
    		final int Y_TECKY= VELIKOST_OBRAZKU/3*2;
    		// tamavší zelená
    		g.setColor(new Color(39,203,88));
    		
    		g.fillOval(x, y, VELIKOST_OBRAZKU, VELIKOST_OBRAZKU);
    		g.setColor(Color.WHITE);
    		g.fillOval(X_TECKY+x,Y_TECKY+y, VELIKOST_TECKY, VELIKOST_TECKY);
    
    		int [] xPoints={ X_TECKY+VELIKOST_TECKY/2+x,
    							VYKRESLI_OD_KRAJE/2*3+x,
    							VELIKOST_OBRAZKU-VYKRESLI_OD_KRAJE/2*3+x,
    						};	
    		int [] yPoints={Y_TECKY+VELIKOST_TECKY/2+y,
    						VYKRESLI_OD_KRAJE+y,
    						VYKRESLI_OD_KRAJE+y
    						};
    		int nPoints=3;
    		g.fillPolygon(xPoints, yPoints, nPoints);
    	}
     }
     
     ,ERROR(){
    	public void nakresli(Graphics g,int x,int y){
    		final int POSUN_DO_LEVA_LEVY_X=VELIKOST_OBRAZKU/8;
    		final int POSUN_DO_PRAVA_PRAVY_X=VELIKOST_OBRAZKU/8;
    		final int VYKRESLI_OD_KRAJE=VELIKOST_OBRAZKU/4;
    		final int SIRKA_CARY=VELIKOST_OBRAZKU/5;
    		
    		g.setColor(Color.RED);
    		g.fillOval(x, y, VELIKOST_OBRAZKU, VELIKOST_OBRAZKU);
    		g.setColor(Color.WHITE);
    		
    		for(int i=0;i<SIRKA_CARY;i++){
	    		g.drawLine(VYKRESLI_OD_KRAJE+i-POSUN_DO_LEVA_LEVY_X+x, VYKRESLI_OD_KRAJE+y, 
	    				VELIKOST_OBRAZKU-VYKRESLI_OD_KRAJE+i-POSUN_DO_LEVA_LEVY_X+x, VELIKOST_OBRAZKU-VYKRESLI_OD_KRAJE+y);
	    		
	    		g.drawLine(VELIKOST_OBRAZKU-VYKRESLI_OD_KRAJE-i+POSUN_DO_PRAVA_PRAVY_X+x, VYKRESLI_OD_KRAJE+y,
	    				VYKRESLI_OD_KRAJE-i+POSUN_DO_PRAVA_PRAVY_X+x , VELIKOST_OBRAZKU-VYKRESLI_OD_KRAJE+y);
    		}
    		
    	}
     };

//== KONSTANTNÍ ATRIBUTY TŘÍDY =================================================
     private static int VELIKOST_OBRAZKU=100;
//== PROMĚNNÉ ATRIBUTY TŘÍDY ===================================================
//== STATICKÝ INICIALIZAČNÍ BLOK - STATICKÝ KONSTRUKTOR ========================
//== KONSTANTNÍ ATRIBUTY INSTANCÍ ==============================================
//== PROMĚNNÉ ATRIBUTY INSTANCÍ ================================================
//== PŘÍSTUPOVÉ METODY VLASTNOSTÍ TŘÍDY ========================================
//== OSTATNÍ NESOUKROMÉ METODY TŘÍDY ===========================================

//##############################################################################
//== KONSTRUKTORY A TOVÁRNÍ METODY =============================================
     private final Canvas ZNAK; 
    /***************************************************************************
     *
     */
    private DruhDialogu()
    {
    	
    	ZNAK=new Znak(this);
    }


//== ABSTRAKTNÍ METODY =========================================================
    /**
     * @param g   Na co se to mý vykreslit
     * @param x   x-ová souřadnice, odkud se to má vykrslit
     * @param y   y-nová souřadnice odkud se to má vykreslit
     */
    abstract public void nakresli(Graphics g,int x,int y);
//== PŘÍSTUPOVÉ METODY VLASTNOSTÍ INSTANCÍ =====================================
    public Component getComponent(){
    	return ZNAK;	
    }
//== OSTATNÍ NESOUKROMÉ METODY INSTANCÍ ========================================
//== SOUKROMÉ A POMOCNÉ METODY TŘÍDY ===========================================
//== SOUKROMÉ A POMOCNÉ METODY INSTANCÍ ========================================
//== INTERNÍ DATOVÉ TYPY =======================================================
    static class Znak extends Canvas{
    	/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private final DruhDialogu D;
    	Znak(DruhDialogu d){
    		this.D=d;
    		
    		this.repaint();
    	}
    	
    	@Override
    	public void paint(Graphics g){
    		Dimension d=this.getSize();
    		D.nakresli(g,(d.width-VELIKOST_OBRAZKU)/2,(d.height-VELIKOST_OBRAZKU)/2);
    	}
    	
    	@Override
    	public Dimension getPreferredSize(){
    		return new Dimension(VELIKOST_OBRAZKU,VELIKOST_OBRAZKU);
    	}
    	
    	@Override
    	public Dimension getMinimumSize(){
    		return getPreferredSize();
    	}
    }
//== TESTOVACÍ METODY A TŘÍDY ==================================================
//
//     /***************************************************************************
//      * Testovací metoda.
//      */
//     public static void test()
//     {
//     }
//     /** @param args Parametry příkazového řádku - nepoužívané. */
//     public static void main(String[] args) { test(); }
}
