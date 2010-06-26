package cn.doitoo.game.framework.arithmetic;

import java.util.ArrayList;
import java.util.List;

import cn.doitoo.game.framework.task.DrawGraphicTask;
import cn.doitoo.game.framework.util.Util;
import cn.doitoo.game.tankwar.task.DrawMapTask;

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
	 private static List<Pathxy> path_FLOYD(int adjVect[][]) {
		List<Pathxy> PathxyList= new ArrayList<Pathxy>();
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
					if (D[i][k] + D[k][j] < D[i][j]) {
						D[i][j] = D[i][k] + D[k][j];
						
					}
				}
			}
		}
		return null;
	}
	/**
	 * 01矩阵转换成邻接矩阵
	 * @param gameMap
	 * @param cols
	 * @param rows
	 * @return
	 */
	public static int[][] gameMap2adjVect(int[][] gameMap,int cols,int rows){
		int [][] adjVect = new int[cols*rows][cols*rows];
		for(int i=0;i<cols;i++){
			for(int j=0;j<rows;j++){
				int nextJ = Math.min(j+1, cols-1);
				int nextI = Math.min(i+1, rows-1);
				if(gameMap[i][j]==0){
				   adjVect[i+cols*j][nextI+cols*j]=Integer.MAX_VALUE;
				   adjVect[i+cols*j][i+cols*nextJ]=Integer.MAX_VALUE;
				}else{
					adjVect[i+cols*j][nextI+cols*j]=(gameMap[nextI][j]==0?Integer.MAX_VALUE:1);
  				    adjVect[i+cols*j][i+cols*nextJ]=(gameMap[i][nextJ]==0?Integer.MAX_VALUE:1);
				}
				adjVect[nextI+cols*j][i+cols*j] =  adjVect[i+cols*j][nextI+cols*j];
				adjVect[i+cols*nextJ][i+cols*j] =  adjVect[i+cols*j][i+cols*nextJ];
			}
		}
		
		return adjVect;
	}
	/**
	 * 游戏地图01矩阵，0表示障碍物，1表示通道
	 * @param gameMap
	 */
	public static List<Pathxy> computeShortestPath(int[][] gameMap,int cols,int rows){
		if(!not01map(gameMap,cols,rows))throw new RuntimeException("invalid game map,only 01 map is allowed");
		int adjVect[][] = gameMap2adjVect(gameMap, cols, rows);
		return path_FLOYD(adjVect);
	}
	
	private static boolean not01map(int[][] gameMap, int cols, int rows) {
	   for (int i = 0; i < cols; i++) {
		 for(int j=0;j<rows;j++){
			 if(gameMap[i][j]!=0&&gameMap[i][j]!=1)return false;
		 }
	}
		return true;
	}
	
	

}
