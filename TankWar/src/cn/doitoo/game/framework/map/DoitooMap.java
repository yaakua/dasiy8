package cn.doitoo.game.framework.map;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import cn.doitoo.game.framework.exception.ViewException;
import cn.doitoo.game.framework.role.MovableRole;
import cn.doitoo.game.framework.util.Util;

public class DoitooMap extends MovableRole {

    /**
     * 世界地图元素组成集合，与地图排列数组当中的值的类型一致。 <code>
     * 如下：
     * //地图排列数据maps当中有1、2、3  3种类型元素
     * int[][] maps={{1,2,3},{1,3,1}}
     * //则在 当前集合当中应当包含有此三种类型对应的Bitmap对象
     * </code>
     */
    public static Map<String, Bitmap> ElementBitmaps = new LinkedHashMap<String, Bitmap>();

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
    private byte[][] mapRect;
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
    private float width;
    /**
     * 世界地图高度
     */
    private float height;

    /**
     * 地图每张元素的宽度
     */
    private float elementWidth = -1;
    /**
     * 地图每张元素的高度
     */
    private float elementHeight = -1;

    /**
     * 当前地图排列数组当中代表通道的类型
     * 凡是小于等于此值都为通道
     */
    public int passValue = 1;


    /**
     * 构造世界地图，并初始化地图的高宽，加载地图元素对应的图片资源
     *
     * @param mapRect   地图排列数组
     * @param passValue 当前地图排列数组当中代表通道的类型
     * @param context   资源上下文
     * @param resIds    地图元素ID数组
     * @param x         地图初始化X坐标
     * @param y         地图初始化Y坐标
     */
    public DoitooMap(byte[][] mapRect, int[] resIds, int passValue, Context context, float x, float y) {
        super(x, y);
        // 初始化世界地图的行与列数
        this.rows = mapRect.length;
        if (this.rows == 0) {
            throw new ViewException("地图排列数组为空");
        }
        this.passValue = passValue;
        this.cols = mapRect[0].length;
        // 保存当前地图排列数组
        this.mapRect = mapRect;
        // 获取当前地图元素对应的图片资源，下标与排列数组当中的元素类型保持一致
        for (int i = 0; i < resIds.length; i++) {
            int resid = resIds[i];
            Bitmap bitmap = Util.getBitMapById(context, resid);
            if (this.elementHeight == -1) this.elementHeight = bitmap.getHeight();
            if (this.elementWidth == -1) this.elementWidth = bitmap.getWidth();
            ElementBitmaps.put("" + (i + 1), bitmap);
        }
        if (ElementBitmaps.isEmpty()) {
            throw new ViewException("ElementBitmaps is empty 地图当中的图片资源为空");
        }
        // 初始化世界地图的高度与宽度及地图元素的高度与宽度，默认取第一张元素的高宽为标准

        width = this.cols * elementHeight;
        height = this.rows * elementWidth;
    }


    @Override
    public void move() {
        //地图暂时不需要自动移动
    }

    /**
     * 绘出世界地图
     *
     * @param c Canvas
     */
    @Override
    public void paint(Canvas c) {
        float deltaX = getX();
        float deltaY = getY();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int elementX = (int) (deltaX + j * this.elementWidth);
                int elementY = (int) (deltaY + i * this.elementHeight);
                int index = mapRect[i][j];
                Bitmap source = ElementBitmaps.get("" + index);
                Rect src = new Rect(0, 0, (int) this.elementWidth, (int) this.elementWidth);
                Rect dst = new Rect(elementX, elementY, (int) (elementX + this.elementWidth), (int) (elementY + this.elementHeight));
                c.drawBitmap(source, src, dst, null);
                //转成世界地图坐标后加入相应的集合当中
                Point screenPoint = new Point(elementX,elementY);
                Util.screen2world(this,screenPoint);
                elementX = screenPoint.x;
                elementY = screenPoint.y;
                Rect rect2 = new Rect(elementX, elementY, (int) (elementX + this.elementWidth), (int) (elementY + this.elementHeight));
                if (index <= this.passValue) {
                    passRectList.add(rect2);
                } else {
                    barrierRectList.add(rect2);
                }
            }
        }
    }


    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    /**
     * @return 当前地图当中存在的通道矩形坐标集合，每次绘画地图时重新更新此集合
     * 返回的Rect为当前屏幕坐标
     */
    public List<Rect> getPassRectList() {
        return passRectList;
    }

    /**
     * @return 当前地图当中存在的障碍物矩形坐标集合，每次绘画地图时重新更新此集合
     * 返回的Rect为当前屏幕坐标
     */
    public List<Rect> getBarrierRectList() {
        return barrierRectList;
    }

    public byte[][] getMapRect() {
        return mapRect;
    }
}
