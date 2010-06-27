package cn.doitoo.game.framework.arithmetic;

import java.util.ArrayList;
import java.util.List;

/**
 * ���·���ĸ��������㷨�� ��֪������Ȩͼ�����ͼ��ÿ������֮������·����������
 * 
 * @author Oliver O
 * 
 */
public class Floyd extends PathSolver{
	/**
	 * 
	 * @param adjVect
	 *            ���Ӿ��󣬴�������ڶ���֮��ľ��룬��������ھ���Ϊ���������������Ϊ��������
	 */
	 public  List<Pathxy> solvePath(int adjVect[][],int gameMapcols) {
		List<Pathxy> pathxyList= new ArrayList<Pathxy>();
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
