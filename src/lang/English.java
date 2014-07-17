package lang;

public class English extends Language {
	private final String yearName = "2E ";
	private final String[] monthName = { "", "Morning Star", "Sun's Dawn",
			"First Seed", "Rain's Hand", "Second Seed", "Midyear",
			"Sun's Height", "Last Seed", "Hearthfire", "Frostfall",
			"Sun's Dusk", "Evening Star" }, dayName = { "Morndas", "Tirads",
			"Middas", "Turdas", "Fredas", "Loredas", "Sundas" };

	public English() {
		super("en");
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
		return hour + ":" + addZero(minute) + ":" + addZero(second);
	}

	@Override
	public String getYearName(double year) {
		return yearName + (int) year;
	}

}
