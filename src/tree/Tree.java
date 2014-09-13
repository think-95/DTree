package tree;

import java.util.ArrayList;

import data.Data;

/**
 * @author:wzc
 * @version:11:13:27 AM
 */
public class Tree {

	private static boolean[][] dataTest = new Data().getDataTest();
	private static final int TREENUM = 5; 
	public static void main(String[] args) {
		Data data = new Data();
		TreeNode root = new TreeNode(0, data.getLine() - 1);
		root.split();
		int[][] result1 = test(root);
		int error = 0;
		int a = 0, b = 0, c = 0, d = 0;
		for (int i = 0; i < 30956; i++) {
			// System.out.println(i + "," + result1[i][0] + "," +
			// result1[i][1]);

			if (result1[i][0] != result1[i][1])
				error++;
			if (result1[i][0] == 1 && result1[i][1] == 1)
				a++;
			if (result1[i][0] == 1 && result1[i][1] == -1)
				b++;
			if (result1[i][0] == -1 && result1[i][1] == -1)
				c++;
			if (result1[i][0] == -1 && result1[i][1] == 1)
				d++;

		}
		System.out.println("使用决策树：");
		System.out.println("1识别为1：" + a);
		System.out.println("1识别为-1:" + b);
		System.out.println("-1识别为-1:" + c);
		System.out.println("-1识别为1:" + d);
		System.out.println("识别错误:" + error);
		System.out.println("识别正确：" + (a + c));
		// for decision tree test
		int i = 0;
		ArrayList<TreeNode> index = new ArrayList<>();
		for (i = 0; i < TREENUM; i++) {
			data = new Data();
			data.ensemble();
			TreeNode rootEnsemble = new TreeNode(0, data.getLine() - 1);
			rootEnsemble.split();
			index.add(rootEnsemble);
			data.refresh();
		}
		int[][] result2 = test(index);
		error = 0;
		a = 0;
		b = 0;
		c = 0;
		d = 0;
		for (i = 0; i < 30956; i++) {
			// System.out.println(i + "," + result2[i][0] + "," +
			// result2[i][1]);
			if (result2[i][0] != result2[i][1])
				error++;

			if (result2[i][0] == 1 && result2[i][1] == 1)
				a++;
			if (result2[i][0] == 1 && result2[i][1] == -1)
				b++;
			if (result2[i][0] == -1 && result2[i][1] == -1)
				c++;
			if (result2[i][0] == -1 && result2[i][1] == 1)
				d++;
		}
		System.out.println("使用随机森林：");
		System.out.println("1识别为1：" + a);
		System.out.println("1识别为-1:" + b);
		System.out.println("-1识别为-1:" + c);
		System.out.println("-1识别为1:" + d);
		System.out.println("识别错误:" + error);
		System.out.println("识别正确：" + (a + c));
	}

	private static int[][] test(TreeNode root) {
		int i = 0;
		int[][] result = new int[30956][2];
		int temp = 0;
		TreeNode tmp = new TreeNode();
		for (i = 0; i < 30956; i++) {
			tmp = root;
			while (tmp.getlNode() != null) {
				if (dataTest[i][tmp.getFeature()]) {
					tmp = tmp.getrNode();
				} else {
					tmp = tmp.getlNode();
				}
			}
			result[i][0] = (dataTest[i][0] == true) ? 1 : -1;
			result[i][1] = tmp.getFeature();
			// System.out.println(dataTest[i][0] + "," + tmp.getFeature());
		}

		return result;
	}

	private static int[][] test(ArrayList<TreeNode> index) {
		int i = 0, j = 0;
		TreeNode tmp = new TreeNode();
		int[][] result = new int[30956][2];
		int temp = 0;
		for (j = 0; j < 30956; j++) {
			for (i = 0; i < TREENUM; i++) {
				tmp = index.get(i);
				while (tmp.getlNode() != null) {
					if (dataTest[j][tmp.getFeature()]) {
						tmp = tmp.getrNode();
					} else {
						tmp = tmp.getlNode();
					}
				}
				if (tmp.getFeature() == 1)
					temp++;
			}
			result[j][0] = (dataTest[j][0] == true) ? 1 : -1;
			if (temp >= 3) {
				// System.out.println("temp>=3");
				result[j][1] = 1;
				temp = 0;
			} else {
				// System.out.println(temp);
				result[j][1] = -1;
				temp = 0;
			}
		}
		return result;
	}
}
