package cn.doitoo.game.tankwar.effect;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import cn.doitoo.game.framework.context.G;

public class ClickEffect extends Effect {
    private Paint paint = null;
    private int radius;
    private int[] colors = {Color.CYAN, Color.BLACK, Color.LTGRAY, Color.TRANSPARENT};

    public ClickEffect(int x, int y) {
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
        // ����������ת����Ļ����
        Point screenPoint = this.getScreenPoint();
        int step = this.getStep();
        paint.setColor(colors[step]);
        c.drawCircle(screenPoint.x, screenPoint.y, radius + 4, paint);
    }
}
