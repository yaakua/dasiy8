package cn.doitoo.game.tankwar.task;

import android.graphics.Canvas;
import android.graphics.Paint;
import cn.doitoo.game.framework.task.GameDrawTask;

/**
 * �滭���Ʋ����
 *
 * @author kui.yang
 */
public class DrawControlLayerTask implements GameDrawTask {
    // ��͸�����α߿�

    private Paint rectPaint = null;

    public DrawControlLayerTask() {
        super();
        rectPaint = new Paint();
    }

    public void draw(Canvas c) {
        // �����˵��밴ť

    }

}
