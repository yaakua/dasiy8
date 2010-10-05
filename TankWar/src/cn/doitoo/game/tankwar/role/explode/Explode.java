package cn.doitoo.game.tankwar.role.explode;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import cn.doitoo.game.framework.role.MovableRole;

/**
 * User: 阳葵
 * Mail:bjawg789@gmail.com
 */
public abstract class Explode extends MovableRole {
    private Bitmap bitmap = null;
    private MovableRole owner = null;//拥有此爆炸对象者

    public Explode(int x, int y) {
        super(x, y);
        bitmap = getBitmap();
    }

    public abstract Bitmap getBitmap();


    @Override
    public void move() {
    }

    @Override
    public void paint(Canvas c) {
    }

    @Override
    public void subLife(int power) {
    }

    public MovableRole getOwner() {
        return owner;
    }

    public void setOwner(MovableRole owner) {
        this.owner = owner;
    }
}
