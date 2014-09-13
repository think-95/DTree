package tree;

import data.Data;

/**
 * @author:wzc
 * @version:3:45:21 PM
 */
public class TreeNode {
	private int begin = 0;
	private int end = 0;
	private TreeNode lNode;
	private TreeNode rNode;
	private int feature;

	public TreeNode() {
		// TODO Auto-generated constructor stub
	}

	public TreeNode(int begin, int end) {
		this.begin = begin;
		this.end = end;
	}

	public int getBegin() {
		return begin;
	}

	public void setBegin(int begin) {
		this.begin = begin;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public TreeNode getlNode() {
		return lNode;
	}

	public void setlNode(TreeNode lNode) {
		this.lNode = lNode;
	}

	public TreeNode getrNode() {
		return rNode;
	}

	public void setrNode(TreeNode rNode) {
		this.rNode = rNode;
	}

	public int getFeature() {
		return feature;
	}

	public void setFeature(int feature) {
		this.feature = feature;
	}

	// 面向对象分析，哪些属于类
	public void split() {

		// if (begin != end) {
		Data data = new Data(this.getBegin(), this.getEnd());
		int feature = data.getFeature();
		// System.out.println(this.getBegin() + "," + this.getEnd() + ","
		// + feature);
		if (0 != feature) {
			this.setFeature(feature);
			int mid = data.split(feature);
			this.setlNode(new TreeNode(begin, begin + mid));
			this.setrNode(new TreeNode(begin + mid + 1, end));
			this.getlNode().split();
			this.getrNode().split();
		} else {
			// 是叶结点
			// System.out.println(this.getBegin() + "," + this.getEnd() + ","
			// + data.getLeaf());
			// System.out.println(data.getLeaf());
			this.setFeature(data.getLeaf());
			this.setlNode(null);
			this.setrNode(null);
		}
		// }
	}
}
