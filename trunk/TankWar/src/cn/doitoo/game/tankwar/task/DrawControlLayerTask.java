package cn.doitoo.game.tankwar.task;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.widget.ImageButton;
import cn.doitoo.game.framework.context.G;
import cn.doitoo.game.framework.task.GameDrawTask;
import cn.doitoo.game.tankwar.R;

/**
 * �滭���Ʋ����
 *
 * @author kui.yang
 */
public class DrawControlLayerTask implements GameDrawTask {
    // ��͸�����α߿�
    private Rect rect = null;
    private Paint rectPaint = null;

    private ImageButton playerControl = null;

    public DrawControlLayerTask() {
        super();
        rectPaint = new Paint();
        rectPaint.setColor(Color.GRAY);
        rectPaint.setAlpha(150);
        int screenWidth = G.getInt("screenWidth");
        rect = new Rect(0, 0, screenWidth, 48);
        Context context = G.getContext();
        playerControl = new ImageButton(context);
        playerControl.setBackgroundResource(R.drawable.tank1_1);
        playerControl.setAlpha(180);

    }

    public void draw(Canvas c) {
        // �����˵��밴ť
        c.drawRect(rect, rectPaint);
    }

}
