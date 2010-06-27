package cn.doitoo.game.framework.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import cn.doitoo.game.framework.exception.ViewException;
import cn.doitoo.game.framework.map.DoitooMap;

import java.util.Random;


/**
 * 常用工具类
 *
 * @author Oliver O
 */
public class Util {
    private static Random random = new Random();

    public static Bitmap getBitMapById(Context context, int id) {
        return BitmapFactory.decodeStream(context.getResources().openRawResource(id));
    }

    /**
     * 将地图排列数组转换成通道与障碍物类型的数组
     *
     * @param map       地图排列数组
     * @param passValue 当前地图为通道类型的值，凡是小于等于此值都为通道
     * @return 0代表障碍物 1 代表通道
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
     * 获取世界坐标某个点在世界地图排列数组当中的下标
     *
     * @param cols 世界地图排列数组总列数
     * @param i    当前点在世界地图的第i行
     * @param j    当前点在世界地图的第j行
     * @return 世界地图排列数组的下标
     */
    public static int convertPoint2Node(int cols, int i, int j) {
        return i + cols * j;
    }

    /**
     * 获取世界坐标某个点在世界地图排列数组当中的下标
     *
     * @param map        世界地图
     * @param worldPoint 世界地图坐标
     * @return 世界地图排列数组的下标
     */
    public static int worldPoint2Node(DoitooMap map, Point worldPoint) {
        if (map == null) {
            throw new ViewException("map is null");
        }
        int w = (int) map.getElementWidth();
        int h = (int) map.getElementHeight();
        int i = (worldPoint.x % w == 0) ? worldPoint.x / w : worldPoint.x / w + 1;
        int j = (worldPoint.y % h == 0) ? worldPoint.y / h : worldPoint.y / h + 1;
        return convertPoint2Node(map.getMapRect()[0].length, Math.max(0, i-1), Math.max(0, j-1));
    }

    /**
     * 获取世界地图排列数组当中的下标对应的世界地图坐标点
     *
     * @param node 世界地图排列数组的下标
     * @param cols 世界地图排列数组总列数
     * @return 世界地图坐标
     */
    public static Point convertNode2Point(int node, int cols) {
        return new Point(node % cols, node / cols);
    }

    /**
     * @param x 游戏地图矩阵坐标
     * @param y
     */
    public static Point getDestination(int gameMap01[][], int x, int y) {
        if (gameMap01[x][y] == 1) return new Point(x, y);
        int x1 = x;
        int y1 = y;
        while (gameMap01[x1--][y1] == 0 && x1 > 0) ;
        Point p1 = new Point(x1 + 1, y1);
        int d1 = Math.abs(x1 - x);
        int d = d1;
        Point p = p1;
        x1 = x;
        while (gameMap01[x1++][y1] == 0 && x1 < gameMap01[0].length) ;
        Point p2 = new Point(x1 + 1, y1);
        int d2 = Math.abs(x1 - x);
        if (d2 < d) {
            d = d2;
            p = p2;
        }
        while (gameMap01[x1][y1--] == 0 && y1 > 0) ;
        Point p3 = new Point(x1 + 1, y1);
        int d3 = Math.abs(y1 - y);
        if (d3 < d) {
            d = d3;
            p = p3;
        }
        y1 = y;
        while (gameMap01[x1][y1++] == 0 && y1 < gameMap01.length) ;
        Point p4 = new Point(x1 + 1, y1);
        int d4 = Math.abs(y1 - y);
        if (d4 < d) {
            d = d4;
            p = p4;
        }

        return p;
    }

    /**
     * @return 随机获取一个指定范围内的值
     */
    public static int getRandomDirect(int range) {
        return random.nextInt(range);
    }

    /**
     * 将世界地图坐标转换为屏幕坐标
     *
     * @param map        世界地图对象
     * @param worldPoint 世界地图坐标
     */
    public static void world2screen(DoitooMap map, Point worldPoint) {
        if (map == null) {
            throw new ViewException("map is null");
        }
        int wx = (int) map.getX();
        int wy = (int) map.getY();
        worldPoint.set(worldPoint.x + wx, worldPoint.y + wy);
    }

    /**
     * 屏幕坐标转世界坐标
     *
     * @param map         世界地图对象
     * @param screenPoint 屏幕地图坐标
     */
    public static void screen2world(DoitooMap map, Point screenPoint) {
        if (map == null) {
            throw new ViewException("map is null");
        }
        int wx = (int) map.getX();
        int wy = (int) map.getY();
        screenPoint.set(screenPoint.x - wx, screenPoint.y - wy);
    }

}
