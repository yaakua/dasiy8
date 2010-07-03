package cn.doitoo.game.framework.util;

import android.graphics.Point;
import cn.doitoo.game.framework.context.G;
import cn.doitoo.game.framework.exception.ViewException;
import cn.doitoo.game.framework.map.DoitooMap;
import cn.doitoo.game.framework.math.IndexPosition;

/**
 * 坐标系工具类，x坐标均表示横向坐标，并从0开始。
 * y坐标均表示纵向坐标，并从0开始。
 * @author Oliver O
 *
 */
public class CoordinateUtil {

    /**
     * 根据世界地图的下标获取当前下标在世界地图中对应X,Y坐标    *
     * 
     * @param node 下标值
     * @return 当前下标在世界地图中对应X,Y坐标
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
     * 屏幕坐标转世界坐标
     *
     * 
     * @param screenPoint 屏幕地图坐标
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
     * 将世界地图坐标转换为屏幕坐标
     *
     * 
     * @param worldPoint 世界地图坐标
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
