package utilities.pomocneProgramy;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.Panel;
import java.util.Arrays;

import javax.swing.JComponent;
import javax.swing.JPanel;
public class VytvareniOken {

	private VytvareniOken(){}
		
	/**
	 * Vytvo�� do toho panelu, kter� mu zad�te jako parametr dal�� ��dek, kter� bude rozta�iteln�, tam kde vy chcete
	 * a p�id� tam ty komponenty, kter� mu zad�te
	 * 
	 * @param panel                - Panel, do kter�ho hcete vlo�it dan� komponenty 
	 * @param gbc                  - GridBagContraints
	 * @param kolikataRoztazitelna - int[] ��sla, kter� chcete aby byli roza�en�
	 * @param comp                 - Iveci v�echny komponenty, kter� chcete vlo�it do panel� 
	 * @return ScrollPane          - s obsahem spr�vn� rozd�len�ch komponent
	 */
	static public void vytvorDalsiRadek(Panel panel,GridBagConstraints gbc,int [] kolikataRoztazitelna,Component... comp){
		Arrays.sort(kolikataRoztazitelna);
		
		int j=0;
		gbc.gridy++;
		gbc.fill=GridBagConstraints.HORIZONTAL;
			
		for(int i=0;i<comp.length;i++){
			gbc.gridx=i;
			
			if(kolikataRoztazitelna.length!=0 && j<kolikataRoztazitelna.length && i==kolikataRoztazitelna[j]-1){				
					gbc.weightx=1.0;		j++;
			}
			
			((GridBagLayout)panel.getLayout()).setConstraints(comp[i], gbc);
			panel.add(comp[i]);
			
			gbc.weightx=0.0;
		}
	}
	
	/**
	 * Odebere v�echny komponenty,kter� mu zad�te v parametru comp, z panelu, kter� mu zad�te v parametru panel
	 * 
	 * panel - Panel        panel,z kter�ho chcete odebrat dan� komponenty
	 * comp  - Component    komponenty, kter� chcete odebrat 
	 */
	static public void odeberRadek(Panel panel,Component... comp){
		for(int i=0;i<comp.length;i++){
			panel.remove(comp[i]);
		}
	}
	
	static public void vlozComponentu(Container p,GridBagConstraints gbc,Component c){	
		((GridBagLayout)p.getLayout()).setConstraints(c, gbc);
		p.add(c);
	}
	
	static public Panel vlozeniDoPomocnehoPanelu(int [] roztazeni,Component...c){
		Panel [] pom=new Panel[(c.length+2)/3];
		for(int i=0;i<pom.length;i++)
			pom[i]=new Panel(new GridBagLayout());
		
		int indexRoztazenych=0;
		if(pom.length==1){
			for(int i=0;i<c.length;i++){
				if(roztazeni.length>indexRoztazenych && roztazeni[indexRoztazenych]==i+1){
					indexRoztazenych++;
					VytvareniOken.vytvorDalsiRadek(pom[0], new GridBagConstraints(), new int[]{1},c[i]);
				}else
					VytvareniOken.vytvorDalsiRadek(pom[0], new GridBagConstraints(), new int[]{},c[i]);
			}
			
			return pom[0];
		}
		
		for(int i=0;i<pom.length;i++){
			for(int j=0;j<3 && j<(c.length-i*3);j++){
				int ind=i*3+j; // index component
				if(roztazeni.length>indexRoztazenych && roztazeni[indexRoztazenych]==ind+1){
					indexRoztazenych++;
					VytvareniOken.vytvorDalsiRadek(pom[i], new GridBagConstraints(), new int[]{1},c[ind]);
				}else
					VytvareniOken.vytvorDalsiRadek(pom[i], new GridBagConstraints(), new int[]{},c[ind]);
				
			}
		}
			return vlozeniDoPomocnehoPanelu(vratPoleCisel(pom.length),pom);
	}
	
	static public Panel vlozeniDoPomocnehoPanelu(Component...c){
			return vlozeniDoPomocnehoPanelu(vratPoleCisel(c.length),c);
	}
	
	static public Component vlozeniDoPomocnehoPaneluSPopiskem(String popisek,Component c){
		Panel pom=new Panel(new GridBagLayout());
		VytvareniOken.vytvorDalsiRadek(pom, new GridBagConstraints(), new int[]{2},new Label(popisek),c);
		return pom;
	}
	
	static public Component vlozeniDoPomocnehoPaneluSPopiskemPopisekNad(String popisek,Component c){
		GridBagLayout gbl=new GridBagLayout();
		GridBagConstraints gbc=new GridBagConstraints();
		
		gbc.fill=GridBagConstraints.HORIZONTAL;
		gbc.weightx=1.0;
		Panel pom=new Panel(gbl);
		
		
		gbc.gridwidth=1;
		gbc.gridheight=1;
		Label l=new Label(popisek);
		gbl.setConstraints(l, gbc);
		pom.add(l);
		gbc.gridy=2;
		gbl.setConstraints(c, gbc);
		pom.add(c);
		
		
		return pom;
	}
	
	/**
	 * 
	 * @param kolikNaRadek
	 * @param kolikataRoztazitelna - určuje kolikátý sloupec má být roztahovací
	 * @param c
	 * @return
	 */
	static public Component vlozDoTabulky(int kolikNaRadek,int [] kolikataRoztazitelna,Component ...c){
		Panel p=new Panel(new GridBagLayout());
		vlozDoTabulky(p, kolikNaRadek, kolikataRoztazitelna, c);
		return p;
	}
	
	/**
	 * @param p  - kontainer, kam se mají komponenty vložir
	 * @param kolikNaRadek  - kolik komponent chcete dat na radek (zapocitavaji se i <b>null</b>)
	 * @param kolikataRoztazitelna - určuje kolikátý sloupec má být roztahovací, korektni je null i prazne pole pro zadny
	 * @param c -  <b>null</b> vynechá sloupec
	 */
	static public JComponent vlozDoTabulkyNew(int kolikNaRadek,int [] kolikataRoztazitelna,Component ...c){
		JPanel p=new JPanel(new GridBagLayout());
		vlozDoTabulky(p, kolikNaRadek, kolikataRoztazitelna, c);
		return p;
	}
	/**
	 * @param p  - kontainer, kam se mají komponenty vložir
	 * @param kolikNaRadek  - kolik komponent chcete dat na radek (zapocitavaji se i <b>null</b>)
	 * @param kolikataRoztazitelna - určuje kolikátý sloupec má být roztahovací, korektni je null i prazne pole pro zadny
	 * @param c -  <b>null</b> vynechá sloupec
	*/
	static public void vlozDoTabulky(Container p,int kolikNaRadek,int [] kolikataRoztazitelna,Component ...c){
		vlozDoTabulky(p,0,kolikNaRadek,kolikataRoztazitelna,c);
	}
	/** 
	 * @param p  - kontainer, kam se mají komponenty vložir
	 * @param startRadek    - radek na kterem se zacina
	 * @param kolikNaRadek  - kolik komponent chcete dat na radek (zapocitavaji se i <b>null</b>)
	 * @param kolikataRoztazitelna - určuje kolikátý sloupec má být roztahovací, korektni je null i prazne pole pro zadny
	 * @param c -  <b>null</b> vynechá sloupec
	 */
	static public void vlozDoTabulky(Container p,int startRadek,int kolikNaRadek,int [] kolikataRoztazitelna,Component ...c){
		GridBagConstraints gbc=new GridBagConstraints();
		
		p.setLayout((p.getLayout() instanceof GridBagLayout)? p.getLayout():new GridBagLayout());
		
		Arrays.sort(kolikataRoztazitelna);
		
		gbc.fill=GridBagConstraints.HORIZONTAL;
		
	
		for(int j=0;j<c.length;j++){
			int min=Math.min(kolikNaRadek,c.length-j*kolikNaRadek);
			for(int i=0;i<min;i++){
				int k=0;
	
				if(kolikataRoztazitelna != null)
				if(kolikataRoztazitelna.length!=0 && k<kolikataRoztazitelna.length && i==kolikataRoztazitelna[k]-1){				
						gbc.weightx=(1.0/kolikataRoztazitelna.length);		k++;
				}
				
				gbc.gridy=j + startRadek;
				gbc.gridx=i;
				if(c[i+j*kolikNaRadek] != null)
					vlozComponentu(p, gbc, c[i+j*kolikNaRadek]);
				
				gbc.weightx=0.0;
			}
		}
	}
	
	/**
	 * vr�t� pole ��sel od 1 do n
	 * @param n - kone�n� prvek(v�etn�), kter� bude v poli
	 * @return pole, kde budou hodnoty 1,2,...n
	 */
	static private int[] vratPoleCisel(int n){
		int [] i=new int[n];
		for(int j=1;j<=i.length;j++)
			i[j-1]=j;
		return i;
	}
}