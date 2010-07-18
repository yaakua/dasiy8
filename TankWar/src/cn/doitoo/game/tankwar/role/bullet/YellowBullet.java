package cn.doitoo.game.tankwar.role.bullet;

import cn.doitoo.game.framework.context.G;
import cn.doitoo.game.framework.util.Util;
import cn.doitoo.game.tankwar.R;
import android.content.Context;
import android.graphics.Bitmap;

public class YellowBullet extends Bullet {

    public YellowBullet(int x, int y) {
        super(x, y);
    }

    @Override
    public Bitmap getBitmap() {
        Context context = G.getContext();
        return Util.getBitMapById(context, R.drawable.bullet);
    }

}
