/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */

package utilities.pomocneProgramy.pomocne;

import java.awt.Graphics;





/*******************************************************************************
 * Instance třídy {@code VyreslovatelTagu} představují ...
 *
 * @author    Vojtěch Sejkora
 * @version   1.00.000
 */
public abstract class AVyreslovatelTagu
{
//== KONSTANTNÍ ATRIBUTY TŘÍDY =================================================
//== PROMĚNNÉ ATRIBUTY TŘÍDY ===================================================
//== STATICKÝ INICIALIZAČNÍ BLOK - STATICKÝ KONSTRUKTOR ========================
//== KONSTANTNÍ ATRIBUTY INSTANCÍ ==============================================
//== PROMĚNNÉ ATRIBUTY INSTANCÍ ================================================
//== PŘÍSTUPOVÉ METODY VLASTNOSTÍ TŘÍDY ========================================
//== OSTATNÍ NESOUKROMÉ METODY TŘÍDY ===========================================

//##############################################################################
//== KONSTRUKTORY A TOVÁRNÍ METODY =============================================

    /***************************************************************************
     *
     */
    public AVyreslovatelTagu()
    {
    }



//== ABSTRAKTNÍ METODY =========================================================
    public abstract int vykresli(String text,Graphics g, int zacniKreslitOd);
    public abstract int getSirka();
//== PŘÍSTUPOVÉ METODY VLASTNOSTÍ INSTANCÍ =====================================
//== OSTATNÍ NESOUKROMÉ METODY INSTANCÍ ========================================
//== SOUKROMÉ A POMOCNÉ METODY TŘÍDY ===========================================
    
    protected static void vykresliNaStred(Graphics g,int sirka,int vyska,String text,int sirkaVykreslovaciOblasti){
    	int kresliX=(sirkaVykreslovaciOblasti-g.getFontMetrics().stringWidth(text))/2+sirka;
    	int kresliY=g.getFontMetrics().getAscent()+vyska;
    	g.drawString(text, kresliX, kresliY);
    }
    
    protected static void vykresliDoprava(Graphics g,int sirka,int vyska,String text,int sirkaVykreslovaciOblasti){
    	int kresliX=(sirkaVykreslovaciOblasti-g.getFontMetrics().stringWidth(text))+sirka;
    	int kresliY=g.getFontMetrics().getAscent()+vyska;
    	g.drawString(text, kresliX, kresliY);
    }
//== SOUKROMÉ A POMOCNÉ METODY INSTANCÍ ========================================
//== INTERNÍ DATOVÉ TYPY =======================================================
//== TESTOVACÍ METODY A TŘÍDY ==================================================
//
//     /***************************************************************************
//      * Testovací metoda.
//      */
//     public static void test()
//     {
//         VyreslovatelTagu instance = new VyreslovatelTagu();
//     }
//     /** @param args Parametry příkazového řádku - nepoužívané. */
//     public static void main(String[] args) { test(); }
}