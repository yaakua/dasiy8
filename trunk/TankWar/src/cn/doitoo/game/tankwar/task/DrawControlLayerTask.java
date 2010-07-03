package cn.doitoo.game.tankwar.task;

import android.content.Context;
import android.graphics.*;
import cn.doitoo.game.framework.context.G;
import cn.doitoo.game.framework.task.GameDrawTask;
import cn.doitoo.game.framework.util.Util;
import cn.doitoo.game.tankwar.R;
import cn.doitoo.game.tankwar.role.control.ImageButton;

/**
 * 绘画控制层界面
 *
 * @author kui.yang
 */
public class DrawControlLayerTask implements GameDrawTask {
    // 半透明矩形边框
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
        Bitmap playerControlImage = Util.getBitMapById(context, R.drawable.tank1_1);
        playerControl = new ImageButton(0, 0, playerControlImage);
    }

    public void draw(Canvas c) {
        // 顶部菜单与按钮
        c.drawRect(rect, rectPaint);
        playerControl.paint(c);
    }

}
