package cn.doitoo.game.tankwar.task;

import android.graphics.Canvas;
import android.graphics.Paint;
import cn.doitoo.game.framework.task.GameDrawTask;

/**
 * 绘画控制层界面
 *
 * @author kui.yang
 */
public class DrawControlLayerTask implements GameDrawTask {
    // 半透明矩形边框

    private Paint rectPaint = null;

    public DrawControlLayerTask() {
        super();
        rectPaint = new Paint();
    }

    public void draw(Canvas c) {
        // 顶部菜单与按钮

    }

}
