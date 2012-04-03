package setting;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.TreeSet;

public class Transfer{
	private static ObjectInputStream in;
	private boolean change;
	private TreeSet<PStationToStation> m = new TreeSet<PStationToStation>();
	
	public PStationToStation[] getStationToStation(){
		return m.toArray(new PStationToStation[0]);
	}
	
	public boolean add(PStationToStation p){
		if(!m.contains(p))
			change = true;
		return m.add(p);
	}
	
	public boolean remove(PStationToStation p){
		if(!m.contains(p))
			change = true;
		return m.remove(p);
	}
	
	public boolean getChange(){
		return change;
	}
	private Transfer(){}
	public static Transfer getTransfer(String nameOfCity, boolean bnew){
		return bnew ? new Transfer() : getDefaultTransfer(nameOfCity);
	}
	
	private static Transfer getDefaultTransfer(String nameOfCity){
		Object o = null;
		try {
			in = new ObjectInputStream(new DataInputStream(new FileInputStream(new File(nameOfCity+".city"))));
			o = in.readObject();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
		
		return (Transfer) o;
	}
}
