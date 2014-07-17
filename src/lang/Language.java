package lang;

public abstract class Language {
	private String language;

	/**
	 * Save the localization
	 * 
	 * @param language
	 *            : String like en for english
	 */
	public Language(String language) {
		setLanguage(language);
	}

	protected String addZero(int num) {
		if (num < 10) {
			return "0" + num;
		}
		return "" + num;
	}

	public abstract String getDayName(int day);

	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	public abstract String getMonthName(int month);

	public abstract String getTime(int hour, int minute, int second);

	public abstract String getYearName(double year);

	/**
	 * @param language
	 *            the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}
}
