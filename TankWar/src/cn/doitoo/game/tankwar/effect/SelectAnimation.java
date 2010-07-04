package cn.doitoo.game.tankwar.effect;

import android.graphics.*;
import android.graphics.Paint.Style;
import cn.doitoo.game.framework.role.MovableRole;
import cn.doitoo.game.framework.util.CoordinateUtil;

public class SelectAnimation extends MovableRole {

    private int radius;
    /**
     * 设置绘画矩形
     */
    private boolean drawRect;
    /**
     * 设置绘画圆
     */
    private boolean drawCircle;

    /**
     * 当前动画的坐标是否按世界坐标来处理，默认为true
     */
    private boolean isWorldCoordinate = true;

    /**
     * 动画显示的每一步的设置
     */
    private int[] colors = {Color.RED, Color.BLUE, Color.GRAY, Color.GREEN};
    private int width;
    private int height;

    public SelectAnimation(int x, int y, int width, int height) {
        super(x, y);
        Paint paint = new Paint();
        paint.setStyle(Style.STROKE);//画空心圆时设置此参数，则只画边框
        paint.setStrokeWidth(6);//设置边框粗细
        radius = width / 2;
        int[] steps = {0, 1, 2, 3};  //默认的动画显示步骤
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
            // 由世界坐标转成屏幕坐标
            Point worldPoint = new Point(x, y);
            CoordinateUtil.world2screen(worldPoint);
            x = worldPoint.x;
            y = worldPoint.y;
        }
        int step = this.getStep();
        paint.setColor(colors[step]);
        if (drawCircle) {  //画圆
            c.drawCircle(x + radius, y + radius, radius + 4, paint);
        } else {//默认为画矩形
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
