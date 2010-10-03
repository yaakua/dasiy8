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
 * 常用工具类
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
     * 将地图排列数组转换成通道与障碍物类型的数组
     *
     * @param map          地图排列数组
     * @param barrierArray 障碍物数组
     * @return 0代表障碍物 1 代表通道
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
     * @return 随机获取一个指定范围内的值
     */
    public static int getRandomDirect(int range) {
        return random.nextInt(range);
    }

    /**
     * 计算坦克与点击坐标间的最短路径
     *
     * @param startNodePoint 坦克在世界地图中坐标点
     * @param endNodePoint   用户点击在世界地图中的坐标
     */
    @SuppressWarnings("unchecked")
    public static List computeShortestPath(Point startNodePoint, Point endNodePoint) {
        int startNode = MatrixIndexUtil.worldPoint2Node(startNodePoint);
        IndexPosition indexP = MatrixIndexUtil.worldPointIn01Vector(endNodePoint); //获取当前坐标在01矩阵中的第i行，第j列
        indexP = MatrixIndexUtil.getDestination((int[][]) G.get("gameMap01Vector"), indexP.rowIndex, indexP.colIndex); //获取坐标点最近的通道第I行，第J列
        int index = MatrixIndexUtil.convertPoint2Node(G.getDoitooMap().getMapArray()[0].length, indexP.rowIndex, indexP.colIndex);  //获取世界坐标某个点在世界地图排列数组当中的下标
        PathSolver pathSolver = (PathSolver) G.get("pathSolver");
        return pathSolver.computeShortestPath((int[][]) G.get("gameMap01Vector"), startNode, index);

    }


    /**
     * 判断一个值是否在一个数组里
     *
     * @param array 已从小到大排列好的数组
     * @param index 要查找的值
     * @return 在的话就返回true,
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
