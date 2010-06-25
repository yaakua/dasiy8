package cn.doitoo.game.framework.arithmetic;

/**
 * 最短路径的弗洛伊德算法， 已知无向有权图，获得图中每个顶点之间的最短路径坐标序列
 * 
 * @author Oliver O
 * 
 */
public class Floyd {
	/**
	 * 
	 * @param adjVect
	 *            连接矩阵，存放着相邻顶点之间的距离，如果不相邻距离为无穷大，行数与列数为顶点数。
	 */
	 private static void path_FLOYD(int adjVect[][]) {
		int i, j, k;
		int length = adjVect.length;// 顶点个数
		int D[][] = new int[length][length];// D存放每对顶点之间的最短路径值
		for (i = 0; i < length; i++) {// 各节点之间的初始已知路径及距离
			for (j = 0; j < length; j++) {
				D[i][j] = adjVect[i][j];//
			}
		}// for

		for (k = 0; k < length; k++) {
			for (i = 0; i < length; i++) {
				for (j = 0; j < length; j++) {
					if (i == j)// 对角线上的元素（即顶点自身之间）不予考虑
						continue;
					if (D[i][k] + D[k][j] < D[i][j]) {// 从i经k到j的一条路径更短
						D[i][j] = D[i][k] + D[k][j];
						// System.out.print(" "+path[i][j]);
					}
				}
			}
		}
	}
	
	private static int[][] gameMap2adjVect(int[][] gameMap,int cols,int rows){
		return new int[4][];
	}
	
	public static void computeShortestPath(int[][] gameMap){
		//return something here
	}

}
