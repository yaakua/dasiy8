package cn.doitoo.game.tankwar.task.tank;

import android.graphics.Canvas;
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
import java.util.List;

/**
 * Created by IntelliJ IDEA. User: ���� Date: 2010-7-5 Time: 21:51:16
 */
public class PlayerAiTankTask implements GameDrawTask {
    private boolean aiTankEmpty;

    public PlayerAiTankTask() {
        aiTankEmpty = true;
        new PlayerAiTankTaskTimeThread(this).start();
    }

    public void draw(Canvas c) {
        deleteAITank();
        G.addDebugInfo("tankNumber", AITank.AITanks.size() + "");
        for (AITank tank : AITank.AITanks) {
            tank.paint(c);
        }
    }

    public void deleteAITank() {
        Iterator iterator = AITank.AITanks.iterator();
        while (iterator.hasNext()) {
            Tank tank = (Tank) iterator.next();
            if (!tank.isVisabled())
                iterator.remove();
        }
        //�жϵ�ǰ����AI̹���Ƿ�Ϊ��
        aiTankEmpty = AITank.AITanks.isEmpty();
    }

    class PlayerAiTankTaskTimeThread extends Thread {
        private PlayerAiTankTask father;
        private int time = 0;
        private int count = 1;

        PlayerAiTankTaskTimeThread(PlayerAiTankTask father) {
            this.father = father;
        }

        @Override
        public void run() {
            try {
                while (count < 4) {
                    Thread.sleep(1000);
                    if (AITank.AITanks.isEmpty()) {
                        time++;
                        if (time >5) {
                            int type = count;
                            int totalCount = 15;
                            new MakePlayAiTankThread(father, totalCount, type).start();
                            time = 0;
                            count++;
                        } else {
                            G.set("attackCount", count, true);
                            G.set("attackTime", time, true);
                        }
                    }
                }
                this.interrupt();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * ÿ��1�����ָ������Ĺ������ΪtotalCount
     */
    class MakePlayAiTankThread extends Thread {
        private PlayerAiTankTask father;
        //��Ҫ�������ܹ�����
        private int totalCount = 0;
        //��������
        private int type = 0;
        //��ǰ�Ѳ����Ĺ�������
        private int currentCount = 0;
        //ÿ�β����Ĺ�������
        private int number = 3;

        MakePlayAiTankThread(PlayerAiTankTask father, int totalCount, int type) {
            this.father = father;
            this.totalCount = totalCount;
            this.type = type;
        }

        @Override
        public void run() {
            while (currentCount < totalCount) {
                try {
                    Thread.sleep(1000);
                    father.addAiTank(number, type);
                    currentCount += number;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //�����߳�
            this.interrupt();
        }
    }

    public void addAiTank(int number, int type) {
        int precent = number / 3;
        for (int i = 0; i < number; i++) {
            int x;
            if (i < precent) {
                x = 31;
            } else if (i < 2 * precent) {
                x = 336;
            } else {
                x = 672;
            }
            x += (i * 31);
            AITank aiTank;
            if (type == 1) {
                aiTank = new AITank1(x, 64);
            } else if (type == 2) {
                aiTank = new AITank2(x, 64);
            } else if (type == 3) {
                aiTank = new AITank3(x, 64);
            } else {
                aiTank = new AITank1(x, 64);
            }
            Point startNodePoint1 = new Point(x, 31);
            Point endNodePoint1 = new Point(465, 868);
            aiTank.setEndPoint(endNodePoint1);
            long start = System.currentTimeMillis();
            List pathList = Util.computeShortestPath(startNodePoint1, endNodePoint1);
            G.addDebugInfo("computeShortestPath" + i, (System.currentTimeMillis() - start) + "");
            aiTank.setPathList(pathList);
        }
    }


}
