package cn.doitoo.game.tankwar.task;

import android.content.Context;
import android.graphics.*;
import android.view.MotionEvent;
import cn.doitoo.game.framework.context.G;
import cn.doitoo.game.framework.event.OnClickEventHandler;
import cn.doitoo.game.framework.task.GameDrawTask;
import cn.doitoo.game.framework.util.ImageUtil;
import cn.doitoo.game.framework.util.Util;
import cn.doitoo.game.tankwar.R;
import cn.doitoo.game.tankwar.effect.SelectEffect;
import cn.doitoo.game.tankwar.role.control.ImageButton;
import cn.doitoo.game.tankwar.role.tank.player.PlayerHeroTank;

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

    private PlayerHeroTank player;
    private Paint tipPaint = new Paint();

    public DrawControlLayerTask() {
        super();
        rectPaint = new Paint();
        rectPaint.setColor(Color.GRAY);
        rectPaint.setAlpha(150);
        tipPaint.setColor(Color.GREEN);
        tipPaint.setTextSize(16F);
        int screenWidth = G.getInt("screenWidth");
        rect = new Rect(0, 0, screenWidth, 48);
        Context context = G.getContext();
        //坦克控制按钮
        final Bitmap playerControlImage = Util.getBitMapById(context, R.drawable.image1262);
        final Bitmap playerControlAlphaImage = ImageUtil.setAlpha(playerControlImage, 50);//设置图片透明度
        playerControl = new ImageButton(0, 0, playerControlAlphaImage);
        player = (PlayerHeroTank) G.get("playerHeroTankTask");
        playerControl.setOnClickEventHandler(new OnClickEventHandler() {
            @Override
            public void onClick(MotionEvent event) {
                player.setSelected(!player.isSelected());
                playerControl.setSelected(player.isSelected());
                player.addSelectEffect();
                if (playerControl.isSelected()) {
                    playerControl.setBitmap(playerControlAlphaImage);
                } else {
                    playerControl.setBitmap(playerControlImage);
                }
//                addSelectedEffect();
            }
        });
    }

    public void draw(Canvas c) {
        // 顶部菜单与按钮
        c.drawRect(rect, rectPaint);
        playerControl.paint(c);

        int count = G.getInt("attackCount");
        int time = G.getInt("attackTime");
        if (time > 0 && time < 100) {
            c.drawText("第" + count + "波攻击即将在" + (100 - time) / 10 + "秒后开始", 240, 200, tipPaint);
        }
    }


    /**
     * 添加选中的效果
     */
    public void addSelectedEffect() {
        SelectEffect effect = (SelectEffect) playerControl.getEffectByKey("circle1");
        if (playerControl.isSelected()) {
            if (effect != null) {
                playerControl.deleteEffect("circle1");
            }
        } else {
            if (effect == null) {
                effect = new SelectEffect(playerControl.getX(), playerControl.getY(), playerControl.getWidth(), playerControl.getHeight());
                int[] step = {2};
                effect.setStep_array(step);
                Paint paint = new Paint();
                paint.setStyle(Paint.Style.STROKE);//画空心圆时设置此参数，则只画边框
                paint.setStrokeWidth(2);//设置边框粗细
                paint.setAlpha(50);
                effect.setPaint(paint);
                effect.setMoving(false);
                effect.setWorldCoordinate(false); //设置动画坐标不以世界坐标移动。
            }
            playerControl.addEffect("circle1", effect);
        }
    }

}
