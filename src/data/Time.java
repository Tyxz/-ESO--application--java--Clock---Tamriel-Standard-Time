package data;

import java.util.GregorianCalendar;

/**
 * 
 * @author Tyx | Arne Rantzen
 * @version 0.1.0
 */
public class Time {
	private Settings settings;

	/**
	 * Constructor calculates a Start time based on the dayLength
	 */
	public Time(Settings settings) {
		this.settings = settings;
		calcStartDate();
	}

	/**
	 * Calculates the start of the Date System so that it is 0 o'clock TST and
	 * near 1.1.2014 0 o'clock
	 */
	private void calcStartDate() {
		// startDate is 3 days and month before ESO was released
		GregorianCalendar date = new GregorianCalendar();
		date.set(2014, 1, 1);
		double startDate = date.getTimeInMillis() / 1000L;
		double dayLength = settings.getDayLength();
		double t = settings.getStartTime() - startDate;
		while ((t - dayLength) > 0) {
			t = t - dayLength;
		}
		startDate = startDate - t;

		// real Start date 4.4.14
		date.set(2014, 4, 4);

		double time = date.getTimeInMillis() / 1000L;
		time -= 3 * dayLength; // 3 days earlier
		time -= startDate; // time between start date and start time

		int m, d;
		for (m = 3, d = 31; time > 0; time -= dayLength) {
			d--;
			if (d == 0) {
				m--;
				if (m == 0) {
					m = 12;
				}
				d = settings.getMonthLength()[m];
			}
		}

		settings.setStartDate(startDate);
		settings.setMonthStart(m);
		settings.setDayStart(d);
	}

	/**
	 * Create a year, month and day in Lore Time
	 * 
	 * @return : LoreDate year, month and day in Tamriel
	 */
	public LoreDate createDate() {
		double tSinceStart = new GregorianCalendar().getTimeInMillis() / 1000L
				- settings.getStartDate();
		double dayLength = settings.getDayLength();
		double yearLength = dayLength * 365;

		// year
		double year = Math.floor(tSinceStart / yearLength);
		tSinceStart -= year * yearLength;
		year = settings.getYearStart() + year;

		// month
		int month = settings.getMonthStart();

		while (tSinceStart - (settings.getMonthLength()[month] * dayLength) > 0) {
			tSinceStart -= settings.getMonthLength()[month] * dayLength;
			month++;
			if (month > 12) {
				month = 1;
			}
		}

		// day
		int day = (int) Math.floor(tSinceStart / dayLength);

		return new LoreDate(year, month, day);
	}

	/**
	 * Creates the current TST + Date in Tamriel
	 * 
	 * @return : LoreDate full date
	 */
	public LoreDate createDateAndTime() {
		LoreDate date = createDate();
		LoreDate time = createTime();

		date.setHour(time.getHour());
		date.setMinute(time.getMinute());
		date.setSecond(time.getSecond());

		return date;
	}

	/**
	 * Sync was at noon so it has to be calced down to midnight
	 * 
	 * @return : double Time when it was midnight in TST
	 */
	private double createMidnight() {
		double nightLength = settings.getNightLength();
		double midToRise = nightLength / 2, riseToSet = settings.getDayLength()
				- nightLength;

		double tSinceStart = settings.getStartTime() - midToRise - riseToSet
				/ 2;

		return tSinceStart;
	}

	/**
	 * calculates a daytime in Tamriel
	 * 
	 * @return LoreDate : hour, minute and seconds in TST
	 */
	public LoreDate createTime() {
		double tSinceMidnight = createMidnight();
		GregorianCalendar date = new GregorianCalendar();
		tSinceMidnight = date.getTimeInMillis() / 1000L - tSinceMidnight;

		double dayLength = settings.getDayLength();

		double daysPast = (Math.floor(tSinceMidnight / dayLength) * dayLength);
		double time = tSinceMidnight - daysPast;

		double tst = 24 * time / dayLength;

		int h = (int) Math.floor(tst);
		tst = (tst - h) * 60;

		int m = (int) Math.floor(tst);
		tst = (tst - m) * 60;

		int s = (int) Math.floor(tst);

		return new LoreDate(h, m, s);
	}

	/**
	 * @return the settings
	 */
	public Settings getSettings() {
		return settings;
	}

	/**
	 * @param settings
	 *            the settings to set
	 */
	public void setSettings(Settings settings) {
		this.settings = settings;
	}
}
