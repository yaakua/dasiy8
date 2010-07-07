package cn.doitoo.game.tankwar.role.building.player;

import android.content.Context;
import android.graphics.Bitmap;
import cn.doitoo.game.framework.context.G;
import cn.doitoo.game.framework.util.Util;
import cn.doitoo.game.tankwar.R;
import cn.doitoo.game.tankwar.role.building.Pagoda;

/**
 * Created by IntelliJ IDEA.
 * User: Ñô¿û
 * Date: 2010-7-6
 * Time: 18:23:10
 */
public class PlayerPagoda1 extends Pagoda {
    @Override
    public PagodaType getPagodaType() {
        return Pagoda.PagodaType.Player;
    }

    public PlayerPagoda1(int x, int y) {
        super(x, y);
    }

    @Override
    public Bitmap getBitmap() {
        Context context = G.getContext();
        return Util.getBitMapById(context, R.drawable.tile_breakable2);
    }


}
