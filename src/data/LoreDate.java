package data;

import data.Moon.Phase;
import lang.Language;

/**
 * Will calculate the Time and Date in Tamriel
 * 
 * @author Arne Rantzen - Tyx
 * @version 0.3.0
 */
public class LoreDate {
	private double year, nextPhase;
	private int month, day, hour, minute, second;
	private Phase moonPhase;

	/**
	 * Create only a calendar date
	 * 
	 * @param year
	 *            : double
	 * @param month
	 *            : int
	 * @param day
	 *            : int
	 */
	public LoreDate(double year, int month, int day) {
		setYear(year);
		setMonth(month);
		setDay(day);
	}

	/**
	 * Create a full Date
	 * 
	 * @param year
	 *            : long
	 * @param month
	 *            : int
	 * @param day
	 *            : int
	 * @param hour
	 *            : int
	 * @param minute
	 *            : int
	 * @param second
	 *            : int
	 */
	public LoreDate(double year, int month, int day, int hour, int minute,
			int second) {
		setYear(year);
		setMonth(month);
		setDay(day);
		setHour(hour);
		setMinute(minute);
		setSecond(second);
	}

	/**
	 * Create only a clock date
	 * 
	 * @param hour
	 *            : int
	 * @param minute
	 *            : int
	 * @param second
	 *            : int
	 */
	public LoreDate(int hour, int minute, int second) {
		setHour(hour);
		setMinute(minute);
		setSecond(second);
	}
	
	/**
	 * Create a Moon date
	 * 
	 * @param moonPhase
	 * 					: String
	 * @param nextPhase
	 * 					: double
	 */
	public LoreDate(Phase moonPhase, double nextPhase) {
		setMoonPhase(moonPhase);
		setNextPhase(nextPhase);
	}

	/**
	 * @return the day
	 */
	public int getDay() {
		return day;
	}

	/**
	 * @return the hour
	 */
	public int getHour() {
		return hour;
	}

	/**
	 * @return the minute
	 */
	public int getMinute() {
		return minute;
	}

	/**
	 * @return the month
	 */
	public int getMonth() {
		return month;
	}

	/**
	 * @return the second
	 */
	public int getSecond() {
		return second;
	}

	/**
	 * @return the year
	 */
	public double getYear() {
		return year;
	}

	public String printClock(Settings settings) {
		Language l = settings.getLocal();
		String format = settings.getClockFormat();

		format = format.replace("_hh", getHour() < 10 ? "0" + getHour() : ""
				+ getHour());
		format = format.replace("_h", "" + getHour());

		format = format.replace("_mm", getMinute() < 10 ? "0" + getMinute()
				: "" + getMinute());
		format = format.replace("_m", "" + getMinute());

		format = format.replace("_ss", getSecond() < 10 ? "0" + getSecond()
				: "" + getSecond());
		format = format.replace("_s", "" + getSecond());

		return format;

	}

	public String printDate(Settings settings) {
		Language l = settings.getLocal();
		String format = settings.getDateFormat();

		format = format.replace("_yy", l.getYearName(getYear()));
		format = format.replace("_y", "" + getYear());

		format = format.replace("_MMM", l.getMonthName(getMonth()));
		format = format.replace("_MM", getMonth() < 10 ? "0" + getMonth() : ""
				+ getMonth());
		format = format.replace("_M", "" + getMonth());

		format = format.replace("_ddd", l.getDayName(getDay()));
		format = format.replace("_dd", getDay() < 10 ? "0" + getDay() : ""
				+ getDay());
		format = format.replace("_d", "" + getDay());

		return format;
	}

	/**
	 * @param day
	 *            the day to set
	 */
	public void setDay(int day) {
		this.day = day;
	}

	/**
	 * @param hour
	 *            the hour to set
	 */
	public void setHour(int hour) {
		this.hour = hour;
	}

	/**
	 * @param minute
	 *            the minute to set
	 */
	public void setMinute(int minute) {
		this.minute = minute;
	}

	/**
	 * @param month
	 *            the month to set
	 */
	public void setMonth(int month) {
		this.month = month;
	}

	/**
	 * @param second
	 *            the second to set
	 */
	public void setSecond(int second) {
		this.second = second;
	}

	/**
	 * @param year
	 *            the year to set
	 */
	public void setYear(double year) {
		this.year = year;
	}

	public Phase getMoonPhase() {
		return moonPhase;
	}

	public void setMoonPhase(Phase moonPhase) {
		this.moonPhase = moonPhase;
	}

	public double getNextPhase() {
		return nextPhase;
	}

	public void setNextPhase(double nextPhase) {
		this.nextPhase = nextPhase;
	}
}
