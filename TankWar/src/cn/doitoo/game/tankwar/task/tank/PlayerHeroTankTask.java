package cn.doitoo.game.tankwar.task.tank;

import android.graphics.Canvas;
import cn.doitoo.game.framework.context.G;
import cn.doitoo.game.framework.thread.GameDrawTask;
import cn.doitoo.game.tankwar.role.tank.player.PlayerHeroTank;

/**
 * 玩家英雄坦克操作相关
 * User: 阳葵
 * Date: 2010-6-27
 * Time: 11:04:38
 */
public class PlayerHeroTankTask extends GameDrawTask  {
    private PlayerHeroTank player;

    public PlayerHeroTankTask() {
        player = new PlayerHeroTank(150,50);
        G.set("playerHeroTankTask", player);
    }

	@Override
	public void draw(Canvas canvas) {
		player.move();
        player.paint(canvas);
		
	}
}
