package Client.vo;

import Server.model.dto.DepartmentRatingDTO;
import Server.model.dto.UnivRatingDTO;
import Server.model.dto.UserBookmarkDTO;

import java.io.Serializable;
import java.util.ArrayList;

public class MyPageInfoVO implements Serializable {

	private ArrayList<String> bookmarkList;
	private UnivRatingDTO univRating;
	private DepartmentRatingDTO deptRating;


	public MyPageInfoVO(ArrayList<String> bookmarkList, UnivRatingDTO univRating, DepartmentRatingDTO deptRating) {
		this.bookmarkList = bookmarkList;
		this.univRating = univRating;
		this.deptRating = deptRating;
	}

	public ArrayList<String> getBookmark() {
		return bookmarkList;
	}

	public UnivRatingDTO getUnivRating() {
		return univRating;
	}

	public DepartmentRatingDTO getDeptRating() {
		return deptRating;
	}

}
