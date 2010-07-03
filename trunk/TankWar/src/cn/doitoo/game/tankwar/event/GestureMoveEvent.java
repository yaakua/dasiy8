package cn.doitoo.game.tankwar.event;

import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import cn.doitoo.game.framework.arithmetic.PathSolver;
import cn.doitoo.game.framework.context.G;
import cn.doitoo.game.framework.event.ITouchEventHandler;
import cn.doitoo.game.framework.map.DoitooMap;
import cn.doitoo.game.framework.util.Util;
import cn.doitoo.game.tankwar.effect.ClickCircle;
import cn.doitoo.game.tankwar.effect.SelectCircle;
import cn.doitoo.game.tankwar.role.tank.player.PlayerHeroTank;

import java.util.List;

public class GestureMoveEvent extends ITouchEventHandler {

    private int preX = -1;
    private int preY = -1;
    private DoitooMap map;
    private int maxWidth;
    private int maxHeight;
    private PlayerHeroTank player;
    private int[][] gameMap01Vector;
    private PathSolver pathSolver;

    public GestureMoveEvent() {
        map = (DoitooMap) G.get(DoitooMap.class.getName());
        player = (PlayerHeroTank) G.get("playerHeroTankTask");
        int screenHeight = G.getInt("screenHeight");
        int screenWidth = G.getInt("screenWidth");
        maxWidth = screenWidth - map.getWidth() - 48;
        maxHeight = screenHeight - map.getHeight() - 48;
        gameMap01Vector = (int[][]) G.get("gameMap01Vector");
        pathSolver = (PathSolver) G.get("pathSolver");
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
                Util.world2screen(map, startNodePoint);
                //动态更新当前坦克可点击的范围
                Rect playerCurrentRect = new Rect(startNodePoint.x, startNodePoint.y, (startNodePoint.x + player.getWidth()), (startNodePoint.y + player.getHeight()));

                // 如果点击的是坦克判断当前坦克是否被选中，如果选中才进行移动
                if (playerCurrentRect.contains(preX, preY)) {
                    //添加选中特效动画
                    SelectCircle circle = (SelectCircle) player.getAnimation("circle0");
                    if (player.isSelected()) {
                        if (circle != null) {
                            player.deleteAnimation("circle0");
                        }
                        player.setSelected(false);
                    } else {
                        if (circle == null)
                            circle = new SelectCircle(playerX, playerY);
                        circle.setMoving(true);//设置为可移动（根据坦克坐标进行移动）
                        player.addAnimation("circle0", circle);
                        player.setSelected(true);
                    }
                } else {
                    if (!player.isSelected())
                        return;
                }

                if (playerCurrentRect.contains(x, y)) {
                    return;
                }

                Point endNodePoint = new Point(preX, preY);
                Util.screen2world(map, endNodePoint);

                //点击特效动画
                ClickCircle clickCircle = (ClickCircle) player.getAnimation("clickCircle");
                if (clickCircle == null)
                    clickCircle = new ClickCircle(endNodePoint.x, endNodePoint.y);
                else
                    clickCircle.setPosition(endNodePoint.x, endNodePoint.y);
                player.addAnimation("clickCircle", clickCircle);

                //坦克坐标需要转换成世界坐标
                Util.screen2world(map, startNodePoint);
                this.computeShortestPath(startNodePoint, endNodePoint);

            }
        }
    }


    /**
     * 计算坦克与点击坐标间的最短路径
     *
     * @param startNodePoint 坦克在世界地图中坐标点
     * @param endNodePoint   用户点击在世界地图中的坐标
     */
    public void computeShortestPath(Point startNodePoint, Point endNodePoint) {
        int startNode = Util.worldPoint2Node(map, startNodePoint);

        int[] a = Util.worldPointIn01Vector(map, endNodePoint); //获取当前坐标在01矩阵中的第i行，第j列
        endNodePoint = Util.getDestination(gameMap01Vector, a[0], a[1]); //获取坐标点最近的通道第I行，第J列
        int index = Util.convertPoint2Node(map.getMapRect()[0].length, endNodePoint.x, endNodePoint.y);  //获取世界坐标某个点在世界地图排列数组当中的下标
        endNodePoint = Util.node2WorldPoint(map, index);
        int endNode = Util.worldPoint2Node(map, endNodePoint);

        List pathList = pathSolver.computeShortestPath(gameMap01Vector, startNode, endNode);
        player.setPathList(pathList);
    }


}

