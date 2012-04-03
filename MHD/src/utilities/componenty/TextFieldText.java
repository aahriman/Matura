/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */

package utilities.componenty;


import java.awt.TextComponent;
import java.awt.TextField;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;


/*******************************************************************************
 * Instance třídy {@code TextFieldNumber} představují TextField, 
 * a při stisku vymazávají popisek
 *
 * @author    Vojtěch Sejkora
 * @version   1.00.000
 */
public class TextFieldText extends TextField implements Serializable
{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//== KONSTANTNÍ ATRIBUTY TŘÍDY =================================================
//== PROMĚNNÉ ATRIBUTY TŘÍDY ===================================================
//== STATICKÝ INICIALIZAČNÍ BLOK - STATICKÝ KONSTRUKTOR ========================
//== KONSTANTNÍ ATRIBUTY INSTANCÍ ==============================================
	final String popisek;
//== PROMĚNNÉ ATRIBUTY INSTANCÍ ================================================
//== PŘÍSTUPOVÉ METODY VLASTNOSTÍ TŘÍDY ========================================
//== OSTATNÍ NESOUKROMÉ METODY TŘÍDY ===========================================

//##############################################################################
//== KONSTRUKTORY A TOVÁRNÍ METODY =============================================

    /***************************************************************************
     * vytvoří TextField
     */
    public TextFieldText()
    {
    	this("");
    }
    
    
    /***************************************************************************
     *   vytvoří TextField s nápiskem
     *   @param s        poznámka co chce nastavit za název
     */
   public TextFieldText(String s)
   {
   	this(s,TextField.WIDTH);
   }
   
   /**
    *  vytvoří TextField se zadanou velikostí
    * @param velikost
    */
   public TextFieldText(int velikost) {
	   this("",velikost);
	}
   /***************************************************************************
    *   vytvoří TextField s nápiskem a zadanou velikostí 
    *   @param s               nastavit za název
    *   @param velikost        velikost
    */
	public TextFieldText(String s, int velikost) {
		super(s,velikost);
		this.popisek=s;
		
		nastavPosluchace();
	}
//== ABSTRAKTNÍ METODY =========================================================
//== PŘÍSTUPOVÉ METODY VLASTNOSTÍ INSTANCÍ =====================================
//== OSTATNÍ NESOUKROMÉ METODY INSTANCÍ ========================================
//== SOUKROMÉ A POMOCNÉ METODY TŘÍDY ===========================================
//== SOUKROMÉ A POMOCNÉ METODY INSTANCÍ ========================================
	private void nastavPosluchace(){
		// focus, který vymaže při stisknutí nápisek
		this.addFocusListener(new FocusListener(){
			@Override
			public void focusGained(FocusEvent e){
				((TextComponent)e.getComponent()).setText("");
			}
			
			@Override
			public void focusLost(FocusEvent e){
				TextComponent t=((TextComponent)e.getComponent());
				if(t.getText().trim().equals("") || t.getText().trim().equals(popisek)){
					t.setText(popisek);
				}else
					t.removeFocusListener(this);
			}
		});
	}
	
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
		in.defaultReadObject();
		this.nastavPosluchace();
	}
//== INTERNÍ DATOVÉ TYPY =======================================================
//== TESTOVACÍ METODY A TŘÍDY ==================================================
//
//     /***************************************************************************
//      * Testovací metoda.
//      */
//     public static void test()
//     {
//         TextFieldNumber instance = new TextFieldNumber();
//     }
//     /** @param args Parametry příkazového řádku - nepoužívané. */
//     public static void main(String[] args)  {  test();  }
}
