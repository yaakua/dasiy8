package cn.doitoo.game.framework.arithmetic;

import java.util.List;

public abstract class PathSolver {
	/**
	 * 01����ת�����ڽӾ���
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
     * ��ȡ�����ڵ�֮������·��
     * @param gameMap �����ͼ01��������
     * @param startNode  ��ʼ�����������ͼ���е��±�ֵ���ڵ㣩
     * @param endNode   ���������������ͼ���е��±�ֵ���ڵ㣩
     * @return ��������·�������ͼ���е��±꼯��
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
	 * @param startV ����Ϸ��ͼ�е�λ�ã���0��ʼ����Ϸ��ͼԪ���ܸ���-1
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
