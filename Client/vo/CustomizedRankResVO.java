package Client.vo;

import java.io.Serializable;

public class CustomizedRankResVO implements Serializable {

	private String univName;
	private String deptName;
	private double scoreOfIdct1;
	private double scoreOfIdct2;
	private double scoreOfIdct3;
	private double scoreOfTotal;


	public CustomizedRankResVO(String univName, String deptName, double scoreOfIdct1, double scoreOfIdct2,
							   double scoreOfIdct3, double scoreOfTotal) {
		this.univName = univName;
		this.deptName = deptName;
		this.scoreOfIdct1 = scoreOfIdct1;
		this.scoreOfIdct2 = scoreOfIdct2;
		this.scoreOfIdct3 = scoreOfIdct3;
		this.scoreOfTotal = scoreOfTotal;
	}


	public String getUnivName() {
		return univName;
	}

	public String getDeptName() {
		return deptName;
	}

	public double getScoreOfIdct1() {
		return scoreOfIdct1;
	}

	public double getScoreOfIdct2() {
		return scoreOfIdct2;
	}

	public double getScoreOfIdct3() {
		return scoreOfIdct3;
	}

	public double getScoreOfTotal() {
		return scoreOfTotal;
	}

}
