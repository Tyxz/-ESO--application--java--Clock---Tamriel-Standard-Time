package lang;

public class German extends Language {
	private final String yearName = "2Ä ";
	private final String[] monthName = { "", "Morgenstart", "Sonnenaufgang",
			"Erste Saat", "Regenhand", "Zweite Saat", "Jahresmitte",
			"Sonnenhöhe", "Letzte Saat", "Herzfeuer", "Eisherbst",
			"Sonnenuntergang", "Abendstern" }, dayName = { "Morndas", "Tirads",
			"Middas", "Turdas", "Fredas", "Loredas", "Sundas" };

	public German() {
		super("de");
	}

	@Override
	public String getDayName(int day) {
		return dayName[day % 7];
	}

	@Override
	public String getMonthName(int month) {
		return monthName[month];
	}

	@Override
	public String getTime(int hour, int minute, int second) {
		return addZero(hour) + ":" + addZero(minute) + ":" + addZero(second);
	}

	@Override
	public String getYearName(double year) {
		return yearName + (int) year;
	}

}
