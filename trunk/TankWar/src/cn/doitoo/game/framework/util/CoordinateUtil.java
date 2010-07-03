package cn.doitoo.game.framework.util;

import android.graphics.Point;
import cn.doitoo.game.framework.context.G;
import cn.doitoo.game.framework.exception.ViewException;
import cn.doitoo.game.framework.map.DoitooMap;
import cn.doitoo.game.framework.math.IndexPosition;

/**
 * ����ϵ�����࣬x�������ʾ�������꣬����0��ʼ��
 * y�������ʾ�������꣬����0��ʼ��
 * @author Oliver O
 *
 */
public class CoordinateUtil {

    /**
     * ���������ͼ���±��ȡ��ǰ�±��������ͼ�ж�ӦX,Y����    *
     * 
     * @param node �±�ֵ
     * @return ��ǰ�±��������ͼ�ж�ӦX,Y����
     */
    public static Point node2WorldPoint(int node) {
    	DoitooMap map = G.getDoitooMap();
        if (map == null) {
            throw new ViewException("map is null");
        }
        IndexPosition point = MatrixIndexUtil.convertNode2Point(node, map.getMapRect()[0].length);
        return new Point(point.colIndex * map.getElementWidth(),point.rowIndex * map.getElementHeight() );
    }

    /**
     * ��Ļ����ת��������
     *
     * 
     * @param screenPoint ��Ļ��ͼ����
     */
    public static void screen2world( Point screenPoint) {
    	DoitooMap map = G.getDoitooMap();
        if (map == null) {
            throw new ViewException("map is null");
        }
        int wx = map.getX();
        int wy = map.getY();
        screenPoint.set(screenPoint.x - wx, screenPoint.y - wy);
    }
    /**
     * �������ͼ����ת��Ϊ��Ļ����
     *
     * 
     * @param worldPoint �����ͼ����
     */
    public static void world2screen(Point worldPoint) {
    	DoitooMap map = G.getDoitooMap();
        if (map == null) {
            throw new ViewException("map is null");
        }
        int wx = map.getX();
        int wy = map.getY();
        worldPoint.set(worldPoint.x + wx, worldPoint.y + wy);
    }
}
