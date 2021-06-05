package Client.vo;

import java.io.Serializable;
import java.util.ArrayList;

public class CustomizedRankReqVO implements Serializable {

	private ArrayList<DeptInfoReqVO> deptList;
	private ArrayList<String> indicators;

	public CustomizedRankReqVO(ArrayList<DeptInfoReqVO> deptList, ArrayList<String> indicators) {
		this.deptList = deptList;
		this.indicators = indicators;
	}


	public ArrayList<DeptInfoReqVO> getDeptList() {
		return deptList;
	}

	public ArrayList<String> getIndicators() {
		return indicators;
	}

}
