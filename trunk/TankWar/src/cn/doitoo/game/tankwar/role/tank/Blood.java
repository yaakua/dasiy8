package cn.doitoo.game.tankwar.role.tank;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import cn.doitoo.game.framework.role.MovableRole;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: 阳葵
 * Date: 2010-7-10
 * Time: 11:39:58
 */
public class Blood extends MovableRole {
    private static List<Blood> bloods = new Vector<Blood>();
    private Paint backPaint;
    private Paint frontPaint;
    private float fullLife;
    private float frontRectWidth;
    private float width;
    private int height;
    //血条所属的角色
    private MovableRole role;

    public Blood(int x, int y, int width, int height, int fullLife) {
        super(x, y);
        this.frontRectWidth = width;
        this.width = width;
        this.height = height;
        this.fullLife = fullLife;

        backPaint = new Paint();
        backPaint.setColor(Color.RED);
        backPaint.setStyle(Paint.Style.STROKE);//画空心圆时设置此参数，则只画边框
        backPaint.setStrokeWidth(2);//设置边框粗细
        frontPaint = new Paint();
        frontPaint.setColor(Color.GREEN);
        this.setVisabled(true);//默认不显示
//        bloods.add(this);
    }

    /**
     * 设置当前血块的生命值
     *
     * @param currentLife 当前血块值
     */
    public void setCurrentLife(int currentLife) {
        float percent = (float) currentLife / fullLife;
        Log.d("percent", percent + "");
        this.frontRectWidth = width * percent;
        Log.d("frontRectWidth", frontRectWidth + "");
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public void move() {

    }

    @Override
    public void paint(Canvas c) {
        if (!this.isVisabled()) return;
        if (this.role == null) return;
        this.setPosition(this.role.getX(), this.role.getY());
        Point screenPoint = this.getScreenPoint();
        int x = screenPoint.x;
        int y = screenPoint.y;
        //底色
        c.drawRect(x, y, x + width, y + height, backPaint);
        //血色
        c.drawRect(x, y, x + frontRectWidth, y + height, frontPaint);
    }

    public void setRole(MovableRole role) {
        this.role = role;
    }

    @Override
    public void subLife(int power) {

    }
}
