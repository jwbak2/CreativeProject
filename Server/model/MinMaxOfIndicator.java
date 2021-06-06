package Server.model;

public class MinMaxOfIndicator {

	private int year;
	private String name;
	private Long min;
	private Long max;

	public MinMaxOfIndicator(int year, String name, Long min, Long max) {
		this.year = year;
		this.name = name;
		this.min = min;
		this.max = max;
	}

	public int getYear() {
		return year;
	}

	public String getName() {
		return name;
	}

	public Long getMin() {
		return min;
	}

	public Long getMax() {
		return max;
	}

}
