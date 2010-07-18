package cn.doitoo.game.tankwar.role.control;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import cn.doitoo.game.framework.role.MovableRole;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ñô¿û
 * Date: 2010-7-3
 * Time: 14:24:17
 */
public class Menu extends MovableRole {

    private List<ImageButton> buttons = new ArrayList<ImageButton>();
    private int width;
    private int height;
    private Paint paint = new Paint();
    // °ëÍ¸Ã÷¾ØÐÎ±ß¿ò
    private Rect rect = null;

    public Menu(int x, int y, int width, int height) {
        super(x, y);
        this.width = width;
        this.height = height;
        paint.setColor(Color.GRAY);
        paint.setAlpha(150);
        rect = new Rect(0, 0, width, height);
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public void addButton(ImageButton button) {
        this.buttons.add(button);
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public void move() {

    }

    @Override
    public void paint(Canvas c) {
        c.drawRect(rect, paint);
        for (ImageButton button : buttons) {
            button.paint(c);
        }
    }

    @Override
    public void subLife(int power) {

    }

}
