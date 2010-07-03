package cn.doitoo.game.framework.util;

import java.util.List;
import java.util.Random;

import cn.doitoo.game.framework.arithmetic.PathSolver;
import cn.doitoo.game.framework.context.G;
import cn.doitoo.game.framework.math.IndexPosition;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

/**
 * ���ù�����
 * 
 * @author Oliver O
 */
public class Util {
	private static Random random = new Random();

	public static Bitmap getBitMapById(Context context, int id) {
		return BitmapFactory.decodeStream(context.getResources()
				.openRawResource(id));
	}

	/**
	 * ����ͼ��������ת����ͨ�����ϰ������͵�����
	 * 
	 * @param map
	 *            ��ͼ��������
	 * @param passValue
	 *            ��ǰ��ͼΪͨ�����͵�ֵ������С�ڵ��ڴ�ֵ��Ϊͨ��
	 * @return 0�����ϰ��� 1 ����ͨ��
	 */
	public static int[][] gameMap01Vector(byte[][] map, int passValue) {
		int[][] vector = new int[map.length][map[0].length];
		for (int i = 0; i < map.length; i++) {
			int[] x = new int[map[i].length];
			for (int j = 0; j < map[i].length; j++) {
				int y = map[i][j];
				y = (y <= passValue) ? 1 : 0;
				x[j] = y;
			}
			vector[i] = x;
		}
		return vector;
	}

	/**
	 * @return �����ȡһ��ָ����Χ�ڵ�ֵ
	 */
	public static int getRandomDirect(int range) {
		return random.nextInt(range);
	}
	
	 /**
     * ����̹���������������·��
     *
     * @param startNodePoint ̹���������ͼ�������
     * @param endNodePoint   �û�����������ͼ�е�����
     */
    @SuppressWarnings("unchecked")
	public static List computeShortestPath(Point startNodePoint, Point endNodePoint) {
        int startNode = MatrixIndexUtil.worldPoint2Node( startNodePoint);
        IndexPosition indexP = MatrixIndexUtil.worldPointIn01Vector( endNodePoint); //��ȡ��ǰ������01�����еĵ�i�У���j��
        indexP = MatrixIndexUtil.getDestination((int[][]) G.get("gameMap01Vector"), indexP.rowIndex, indexP.colIndex); //��ȡ����������ͨ����I�У���J��
        int index = MatrixIndexUtil.convertPoint2Node(G.getDoitooMap().getMapRect()[0].length, indexP.rowIndex,indexP.colIndex);  //��ȡ��������ĳ�����������ͼ�������鵱�е��±�
        PathSolver  pathSolver = (PathSolver) G.get("pathSolver");
        return pathSolver.computeShortestPath((int[][]) G.get("gameMap01Vector"), startNode, index);
       
    }
	

}
