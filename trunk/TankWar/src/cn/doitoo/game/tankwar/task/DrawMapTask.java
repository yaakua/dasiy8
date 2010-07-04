package cn.doitoo.game.tankwar.task;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.MotionEvent;
import cn.doitoo.game.framework.arithmetic.Dijkstra;
import cn.doitoo.game.framework.arithmetic.PathSolver;
import cn.doitoo.game.framework.context.G;
import cn.doitoo.game.framework.event.TouchEventHandler;
import cn.doitoo.game.framework.map.DoitooMap;
import cn.doitoo.game.framework.task.GameDrawTask;
import cn.doitoo.game.framework.util.CoordinateUtil;
import cn.doitoo.game.framework.util.Util;
import cn.doitoo.game.tankwar.R;
import cn.doitoo.game.tankwar.effect.ClickCircle;
import cn.doitoo.game.tankwar.role.tank.player.PlayerHeroTank;

public class DrawMapTask implements GameDrawTask {

    private DoitooMap map = null;

    /**
     * ��ͼ�ֲ�ͼ����һ�أ�
     */
    public static final byte[][] tank_map1 = {{4, 4, 4, 4, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3},
            {4, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3}, {4, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3},
            {1, 1, 1, 1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1, 3, 3, 3, 1, 1, 3}, {1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3},
            {1, 3, 1, 1, 3, 3, 1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1, 1, 1, 3}, {1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 1, 3},
            {1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3}, {1, 3, 1, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3},
            {1, 3, 1, 1, 3, 3, 3, 3, 1, 3, 3, 3, 1, 3, 3, 1, 1, 1, 1, 3}, {1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4},
            {1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1, 1, 1, 2, 4}, {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4},};

    /**
     * ��ǰ��ͼ�������鵱�д���ͨ�������� ����С�ڵ��ڴ�ֵ��Ϊͨ��
     */
    public static final int passValue = 1;

    public DrawMapTask() {
        Context context = G.getContext();
        int[] resIds = {R.drawable.tile_regular2, R.drawable.tile_trench1, R.drawable.tile_breakable, R.drawable.tile_breakable1};
        map = new DoitooMap(tank_map1, resIds, passValue, context, 0, 0);
        // ���浱ǰmap������ȫ�ֱ������У��Ա��������ȡ��ǰ��ͼ����
        G.setDoitooMap(map);
        // ����ͼ��������ת��01����
        PathSolver pathSolver = new Dijkstra();
        int[][] gameMap01Vector = Util.gameMap01Vector(tank_map1, passValue);
        G.set("gameMap01Vector", gameMap01Vector);
        G.set("pathSolver", pathSolver);
        map.setTouchEventHandler(new MyMapOnTouchEvent());

    }


    public void draw(Canvas canvas) {
        // ����ɫ
        canvas.drawRGB(0, 0, 0);
        map.paint(canvas);
    }

    class MyMapOnTouchEvent extends TouchEventHandler {
        private int preX = -1;
        private int preY = -1;
        private int maxWidth;
        private int maxHeight;
        private PlayerHeroTank player;

        MyMapOnTouchEvent() {
            int screenHeight = G.getInt("screenHeight");
            int screenWidth = G.getInt("screenWidth");
            maxWidth = screenWidth - map.getWidth() - 48;
            maxHeight = screenHeight - map.getHeight() - 48;
            player = (PlayerHeroTank) G.get("playerHeroTankTask");
        }

        @Override
        public void onTouchDown(MotionEvent event) {
            preX = (int) event.getX();
            preY = (int) event.getY();
        }

        @Override
        public void onTouchMove(MotionEvent event) {
            int x = (int) event.getX();
            int y = (int) event.getY();

            int deltaX = x - preX;
            int deltaY = y - preY;

            if (Math.abs(deltaX) < 5 && Math.abs(deltaY) < 5)
                return;

            int px = Math.min(Math.max(map.getX() + deltaX, maxWidth), 48);
            int py = Math.min(Math.max(map.getY() + deltaY, maxHeight), 48);
            map.setPosition(px, py);
            preX = x;
            preY = y;
        }

        @Override
        public void onTouchUp(MotionEvent event) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            int rect = 2;
            if (Math.abs(x - preX) < rect && Math.abs(y - preY) < rect) {
                if (player != null) {
                    int playerX = player.getX();
                    int playerY = player.getY();
                    Point startNodePoint = new Point(playerX, playerY);
                    CoordinateUtil.world2screen(startNodePoint);

                    if (!player.isSelected())
                        return;

                    Point endNodePoint = new Point(preX, preY);
                    CoordinateUtil.screen2world(endNodePoint);

                    //�����Ч����
                    ClickCircle clickCircle = (ClickCircle) player.getAnimation("clickCircle");
                    if (clickCircle == null)
                        clickCircle = new ClickCircle(endNodePoint.x, endNodePoint.y);
                    else
                        clickCircle.setPosition(endNodePoint.x, endNodePoint.y);
                    player.addAnimation("clickCircle", clickCircle);

                    //̹��������Ҫת������������
                    CoordinateUtil.screen2world(startNodePoint);
                    player.setPathList(Util.computeShortestPath(startNodePoint, endNodePoint));

                }
            }
        }
    }

}
