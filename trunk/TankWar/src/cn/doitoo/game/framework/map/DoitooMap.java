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
     * �����ͼԪ����ɼ��ϣ����ͼ�������鵱�е�ֵ������һ�¡� <code>
     * ���£�
     * //��ͼ��������maps������1��2��3  3������Ԫ��
     * int[][] maps={{1,2,3},{1,3,1}}
     * //���� ��ǰ���ϵ���Ӧ�������д��������Ͷ�Ӧ��Bitmap����
     * </code>
     */
    public static Map<String, Bitmap> ElementBitmaps = new LinkedHashMap<String, Bitmap>();

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
    private byte[][] mapRect;
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
    private float elementWidth = -1;
    /**
     * ��ͼÿ��Ԫ�صĸ߶�
     */
    private float elementHeight = -1;

    /**
     * ��ǰ��ͼ�������鵱�д���ͨ��������
     * ����С�ڵ��ڴ�ֵ��Ϊͨ��
     */
    public int passValue = 1;


    /**
     * ���������ͼ������ʼ����ͼ�ĸ߿����ص�ͼԪ�ض�Ӧ��ͼƬ��Դ
     *
     * @param mapRect   ��ͼ��������
     * @param passValue ��ǰ��ͼ�������鵱�д���ͨ��������
     * @param context   ��Դ������
     * @param resIds    ��ͼԪ��ID����
     * @param x         ��ͼ��ʼ��X����
     * @param y         ��ͼ��ʼ��Y����
     */
    public DoitooMap(byte[][] mapRect, int[] resIds, int passValue, Context context, float x, float y) {
        super(x, y);
        // ��ʼ�������ͼ����������
        this.rows = mapRect.length;
        if (this.rows == 0) {
            throw new ViewException("��ͼ��������Ϊ��");
        }
        this.passValue = passValue;
        this.cols = mapRect[0].length;
        // ���浱ǰ��ͼ��������
        this.mapRect = mapRect;
        // ��ȡ��ǰ��ͼԪ�ض�Ӧ��ͼƬ��Դ���±����������鵱�е�Ԫ�����ͱ���һ��
        for (int i = 0; i < resIds.length; i++) {
            int resid = resIds[i];
            Bitmap bitmap = Util.getBitMapById(context, resid);
            if (this.elementHeight == -1) this.elementHeight = bitmap.getHeight();
            if (this.elementWidth == -1) this.elementWidth = bitmap.getWidth();
            ElementBitmaps.put("" + (i + 1), bitmap);
        }
        if (ElementBitmaps.isEmpty()) {
            throw new ViewException("ElementBitmaps is empty ��ͼ���е�ͼƬ��ԴΪ��");
        }
        // ��ʼ�������ͼ�ĸ߶����ȼ���ͼԪ�صĸ߶����ȣ�Ĭ��ȡ��һ��Ԫ�صĸ߿�Ϊ��׼

        width = this.cols * elementHeight;
        height = this.rows * elementWidth;
    }


    @Override
    public void move() {
        //��ͼ��ʱ����Ҫ�Զ��ƶ�
    }

    /**
     * ��������ͼ
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
                //ת�������ͼ����������Ӧ�ļ��ϵ���
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
     * @return ��ǰ��ͼ���д��ڵ�ͨ���������꼯�ϣ�ÿ�λ滭��ͼʱ���¸��´˼���
     * ���ص�RectΪ��ǰ��Ļ����
     */
    public List<Rect> getPassRectList() {
        return passRectList;
    }

    /**
     * @return ��ǰ��ͼ���д��ڵ��ϰ���������꼯�ϣ�ÿ�λ滭��ͼʱ���¸��´˼���
     * ���ص�RectΪ��ǰ��Ļ����
     */
    public List<Rect> getBarrierRectList() {
        return barrierRectList;
    }

    public byte[][] getMapRect() {
        return mapRect;
    }
}
