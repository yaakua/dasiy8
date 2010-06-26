package cn.doitoo.game.framework.arithmetic;

import java.util.ArrayList;
import java.util.List;

import cn.doitoo.game.framework.task.DrawGraphicTask;
import cn.doitoo.game.framework.util.Util;
import cn.doitoo.game.tankwar.task.DrawMapTask;

/**
 * ���·���ĸ��������㷨�� ��֪������Ȩͼ�����ͼ��ÿ������֮������·����������
 * 
 * @author Oliver O
 * 
 */
public class Floyd {
	/**
	 * 
	 * @param adjVect
	 *            ���Ӿ��󣬴�������ڶ���֮��ľ��룬��������ھ���Ϊ���������������Ϊ��������
	 */
	 private static List<Pathxy> path_FLOYD(int adjVect[][]) {
		List<Pathxy> PathxyList= new ArrayList<Pathxy>();
		int i, j, k;
		int length = adjVect.length;// �������
		int D[][] = new int[length][length];// D���ÿ�Զ���֮������·��ֵ
		for (i = 0; i < length; i++) {// ���ڵ�֮��ĳ�ʼ��֪·��������
			for (j = 0; j < length; j++) {
				D[i][j] = adjVect[i][j];//
			}
		}// for

		for (k = 0; k < length; k++) {
			for (i = 0; i < length; i++) {
				for (j = 0; j < length; j++) {
					if (i == j)// �����迼��
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
	 * 01����ת�����ڽӾ���
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
	 * ��Ϸ��ͼ01����0��ʾ�ϰ��1��ʾͨ��
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
