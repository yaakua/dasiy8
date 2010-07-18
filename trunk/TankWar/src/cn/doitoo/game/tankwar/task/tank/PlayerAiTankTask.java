package cn.doitoo.game.tankwar.task.tank;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import cn.doitoo.game.framework.context.G;
import cn.doitoo.game.framework.task.GameDrawTask;
import cn.doitoo.game.framework.util.Util;
import cn.doitoo.game.tankwar.role.tank.Tank;
import cn.doitoo.game.tankwar.role.tank.aitank.AITank;
import cn.doitoo.game.tankwar.role.tank.aitank.AITank1;
import cn.doitoo.game.tankwar.role.tank.aitank.AITank2;
import cn.doitoo.game.tankwar.role.tank.aitank.AITank3;

import java.util.Iterator;

/**
 * Created by IntelliJ IDEA. User: Ñô¿û Date: 2010-7-5 Time: 21:51:16
 */
public class PlayerAiTankTask implements GameDrawTask {
    private int time = 0;
    private boolean aiTankEmpty;

    private int count = 1;

    public PlayerAiTankTask() {
        aiTankEmpty = true;

    }

    public void draw(Canvas c) {
        deleteAITank();
        for (AITank tank : AITank.AITanks) {
            tank.paint(c);
        }

        if (aiTankEmpty) {
            time++;
            if (time > 100) {
                addAiTank(6);
            } else {
                G.set("attackCount", count, true);
                G.set("attackTime", time, true);
            }
        }
    }

    public void deleteAITank() {
        Iterator iterator = AITank.AITanks.iterator();
        while (iterator.hasNext()) {
            Tank tank = (Tank) iterator.next();
            if (!tank.isVisabled())
                iterator.remove();
        }
        if (AITank.AITanks.isEmpty()) {
            aiTankEmpty = true;
        } else {
            aiTankEmpty = false;
        }
    }

    public void addAiTank(int number) {
        if (count > 3) return;
        AITank aiTank = new AITank1(48, 48);
        int precent = number / 3;
        for (int i = 0; i < number; i++) {
            int x = 48;
            if (i < precent) {
                x = 48;
            } else if (i < 2 * precent) {
                x = 432;
            } else {
                x = 864;
            }
            if (count == 1) {
                aiTank = new AITank1(x, 48);
            } else if (count == 2) {
                aiTank = new AITank2(x, 48);
            } else if (count == 3) {
                aiTank = new AITank3(x, 48);
            }
            Point startNodePoint1 = new Point(x, 48);
            Point endNodePoint1 = new Point(288, 1392);
            aiTank.setEndPoint(endNodePoint1);
            aiTank.setPathList(Util.computeShortestPath(startNodePoint1, endNodePoint1));
        }
        count++;
        time = 0;
    }

}
