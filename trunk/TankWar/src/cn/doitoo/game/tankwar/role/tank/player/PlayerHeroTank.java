package cn.doitoo.game.tankwar.role.tank.player;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import cn.doitoo.game.framework.context.G;
import cn.doitoo.game.framework.role.MovableRole;
import cn.doitoo.game.tankwar.R;
import cn.doitoo.game.tankwar.effect.SelectEffect;
import cn.doitoo.game.tankwar.role.tank.HeroTank;
import cn.doitoo.game.tankwar.role.tank.Tank;

/**
 * Created by IntelliJ IDEA.
 * User: 阳葵
 * Date: 2010-6-27
 * Time: 10:18:24
 */
public class PlayerHeroTank extends HeroTank {


    @Override
    public Tank.TankType getTankType() {
        return Tank.TankType.PlayerHeroTank;
    }

    public PlayerHeroTank(int x, int y) {
        super(x, y);
        this.setDirection(MovableRole.move_direct.UP);
    }

    @Override
    public Bitmap[] getBitmaps() {
        Context context = G.getContext();
        Resources res = context.getResources();
        Bitmap left_source = BitmapFactory.decodeResource(res, R.drawable.tank1_2);
        Bitmap right_source = BitmapFactory.decodeResource(res, R.drawable.tank1_4);
        Bitmap down_source = BitmapFactory.decodeResource(res, R.drawable.tank1_3);
        Bitmap up_source = BitmapFactory.decodeResource(res, R.drawable.tank1_1);
        Bitmap[] source = new Bitmap[4];
        source[0] = left_source;
        source[1] = right_source;
        source[2] = up_source;
        source[3] = down_source;
        return source;
    }

    /**
     * 为坦克添加选中的效果
     */
    public void addSelectEffect() {
        SelectEffect effect = (SelectEffect) this.getEffectByKey("circle0");
        if (this.isSelected()) {
            if (effect != null) {
                this.deleteEffect("circle0");
            }
        } else {
            if (effect == null)
                effect = new SelectEffect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
            effect.setMoving(true);//设置为可移动（根据坦克坐标进行移动）
            this.addEffect("circle0", effect);
        }
    }


}
