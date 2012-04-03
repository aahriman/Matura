package utilities.pomocneProgramy;

import java.awt.Canvas;
import java.awt.Graphics;

import utilities.componenty.pomocne.OknoProTesty;
import utilities.pomocneProgramy.pomocne.MojeTagy;

final public class SpravceTagu{
	private final static int MEZERA_MEZI_TAGAMA=10;
	
	private SpravceTagu(){}
	public static PrepravkaSirkaVyska vykresli(String text, Graphics g) {
		if(text==null)
			return new PrepravkaSirkaVyska(0,0);
		int zacniKreslitOd=0;
		int sirka=0;
		for(int odkud=0;odkud<text.length();){
			MojeTagy tag=MojeTagy.getPrvniTag(odkud, text);
			String castKterouVykresli=MojeTagy.orizniDleTagu(text, tag, odkud);
			odkud+=castKterouVykresli.length();
			
			if(tag==MojeTagy.TEXT){
				castKterouVykresli=castKterouVykresli.substring(MojeTagy.TEXT.getZACATEK().length(),castKterouVykresli.length()-MojeTagy.TEXT.getKONEC().length());
				
				if(castKterouVykresli.charAt(0)=='\n')
					castKterouVykresli=castKterouVykresli.substring(1);
			}
			
			zacniKreslitOd=tag.getVYKRESLOVAC().vykresli(castKterouVykresli, g, zacniKreslitOd)+MEZERA_MEZI_TAGAMA;
			if(sirka<tag.getVYKRESLOVAC().getSirka()){
				sirka=tag.getVYKRESLOVAC().getSirka();
			}
		}
		
		return new PrepravkaSirkaVyska(sirka,zacniKreslitOd);
	}

	public static void main(String[] args) {
		final OknoProTesty o=new OknoProTesty();
        Canvas c=new Canvas(){
       	 /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
       	 public void paint(Graphics g){
       		 String text=
       		"{table name=Tabulka zastrašení rows=charisma columns=bojovnost}\n"+
			 "{title}\n"+
				"{column}1	DVĚ	3	4	5	6	7	8	9	10	11	12	13	14	15	16	17	18{/column}\n"+
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
			"{/table}\n"+
			 "{text}{color(255,0,0)}Válečník může zastrašovat skupinu, jen proti které družina bouje jako celek. Tedy si nemůže vybrat jen jednu oběť.{/color(255,0,0)}{/b}\n"+
"Pokud zastrašuje skupinu, pak se pravděpodobnost z tabulky {b}dělí počtem protivníků v t0 skupině{/b}.\n"+
"Zastrašování se musí oznámit PJ. Zastrašování se bere jako útok.\n{/text}" +
"{color(255,0,0)}Válečník může zastrašovat skupinu, jen proti které družina bouje jako celek. Tedy si nemůže vybrat jen jednu oběť.{/color(255,0,0)}{/b}\n"+
"Pokud zastrašuje skupinu, pak se pravděpodobnost z tabulky {b}dělí počtem protivníků v t0 skupině{/b}.\n"+
"Zastrašování se musí oznámit PJ. Zastrašování se bere jako útok.\n";
       		PrepravkaSirkaVyska p=SpravceTagu.vykresli(text, g);
			if(o.getSize().height<p.VYSKA || o.getSize().width<p.SIRKA)
				o.setSize(p.SIRKA,p.VYSKA);
       	 }
        };
        
        o.add(c);
        o.pack();
        o.setSize(100,100);
        o.setVisible(true);
	}
}
