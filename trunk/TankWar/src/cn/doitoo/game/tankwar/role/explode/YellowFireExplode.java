package cn.doitoo.game.tankwar.role.explode;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import cn.doitoo.game.framework.context.G;
import cn.doitoo.game.framework.util.Util;
import cn.doitoo.game.tankwar.R;

import java.util.ArrayList;
import java.util.List;

/**
 * User: 阳葵
 * Date: 2010-10-5
 * Time: 15:23:34
 * Mail:bjawg789@gmail.com
 */
public class YellowFireExplode extends Explode {
    //爆炸物集合图片
    private static Bitmap bitmap = null;
    //子弹图片分解后的集合
    private static List<Bitmap> animations = new ArrayList<Bitmap>();
    private Bitmap drawMap = null;

    public YellowFireExplode(int x, int y) {
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
        this.setStep_array(new int[]{0, 1, 2, 3, 4});
        new YellowFireExplodeThread(this).start();
    }

    @Override
    public Bitmap getBitmap() {
        Context context = G.getContext();
        if (bitmap == null) {
            bitmap = Util.getBitMapById(context, R.drawable.yellowfireexplode02);
        }
        return bitmap;
    }

    @Override
    public int getWidth() {
        return 192;
    }

    @Override
    public int getHeight() {
        return 192;
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

    class YellowFireExplodeThread extends Thread {
        private YellowFireExplode father;

        YellowFireExplodeThread(YellowFireExplode father) {
            this.father = father;
        }

        @Override
        public void run() {
            while (father.isVisabled()) {
                try {
                    Thread.sleep(200);
                    int step = father.getStep_array()[father.getStep()];
                    father.drawMap = animations.get(step);
                    if (step == 4) {
                        father.setVisabled(false);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            this.interrupt();
        }
    }
}
