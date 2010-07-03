package cn.doitoo.game.framework.util;

import android.graphics.Point;
import cn.doitoo.game.framework.context.G;
import cn.doitoo.game.framework.exception.ViewException;
import cn.doitoo.game.framework.map.DoitooMap;
import cn.doitoo.game.framework.math.IndexPosition;

/**
 * �±�ϵ��ع����ࡣ��ά�����е��±�ϵ��
 * rowIndex ��ʾ�ڼ��� ��0��ʼ
 * colIndex ��ʾ�ڼ��д�0��ʼ
 * @author Oliver O
 *
 */
public class MatrixIndexUtil {

	 /**
     * 
     *
     * @param cols �����ͼ��������������
     * @param rowIndex    ��ǰ���������ͼ�ĵ�rowIndex��
     * @param colIndex    ��ǰ���������ͼ�ĵ�colIndex��
     * @return node �����ͼ����������±�
     */
    public static int convertPoint2Node(int cols, int rowIndex, int colIndex) {
        return colIndex + cols * rowIndex;
    }
    
    /**
     * ��ȡ��������ĳ�����������ͼ�������鵱�е��±�
     *
     * 
     * @param worldPoint ��������
     * @return �����ͼ����������±�
     */
    public static int worldPoint2Node( Point worldPoint) {
    	DoitooMap map = G.getDoitooMap();
    	IndexPosition p = worldPointIn01Vector( worldPoint); //��ȡ����������01�����еĵڼ��еڼ���
        return convertPoint2Node(map.getMapRect()[0].length, Math.max(0, p.rowIndex), Math.max(0, p.colIndex));
    }
    
    /**
     * ����������01�����еĵڼ��еڼ���
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
     * ��ȡ�����ͼ�������鵱�е��±��Ӧ�������ͼ���������rowIndex��colIndex��
     *
     * @param node �����ͼ����������±�
     * @param cols �����ͼ��������������
     * @return �����ͼ����
     */
    public static IndexPosition convertNode2Point(int node, int cols) {
        return new IndexPosition(node % cols, node / cols);
    }

    
    /**
     * ��ȡָ������ΧΪͨ���ĵ�
     *
     * @param gameMap01 �����ͼ01����
     * @param rowIndex         ��ǰ���������ͼ�ĵڼ���,��0��ʼ
     * @param colIndex         ��ǰ���������ͼ�ĵڼ��У���0��ʼ
     * @return ͨ����01�����еĵ�rowIndex�У���colIndex��
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
