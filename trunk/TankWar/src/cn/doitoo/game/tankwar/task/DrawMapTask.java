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
import cn.doitoo.game.tankwar.role.tank.player.PlayerHeroTank;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DrawMapTask implements GameDrawTask {

    private DoitooMap map = null;

    private Rect mapRect = null;

    private List<Pagoda> pagodas = new ArrayList<Pagoda>();

    public static final byte[][] tank_map1 = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                    1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                    1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 14, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                    1, 1, 1, 1, 1, 1, 1, 1, 1, 11, 1, 1, 1, 1},
            {1, 14, 1, 1, 11, 81, 11, 1, 1, 14, 14, 1, 1, 1, 1, 1,
                    1, 1, 1, 11, 81, 11, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 11, 89, 11, 1, 14, 14, 1, 20, 20, 1, 14, 14,
                    20, 1, 1, 11, 89, 11, 1, 11, 20, 1, 1, 24, 1, 1},
            {1, 14, 11, 1, 6, 89, 6, 1, 20, 1, 1, 1, 1, 24, 1, 1,
                    14, 14, 1, 6, 89, 6, 1, 1, 20, 14, 1, 1, 1, 1},
            {20, 14, 1, 1, 11, 89, 11, 1, 20, 1, 1, 1, 1, 1, 1, 1,
                    1, 14, 1, 11, 89, 11, 24, 1, 1, 11, 1, 1, 11, 1},
            {1, 14, 14, 1, 6, 89, 6, 1, 14, 20, 20, 20, 20, 20, 20, 20,
                    1, 14, 1, 6, 89, 6, 1, 1, 1, 1, 20, 1, 1, 1},
            {24, 14, 20, 20, 11, 89, 11, 1, 14, 20, 20, 20, 31, 31, 20, 20,
                    1, 1, 1, 11, 89, 11, 1, 1, 14, 1, 20, 1, 24, 1},
            {1, 14, 14, 14, 11, 89, 11, 24, 20, 1, 20, 20, 20, 20, 20, 20,
                    1, 24, 1, 11, 89, 11, 1, 20, 14, 14, 1, 1, 24, 1},
            {14, 14, 20, 1, 11, 89, 11, 1, 14, 24, 1, 1, 1, 1, 1, 1,
                    14, 1, 1, 11, 89, 11, 1, 20, 1, 14, 14, 1, 1, 1},
            {11, 14, 20, 1, 11, 97, 11, 1, 14, 1, 1, 1, 1, 1, 1, 1,
                    14, 1, 1, 11, 97, 11, 1, 1, 1, 1, 14, 20, 1, 1},
            {14, 20, 1, 1, 6, 7, 6, 1, 20, 1, 1, 24, 1, 1, 1, 1,
                    14, 1, 1, 6, 7, 6, 1, 1, 11, 20, 20, 20, 1, 1},
            {14, 20, 11, 1, 6, 7, 6, 1, 14, 14, 1, 1, 1, 1, 1, 1,
                    14, 1, 1, 6, 7, 6, 1, 1, 20, 20, 1, 24, 1, 1},
            {14, 20, 1, 1, 6, 7, 6, 1, 1, 14, 14, 1, 1, 1, 1, 14,
                    14, 1, 1, 6, 7, 6, 1, 1, 20, 1, 1, 1, 11, 1},
            {1, 14, 1, 1, 1, 24, 1, 1, 1, 1, 20, 14, 1, 1, 1, 14,
                    1, 1, 1, 1, 1, 1, 1, 1, 14, 14, 1, 1, 1, 1},
            {1, 14, 14, 1, 11, 1, 1, 1, 24, 1, 1, 14, 14, 24, 1, 1,
                    1, 1, 1, 1, 1, 1, 1, 1, 1, 14, 1, 1, 1, 1},
            {1, 1, 14, 14, 1, 1, 1, 1, 1, 1, 1, 1, 14, 14, 1, 1,
                    11, 11, 1, 1, 1, 11, 1, 1, 14, 14, 24, 11, 1, 1},
            {24, 1, 1, 14, 1, 1, 1, 14, 14, 14, 14, 14, 14, 14, 14, 1,
                    1, 1, 1, 1, 1, 1, 14, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 14, 14, 1,
                    1, 1, 14, 14, 14, 14, 1, 1, 11, 11, 11, 11, 1, 1},
            {1, 1, 1, 24, 24, 24, 1, 1, 1, 24, 1, 1, 1, 14, 1, 1,
                    1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 14, 1, 1,
                    1, 1, 1, 1, 1, 1, 1, 1, 11, 11, 1, 1, 1, 1},
            {7, 13, 7, 7, 13, 7, 7, 13, 7, 7, 7, 1, 1, 14, 14, 14,
                    1, 1, 7, 13, 7, 7, 7, 7, 7, 13, 13, 13, 13, 13},
            {99, 100, 100, 100, 100, 100, 100, 100, 100, 100, 101, 1, 1, 1, 14, 1,
                    1, 1, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 101},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 23, 1, 1, 1, 14, 1,
                    1, 1, 23, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 23, 1, 1, 1, 14, 1,
                    1, 1, 23, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 23, 1, 1, 1, 14, 1,
                    1, 1, 23, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 20, 20, 20, 20,
                    20, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 11, 11, 11, 30, 111, 30,
                    11, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 11, 11, 1, 30, 30, 30,
                    11, 11, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };

    /**
     * 代表障碍物的数值数组
     */
    public static final int[] barrierArray = {5,6,7,8,13,23,24,30,31,32,81,82,83,84,85,89,90,97,98,99,100,101};

    public DrawMapTask() {
        Context context = G.getContext();
        map = new DoitooMap(tank_map1, R.drawable.grassland01, barrierArray, context, -140, -504,32,32);
        // 保存当前map对象至全局变量当中，以便其它类获取当前地图对象
        G.setDoitooMap(map);
        // 将地图排列数组转成01矩阵
        PathSolver pathSolver = new Dijkstra();
        int[][] gameMap01Vector = Util.gameMap01Vector(tank_map1, barrierArray);
        G.set("gameMap01Vector", gameMap01Vector);
        G.set("pathSolver", pathSolver);
        map.setTouchEventHandler(new MyMapOnTouchEvent());
        mapRect = new Rect(0, 32, (Integer) G.get("screenWidth"), (Integer) G.get("screenHeight") -32);
        initPagodas();
    }

    private void initPagodas() {
        PlayerPagoda1 pagoda1 = new PlayerPagoda1(320, 672);
        PlayerPagoda1 pagoda2 = new PlayerPagoda1(544, 672);
        pagodas.add(pagoda1);
        pagodas.add(pagoda2);
    }


    public void draw(Canvas canvas) {
        // 背景色
        canvas.drawRGB(0, 0, 0);
        map.paint(canvas);
        deletePagoda();
        for (Pagoda pagoda : pagodas) {
            pagoda.paint(canvas);
        }
    }

    private void deletePagoda() {
        Iterator iterator = pagodas.iterator();
        while (iterator.hasNext()) {
            Pagoda pagoda = (Pagoda) iterator.next();
            if (!pagoda.isVisabled()) {
                iterator.remove();
            }
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
            maxWidth = screenWidth - map.getWidth() - 32;
            maxHeight = screenHeight - map.getHeight() - 32;
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

            int px = Math.min(Math.max(map.getX() + deltaX, maxWidth), 32);
            int py = Math.min(Math.max(map.getY() + deltaY, maxHeight), 32);
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
                if (clickEffect == null) {
                    clickEffect = new ClickEffect(endNodePoint.x, endNodePoint.y);
                    clickEffect.setTime(6);
                } else {
                    clickEffect.setPosition(endNodePoint.x, endNodePoint.y);
                }
                player.addEffect("clickEffect", clickEffect);
                player.setPathList(Util.computeShortestPath(startNodePoint, endNodePoint));
            }
        }
    }

}
