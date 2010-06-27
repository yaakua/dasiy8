package cn.doitoo.game.framework.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import cn.doitoo.game.framework.exception.ViewException;
import cn.doitoo.game.framework.map.DoitooMap;

import java.util.Random;


/**
 * ���ù�����
 *
 * @author Oliver O
 */
public class Util {
    private static Random random = new Random();

    public static Bitmap getBitMapById(Context context, int id) {
        return BitmapFactory.decodeStream(context.getResources().openRawResource(id));
    }

    /**
     * ����ͼ��������ת����ͨ�����ϰ������͵�����
     *
     * @param map       ��ͼ��������
     * @param passValue ��ǰ��ͼΪͨ�����͵�ֵ������С�ڵ��ڴ�ֵ��Ϊͨ��
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
     * ��ȡ��������ĳ�����������ͼ�������鵱�е��±�
     *
     * @param cols �����ͼ��������������
     * @param i    ��ǰ���������ͼ�ĵ�i��
     * @param j    ��ǰ���������ͼ�ĵ�j��
     * @return �����ͼ����������±�
     */
    public static int convertPoint2Node(int cols, int i, int j) {
        return i + cols * j;
    }

    /**
     * ��ȡ��������ĳ�����������ͼ�������鵱�е��±�
     *
     * @param map        �����ͼ
     * @param worldPoint �����ͼ����
     * @return �����ͼ����������±�
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
     * ��ȡ�����ͼ�������鵱�е��±��Ӧ�������ͼ�����
     *
     * @param node �����ͼ����������±�
     * @param cols �����ͼ��������������
     * @return �����ͼ����
     */
    public static Point convertNode2Point(int node, int cols) {
        return new Point(node % cols, node / cols);
    }

    /**
     * @param x ��Ϸ��ͼ��������
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
     * @return �����ȡһ��ָ����Χ�ڵ�ֵ
     */
    public static int getRandomDirect(int range) {
        return random.nextInt(range);
    }

    /**
     * �������ͼ����ת��Ϊ��Ļ����
     *
     * @param map        �����ͼ����
     * @param worldPoint �����ͼ����
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
     * ��Ļ����ת��������
     *
     * @param map         �����ͼ����
     * @param screenPoint ��Ļ��ͼ����
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
