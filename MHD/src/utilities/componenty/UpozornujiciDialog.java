/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */

package utilities.componenty;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.TextArea;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serializable;

import utilities.componenty.pomocne.DruhDialogu;
import utilities.componenty.pomocne.OknoProTesty;

/*******************************************************************************
 * Instance třídy {@code UpozornujiciDialog} představují ...
 *
 * @author    Vojtěch Sejkora
 * @version   1.00.000
 */
public class UpozornujiciDialog extends Dialog implements Serializable
{
//== KONSTANTNÍ ATRIBUTY TŘÍDY =================================================
	/**	 */
	private static final long serialVersionUID = 3697663906817936626L;
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
    public UpozornujiciDialog(final Window okno,String title,boolean modal,String zprava,DruhDialogu druh)
    {
    	this(okno,title,modal,zprava);
    	Component c=druh.getComponent();
    	
    	
    	this.add(c,BorderLayout.WEST);
    	
    	this.pack();
    	
    }

    
    public UpozornujiciDialog(final Window okno,String title,boolean modal,String zprava){
    	super(okno);
    	
    	TextArea ta=new TextArea(zprava);
    	ta.setEditable(false);
    	
    	this.setModal(modal);
    	this.setTitle(title);
    	
    	this.setLayout(new BorderLayout());
    	this.add(ta,BorderLayout.CENTER);
    	
    	this.addWindowListener(new WindowAdapter(){
    		@Override
    		public void windowClosing(WindowEvent e){
    			skryj();
    		}
    	});
    	
    	this.pack();
    }

//== ABSTRAKTNÍ METODY =========================================================
//== PŘÍSTUPOVÉ METODY VLASTNOSTÍ INSTANCÍ =====================================
//== OSTATNÍ NESOUKROMÉ METODY INSTANCÍ ========================================
    public void zobraz(){
    	this.setVisible(true);
    }
    
    public void skryj(){
    	this.setVisible(false);
    }
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
    	 OknoProTesty o=new OknoProTesty();
    	 o.setVisible(true);
         UpozornujiciDialog instance = new UpozornujiciDialog(o,"test",false,"TEST test TEST test TEST",DruhDialogu.ERROR);
         instance.setLocation(0,0);
         instance.zobraz();
         
         instance = new UpozornujiciDialog(o,"test",false,"TEST test TEST test TEST",DruhDialogu.WARNING);
         instance.setLocation(150,0);
         instance.zobraz();
         
         instance = new UpozornujiciDialog(o,"test",false,"TEST test TEST test TEST");
         instance.setLocation(100,100);
         instance.zobraz();
     }
     /** @param args Parametry příkazového řádku - nepoužívané. */
     public static void main(String[] args)  {  test();  }
}
