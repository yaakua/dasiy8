package cn.doitoo.game.tankwar.effect;

import android.graphics.*;
import android.graphics.Paint.Style;
import cn.doitoo.game.framework.role.MovableRole;
import cn.doitoo.game.framework.util.CoordinateUtil;

public class SelectAnimation extends MovableRole {

    private int radius;
    /**
     * ���û滭����
     */
    private boolean drawRect;
    /**
     * ���û滭Բ
     */
    private boolean drawCircle;

    /**
     * ��ǰ�����������Ƿ���������������Ĭ��Ϊtrue
     */
    private boolean isWorldCoordinate = true;

    /**
     * ������ʾ��ÿһ��������
     */
    private int[] colors = {Color.RED, Color.BLUE, Color.GRAY, Color.GREEN};
    private int width;
    private int height;

    public SelectAnimation(int x, int y, int width, int height) {
        super(x, y);
        Paint paint = new Paint();
        paint.setStyle(Style.STROKE);//������Բʱ���ô˲�������ֻ���߿�
        paint.setStrokeWidth(6);//���ñ߿��ϸ
        radius = width / 2;
        int[] steps = {0, 1, 2, 3};  //Ĭ�ϵĶ�����ʾ����
        this.setStep_array(steps);
        this.width = width;
        this.height = height;
        this.setPaint(paint);
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public void move() {

    }

    @Override
    public void paint(Canvas c) {
        Paint paint = this.getPaint();
        int x = this.getX();
        int y = this.getY();
        if (isWorldCoordinate) {
            // ����������ת����Ļ����
            Point worldPoint = new Point(x, y);
            CoordinateUtil.world2screen(worldPoint);
            x = worldPoint.x;
            y = worldPoint.y;
        }
        int step = this.getStep();
        paint.setColor(colors[step]);
        if (drawCircle) {  //��Բ
            c.drawCircle(x + radius, y + radius, radius + 4, paint);
        } else {//Ĭ��Ϊ������
            c.drawRect(x, y, x + width, y + height, paint);
        }

    }

    public void setDrawCircle(boolean drawCircle) {
        this.drawCircle = drawCircle;
    }

    public void setDrawRect(boolean drawRect) {
        this.drawRect = drawRect;
    }

    public void setWorldCoordinate(boolean worldCoordinate) {
        isWorldCoordinate = worldCoordinate;
    }
}
