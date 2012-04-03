package utilities.pomocneProgramy;

public class PTimeStationLine {
	public final int TIME;
	public final String STATION;
	/**
	 * how much time we will spend on this way
	 */
	public final int VALUE;
	public final int LINE;
	
	private final String  SIGNATURE;
	
	public PTimeStationLine(int time,String station, int value,int line) {
		TIME = time;
		STATION = station;
		VALUE = value;
		LINE = line;
		
		SIGNATURE = "at "+TIME/60+":"+TIME%60+ " to " + STATION + " from " + VALUE + " with " + LINE; 
	}
	
	@Override
	public String toString() {
		return SIGNATURE;
	}
}
