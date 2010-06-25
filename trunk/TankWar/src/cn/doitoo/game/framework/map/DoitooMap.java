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
	 * �����ͼԪ����ɼ��ϣ����ͼ�������鵱�е�ֵ������һ�¡� <code>
	 * 	���£�
	 *  //��ͼ��������maps������1��2��3  3������Ԫ��
	 *   int[][] maps={{1,2,3},{1,3,1}}
	 *  //���� ��ǰ���ϵ���Ӧ�������д��������Ͷ�Ӧ��Bitmap����
	 * </code>
	 */
	public static Map<Integer, Bitmap> ElementBitmaps = new LinkedHashMap<Integer, Bitmap>();

	/**
	 * ��ǰ��ͼ��������
	 */
	private int[][] mapRect;
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
	private float width;
	/**
	 * �����ͼ�߶�
	 */
	private float height;

	/**
	 * ��ͼÿ��Ԫ�صĿ��
	 */
	private float elementWidth;
	/**
	 * ��ͼÿ��Ԫ�صĸ߶�
	 */
	private float elementHeight;

	/**
	 * ��ͼ�ڵ�ǰ��Ļ���е�X����
	 */
	private float x = 0;
	/**
	 * ��ͼ�ڵ�ǰ��Ļ���е�Y����
	 */
	private float y = 0;

	/**
	 * ���������ͼ������ʼ����ͼ�ĸ߿����ص�ͼԪ�ض�Ӧ��ͼƬ��Դ
	 * 
	 * @param mapRect
	 *            ��ͼ��������
	 * @param context
	 *            ��Դ������
	 * @param resIds
	 *            ��ͼԪ��ID����
	 */
	public DoitooMap(int[][] mapRect,  int[] resIds) {
		// ��ʼ�������ͼ����������
		this.rows = mapRect.length;
		if (this.rows == 0) {
			throw new ViewException("��ͼ��������Ϊ��");
		}
		this.cols = mapRect[0].length;
		// ���浱ǰ��ͼ��������
		this.mapRect = mapRect;
		// ��ȡ��ǰ��ͼԪ�ض�Ӧ��ͼƬ��Դ���±����������鵱�е�Ԫ�����ͱ���һ��
		for (int i = 0; i < resIds.length; i++) {
			int resid = resIds[i];
			Bitmap bitmap = Util.getBitMapById(CONTEXT, resid);
			ElementBitmaps.put(resid, bitmap);
		}
		if (ElementBitmaps.isEmpty()) {
			throw new ViewException("ElementBitmaps is empty ��ͼ���е�ͼƬ��ԴΪ��");
		}
		// ��ʼ�������ͼ�ĸ߶����ȼ���ͼԪ�صĸ߶����ȣ�Ĭ��ȡ��һ��Ԫ�صĸ߿�Ϊ��׼
		Bitmap bitmap = ElementBitmaps.get(0);
		this.elementHeight = bitmap.getHeight();
		this.elementWidth = bitmap.getWidth();
		width = this.cols * elementHeight;
		height = this.rows * elementWidth;
	}

	/**
	 * ��������ͼ
	 * 
	 * @param c
	 * @param deltaX
	 *            �����ͼ����Ļ�ϵ�X����
	 * @param deltaY
	 *            �����ͼ����Ļ�ϵ�Y����
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
