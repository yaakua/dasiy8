package cn.doitoo.game.tankwar.role.tank;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: 阳葵
 * Date: 2010-7-11
 * Time: 19:06:13
 */
public abstract class HeroTank extends Tank {
    public static List<HeroTank> HeroTanks = new Vector<HeroTank>();

    /**
     * 初始化英雄坦克
     *
     * @param x 初始化X坐标
     * @param y 初始化Y坐标
     */
    public HeroTank(int x, int y) {
        super(x, y);
        HeroTanks.add(this);
    }


}
