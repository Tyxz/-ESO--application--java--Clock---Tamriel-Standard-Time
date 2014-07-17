package data;

import java.util.GregorianCalendar;

/**
 * 
 * @author Tyx | Arne Rantzen
 * @version 0.1.0
 */
public class Moon {
	public static enum Phase {
		full,
		waning,
		nev,
		waxing,
	}
	
	private Settings settings;

	public Moon(Settings settings) {
		this.settings = settings;
	}
	
	public LoreDate createMoon() {
		int fullT = settings.getMoonFull();
		int newT = settings.getMoonNew();
		int wayT = settings.getMoonWay();
		
		int phase = fullT + newT + wayT * 2;
		
		double start = settings.getStartMoon();
		double time = new GregorianCalendar().getTimeInMillis()/1000L;
		
		while (start + phase < time) {
			start += phase;
		}
		
		time -= start;
		
		int full = fullT;
		int waning = full + wayT;
		int nev = waning + newT;
		int waxing = nev + wayT;
		
		Phase moon;
		
		if (full >= time) {
			moon = Phase.full;
			time = full - time;
		} else if (waning >= time) {
			moon = Phase.waning;
			time = waning - time;
		} else if (nev >= time) {
			moon = Phase.nev;
			time = nev - time;
		} else {
			moon = Phase.waxing;
			time = waxing - time;
		}
		
		return new LoreDate(moon, time);
	}

}
