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

import utilities.componenty.pomocne.DruhDialogu;
import utilities.pomocneProgramy.UdalostProTextField;
import utilities.pomocneProgramy.UdalostiTextFieldu;


/*******************************************************************************
 * Instance třídy {@code TextFieldNumber} představují TextField, 
 * ale mají přidanou kontrolu jestli opravdu zapsal číslo 
 *
 * @author    Vojtěch Sejkora
 * @version   1.00.000
 */
public class TextFieldNumber extends TextField implements Serializable
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
	private int minimum=Integer.MIN_VALUE;
	private int maximum=Integer.MAX_VALUE;
	
	private String jednotka="";
	private int defaultniHodnota=0;
//== PŘÍSTUPOVÉ METODY VLASTNOSTÍ TŘÍDY ========================================
//== OSTATNÍ NESOUKROMÉ METODY TŘÍDY ===========================================

//##############################################################################
//== KONSTRUKTORY A TOVÁRNÍ METODY =============================================

    /***************************************************************************
     * vytvoří TextField s kontrolou, jestli zadal jen číslo
     */
    public TextFieldNumber()
    {
    	this("");
    }
    
    /***************************************************************************
     * vytvoří TextField s kontrolou, jestli zadal jen číslo, dálí editable dle proměnné
     */
    public TextFieldNumber(boolean editable)
    {
    	this("");
    	this.setEditable(editable);
    }
    
    /***************************************************************************
     * vytvoří TextField s kontrolou, jestli zadal jen číslo, dálí editable dle proměnné
     * a dá tam i popisek
     */
    public TextFieldNumber(String s,boolean editable)
    {
    	this(s);
    	this.setEditable(editable);
    }
    
    /***************************************************************************
     * vytvoří TextField s kontrolou, jestli zadal jen číslo
     * 
     * @param min      jakou minimální hodnotu ještě může zapsat
     * @param max      jakou maximální hodnotu ještě může zapsat
     */
    public TextFieldNumber(int min, int max)
    {
    	this("",min,max);
    }
    
    /***************************************************************************
     *   vytvoří TextField s nápiskem a s kontrolou, jestli zadal jen číslo 
     *   @param s        poznámka co chce nastavit za název
     */
   public TextFieldNumber(String s)
   {
	   super(s);
		this.popisek=s;
		
		new UdalostiTextFieldu(new UdalostProTextField(){
			@Override
			public void udalost(){
				teloPosluchace();
			}
		},this);
		
		// focus, který vymaže při stisknutí nápisek
			this.nastavPosluchace();
   }
   
   /***************************************************************************
    *   vytvoří TextField s nápiskem a s kontrolou, jestli zadal jen číslo 
    *   @param s        poznámka co chce nastavit za název
    *   @param min      jakou minimální hodnotu ještě může zapsat
    *   @param max      jakou maximální hodnotu ještě může zapsat
    */
   public TextFieldNumber(String s,int min, int max)
   {
		this(s);
		this.maximum=max;
		this.minimum=min;
   }


   /***************************************************************************
    *   vytvoří TextField s nápiskem, novou velikostí a s kontrolou, jestli zadal jen číslo 
    *   @param s               nastavit za název
    *   @param velikost        velikost
    */
	public TextFieldNumber(String s, int velikost) {
		super(s,velikost);
		this.popisek=s;
		
		new UdalostiTextFieldu(new UdalostProTextField(){
			@Override
			public void udalost(){
				teloPosluchace();
			}
		},this);
		
		// focus, který vymaže při stisknutí nápisek
		this.nastavPosluchace();
	}

	/***************************************************************************
	    *   vytvoří TextField s nápiskem, novou velikostí a s kontrolou, jestli zadal jen číslo 
	    *   @param s               nastavit za název
	    *   @param velikost        velikost
	    *   @param min      jakou minimální hodnotu ještě může zapsat
	    *   @param max      jakou maximální hodnotu ještě může zapsat
	    */
	public TextFieldNumber(String s, int velikost,int min,int max) {
		this(s,velikost);
		this.minimum=min;
		this.maximum=max;
	}
	
	public TextFieldNumber(String s,String jednotka, int velikost,int min,int max) {
		this(s,velikost,min,max);
		this.jednotka=jednotka;
	}
	
	public TextFieldNumber(String s,String jednotka,int min,int max) {
		this(s,min,max);
		this.jednotka=jednotka;
	}
	
	public TextFieldNumber(String s,String jednotka, int velikost,int min,int max,int defaultniHodnota) {
		this(s,velikost,min,max);
		this.jednotka=jednotka;
		this.defaultniHodnota=defaultniHodnota;
	}

//== ABSTRAKTNÍ METODY =========================================================
//== PŘÍSTUPOVÉ METODY VLASTNOSTÍ INSTANCÍ =====================================
	public void setMinimumValue(int minimum){
		this.minimum=minimum;
	}
	
	public void setMaximumValue(int maximum){
		this.maximum=maximum;
	}
	
	public void setDefaultValue(int defaultni){
		this.defaultniHodnota=defaultni;
	}
	
	public int getValue(){
		String text=this.getText();
		if(text.equals(popisek) || !obsahujeJednotku(text))
			return defaultniHodnota;
		else if(text.length()>jednotka.length())
			return Integer.parseInt(text.substring(0,text.length()-jednotka.length()));
		else
			return defaultniHodnota;
		
	}
//== PŘEKRÝVANÉ METODY INSTANCÍ ================================================
	@Override
	public void setText(String text){
		if(text.equals("") || text.equals(popisek))
			super.setText(text);
		else{
			if(obsahujeJednotku(text))
				super.setText(text);
			else
				super.setText(text+jednotka);
		}
	}
	
	public void setText(int i){
		this.setText("" + i);
	}

//== OSTATNÍ NESOUKROMÉ METODY INSTANCÍ ========================================
//== SOUKROMÉ A POMOCNÉ METODY TŘÍDY ===========================================
//== SOUKROMÉ A POMOCNÉ METODY INSTANCÍ ========================================
	private void nastavPosluchace(){
		this.addFocusListener(new FocusListener(){
			@Override
			public void focusGained(FocusEvent e){
				TextComponent t=((TextComponent)e.getComponent());
				if(t.getText().equals(popisek)){
					t.setText("");
				}else{
					String text=t.getText();
					t.select(0, text.length()-jednotka.length());
				}		
			}
			
			@Override
			public void focusLost(FocusEvent e){
				TextComponent t=((TextComponent)e.getComponent());
				if(t.getText().trim().equals("") || t.getText().trim().equals(popisek)){
					t.setText(popisek);
				}
			}
		});
	}
	private void teloPosluchace(){
		String text=getText();
		
		if(text.equals("") || text.equals(popisek))
			return;
		
		String regex="[1234567890-"+jednotka+"]";
		String[] t=text.split(regex);
			
		for(int i=0;i<t.length;i++){
			if(t[i].length()!=0){
				UpozornujiciDialog u=new UpozornujiciDialog(null,"špatně zadaný vstup",true," do tohoto panelu můžete zadávat pouze číslice a již žádné jiné znaky",DruhDialogu.ERROR);
				u.setVisible(true);
				super.setText(popisek);
				return;
			}	
		}
		
		if(!obsahujeJednotku(getText()))
			setText(getText()+jednotka);
		
		if(getValue()<minimum){
			UpozornujiciDialog u=new UpozornujiciDialog(null,"špatně zadaný vstup",true," přesáhli jste povolenou hodnotu - minimální hodnotu můžete zadat "+ minimum+ "/n Proto nastavuji na minimální hodnotu." ,DruhDialogu.ERROR);
			u.setVisible(true);
			setText(""+minimum+jednotka);
		}else		if(getValue()>maximum){
			UpozornujiciDialog u=new UpozornujiciDialog(null,"špatně zadaný vstup",true," přesáhli jste povolenou hodnotu - maximální hodnotu můžete zadat "+ maximum + "/n Proto nastavuji na maximální hodnotu." ,DruhDialogu.ERROR);
			u.setVisible(true);
			setText(""+maximum+jednotka);
		}
	}
	
	private boolean obsahujeJednotku(String text){
		if(text.length()<jednotka.length())
			return false;
		else if(text.substring(text.length()-jednotka.length()).equals(jednotka)){
				return true;
			}
		return false;
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
