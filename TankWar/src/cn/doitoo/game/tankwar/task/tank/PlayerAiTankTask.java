package cn.doitoo.game.tankwar.task.tank;

import android.graphics.Canvas;
import android.graphics.Point;
import cn.doitoo.game.framework.task.GameDrawTask;
import cn.doitoo.game.framework.util.Util;
import cn.doitoo.game.tankwar.role.tank.aitank.AITank;
import cn.doitoo.game.tankwar.role.tank.aitank.AITank1;
import cn.doitoo.game.tankwar.role.tank.aitank.AITank2;
import cn.doitoo.game.tankwar.role.tank.aitank.AITank3;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ñô¿û
 * Date: 2010-7-5
 * Time: 21:51:16
 */
public class PlayerAiTankTask implements GameDrawTask {

    private List<AITank> aiTanks = new ArrayList<AITank>();

    public PlayerAiTankTask() {
        AITank1 aiTank1 = new AITank1(50, 100);
        Point startNodePoint1 = new Point(50, 100);
        Point endNodePoint1 = new Point(480, 320);
        aiTank1.setPathList(Util.computeShortestPath(startNodePoint1, endNodePoint1));
        aiTanks.add(aiTank1);

        AITank2 aiTank2 = new AITank2(50, 150);
        Point startNodePoint2 = new Point(50, 150);
        Point endNodePoint2 = new Point(480, 320);
        aiTank2.setPathList(Util.computeShortestPath(startNodePoint2, endNodePoint2));
        aiTanks.add(aiTank2);

        AITank3 aiTank3 = new AITank3(150, 200);
        Point startNodePoint3 = new Point(150, 200);
        Point endNodePoint3 = new Point(480, 320);
        aiTank3.setPathList(Util.computeShortestPath(startNodePoint3, endNodePoint3));
        aiTanks.add(aiTank3);


    }

    public void draw(Canvas c) {
        for (AITank aiTank : aiTanks) {
            aiTank.move();
            aiTank.paint(c);
        }
    }
}
