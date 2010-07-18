package cn.doitoo.game.tankwar.role.control;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import cn.doitoo.game.framework.role.MovableRole;

/**
 * Created by IntelliJ IDEA.
 * User: Ñô¿û
 * Date: 2010-7-3
 * Time: 11:55:50
 */
public class ImageButton extends MovableRole {
    private Bitmap bitmap = null;
    private Paint paint = new Paint();

    public ImageButton(int x, int y, Bitmap bitmap) {
        super(x, y);
        this.bitmap = bitmap;
        paint.setAlpha(180);
    }

    @Override
    public int getHeight() {
        return bitmap.getHeight();
    }

    @Override
    public int getWidth() {
        return bitmap.getWidth();
    }

    @Override
    public void move() {

    }

    @Override
    public void paint(Canvas c) {
        this.paintEffects(c);
        c.drawBitmap(this.bitmap, this.getX(), this.getY(), null);
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    @Override
    public void subLife(int power) {

    }
}
