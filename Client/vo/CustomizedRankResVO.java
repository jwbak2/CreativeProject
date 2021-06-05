package Client.vo;

public class CustomizedRankResVO {

	private String univName;
	private String deptName;
	private int scoreOfIndicator1;
	private int scoreOfIndicator2;
	private int scoreOfIndicator3;
	private int scoreOfTotal;

	public CustomizedRankResVO(String univName, String deptName, int scoreOfIndicator1, int scoreOfIndicator2, int scoreOfIndicator3, int scoreOfTotal) {
		this.univName = univName;
		this.deptName = deptName;
		this.scoreOfIndicator1 = scoreOfIndicator1;
		this.scoreOfIndicator2 = scoreOfIndicator2;
		this.scoreOfIndicator3 = scoreOfIndicator3;
		this.scoreOfTotal = scoreOfTotal;
	}


}
