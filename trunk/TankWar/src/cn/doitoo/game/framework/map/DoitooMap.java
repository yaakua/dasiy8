package cn.doitoo.game.framework.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import cn.doitoo.game.framework.context.G;
import cn.doitoo.game.framework.event.TouchEventHandler;
import cn.doitoo.game.framework.exception.ViewException;
import cn.doitoo.game.framework.role.MovableRole;
import cn.doitoo.game.framework.util.CoordinateUtil;
import cn.doitoo.game.framework.util.Util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DoitooMap extends MovableRole {

    private TouchEventHandler touchEventHandler;

    /**
     * �����ͼԪ����ɼ��ϣ����ͼ�������鵱�е�ֵ������һ�¡� <code>
     * ���£�
     * //��ͼ��������maps������1��2��3  3������Ԫ��
     * int[][] maps={{1,2,3},{1,3,1}}
     * //���� ��ǰ���ϵ���Ӧ�������д��������Ͷ�Ӧ��Bitmap����
     * </code>
     */
    public static Map<String, Bitmap> MapSourceArrays = new LinkedHashMap<String, Bitmap>();

    /**
     * ��ǰ��ͼ���д��ڵ��ϰ���������꼯�ϣ�ÿ�λ滭��ͼʱ���¸��´˼���
     */
    private List<Rect> barrierRectList = new ArrayList<Rect>();

    /**
     * ��ǰ��ͼ���д��ڵ�ͨ���������꼯�ϣ�ÿ�λ滭��ͼʱ���¸��´˼���
     */
    private List<Rect> passRectList = new ArrayList<Rect>();

    /**
     * ��ǰ��ͼ��������
     */
    private byte[][] mapArray;
    /**
     * ��ǰ��ͼ������
     */
    private int rows;
    /**
     * ��ǰ��ͼ������
     */
    private int cols;
    /**
     * �����ͼ���
     */
    private int width;
    /**
     * �����ͼ�߶�
     */
    private int height;

    /**
     * �����ϰ�ֵ������
     */
    public int[] barrierArray = {};

    /**
     * ��Ļ����ʾ������
     */
    private int screenRows;
    /**
     * ��Ļ����ʾ������
     */
    private int screenCols;

    /**
     * ��ͼ���ͼƬ
     */
    private Bitmap spliterMap;

    private int mapElementWidth;
    private int mapElementHeight;

    /**
     * ���������ͼ������ʼ����ͼ�ĸ߿����ص�ͼԪ�ض�Ӧ��ͼƬ��Դ
     *
     * @param mapArray         ��ͼ��������
     * @param barrierArray     ��ǰ��ͼ�������鵱�д����ϰ�������
     * @param context          ��Դ������
     * @param spliterMapResId  ��ͼԪ��ID
     * @param x                ��ͼ��ʼ��X����
     * @param y                ��ͼ��ʼ��Y����
     * @param mapElementWidth  ��ɵ�ͼ�Ļ���Ԫ�ؿ��
     * @param mapElementHeight ��ɵ�ͼ�Ļ���Ԫ�ظ߶�
     */
    public DoitooMap(byte[][] mapArray, int spliterMapResId, int[] barrierArray,
                     Context context, int x, int y, int mapElementWidth, int mapElementHeight) {
        super(x, y);
        spliterMap = Util.getBitMapById(context, spliterMapResId);
        this.mapElementHeight = mapElementHeight;
        this.mapElementWidth = mapElementWidth;
        this.rows = mapArray.length;
        this.cols = mapArray[0].length;
        this.barrierArray = barrierArray;
        this.mapArray = mapArray;

        if (this.rows == 0) {
            throw new ViewException("��ͼ��������Ϊ��");
        }
        this.spliterMap2Array(spliterMap);

        if (MapSourceArrays.isEmpty()) {
            throw new ViewException("MapSourceArrays is empty ��ͼ���е�ͼƬ��ԴΪ��");
        }
        // ��ʼ�������ͼ�ĸ߶����ȼ���ͼԪ�صĸ߶����ȣ�Ĭ��ȡ��һ��Ԫ�صĸ߿�Ϊ��׼
        width = this.cols * mapElementHeight;
        height = this.rows * mapElementWidth;

        // ������Ļ����ʾ������������
        int screenHeight = G.getInt("screenHeight");
        int screenWidth = G.getInt("screenWidth");
        if (screenHeight % mapElementHeight == 0) {
            screenRows = (screenHeight / mapElementHeight);
        } else {
            screenRows = (screenHeight / mapElementHeight + 1);
        }
        if (screenWidth % mapElementWidth == 0) {
            screenCols = (screenWidth / mapElementWidth);
        } else {
            screenCols = (screenWidth / mapElementWidth + 1);
        }
    }

    /**
     * �ָ��ͼͼƬԭ�ͣ���ȡ��ɵ�ͼԪ��ͼƬ
     *
     * @param spliterMap
     */
    private void spliterMap2Array(Bitmap spliterMap) {
        int sourceWidth = spliterMap.getWidth();
        int sourceHeight = spliterMap.getHeight();
        int w = this.mapElementHeight;
        int h = this.mapElementWidth;
        int rows = sourceHeight / h;
        int cols = sourceWidth / w;
        int index = 1;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int left = j * w;
                int top = i * h;
                Bitmap mapSourceArray = Bitmap.createBitmap(spliterMap, left, top, w, h);
//                Log.d("putIndex:",index+"");
                MapSourceArrays.put("" + index, mapSourceArray);
                index++;
            }
        }
        this.spliterMap = null;     //�ͷŴ�ͼƬ��Դ
    }

    @Override
    public void move() {
        // ��ͼ��ʱ����Ҫ�Զ��ƶ�
    }

    /**
     * ��������ͼ
     *
     * @param c Canvas
     */
    @Override
    public void paint(Canvas c) {
//        draw1(c);
        draw2(c);
    }

    private void draw1(Canvas c) {
        int x = getX();
        int y = getY();
        //c.drawBitmap(bigmap, x, y, null);
    }

    private void draw2(Canvas c) {
        int deltaX = getX();
        int deltaY = getY();

        int nArrayJ = (Math.abs(deltaX - mapElementWidth) / mapElementWidth - 1);
        int nArrayI = (Math.abs(deltaY - mapElementHeight) / mapElementHeight - 1);
        Bitmap backgroudsource = MapSourceArrays.get("1");    //Ĭ�ϱ���ͼƬ
        for (int i = nArrayI; i <= nArrayI + screenRows; i++) {
            if (i >= rows)
                break;
            for (int j = nArrayJ; j <= nArrayJ + screenCols; j++) {
                if (i < 0 || j < 0 || i > rows || j >= cols) {
                    continue;
                } else {
                    int elementX = (deltaX + j * this.mapElementWidth);
                    int elementY = (deltaY + i * this.mapElementHeight);
                    int index = mapArray[i][j];
//                    Log.d("drawIndex:",index+"");
                    Rect src = new Rect(0, 0, this.mapElementWidth, this.mapElementHeight);
                    Rect dst = new Rect(elementX, elementY, (elementX + this.mapElementWidth), (elementY + this.mapElementHeight));
                    //�����Ǳ���ͼƬʱ���Ȼ��������ٻ�����ͼƬ����ֹ���ֺ�ɫ����ͼ��
                    if (index != 1) {
                        c.drawBitmap(backgroudsource, src, dst, null);
                    }
                    Bitmap source = MapSourceArrays.get("" + index);
                    c.drawBitmap(source, src, dst, null);
                    // ת�������ͼ����������Ӧ�ļ��ϵ���
                    Point screenPoint = new Point(elementX, elementY);
                    CoordinateUtil.screen2world(screenPoint);
                    elementX = screenPoint.x;
                    elementY = screenPoint.y;
                    Rect rect2 = new Rect(elementX, elementY, (elementX + this.mapElementWidth), (elementY + this.mapElementHeight));
                    if (Util.inArray(this.barrierArray, index)) {
                        barrierRectList.add(rect2);
                    } else {
                        passRectList.add(rect2);
                    }
                    src = null;
                    dst = null;
                    rect2 = null;
                }
            }
        }
    }


    private Bitmap createBitmap(byte[][] mapRect) {
        int rows = mapRect.length;
        int cols = mapRect[0].length;
        Bitmap mapBit = Bitmap.createBitmap(cols * this.mapElementWidth, rows * this.mapElementHeight, null);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int x = j * this.mapElementWidth;
                int y = i * this.mapElementHeight;
                int index = mapRect[i][j];
                Bitmap source = MapSourceArrays.get("" + index);
//                Rect src = new Rect(0, 0, this.elementWidth, this.elementWidth);
//                Rect dst = new Rect(x, y, (x + this.elementWidth), (y + this.elementHeight));
                mapBit = mapBit.copy(source.getConfig(), true);

            }
        }
        return mapBit;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * @return ��ǰ��ͼ���д��ڵ�ͨ���������꼯�ϣ�ÿ�λ滭��ͼʱ���¸��´˼��� ���ص�RectΪ��ǰ��Ļ����
     */
    public List<Rect> getPassRectList() {
        return passRectList;
    }

    /**
     * @return ��ǰ��ͼ���д��ڵ��ϰ���������꼯�ϣ�ÿ�λ滭��ͼʱ���¸��´˼��� ���ص�RectΪ��ǰ��Ļ����
     */
    public List<Rect> getBarrierRectList() {
        return barrierRectList;
    }

    public byte[][] getMapArray() {
        return mapArray;
    }

    public void setTouchEventHandler(TouchEventHandler touchEventHandler) {
        this.touchEventHandler = touchEventHandler;
    }

    @Override
    public void subLife(int power) {

    }

    public int getMapElementWidth() {
        return mapElementWidth;
    }


    public int getMapElementHeight() {
        return mapElementHeight;
    }


}
