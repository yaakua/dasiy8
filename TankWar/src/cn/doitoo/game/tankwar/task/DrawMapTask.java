package cn.doitoo.game.tankwar.task;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
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
import cn.doitoo.game.tankwar.effect.ClickEffect;
import cn.doitoo.game.tankwar.role.building.Pagoda;
import cn.doitoo.game.tankwar.role.building.player.PlayerPagoda1;
import cn.doitoo.game.tankwar.role.tank.aitank.AITank;
import cn.doitoo.game.tankwar.role.tank.player.PlayerHeroTank;

import java.util.ArrayList;
import java.util.List;

public class DrawMapTask implements GameDrawTask {

    private DoitooMap map = null;

    private Rect mapRect = null;

    private List<Pagoda> pagodas = new ArrayList<Pagoda>();

    /**
     * 地图分布图（第一关）
     */
    public static final byte[][] tank_map1 = {{4, 4, 4, 4, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3},
            {4, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3}, {4, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3},
            {1, 1, 1, 1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1, 3, 3, 3, 1, 1, 3}, {1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3},
            {1, 3, 1, 1, 3, 3, 1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1, 1, 1, 3}, {1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 1, 3},
            {1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3}, {1, 3, 1, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3},
            {1, 3, 1, 1, 3, 3, 3, 3, 1, 3, 3, 3, 1, 3, 3, 1, 1, 1, 1, 3}, {1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4},
            {1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1, 1, 1, 2, 4}, {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4},};

    /**
     * 当前地图排列数组当中代表通道的类型 凡是小于等于此值都为通道
     */
    public static final int passValue = 1;

    public DrawMapTask() {
        Context context = G.getContext();
        int[] resIds = {R.drawable.tile_regular2, R.drawable.tile_trench1, R.drawable.tile_breakable, R.drawable.tile_breakable1};
        map = new DoitooMap(tank_map1, resIds, passValue, context, 0, 0);
        // 保存当前map对象至全局变量当中，以便其它类获取当前地图对象
        G.setDoitooMap(map);
        // 将地图排列数组转成01矩阵
        PathSolver pathSolver = new Dijkstra();
        int[][] gameMap01Vector = Util.gameMap01Vector(tank_map1, passValue);
        G.set("gameMap01Vector", gameMap01Vector);
        G.set("pathSolver", pathSolver);
        map.setTouchEventHandler(new MyMapOnTouchEvent());
        mapRect = new Rect(0, 48, (Integer) G.get("screenWidth"), (Integer) G.get("screenHeight") - 48);
        initPagodas();
    }

    private void initPagodas() {
        PlayerPagoda1 pagoda1 = new PlayerPagoda1(48, 432);
        PlayerPagoda1 pagoda2 = new PlayerPagoda1(432, 240);
        PlayerPagoda1 pagoda3 = new PlayerPagoda1(912, 288);
        pagodas.add(pagoda1);
        pagodas.add(pagoda2);
        pagodas.add(pagoda3);

    }


    public void draw(Canvas canvas) {
        // 背景色
        canvas.drawRGB(0, 0, 0);
        map.paint(canvas);
        for (Pagoda pagoda : pagodas) {
            pagoda.setTanks(AITank.tanks);
            pagoda.paint(canvas);
        }
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
            player = (PlayerHeroTank) G.get("playerHeroTankTask");
            if (player == null || player.isSelected() || !mapRect.contains(x, y)) {
                return;
            }
            if (Math.abs(x - preX) < rect && Math.abs(y - preY) < rect) {
                int playerX = player.getX();
                int playerY = player.getY();
                Point startNodePoint = new Point(playerX, playerY);

                Point endNodePoint = new Point(preX, preY);
                CoordinateUtil.screen2world(endNodePoint);

                //点击特效动画
                ClickEffect clickEffect = (ClickEffect) player.getEffectByKey("clickEffect");
                if (clickEffect == null)
                    clickEffect = new ClickEffect(endNodePoint.x, endNodePoint.y);
                else {
                    clickEffect.setPosition(endNodePoint.x, endNodePoint.y);
                    clickEffect.setTime(6000);
                }
                player.addEffect("clickEffect", clickEffect);
                player.setPathList(Util.computeShortestPath(startNodePoint, endNodePoint));
            }
        }
    }

}
