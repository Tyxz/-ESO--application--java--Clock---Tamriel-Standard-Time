package data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

import lang.English;
import lang.German;
import lang.Language;

/**
 * 
 * @author Tyx | Arne Rantzen
 * @version 0.2.0
 */
public class Settings implements Serializable {
	/**
	 * @return the Version
	 */
	public static String getVersion() {
		return "0.3.2";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 31L;
	private double startDate = 1396569600, startTime = 1398044126,
			startMoon = 1400340861, dayLength = 20956, nightLength = 7200;
	private final int yearStart = 582;
	private int monthStart = 4, dayStart = 4, moonFull = 6, moonNew = 6,
			moonWay = 10;

	private final int[] monthLength = { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30,
			31, 30, 31 };
	private String local = "en";

	private String dateFormat = "_ddd, _d. _MMM _yy",
			clockFormat = "_hh:_mm:_ss";

	public Settings() {
	}

	/**
	 * @return the clockFormat
	 */
	public String getClockFormat() {
		return clockFormat;
	}

	/**
	 * @return the dateFormat
	 */
	public String getDateFormat() {
		return dateFormat;
	}

	/**
	 * @return the dayLength
	 */
	public double getDayLength() {
		return dayLength;
	}

	/**
	 * @return the dayStart
	 */
	public int getDayStart() {
		return dayStart;
	}

	/**
	 * @return the language
	 */
	public Language getLocal() {
		switch (local) {
		case "en":
			return new English();
		case "de":
			return new German();
		default:
			return new English();
		}
	}

	/**
	 * @return the monthLength
	 */
	public int[] getMonthLength() {
		return monthLength;
	}

	/**
	 * @return the monthStart
	 */
	public int getMonthStart() {
		return monthStart;
	}

	/**
	 * @return the moonFull
	 */
	public int getMoonFull() {
		return moonFull;
	}

	/**
	 * @return the moonNew
	 */
	public int getMoonNew() {
		return moonNew;
	}

	/**
	 * @return the moonWay
	 */
	public int getMoonWay() {
		return moonWay;
	}

	/**
	 * @return the nightLength
	 */
	public double getNightLength() {
		return nightLength;
	}

	/**
	 * @return the startDate
	 */
	public double getStartDate() {
		return startDate;
	}

	/**
	 * @return the startMoon
	 */
	public double getStartMoon() {
		return startMoon;
	}

	/**
	 * @return the startTime
	 */
	public double getStartTime() {
		return startTime;
	}

	/**
	 * @return the yearStart
	 */
	public int getYearStart() {
		return yearStart;
	}

	/**
	 * Will create the file .settings (or overwrite it) with the given settings
	 * 
	 * @param settings
	 *            : Settings to be saved
	 * @return error true if there went something wrong
	 */
	@SuppressWarnings("resource")
	public boolean saveSettings(Settings settings) {
		OutputStream fos = null;
		boolean error = false;

		try {
			fos = new FileOutputStream(new File(".settings"));
			ObjectOutputStream o = new ObjectOutputStream(fos);
			o.writeObject(settings);
			error = true;
		} catch (Exception e) {
			System.err.println(e);
		} finally {
			try {
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return error;
	}

	/**
	 * @param clockFormat
	 *            the clockFormat to set
	 */
	public void setClockFormat(String clockFormat) {
		this.clockFormat = clockFormat;
	}

	/**
	 * @param clockFormat
	 *            the clockFormat to set
	 */
	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	/**
	 * @param dayLength
	 *            the dayLength to set
	 */
	public void setDayLength(double dayLength) {
		this.dayLength = dayLength;
	}

	/**
	 * @param dayStart
	 *            the dayStart to set
	 */
	public void setDayStart(int dayStart) {
		this.dayStart = dayStart;
	}

	/**
	 * @param language
	 *            the language to set
	 */
	public void setLocal(Language local) {
		this.local = local.getLanguage();
	}

	/**
	 * @param monthStart
	 *            the monthStart to set
	 */
	public void setMonthStart(int monthStart) {
		this.monthStart = monthStart;
	}

	/**
	 * @param moonFull
	 *            the moonFull to set
	 */
	public void setMoonFull(int moonFull) {
		this.moonFull = moonFull;
	}

	/**
	 * @param moonNew
	 *            the moonNew to set
	 */
	public void setMoonNew(int moonNew) {
		this.moonNew = moonNew;
	}

	/**
	 * @param moonWay
	 *            the moonWay to set
	 */
	public void setMoonWay(int moonWay) {
		this.moonWay = moonWay;
	}

	/**
	 * @param nightLength
	 *            the nightLength to set
	 */
	public void setNightLength(double nightLength) {
		this.nightLength = nightLength;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(double startDate) {
		this.startDate = startDate;
	}

	/**
	 * @param startMoon
	 *            the startMoon to set
	 */
	public void setStartMoon(double startMoon) {
		this.startMoon = startMoon;
	}

	/**
	 * @param startTime
	 *            the startTime to set
	 */
	public void setStartTime(double startTime) {
		this.startTime = startTime;
	}
}
