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
        //̹�˿��ư�ť
        final Bitmap playerControlImage = Util.getBitMapById(context, R.drawable.image1262);
        final Bitmap playerControlAlphaImage = ImageUtil.setAlpha(playerControlImage, 50);//����ͼƬ͸����
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
        // �����˵��밴ť
        c.drawRect(rect, rectPaint);
        playerControl.paint(c);

        int count = G.getInt("attackCount");
        int time = G.getInt("attackTime");
        if (time > 0 && time < 100) {
            c.drawText("��" + count + "������������" + (100 - time) / 10 + "���ʼ", 240, 200, tipPaint);
        }
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
