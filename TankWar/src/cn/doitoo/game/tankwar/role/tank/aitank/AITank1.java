package cn.doitoo.game.tankwar.role.tank.aitank;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import cn.doitoo.game.framework.context.G;
import cn.doitoo.game.framework.role.MovableRole;
import cn.doitoo.game.tankwar.R;
import cn.doitoo.game.tankwar.role.tank.Tank;

/**
 * 电脑坦克主类，
 * User: 阳葵
 * Date: 2010-6-27
 * Time: 10:13:14
 */
public class AITank1 extends AITank {

    @Override
    public TankType getTankType() {
        return Tank.TankType.PlayerAiTank;
    }

    public AITank1(int x, int y) {
        super(x, y);
        this.setDirection(MovableRole.move_direct.DOWN);
    }

    @Override
    public Bitmap[] getBitmaps() {
        Context context = G.getContext();
        Resources res = context.getResources();
        Bitmap source = BitmapFactory.decodeResource(res, R.drawable.pakechara_conew2);
        width = 31;
        height = 31;
        return new Bitmap[]{source};
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
}
