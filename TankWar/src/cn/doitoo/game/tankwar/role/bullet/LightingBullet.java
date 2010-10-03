package cn.doitoo.game.tankwar.role.bullet;

import android.content.Context;
import android.graphics.Bitmap;
import cn.doitoo.game.framework.context.G;
import cn.doitoo.game.framework.util.Util;
import cn.doitoo.game.tankwar.R;

public class LightingBullet extends Bullet {
    private static Bitmap bitmap = null;

    public LightingBullet(int x, int y) {
        super(x, y);
    }

    @Override
    public Bitmap getBitmap() {
        Context context = G.getContext();
        if (bitmap == null) {
            Bitmap sourceImage = Util.getBitMapById(context, R.drawable.lighting_bullet01);
            int w = 240;
            int h = 192;
            int left = 2 * w;
            int top = 1 * h;
            bitmap = Bitmap.createBitmap(sourceImage, left, top, w, h);
        }
        return bitmap;
    }

}
