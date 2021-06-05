package Server.model;

public class Pair<L, R> {

	final public L left;
	final public R right;
	int count;

	public Pair(L left, R right) {
		this.left = left;
		this.right = right;
		count = 0;
	}

}
