package cn.doitoo.game.framework.arithmetic;

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
	 private static void path_FLOYD(int adjVect[][]) {
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
					if (i == j)// �Խ����ϵ�Ԫ�أ�����������֮�䣩���迼��
						continue;
					if (D[i][k] + D[k][j] < D[i][j]) {// ��i��k��j��һ��·������
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
