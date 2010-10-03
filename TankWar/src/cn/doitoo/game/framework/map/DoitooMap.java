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
     * 世界地图元素组成集合，与地图排列数组当中的值的类型一致。 <code>
     * 如下：
     * //地图排列数据maps当中有1、2、3  3种类型元素
     * int[][] maps={{1,2,3},{1,3,1}}
     * //则在 当前集合当中应当包含有此三种类型对应的Bitmap对象
     * </code>
     */
    public static Map<String, Bitmap> MapSourceArrays = new LinkedHashMap<String, Bitmap>();

    /**
     * 当前地图当中存在的障碍物矩形坐标集合，每次绘画地图时重新更新此集合
     */
    private List<Rect> barrierRectList = new ArrayList<Rect>();

    /**
     * 当前地图当中存在的通道矩形坐标集合，每次绘画地图时重新更新此集合
     */
    private List<Rect> passRectList = new ArrayList<Rect>();

    /**
     * 当前地图排列数据
     */
    private byte[][] mapArray;
    /**
     * 当前地图的行数
     */
    private int rows;
    /**
     * 当前地图的列数
     */
    private int cols;
    /**
     * 世界地图宽度
     */
    private int width;
    /**
     * 世界地图高度
     */
    private int height;

    /**
     * 代表障碍值的数组
     */
    public int[] barrierArray = {};

    /**
     * 屏幕能显示的行数
     */
    private int screenRows;
    /**
     * 屏幕能显示的列数
     */
    private int screenCols;

    /**
     * 地图组合图片
     */
    private Bitmap spliterMap;

    private int mapElementWidth;
    private int mapElementHeight;

    /**
     * 构造世界地图，并初始化地图的高宽，加载地图元素对应的图片资源
     *
     * @param mapArray         地图排列数组
     * @param barrierArray     当前地图排列数组当中代表障碍的类型
     * @param context          资源上下文
     * @param spliterMapResId  地图元素ID
     * @param x                地图初始化X坐标
     * @param y                地图初始化Y坐标
     * @param mapElementWidth  组成地图的基本元素宽度
     * @param mapElementHeight 组成地图的基本元素高度
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
            throw new ViewException("地图排列数组为空");
        }
        this.spliterMap2Array(spliterMap);

        if (MapSourceArrays.isEmpty()) {
            throw new ViewException("MapSourceArrays is empty 地图当中的图片资源为空");
        }
        // 初始化世界地图的高度与宽度及地图元素的高度与宽度，默认取第一张元素的高宽为标准
        width = this.cols * mapElementHeight;
        height = this.rows * mapElementWidth;

        // 计算屏幕能显示的行数与列数
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
     * 分割地图图片原型，获取组成地图元素图片
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
        this.spliterMap = null;     //释放大图片资源
    }

    @Override
    public void move() {
        // 地图暂时不需要自动移动
    }

    /**
     * 绘出世界地图
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
        Bitmap backgroudsource = MapSourceArrays.get("1");    //默认背景图片
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
                    //当不是背景图片时，先画背景，再画其它图片，防止出现黑色背景图。
                    if (index != 1) {
                        c.drawBitmap(backgroudsource, src, dst, null);
                    }
                    Bitmap source = MapSourceArrays.get("" + index);
                    c.drawBitmap(source, src, dst, null);
                    // 转成世界地图坐标后加入相应的集合当中
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
     * @return 当前地图当中存在的通道矩形坐标集合，每次绘画地图时重新更新此集合 返回的Rect为当前屏幕坐标
     */
    public List<Rect> getPassRectList() {
        return passRectList;
    }

    /**
     * @return 当前地图当中存在的障碍物矩形坐标集合，每次绘画地图时重新更新此集合 返回的Rect为当前屏幕坐标
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
