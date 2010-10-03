package cn.doitoo.game.framework.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.util.Log;
import cn.doitoo.game.framework.arithmetic.PathSolver;
import cn.doitoo.game.framework.context.G;
import cn.doitoo.game.framework.math.IndexPosition;

import java.util.List;
import java.util.Random;

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
     * @param map          ��ͼ��������
     * @param barrierArray �ϰ�������
     * @return 0�����ϰ��� 1 ����ͨ��
     */
    public static int[][] gameMap01Vector(byte[][] map, int[] barrierArray) {
        int[][] vector = new int[map.length][map[0].length];
        for (int i = 0; i < map.length; i++) {
            int[] x = new int[map[i].length];
            for (int j = 0; j < map[i].length; j++) {
                int y = map[i][j];
                y = Util.inArray(barrierArray, y) ? 0 : 1;
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
        int startNode = MatrixIndexUtil.worldPoint2Node(startNodePoint);
        IndexPosition indexP = MatrixIndexUtil.worldPointIn01Vector(endNodePoint); //��ȡ��ǰ������01�����еĵ�i�У���j��
        indexP = MatrixIndexUtil.getDestination((int[][]) G.get("gameMap01Vector"), indexP.rowIndex, indexP.colIndex); //��ȡ����������ͨ����I�У���J��
        int index = MatrixIndexUtil.convertPoint2Node(G.getDoitooMap().getMapArray()[0].length, indexP.rowIndex, indexP.colIndex);  //��ȡ��������ĳ�����������ͼ�������鵱�е��±�
        PathSolver pathSolver = (PathSolver) G.get("pathSolver");
        return pathSolver.computeShortestPath((int[][]) G.get("gameMap01Vector"), startNode, index);

    }


    /**
     * �ж�һ��ֵ�Ƿ���һ��������
     *
     * @param array �Ѵ�С�������кõ�����
     * @param index Ҫ���ҵ�ֵ
     * @return �ڵĻ��ͷ���true,
     */
    public static boolean inArray(int[] array, int index) {
//        Log.d("InArrayIndex:",index+"");
        int first = 0;
        int last = array.length;
        if (last == 0)
            return false;
        int mid;
        while (first < last) {
            if (last < 0 || last > array.length) {
                return false;
            }
            mid = (first + last) / 2;
            int midValue = array[mid];
            if (midValue == index) {
                return true;
            } else if (midValue < index) {
                first = mid + 1;
            } else if (midValue > index) {
                last = mid - 1;
            }
        }
        return false;
    }

}
