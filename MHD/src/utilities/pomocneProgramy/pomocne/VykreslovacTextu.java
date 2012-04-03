package utilities.pomocneProgramy.pomocne;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.ScrollPane;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import utilities.componenty.pomocne.OknoProTesty;

public class VykreslovacTextu extends AVyreslovatelTagu{
	 
	private String text="";
	private PrapravkaPoliStringAFont prepravka;
	private int odkudKresli;
	static private final Font DEFAULT_FONT=Tagy.getTypeFace(new String[]{});
	static private final Color DEFAULT_COLOR=Tagy.getColorOfFont(new String[]{});
	
	public VykreslovacTextu(){	}
	
	private PrapravkaPoliStringAFont parsovaniTagu(String text){

		ArrayList<PrepravkaFontColor> mapa1=new ArrayList<PrepravkaFontColor>();
		ArrayList<String> 			  mapa2=new ArrayList<String>();
				
		int kon=0;
		int pocatekTagu =text.indexOf('{');
		int konecTagu   =text.indexOf('}');
		
		ArrayList<String> tags= new ArrayList<String>();
		if(pocatekTagu>-1){
			delicTextuDleRadku(mapa1,mapa2,text.substring(0,pocatekTagu),new PrepravkaFontColor(DEFAULT_FONT,DEFAULT_COLOR));
		}
		
		while(pocatekTagu!=-1 && konecTagu!= -1){
			kon=konecTagu;
	
				int zacatekSlova=konecTagu;
			
				
				while((zacatekSlova+1)<text.length() && text.charAt(zacatekSlova+1)=='{'){
					zacatekSlova=text.indexOf('}',zacatekSlova+1);
				}
				
				String [] tag2=text.substring(pocatekTagu, zacatekSlova).split("}");
				for(String s:tag2)
					tags.add(s);
				
				int konecSlova=text.indexOf('{',zacatekSlova);
				if(konecSlova==-1)
					konecSlova=text.length();
				
				String a=text.substring(zacatekSlova+1,konecSlova);
				String [] tagy=tags.toArray(new String[0]);
				PrepravkaFontColor prepravka=new PrepravkaFontColor(Tagy.getTypeFace(tagy),Tagy.getColorOfFont(tagy));
				
				delicTextuDleRadku(mapa1,mapa2,a,prepravka);
				
			konecTagu=konecSlova;
			String posledniTag;
					
				do{
					konecTagu   =text.indexOf("}",konecTagu+1);
					pocatekTagu =text.lastIndexOf("{",konecTagu);
					
					if(pocatekTagu!=-1 && konecTagu!= -1){
						posledniTag=text.substring(pocatekTagu,konecTagu+1);
			
						if(posledniTag.charAt(1)=='/'){ 
							tags.remove(Tagy.CHAR_OF_START_OF_TAG+posledniTag.substring(2,posledniTag.length()-1));
						}else
							break;
					}else
						break;
						
	
					if((konecTagu+1)==text.length())
						break;
				}while(text.charAt(konecTagu+1)==Tagy.CHAR_OF_START_OF_TAG);
					
			}

		if(kon<text.length()){
			delicTextuDleRadku(mapa1,mapa2,text.substring(text.lastIndexOf("}")+1),new PrepravkaFontColor(DEFAULT_FONT,DEFAULT_COLOR));
		}
		
		return new PrapravkaPoliStringAFont(mapa2.toArray(new String[0]),mapa1.toArray(new PrepravkaFontColor[0]));
	}
	
	private void delicTextuDleRadku(List<PrepravkaFontColor> mapa1,List<String> mapa2,String text,PrepravkaFontColor prepravka){

		int novyRadek=0;
		if((novyRadek=text.indexOf('\n'))!=-1){
			// slovo před koncem řádku i to slovo na dalším řádku mají stejný formát
			mapa2.add(text.substring(0,novyRadek+1));
			mapa1.add(prepravka);
			text=text.substring(novyRadek+1);
			
			while((novyRadek=text.indexOf('\n'))!=-1){
				mapa2.add(text.substring(0,novyRadek+1));
				mapa1.add(prepravka);
				text=text.substring(novyRadek+1);
			}
			
		}
		
			mapa2.add(text);
			mapa1.add(prepravka);
		
	}
	
	
	private int radek=0;
	private int nejvyssiFont=0;
	private int sirkaNaRadku=0;
	private int sirka=10;
	private int vyska=50;
	private ArrayList<Integer> vyskaRadku=new ArrayList<Integer>();
	private ArrayList<String[]> jedenRadekTextu=new ArrayList<String[]>();
	private String minulyText="";

	private final ScrollPane sp=new ScrollPane(ScrollPane.SCROLLBARS_NEVER);
	private static final long serialVersionUID = 1L;
		
			
			private void kresli(Graphics g,PrapravkaPoliStringAFont prepravka){
				sirkaNaRadku=0;
				radek=0;
				int k=0;
				for(Iterator<String[]> it=jedenRadekTextu.iterator();it.hasNext();){		
					String[] texty2=it.next();

					sirkaNaRadku=0;
					for(int j=0;j<texty2.length;j++){
						PrepravkaFontColor tags=prepravka.pfc[k++];
						paint2(g, texty2[j], tags.f, tags.c);
					}
					radek++;
				}
			}
			
			/**
			 * 
			 * @param g
			 */
			private void paintClassic(Graphics g){
				sirkaNaRadku=0;	
						
				if(text.length()==0)
					return;

					if(!minulyText.equals(text)){
						prepravka=parsovaniTagu(text);
						
						String [] texty=prepravka.s;
						vyska=0;
						sirka=0;
						vyskaRadku=new ArrayList<Integer>();
						jedenRadekTextu=new ArrayList<String[]>();
						int zkoncilJsemNa=0;

							while(zkoncilJsemNa<texty.length){
								zkoncilJsemNa=nastavNaKresleni(g,texty,prepravka,zkoncilJsemNa);
							}

							sp  .setSize(sirka+30, vyska+30);
					}
					
					kresli(g, prepravka);
				
				minulyText=text;
			}
		
		public Dimension getSize(){
			return new Dimension(sirka,vyska);
		}
		
		private void paint2(Graphics g,String text,Font f,Color c){	
			g.setColor(c);
			g.setFont(f);
			g.drawString(text, sirkaNaRadku, vyskaRadku.get(radek)-g.getFontMetrics().getDescent()+odkudKresli);
			sirkaNaRadku+=g.getFontMetrics().stringWidth(text);
		}
		
		private void zjistiSirkyTextu(Graphics g,String text,Font f){
			sirkaNaRadku+=(int)g.getFontMetrics(f).getStringBounds(text, g).getWidth();
		}
		
		private int nastavNaKresleni(Graphics g,String [] texty,PrapravkaPoliStringAFont prepravka,int zkoncilJsemNa){
			int i;

				nejvyssiFont=0;
				ArrayList<String> radekTextu=new ArrayList<String>();
				for(i=zkoncilJsemNa;i<texty.length-1;i++)
					if(texty[i].indexOf('\n')!=-1)
						break;
	
				for(int j=zkoncilJsemNa;j<=i;j++){
					PrepravkaFontColor tags=prepravka.pfc[j];
					
					if(tags.f.getSize()>nejvyssiFont){
						g.setFont(tags.f);
						nejvyssiFont=g.getFontMetrics().getHeight();
					}
				}
			
				int j=zkoncilJsemNa;
				
				for(;j<=i;j++){
					PrepravkaFontColor tags=prepravka.pfc[j];				
					zjistiSirkyTextu(g, texty[j], tags.f);
					radekTextu.add(texty[j]);
				}
				
				zkoncilJsemNa=j;
				
				vyska+=nejvyssiFont;
				vyskaRadku.add(vyska);
				if(sirka<sirkaNaRadku+5){
					sirka=sirkaNaRadku;
				}
				jedenRadekTextu.add(radekTextu.toArray(new String[0]));
				sirkaNaRadku=0;
			
			return zkoncilJsemNa;
		}

		@Override
		public int vykresli(String text, Graphics g, int zacniKreslitOd) {
			this.text=text;
			
			this.odkudKresli=zacniKreslitOd;
			paintClassic(g);
			
			return zacniKreslitOd+vyska;
		}
		
		 public int getSirka(){
			 if(sirka<300){
		    	return sirka;
			 }else
				 return 300;
		  }
		 
	public static void main(String[] args) {
		final VykreslovacTextu instance = new VykreslovacTextu();
        Canvas c=new Canvas(){
       	 /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
       	 public void paint(Graphics g){
       		 String text="{color(255,0,0)}Válečník může zastrašovat skupinu, jen proti které družina bouje jako celek. Tedy si nemůže vybrat jen jednu oběť.{/color(255,0,0)}{/b}\n"+
"Pokud zastrašuje skupinu, pak se pravděpodobnost z tabulky {b}dělí počtem protivníků v t0 skupině{/b}.\n"+
"Zastrašování se musí oznámit PJ. Zastrašování se bere jako útok.\n";
       		int kresli= instance.vykresli(text, g, 0);
       		instance.vykresli(text, g, kresli);
       	 }
        };
        
        OknoProTesty o=new OknoProTesty();
        o.add(c);
        o.pack();
        o.setSize(100,100);
        o.setVisible(true);
	}
}

class PrepravkaFontColor{
	public final Font f;
	public final Color c;
	
	public PrepravkaFontColor(Font f,Color c){
		this.f=f;
		this.c=c;
	}
}

class PrapravkaPoliStringAFont{
	public final String[] s;
	public final PrepravkaFontColor[] pfc;
	
	public PrapravkaPoliStringAFont(String [] s,PrepravkaFontColor[] pfc){
		this.s=s;
		this.pfc=pfc;
	}
}

 class Tagy {
	
	private static final int DEFAULT_SIZE=15;
	private static final int DEFAULT_STYLE=Font.PLAIN;
	private static final String DEFAULT_PARRENT="Serif";
	
	public static final  char CHAR_OF_START_OF_TAG='{';
		
	private Tagy(){}
		
		public static Font getTypeFace(String ... tags) {
			int style=0;
			int size=DEFAULT_SIZE;
			String parrent=DEFAULT_PARRENT;
			
			int i;
			if(tags!=null){
				String hledanyVyraz=CHAR_OF_START_OF_TAG+"size=";
				for(String tag:tags)
					if((i=tag.indexOf(hledanyVyraz))!=-1){
						
						int konecCisla= tag.indexOf(" ",i+ hledanyVyraz.length());
						if( konecCisla==-1)
							konecCisla=tag.length();
						size=Integer.parseInt(tag.substring(i+hledanyVyraz.length(),konecCisla));
					}
				
				for(String tag:tags){
					if((tag.indexOf(CHAR_OF_START_OF_TAG+"i"))!=-1){
						style+=Font.ITALIC;
					}else
					
					if((tag.indexOf(CHAR_OF_START_OF_TAG+"b"))!=-1){
						style+=Font.BOLD;
					}	
				}
				
				hledanyVyraz=CHAR_OF_START_OF_TAG+"parrent=";
				for(String tag:tags){
					if((i=tag.indexOf(hledanyVyraz))!=-1){
						int konec= tag.indexOf(" ",i+ hledanyVyraz.length());
						if( konec==-1)
							konec=tag.length();
						parrent=tag.substring(i + hledanyVyraz.length(), konec);
					}	
				}
			}
			if(style==0)
				style=DEFAULT_STYLE;
			
			return new Font(parrent,style,size);
		}

		public static Color getColorOfFont(String...tags){
			int red=0;
			int blue=0;
			int green=0;
			
			int i;
			
			if(tags==null)
				return new Color(red, blue, green);
			
			String hledanyVyraz=CHAR_OF_START_OF_TAG+"color(";
				for(String tag:tags)
					if((i=tag.indexOf(hledanyVyraz))!=-1){
						try{
							String [] rgb=tag.substring(i+ hledanyVyraz.length(), tag.indexOf(")",i+ hledanyVyraz.length())).split(",");
							
							red=Integer.parseInt(rgb[0]);
							green=Integer.parseInt(rgb[1]);
							blue=Integer.parseInt(rgb[2]);
						}catch(java.lang.StringIndexOutOfBoundsException e){
							return new Color(red,green,blue);
						}
					}
			
			return new Color(red, green, blue);
		}
}

