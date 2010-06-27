package cn.doitoo.game.tankwar.role.tank.player;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import cn.doitoo.game.framework.context.G;
import cn.doitoo.game.tankwar.R;
import cn.doitoo.game.tankwar.role.tank.HeroTank;

/**
 * Created by IntelliJ IDEA.
 * User: Ñô¿û
 * Date: 2010-6-27
 * Time: 10:18:24
 */
public class PlayerHeroTank extends HeroTank {


    public PlayerHeroTank(float x, float y) {
        super(x, y);
    }

    @Override
    public Bitmap[] getBitmaps() {
        Context context = (Context) G.get("context");
        Resources res = context.getResources();
        Bitmap left_source = BitmapFactory.decodeResource(res, R.drawable.tank1_2);
        Bitmap right_source = BitmapFactory.decodeResource(res, R.drawable.tank1_4);
        Bitmap down_source = BitmapFactory.decodeResource(res, R.drawable.tank1_3);
        Bitmap up_source = BitmapFactory.decodeResource(res, R.drawable.tank1_1);
        Bitmap[] source = new Bitmap[4];
        source[0] = left_source;
        source[1] = up_source;
        source[2] = right_source;
        source[3] = down_source;
        return source;
    }


}
