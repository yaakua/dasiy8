package cn.doitoo.game.tankwar.task.tank;

import java.util.List;

import android.graphics.Canvas;
import android.graphics.Rect;
import cn.doitoo.game.framework.context.G;
import cn.doitoo.game.framework.map.DoitooMap;
import cn.doitoo.game.framework.task.DrawGraphicTask;
import cn.doitoo.game.tankwar.role.tank.player.PlayerHeroTank;

/**
 * ���Ӣ��̹�˲������
 * User: ����
 * Date: 2010-6-27
 * Time: 11:04:38
 */
public class PlayerHeroTankTask extends DrawGraphicTask {
    private PlayerHeroTank player;
    private DoitooMap map;
    private List<Rect> passRectList;

    public PlayerHeroTankTask() {
//        map = (DoitooMap) G.get(DoitooMap.class.getName());
//        if (map == null) {
//            throw new ViewException("Doitoomap is null");
//        }

//        passRectList = map.getPassRectList();
        //�����ȡ��Ļ��һ��Ϊͨ���ľ��Σ����ڳ�ʼ��̹������
        //TODO ��ȡ�����ļ���ȡ̹������
//        int randrom = Util.getRandomDirect(passRectList.size());
//        Rect rect = passRectList.get(10);
        player = new PlayerHeroTank(0,0);
        G.set("playerHeroTankTask", player);
    }

    @Override
    public void draw(Canvas c) {
        player.move();
        player.paint(c);
    }
}
