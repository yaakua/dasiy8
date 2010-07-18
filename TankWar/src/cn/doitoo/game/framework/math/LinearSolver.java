package cn.doitoo.game.framework.math;

import android.graphics.Point;
import android.util.FloatMath;

/**
 * 线性问题的解决
 *
 * @author Oliver O
 */
public class LinearSolver {

    /**
     * 判断两条直线是否平行
     *
     * @return
     */
    public static boolean isParallel(float L1x1, float L1y1, float L1x2, float L1y2, float L2x1, float L2y1, float L2x2, float L2y2) {

        float deltaL1X = L1x1 - L1x2;
        float deltaL1Y = L1y1 - L1y2;

        float deltaL2X = L2x1 - L2x2;
        float deltaL2Y = L2y1 - L2y2;

        if (deltaL1X == 0 && deltaL2X == 0) return true;
        if (deltaL1X * deltaL2X == 0) return false;

        float k1 = deltaL1Y / deltaL1X;
        float k2 = deltaL2Y / deltaL2X;


        return Math.abs(k1) == Math.abs(k2);
    }


    /**
     * 计算两条直线的交点
     *
     * @param L1x1
     * @param L1y1
     * @param L1x2
     * @param L1y2
     * @param L2x1
     * @param L2y1
     * @param L2x2
     * @param L2y2
     */
    public static Point intersectionPoint(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {
        if (isParallel(x1, y1, x2, y2, x3, y3, x4, y4)) return null;
        Point intersectPoint = new Point();
        intersectPoint.x = (int) (((x1 - x2) * (x3 * y4 - x4 * y3) - (x3 - x4)
                * (x1 * y2 - x2 * y1)) / ((x3 - x4) * (y1 - y2) - (x1 - x2)
                * (y3 - y4)));

        intersectPoint.y = (int) (((y1 - y2) * (x3 * y4 - x4 * y3) - (x1
                * y2 - x2 * y1)
                * (y3 - y4)) / ((y1 - y2) * (x3 - x4) - (x1 - x2)
                * (y3 - y4)));
        return intersectPoint;

    }

    /**
     * 计算向量交点
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param x3
     * @param y3
     * @param x4
     * @param y4
     * @return
     */
    public static Point vectorIntsecPoint(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {
        if (isParallel(x1, y1, x2, y2, x3, y3, x4, y4)) return null;
        Point p = intersectionPoint(x1, y1, x2, y2, x3, y3, x4, y4);
        if (distance(x1, y1, p.x, p.y) < distance(x2, y2, p.x, p.y) || distance(x3, y3, p.x, p.y) < distance(x4, y4, p.x, p.y))
            return null;
        return p;
    }

    /**
     * 计算两点之间的距离
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    public static float distance(float x1, float y1, float x2,float y2){
		return FloatMath.sqrt((float) (Math.pow(x1-x2, 2)+Math.pow(y1-y2, 2)));
	}
	
	
	
	
}
