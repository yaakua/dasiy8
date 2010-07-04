package cn.doitoo.game.tankwar.role.tank.player;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import cn.doitoo.game.framework.context.G;
import cn.doitoo.game.framework.role.MovableRole;
import cn.doitoo.game.tankwar.R;
import cn.doitoo.game.tankwar.effect.SelectAnimation;
import cn.doitoo.game.tankwar.role.tank.HeroTank;

/**
 * Created by IntelliJ IDEA.
 * User: 阳葵
 * Date: 2010-6-27
 * Time: 10:18:24
 */
public class PlayerHeroTank extends HeroTank {


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
    public void addSelectedAnimation() {
        SelectAnimation animation = (SelectAnimation) this.getAnimation("circle0");
        if (this.isSelected()) {
            if (animation != null) {
                this.deleteAnimation("circle0");
            }
        } else {
            if (animation == null)
                animation = new SelectAnimation(this.getX(), this.getY(), this.getWidth(), this.getHeight());
            animation.setMoving(true);//设置为可移动（根据坦克坐标进行移动）
            this.addAnimation("circle0", animation);
        }
    }


}
