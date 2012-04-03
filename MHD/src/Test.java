import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import utilities.pomocneProgramy.PTimeStationLine;
import utilities.pomocneProgramy.PraceSRetezci;
import utilities.struct.Trie;

public class Test {
	private final static String HOME_ADRES = "http://www.dpmp.cz";
	private final static PraceSRetezci.HledajiciAutomat znackaProOdkaz = new PraceSRetezci.HledajiciAutomat(
			"<a href=");
	// toto tu je pro rychlejší chod programu
	private final static PraceSRetezci.HledajiciAutomat zacatekLinkyA = new PraceSRetezci.HledajiciAutomat(
			"\"list dir-A");
	private final static PraceSRetezci.HledajiciAutomat konecLinkyA = new PraceSRetezci.HledajiciAutomat(
			"</div>");
	private final static PraceSRetezci.HledajiciAutomat zacatekLinkyB = new PraceSRetezci.HledajiciAutomat(
			"\"list dir-B");
	private final static PraceSRetezci.HledajiciAutomat konecLinkyB = new PraceSRetezci.HledajiciAutomat(
			"</div>");
	private final static PraceSRetezci.HledajiciAutomat zacatekStanic = new PraceSRetezci.HledajiciAutomat(
			"class=\"stations\"");
	private final static PraceSRetezci.HledajiciAutomat konecStanic = new PraceSRetezci.HledajiciAutomat(
			"</div>");
	private final static PraceSRetezci.HledajiciAutomat zacatekNazvuStanice = new PraceSRetezci.HledajiciAutomat(
			"scope=\"row\"");

	private final static PraceSRetezci.HledajiciAutomat zacatekCas = new PraceSRetezci.HledajiciAutomat(
			"headers=");
	private final static PraceSRetezci.HledajiciAutomat konecCas = new PraceSRetezci.HledajiciAutomat(
			"</div>");
	
	/**
	 * prvni je pro pracovni dny, druhy pro prazdniny a třetí pro víkend a svátek
	 */
	private final static PraceSRetezci.HledajiciAutomat[] casHodiny = {new PraceSRetezci.HledajiciAutomat(
			"class=\"hour\""),new PraceSRetezci.HledajiciAutomat(
			"class=\"hour holidays\""),new PraceSRetezci.HledajiciAutomat(
			"class=\"hour weekends\"")};
	
	/**
	 * prvni je pro pracovni dny, druhy pro prazdniny a třetí pro víkend a svátek
	 */
	private final static PraceSRetezci.HledajiciAutomat[] casMinuty = {new PraceSRetezci.HledajiciAutomat(
			"class=\"minute\""),new PraceSRetezci.HledajiciAutomat(
			"class=\"minute holidays\""),new PraceSRetezci.HledajiciAutomat(
			"class=\"minute weekends\"")};
	
	private final static HashSet<String> odkazyZastavekA = new HashSet<String>();
	private final static HashSet<String> odkazyZastavekB = new HashSet<String>();

	private final static PraceSRetezci.HledajiciAutomat[][] limity = {{ zacatekLinkyA, zacatekLinkyB }, 
												 { konecLinkyA, konecLinkyB } };
	@SuppressWarnings("unchecked")
	private final static Set[] zastavky = { odkazyZastavekA, odkazyZastavekB };
	
	private final static Collection<PTimeStationLine> odjezdyPracovni= new ArrayList<PTimeStationLine>();
	private final static Collection<PTimeStationLine> odjezdyPrazdniny= new ArrayList<PTimeStationLine>();
	private final static Collection<PTimeStationLine> odjezdySvatek= new ArrayList<PTimeStationLine>();
	
	@SuppressWarnings("unchecked")
	private final static Collection[] odjezdy = {odjezdyPracovni,odjezdyPrazdniny, odjezdySvatek};
	
	
	private static URL a;
	private static BufferedReader in;
	private static String inputLine;
	
	
	
	
	final static Set<String> zvlastniLinky = new HashSet<String>(4);
	static{
		zvlastniLinky.add("?rady=900");
		zvlastniLinky.add("?rady=100");
		zvlastniLinky.add("?rady=52");
		zvlastniLinky.add("?rady=51");
	}
	private static String nameOfCity = "Pardubice";
	
	private static String[] adresyLinek() throws MalformedURLException,UnsupportedEncodingException, IOException {
		in = openStream(HOME_ADRES);

		PraceSRetezci.HledajiciAutomat zacatekTabulky = new PraceSRetezci.HledajiciAutomat(
				"class=\"zastavkove-rady\"");
		PraceSRetezci.HledajiciAutomat konecTabulky = new PraceSRetezci.HledajiciAutomat(
				"</table>");
	

		HashSet<String> odkazyLinek = new HashSet<String>();
		while ((inputLine = in.readLine()) != null) {
			if (PraceSRetezci.najdiZacatekSlova(inputLine, zacatekTabulky) != -1)
				do {
					
					for(int od:PraceSRetezci.najdiVsechnyKonceSlova(inputLine,
							znackaProOdkaz)){
							// odkaz je v uvozovkách proto je hledám
							od = inputLine.indexOf('\"', od);
							odkazyLinek.add(inputLine.substring(++od, inputLine
									.indexOf('\"', od + 1)));
					}
				}while((inputLine = in.readLine()) != null
						&& PraceSRetezci.najdiZacatekSlova(inputLine,
								konecTabulky) == -1);
		}
		in.close();
		return odkazyLinek.toArray(new String[odkazyLinek.size()]);
	}
	
	/**
	 * 
	 * @param adresaLinky
	 * @return pole s adresama linek 
	 * <ul><li>[0][] je pro linky jedním směrem</li>
	 * <li>[1][] druhým</li></ul>
	 * @throws MalformedURLException
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	private static String[][] adresyZastavek(final String adresaLinky) throws MalformedURLException,UnsupportedEncodingException, IOException  {		
			odkazyZastavekA.clear();
			odkazyZastavekB.clear();
			if(zvlastniLinky.contains(adresaLinky))
				return null;
			in = openStream(HOME_ADRES + adresaLinky);
			// i = 0... zastavky A, i = 1 ... zastavky B
			int i = 0;
			
				while ((inputLine = in.readLine()) != null) {
					//	boolean readStation = false;
					if (PraceSRetezci.najdiZacatekSlova(inputLine, limity[0][i]) != -1) {
					//	readStation = true;
						do{
							for(int od :PraceSRetezci.najdiVsechnyKonceSlova(inputLine,znackaProOdkaz)){
								// odkaz je v uvozovkách proto je hledám
								od = inputLine.indexOf('\"', od);
								zastavky[i].add(inputLine.substring(++od,
										inputLine.indexOf('\"', od + 1)));

							}
						}while ((inputLine = in.readLine()) != null
								&& PraceSRetezci.najdiZacatekSlova(inputLine,limity[1][i]) == -1);
						
							if( i == 0){
								i = 1;
							}else{
								break; // mám již načtené všechny odkazy zastávek
							}
					}
				}
				in.close();
				return new String[][]{odkazyZastavekA.toArray(new String[odkazyZastavekA.size()]),odkazyZastavekB.toArray(new String[odkazyZastavekB.size()])};
	}
	final private static Trie TRIE = new Trie();
	@SuppressWarnings("unchecked")
	public static PTimeStationLine[][] odjezdyZeZastavky(String odkazZastavky,boolean leanNameOfStation) throws MalformedURLException,UnsupportedEncodingException, IOException  {
		odjezdyPracovni.clear();
		odjezdyPrazdniny.clear();
		odjezdySvatek.clear();
		
		in = openStream(HOME_ADRES + odkazZastavky);
		String nextStation = "";
		int value = 2;
		int line = 2;
		ArrayList[] hodina = {new ArrayList<Integer>(),new ArrayList<Integer>(),new ArrayList<Integer>()};
		if(leanNameOfStation)
		while ((inputLine = in.readLine()) != null) {
				if (PraceSRetezci.najdiZacatekSlova(inputLine, zacatekStanic) != -1) 
					while((inputLine = in.readLine()) != null && PraceSRetezci.najdiKonecSlova(inputLine, konecStanic) == -1){
						for(int od:PraceSRetezci.najdiVsechnyKonceSlova(inputLine, zacatekNazvuStanice)){
							od = inputLine.indexOf('>',od);
							if(nextStation.isEmpty()){
								nextStation = inputLine.substring(od,inputLine.indexOf("</th",od));
								TRIE.addWord(nextStation);
							}
							TRIE.addWord(inputLine.substring(od,inputLine.indexOf("</th",od)));
						}
					}
		}
		while ((inputLine = in.readLine()) != null) {
			if (PraceSRetezci.najdiZacatekSlova(inputLine, zacatekCas) != -1) {			
				do{
					for(int i = 0; i < casHodiny.length; i++){
						int[] od = PraceSRetezci.najdiVsechnyKonceSlova(inputLine, casHodiny[i]);
						for(int j = 0; j < od.length; j++){
							
								od[j] = inputLine.indexOf('>', od[j]);
								hodina[i].add(Integer.parseInt(inputLine.substring(++od[j],inputLine.indexOf('<',od[j]+1)).trim()));
								
						}
					}
					for(int i = 0; i < casMinuty.length ; i++){
						int[] od = PraceSRetezci.najdiVsechnyKonceSlova(inputLine, casMinuty[i]);
						for(int j = 0; j < od.length; j++){
							od[j] = inputLine.indexOf('>', od[j]);
							String minutaSTR =  inputLine.substring(++od[j],inputLine.indexOf('<',od[j]+1)).trim();
							try{
								int h = (Integer)hodina[i].get(j);
								odjezdy[i].add(new PTimeStationLine(h*60+Integer.parseInt(minutaSTR),nextStation,value,line));
							}catch( NumberFormatException e){
								// zde opravdu nemá nic být
							}
						}
					}
					
					
					
				}while((inputLine = in.readLine()) != null && PraceSRetezci.najdiZacatekSlova(inputLine, konecCas) == -1);
			}
		}
		
		in.close();
		return new PTimeStationLine[][]{odjezdyPracovni.toArray(new PTimeStationLine[0]), odjezdyPrazdniny.toArray(new PTimeStationLine[0]),odjezdySvatek.toArray(new PTimeStationLine[0])};
	}
	
	private static BufferedReader openStream(String urlAdres) throws UnsupportedEncodingException, MalformedURLException, IOException{
		return new BufferedReader(new InputStreamReader(new URL(urlAdres).openStream(), "ISO8859-2"));
	}
	public static void main(String[] args) throws IOException {
//		String[] odkazyLinek = adresyLinek();
//		System.out.println(Arrays.toString(odkazyLinek));
//		String[][] odkazyZastavek = adresyZastavek(odkazyLinek[3]);
//		System.out.println(Arrays.toString(odkazyZastavek[0]));
		long l = System.currentTimeMillis();
		odjezdyZeZastavky("/jr/platnost_20120304/JR2100000061001A.htm",true);
		System.out.println(TRIE);
			//System.out.println(Arrays.toString(a));
		System.out.println(System.currentTimeMillis()-l);
	}

}
