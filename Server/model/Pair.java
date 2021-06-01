package Server.model;

public class Pair<L, R> {

	final L left;
	final R right;
	int count;

	public Pair(L left, R right) {
		this.left = left;
		this.right = right;
		count = 0;
	}

}
