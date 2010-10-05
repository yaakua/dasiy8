package cn.doitoo.game.tankwar.role.bullet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import cn.doitoo.game.framework.context.G;
import cn.doitoo.game.framework.role.MovableRole;
import cn.doitoo.game.framework.util.Util;
import cn.doitoo.game.tankwar.R;

import java.util.ArrayList;
import java.util.List;

public class LightingBullet extends Bullet {
    //子弹图片集合
    private static Bitmap bitmap = null;
    //子弹图片分解后的集合
    private static List<Bitmap> animations = new ArrayList<Bitmap>();
    //当前显示的动画图片
    private Bitmap drawMap;
    //被攻击者
    private List<MovableRole> attackeds = new ArrayList<MovableRole>();

    public LightingBullet(int x, int y) {
        super(x, y);
        if (animations.isEmpty()) {
            int sourceWidth = bitmap.getWidth();
            int sourceHeight = bitmap.getHeight();
            int w = this.getWidth();
            int h = this.getHeight();
            int rows = sourceHeight / h;
            int cols = sourceWidth / w;
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    int left = j * w;
                    int top = i * h;
                    Bitmap map = Bitmap.createBitmap(bitmap, left, top, w, h);
                    animations.add(map);
                }
            }
        }
        this.setStep_array(new int[]{0, 1, 2, 3, 4,5});
        new LightingBulletThread(this).start();
    }

    @Override
    public Bitmap getBitmap() {
        Context context = G.getContext();
        if (bitmap == null) {
            bitmap = Util.getBitMapById(context, R.drawable.linghtbullet02);
        }
        return bitmap;
    }

    @Override
    public int getWidth() {
        return 100;
    }

    @Override
    public int getHeight() {
        return 175;
    }

    @Override
    public void paint(Canvas c) {
        // 由世界坐标转成屏幕坐标
        Point screenPoint = this.getScreenPoint();
        if (drawMap == null) {
            drawMap = animations.get(0);
        }
        c.drawBitmap(drawMap, screenPoint.x, screenPoint.y, null);
    }

    /**
     * 由线程来控制子弹的动画显示
     */
    class LightingBulletThread extends Thread {
        private LightingBullet father;

        public LightingBulletThread(LightingBullet father) {
            this.father = father;
        }

        @Override
        public void start() {
            super.start();
        }

        @Override
        public void run() {
            while (father.isVisabled()) {
                try {
                    Thread.sleep(200);
                    int step = father.getStep_array()[father.getStep()];
                    father.drawMap = animations.get(step);
                    if (step != 0 && step != 1) {
                        MovableRole attackRole = father.getAttackRole();
                        Point centerPoint = attackRole.getCenterPoint();
                        father.setPosition(centerPoint.x-father.getWidth()/2, centerPoint.y-father.getHeight());
                    }
                    //G.addDebugInfo("step", step + "");
                    //子弹动画显示完成后，减少被攻击者的生命值
                    if (step == 5) {
                        //G.addDebugInfo("bullet", "正在减少生命值");
                        father.subLife(father.getPower());
                        father.setVisabled(false);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            this.interrupt();
        }

    }

    @Override
    public void subLife(int power) {
        for (MovableRole attackRole : this.attackeds) {
            attackRole.subLife(power);
            attackRole.setAttack(true);
            attackRole.setAttacker(this.getAttacker());
        }
    }

    public void setAttackeds(List<MovableRole> attackeds) {
        this.attackeds = attackeds;
    }
}
