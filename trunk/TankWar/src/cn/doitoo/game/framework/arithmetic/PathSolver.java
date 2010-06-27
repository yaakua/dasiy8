package cn.doitoo.game.framework.arithmetic;

import java.util.List;

public abstract class PathSolver {
	/**
	 * 01矩阵转换成邻接矩阵
	 * @param gameMap
	 * @param cols
	 * @param rows
	 * @return
	 */
	public  int[][] gameMap2adjVect(int[][] gameMap,int cols,int rows){
		int [][] adjVect = new int[cols*rows][cols*rows];
		initAdjVect(adjVect);		
		for(int i=0;i<rows;i++){
			for(int j=0;j<cols;j++){
				int nextI = Math.min(i+1, rows-1);
				int nextJ = Math.min(j+1, cols-1);
				if(gameMap[i][j]==1){
					adjVect[j+cols*i][nextJ+cols*i]=(gameMap[i][nextJ]==0?Integer.MAX_VALUE:1);
					adjVect[j+cols*i][j+cols*nextI]=(gameMap[nextI][j]==0?Integer.MAX_VALUE:1);
				}
				adjVect[nextJ+cols*i][j+cols*i] =  adjVect[j+cols*i][nextJ+cols*i];
				adjVect[j+cols*nextI][j+cols*i] =  adjVect[j+cols*i][j+cols*nextI];
			}
		}
		
		return adjVect;
	}
	
	private  void initAdjVect(int[][] adjVect) {
	    for (int i = 0; i < adjVect.length; i++) {
			for (int j = 0; j < adjVect.length; j++) {
				adjVect[i][j]=Integer.MAX_VALUE;
			}
		}
		
	}

    /**
     * 获取两个节点之间的最短路径
     * @param gameMap 世界地图01矩阵数组
     * @param startNode  开始点所在世界地图当中的下标值（节点）
     * @param endNode   结束点所在世界地图当中的下标值（节点）
     * @return 两点间的线路在世界地图当中的下标集合
     */
	public  List computeShortestPath(int[][] gameMap,int startNode,int endNode){
        int cols = gameMap[0].length;
        int rows = gameMap.length;
		if(!not01map(gameMap,cols,rows))throw new RuntimeException("invalid game map,only 01 map is allowed");
		int adjVect[][] = gameMap2adjVect(gameMap, cols, rows);
		return solvePath(adjVect,startNode,endNode);
	}
	
	/**
	 * 
	 * @param adjVect
	 * @param startV 在游戏地图中的位置，从0开始到游戏地图元素总个数-1
	 * @return
	 */
	protected abstract List solvePath(int[][] adjVect,int startV,int endV) ;

	private  boolean not01map(int[][] gameMap, int cols, int rows) {
	   for (int i = 0; i < cols; i++) {
		 for(int j=0;j<rows;j++){
			 if(gameMap[i][j]!=0&&gameMap[i][j]!=1)return false;
		 }
	}
		return true;
	}
}
