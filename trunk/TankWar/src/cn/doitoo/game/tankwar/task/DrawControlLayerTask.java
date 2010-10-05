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

import java.util.Map;
import java.util.Set;

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
        tipPaint.setColor(Color.RED);
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
        if (time > 0 && time < 15) {
            c.drawText("��" + count + "������������" + (15-time) + "���ʼ", 200, 200, tipPaint);
        }

        int hiddenTankCount = G.getInt("hiddenTankCount");
        if (hiddenTankCount > 0 && hiddenTankCount < 10) {
            c.drawText("��ʣ��" + (10 - hiddenTankCount) + "��", 240, 100, tipPaint);
        } else if (hiddenTankCount >= 10) {
            c.drawText("GAME OVER", 240, 100, tipPaint);
        }
        this.drawDebugInfo(c);
    }

    /**
     * ��ʾDEBUG��Ϣ
     *
     * @param c
     */
    private void drawDebugInfo(Canvas c) {
        Map<String, String> debugInfoMap = G.getDebugInfoMap();
        Set<String> set = debugInfoMap.keySet();
        int x = 10;
        int y = 40;
        for (String key : set) {
            String value = debugInfoMap.get(key);
            c.drawText("INFO:" + key + "==>" + value, x, y, tipPaint);
            y += 13;
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
