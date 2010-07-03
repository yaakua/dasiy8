package cn.doitoo.game.tankwar.effect;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Paint.Style;
import cn.doitoo.game.framework.context.G;
import cn.doitoo.game.framework.role.MovableRole;
import cn.doitoo.game.framework.util.CoordinateUtil;
import cn.doitoo.game.framework.util.Util;

public class ClickCircle extends MovableRole {
    private Paint paint = null;
    private int radius;
    private int[] colors = {Color.CYAN, Color.BLACK, Color.LTGRAY, Color.TRANSPARENT};

    public ClickCircle(int x, int y) {
        super(x, y);
        paint = new Paint();
        paint.setStyle(Style.STROKE);//������Բʱ���ô˲�������ֻ���߿�
        paint.setStrokeWidth(6);//���ñ߿��ϸ
        int tankElementWidth = (Integer) G.get("tankElementWidth");
        radius = tankElementWidth / 2;
        int[] steps = {0, 1, 2, 3};
        this.setStep_array(steps);
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public void move() {

    }

    @Override
    public void paint(Canvas c) {
        int x = this.getX();
        int y = this.getY();
        // ����������ת����Ļ����
        Point worldPoint = new Point(x, y);
        CoordinateUtil.world2screen( worldPoint);
        x = worldPoint.x;
        y = worldPoint.y;
        int step = this.getStep();
        paint.setColor(colors[step]);
        c.drawCircle(x, y, radius + 4, paint);
    }

}
