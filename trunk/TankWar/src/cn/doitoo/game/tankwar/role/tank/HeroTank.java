package cn.doitoo.game.tankwar.role.tank;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: ����
 * Date: 2010-7-11
 * Time: 19:06:13
 */
public abstract class HeroTank extends Tank {
    public static List<HeroTank> HeroTanks = new Vector<HeroTank>();

    /**
     * ��ʼ��Ӣ��̹��
     *
     * @param x ��ʼ��X����
     * @param y ��ʼ��Y����
     */
    public HeroTank(int x, int y) {
        super(x, y);
        HeroTanks.add(this);
    }


}
