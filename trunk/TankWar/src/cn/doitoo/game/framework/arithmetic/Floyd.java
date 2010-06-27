package cn.doitoo.game.framework.arithmetic;

import java.util.ArrayList;
import java.util.List;

/**
 * 最短路径的弗洛伊德算法， 已知无向有权图，获得图中每个顶点之间的最短路径坐标序列
 * 
 * @author Oliver O
 * 
 */
public class Floyd extends PathSolver{
	/**
	 * 
	 * @param adjVect
	 *            连接矩阵，存放着相邻顶点之间的距离，如果不相邻距离为无穷大，行数与列数为顶点数。
	 */
	 public  List<Pathxy> solvePath(int adjVect[][],int gameMapcols) {
		List<Pathxy> pathxyList= new ArrayList<Pathxy>();
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
					if (i == j)// 自身不予考虑
						continue;
					if (D[i][k] + D[k][j] < D[i][j]) {//i->k-j is the shortest path
						D[i][j] = D[i][k] + D[k][j];
						Pathxy  pathxy = new Pathxy();
						pathxy.startPosition.x = i/gameMapcols;
						pathxy.startPosition.y = i%gameMapcols;
						pathxy.midPosition.x = k/gameMapcols;
						pathxy.midPosition.y = k%gameMapcols;
						pathxy.endPosition.x = j/gameMapcols;
						pathxy.endPosition.y = j/gameMapcols;
						pathxyList.add(pathxy);
						
					}
				}
			}
		}
		return pathxyList;
	}

	@Override
	public List solvePath(int[][] adjVect, int startV, int endV) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	

}
