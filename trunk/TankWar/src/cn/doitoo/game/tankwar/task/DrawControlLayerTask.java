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
 * �滭���Ʋ����
 *
 * @author kui.yang
 */
public class DrawControlLayerTask implements GameDrawTask {
    // ��͸�����α߿�
    private Rect rect = null;
    private Paint rectPaint = null;

    private ImageButton playerControl = null;

    private PlayerHeroTank player;

    public DrawControlLayerTask() {
        super();
        rectPaint = new Paint();
        rectPaint.setColor(Color.GRAY);
        rectPaint.setAlpha(150);
        int screenWidth = G.getInt("screenWidth");
        rect = new Rect(0, 0, screenWidth, 48);
        Context context = G.getContext();
        //̹�˿��ư�ť
        Bitmap playerControlImage = Util.getBitMapById(context, R.drawable.tank1_1);
        playerControlImage = ImageUtil.setAlpha(playerControlImage, 50);//����ͼƬ͸����
        playerControl = new ImageButton(0, 0, playerControlImage);
        player = (PlayerHeroTank) G.get("playerHeroTankTask");
        playerControl.setOnClickEventHandler(new OnClickEventHandler() {
            @Override
            public void onClick(MotionEvent event) {
                player.setSelected(!player.isSelected());
                playerControl.setSelected(player.isSelected());
                player.addSelectEffect();
                addSelectedEffect();
            }
        });
    }

    public void draw(Canvas c) {
        // �����˵��밴ť
        c.drawRect(rect, rectPaint);
        playerControl.paint(c);
    }


    /**
     * ���ѡ�е�Ч��
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
                paint.setStyle(Paint.Style.STROKE);//������Բʱ���ô˲�������ֻ���߿�
                paint.setStrokeWidth(2);//���ñ߿��ϸ
                paint.setAlpha(50);
                effect.setPaint(paint);
                effect.setMoving(false);
                effect.setWorldCoordinate(false); //���ö������겻�����������ƶ���
            }
            playerControl.addEffect("circle1", effect);
        }
    }

}
