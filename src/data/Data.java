package data;

import java.util.Random;

import file.ReadFile;

/**
 * @author:wzc
 * @version:4:17:41 PM
 */
public class Data {
	private final static int featureNum = 123;
	private int[][] feature = new int[featureNum + 1][6];
	private int oneNum = 0;
	private int zeroNum = 0;
	// private int featureOneNum = 0;
	// private int featureZeroNum = 0;
	private static ReadFile rf = new ReadFile();
	private static ReadFile rfTest = new ReadFile("E:\\a1a.test");//测试数据目录
	private static boolean[][] data = rf.format();
	private static boolean[][] dataTest = rfTest.format();
	private static int line = rf.getLine();
	private int begin = 0;
	private int end = 0;
	// private int same = 0;
	private int numLine;

	public static boolean[][] getDataTest() {
		return dataTest;
	}

	public Data() {
		// TODO Auto-generated constructor stub
		this.numLine = line;
	}

	public Data(int begin, int end) {
		// TODO Auto-generated constructor stub
		this.begin = begin;
		this.end = end;
		this.numLine = end - begin + 1;
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

	// 随机选属性的时候，可以把属性设置为false

	public int[][] count() {

		boolean tmp = false;
		// boolean same = false;
		int i = 0, j = 0;
		for (i = begin; i <= end; i++) {
			tmp = data[i][0];
			//
			if (tmp) {
				oneNum++;
				for (j = 1; j <= featureNum; j++) {
					tmp = data[i][j];
					if (tmp) {
						feature[j][0]++;// 1，1总数

					} else {
						feature[j][1]++;// 1，0总数
					}
				}

			} else {
				zeroNum++;
				// same做了假设
				// same = false;
				for (j = 1; j <= featureNum; j++) {
					tmp = data[i][j];
					if (tmp) {
						feature[j][2]++;// 0,1
					} else {
						feature[j][3]++;// 0,0
					}
				}
			}
		}
		for (j = 1; j <= featureNum; j++) {
			feature[j][4] = feature[j][0] + feature[j][2];// 1
			feature[j][5] = feature[j][1] + feature[j][3];// 0
		}

		return null;
	}

	public int split(int feature) {

		int i = 0;
		int j = 0;
		int note1 = end;
		boolean a;

		for (i = begin; i <= end; i++) {
			if (data[i][feature]) {
				// 附加了一个前提，一定能找到
				for (; note1 > i; note1--) {
					if (data[note1][feature]) {
						continue;
					} else {
						for (j = 0; j <= featureNum; j++) {
							a = data[i][j];
							data[i][j] = data[note1][j];
							data[note1][j] = a;
						}
						break;
					}
				}
				// 状态，该变，不该变
				// note1--;
			}
			// 程序写的便于调试
		}
		//
		// System.out.println(begin + "," + end + ","
		// + data[this.feature[feature][5] - 1][feature] + ","
		// + this.feature[feature][4] + "," + this.feature[feature][5]
		// + "," + feature + "," + zeroNum + "," + oneNum);
		return this.feature[feature][5] - 1;
	}

	public int getFeature() {
		// 首先测试目标feature是否一致
		int j;
		int maxFeature = 0;
		double max = 0;
		double gainRatio = 0;
		double entropy = 0;
		double tmp = 0;
		double p1, p2;
		// double lg2 = Math.log(2);
		count();

		tmp = (double) zeroNum / (double) numLine;

		if (zeroNum == 0 || oneNum == 0)
			return 0;
		// 并行，因为数据一份
		entropy = -tmp * NewMath.log(tmp) - (1 - tmp) * NewMath.log(1 - tmp);
		// System.out.println(entropy);
		for (j = 1; j <= featureNum; j++) {

			if (feature[j][4] == 0 || feature[j][5] == 0)
				continue;
			tmp = (double) feature[j][4] / (double) numLine;
			p1 = (double) feature[j][0] / (double) feature[j][4];
			p2 = (double) feature[j][1] / (double) feature[j][5];
			// 最后的数据需要什么 feature数组每必要设置为6
			gainRatio = entropy + tmp
					* (-p1 * NewMath.log(p1) - (1 - p1) * NewMath.log(1 - p1))
					+ (1 - tmp)
					* (-p2 * NewMath.log(p2) - (1 - p2) * NewMath.log(1 - p2));
			gainRatio = gainRatio/(-tmp*NewMath.log(tmp)-(1-tmp)*NewMath.log(1-tmp));
			// System.out.println(gainRatio);
			// why
			/*
			 * if (Double.isNaN(gainRatio)) System.out.println(tmp + "," +
			 * feature[j][0] + "," + p1 + "," + p2);
			 */
			if (gainRatio > max) {
				max = gainRatio;
				maxFeature = j;
			}
		}
		// System.out.println(max);
		// System.out.println(maxFeature);
		return maxFeature;
	}

	public int getLeaf() {
		boolean tmp = data[begin][0];
		//默认了tmp为boolean
		if (tmp) {
			return 1;
		} else {
			return -1;
		}
	}

	public void ensemble() {
		// 清0 //8 features
		int i = 0, j = 0;
		final int num=8;//属性的个数
		Random rdm = new Random();
		int[] block = new int[num];
		for (i = 0; i < num; i++) {
			block[i] = rdm.nextInt(122) + 1;
		}
		for (i = 0; i < 30956; i++) {
			for (j = 0; j < num; j++) {
				data[i][block[j]] = false;
			}
		}

	}

	public void refresh() {
		data = rf.format();
	}
}
