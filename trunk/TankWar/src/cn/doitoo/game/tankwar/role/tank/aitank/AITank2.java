package cn.doitoo.game.tankwar.role.tank.aitank;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import cn.doitoo.game.framework.context.G;
import cn.doitoo.game.tankwar.R;
import cn.doitoo.game.tankwar.role.tank.Tank;

/**
 * 电脑坦克主类，
 * User: 阳葵
 * Date: 2010-6-27
 * Time: 10:13:14
 */
public class AITank2 extends AITank {
    public AITank2(int x, int y) {
        super(x, y);
        this.setDirection(move_direct.UP);
    }


    @Override
    public Bitmap[] getBitmaps() {
        Context context = G.getContext();
        Resources res = context.getResources();
        Bitmap source = BitmapFactory.decodeResource(res, R.drawable.pakechara_conew3);
        width = 31;
        height = 31;
//        Bitmap right_source = BitmapFactory.decodeResource(res, R.drawable.tank2_4);
//        Bitmap down_source = BitmapFactory.decodeResource(res, R.drawable.tank2_3);
//        Bitmap up_source = BitmapFactory.decodeResource(res, R.drawable.tank2_1);
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

    @Override
    public TankType getTankType() {
        return Tank.TankType.PlayerAiTank;
    }
}