/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */

package utilities.pomocneProgramy;

import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

/*******************************************************************************
 * Pomocí instancí třídy {@code UdalostiTextFieldu} můžete v jedné metodě nastavit
 * stejnou akci pro metodu focusLost(FocusEvent e) a actionPerformed(ActionEvent e)
 *
 * @author    Vojtěch Sejkora
 * @version   1.00.000
 */
public class UdalostiTextFieldu
{
//== KONSTANTNÍ ATRIBUTY TŘÍDY =================================================
//== PROMĚNNÉ ATRIBUTY TŘÍDY ===================================================
//== STATICKÝ INICIALIZAČNÍ BLOK - STATICKÝ KONSTRUKTOR ========================
//== KONSTANTNÍ ATRIBUTY INSTANCÍ ==============================================
	private final UdalostProTextField UDALOST;
//== PROMĚNNÉ ATRIBUTY INSTANCÍ ================================================
	private long aktivovalV=0; 
//== PŘÍSTUPOVÉ METODY VLASTNOSTÍ TŘÍDY ========================================
//== OSTATNÍ NESOUKROMÉ METODY TŘÍDY ===========================================

//##############################################################################
//== KONSTRUKTORY A TOVÁRNÍ METODY =============================================

    /***************************************************************************
     *
     */
    public UdalostiTextFieldu(final UdalostProTextField UDALOST,TextField f)
    {    	
    	this.UDALOST=UDALOST;
    	
    	f.addFocusListener(new FocusAdapter(){
			@Override
			public void focusLost(FocusEvent f){
				cinnost();
			}
		});
		
		f.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				cinnost();
			}
		});	
    }
//== ABSTRAKTNÍ METODY =========================================================
//== PŘÍSTUPOVÉ METODY VLASTNOSTÍ INSTANCÍ =====================================
//== OSTATNÍ NESOUKROMÉ METODY INSTANCÍ =======================================
//== SOUKROMÉ A POMOCNÉ METODY TŘÍDY ===========================================
//== SOUKROMÉ A POMOCNÉ METODY INSTANCÍ ========================================
    private void cinnost(){
    	long cas=System.currentTimeMillis();
		if((cas-aktivovalV)>1000){
			aktivovalV=cas;
			UDALOST.udalost();
		}
    }
//== INTERNÍ DATOVÉ TYPY =======================================================
//== TESTOVACÍ METODY A TŘÍDY ==================================================
//
//     /***************************************************************************
//      * Testovací metoda.
//      */
//     public static void test()
//     {
//         UDALOSTiTextFieldu instance = new UDALOSTiTextFieldu();
//     }
//     /** @param args Parametry příkazového řádku - nepoužívané. */
//     public static void main(String[] args)  {  test();  }
}
