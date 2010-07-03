package cn.doitoo.game.framework.util;

import android.graphics.Point;
import cn.doitoo.game.framework.context.G;
import cn.doitoo.game.framework.exception.ViewException;
import cn.doitoo.game.framework.map.DoitooMap;
import cn.doitoo.game.framework.math.IndexPosition;

/**
 * 下标系相关工具类。二维数组中的下标系。
 * rowIndex 表示第几行 从0开始
 * colIndex 表示第几列从0开始
 * @author Oliver O
 *
 */
public class MatrixIndexUtil {

	 /**
     * 
     *
     * @param cols 世界地图排列数组总列数
     * @param rowIndex    当前点在世界地图的第rowIndex行
     * @param colIndex    当前点在世界地图的第colIndex列
     * @return node 世界地图排列数组的下标
     */
    public static int convertPoint2Node(int cols, int rowIndex, int colIndex) {
        return colIndex + cols * rowIndex;
    }
    
    /**
     * 获取世界坐标某个点在世界地图排列数组当中的下标
     *
     * 
     * @param worldPoint 世界坐标
     * @return 世界地图排列数组的下标
     */
    public static int worldPoint2Node( Point worldPoint) {
    	DoitooMap map = G.getDoitooMap();
    	IndexPosition p = worldPointIn01Vector( worldPoint); //获取世界坐标在01矩阵当中的第几行第几列
        return convertPoint2Node(map.getMapRect()[0].length, Math.max(0, p.rowIndex), Math.max(0, p.colIndex));
    }
    
    /**
     * 世界坐标在01矩阵当中的第几行第几列
     *
     * @param map
     * @param worldPoint
     * @return 
     */
    public static IndexPosition worldPointIn01Vector( Point worldPoint) {
    	DoitooMap map = G.getDoitooMap();
        if (map == null) {
            throw new ViewException("map is null");
        }
        int w = map.getElementWidth();
        int h = map.getElementHeight();
        int i = worldPoint.x / w;
        int j = worldPoint.y / h;
        IndexPosition p = new IndexPosition(j, i);
        return p;
    }
    
    /**
     * 获取世界地图排列数组当中的下标对应的世界地图排列数组的rowIndex行colIndex列
     *
     * @param node 世界地图排列数组的下标
     * @param cols 世界地图排列数组总列数
     * @return 世界地图坐标
     */
    public static IndexPosition convertNode2Point(int node, int cols) {
        return new IndexPosition(node % cols, node / cols);
    }

    
    /**
     * 获取指定点周围为通道的点
     *
     * @param gameMap01 世界地图01矩阵
     * @param rowIndex         当前点在世界地图的第几行,从0开始
     * @param colIndex         当前点在世界地图的第几列，从0开始
     * @return 通道在01矩阵当中的第rowIndex行，第colIndex列
     */
    public static IndexPosition getDestination(int gameMap01[][], int rowIndex, int colIndex) {
        if (gameMap01[rowIndex][colIndex] == 1) return new IndexPosition(rowIndex, colIndex);
        int x1 = rowIndex;
        int y1 = colIndex;
        while (gameMap01[x1--][y1] == 0 && x1 > 0) ;
        IndexPosition p1 = new IndexPosition(x1 + 1, y1);
        int d1 = Math.abs(x1 - rowIndex);
        int d = d1;
        IndexPosition p = p1;
        x1 = rowIndex;
        while (gameMap01[x1++][y1] == 0 && x1 < gameMap01[0].length) ;
        IndexPosition p2 = new IndexPosition(x1 - 1, y1);
        int d2 = Math.abs(x1 - rowIndex);
        if (d2 < d) {
            d = d2;
            p = p2;
        }
        while (gameMap01[x1][y1--] == 0 && y1 > 0) ;
        IndexPosition p3 = new IndexPosition(x1, y1+1);
        int d3 = Math.abs(y1 - colIndex);
        if (d3 < d) {
            d = d3;
            p = p3;
        }
        y1 = colIndex;
        while (gameMap01[x1][y1++] == 0 && y1 < gameMap01.length) ;
        IndexPosition p4 = new IndexPosition(x1 , y1-1);
        int d4 = Math.abs(y1 - colIndex);
        if (d4 < d) {
            d = d4;
            p = p4;
        }

        return p;
    }
}
