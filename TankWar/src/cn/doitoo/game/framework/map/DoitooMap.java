package cn.doitoo.game.framework.map;

import java.util.LinkedHashMap;
import java.util.Map;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import cn.doitoo.game.framework.context.GameContext;
import cn.doitoo.game.framework.exception.ViewException;
import cn.doitoo.game.framework.util.Util;

public class DoitooMap {

	/**
	 * 世界地图元素组成集合，与地图排列数组当中的值的类型一致。 <code>
	 * 	如下：
	 *  //地图排列数据maps当中有1、2、3  3种类型元素
	 *   int[][] maps={{1,2,3},{1,3,1}}
	 *  //则在 当前集合当中应当包含有此三种类型对应的Bitmap对象
	 * </code>
	 */
	public static Map<Integer, Bitmap> ElementBitmaps = new LinkedHashMap<Integer, Bitmap>();

	/**
	 * 当前地图排列数据
	 */
	private int[][] mapRect;
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
	private float elementWidth;
	/**
	 * 地图每张元素的高度
	 */
	private float elementHeight;

	/**
	 * 地图在当前屏幕当中的X坐标
	 */
	private float x = 0;
	/**
	 * 地图在当前屏幕当中的Y坐标
	 */
	private float y = 0;

	/**
	 * 构造世界地图，并初始化地图的高宽，加载地图元素对应的图片资源
	 * 
	 * @param mapRect
	 *            地图排列数组
	 * @param context
	 *            资源上下文
	 * @param resIds
	 *            地图元素ID数组
	 */
	public DoitooMap(int[][] mapRect,  int[] resIds) {
		// 初始化世界地图的行与列数
		this.rows = mapRect.length;
		if (this.rows == 0) {
			throw new ViewException("地图排列数组为空");
		}
		this.cols = mapRect[0].length;
		// 保存当前地图排列数组
		this.mapRect = mapRect;
		// 获取当前地图元素对应的图片资源，下标与排列数组当中的元素类型保持一致
		for (int i = 0; i < resIds.length; i++) {
			int resid = resIds[i];
			Bitmap bitmap = Util.getBitMapById(CONTEXT, resid);
			ElementBitmaps.put(resid, bitmap);
		}
		if (ElementBitmaps.isEmpty()) {
			throw new ViewException("ElementBitmaps is empty 地图当中的图片资源为空");
		}
		// 初始化世界地图的高度与宽度及地图元素的高度与宽度，默认取第一张元素的高宽为标准
		Bitmap bitmap = ElementBitmaps.get(0);
		this.elementHeight = bitmap.getHeight();
		this.elementWidth = bitmap.getWidth();
		width = this.cols * elementHeight;
		height = this.rows * elementWidth;
	}

	/**
	 * 绘出世界地图
	 * 
	 * @param c
	 * @param deltaX
	 *            世界地图在屏幕上的X坐标
	 * @param deltaY
	 *            世界地图在屏幕上的Y坐标
	 */
	public void draw(Canvas c) {
		float deltaX = getX();
		float deltaY = getY();
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				int elementX = (int) (deltaX + j * this.elementWidth);
				int elementY = (int) (deltaY + i * this.elementWidth);
				int index = mapRect[i][j];
				Bitmap source = ElementBitmaps.get(index);
				Rect src = new Rect(0, 0, (int) this.elementWidth, (int) this.elementWidth);
				Rect dst = new Rect(elementX, elementY, (int) (elementX + this.elementWidth), (int) (elementY + this.elementWidth));
				c.drawBitmap(source, src, dst, null);
				// c.drawBitmap(bms[mapRect[i][j]-1], left, top, null);
			}

		}
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
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
}
