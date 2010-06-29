package cn.doitoo.game.tankwar.event;

import java.util.List;

import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import cn.doitoo.game.framework.arithmetic.PathSolver;
import cn.doitoo.game.framework.context.G;
import cn.doitoo.game.framework.event.ITouchEventHandler;
import cn.doitoo.game.framework.map.DoitooMap;
import cn.doitoo.game.framework.util.Util;
import cn.doitoo.game.tankwar.role.animation.ClickCircle;
import cn.doitoo.game.tankwar.role.animation.SelectCircle;
import cn.doitoo.game.tankwar.role.tank.player.PlayerHeroTank;

public class GestureMoveEvent extends ITouchEventHandler {

	private float preX = -1;
	private float preY = -1;
	private DoitooMap map;
	private float maxWidth;
	private float maxHeight;
	private PlayerHeroTank player;
	private int[][] gameMap01Vector;
	private PathSolver pathSolver;

	public GestureMoveEvent() {
		map = (DoitooMap) G.get(DoitooMap.class.getName());
		player = (PlayerHeroTank) G.get("playerHeroTankTask");
		float screenHeight = (Float) G.get("screenHeight");
		float screenWidth = (Float) G.get("screenWidth");
		maxWidth = screenWidth - map.getWidth();
		maxHeight = screenHeight - map.getHeight();
		gameMap01Vector = (int[][]) G.get("gameMap01Vector");
		pathSolver = (PathSolver) G.get("pathSolver");
	}

	@Override
	public void onTouchDown(MotionEvent event) {
		preX = event.getX();
		preY = event.getY();
	}

	@Override
	public void onTouchMove(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();

		float deltaX = x - preX;
		float deltaY = y - preY;

		if (Math.abs(deltaX) < 5 && Math.abs(deltaY) < 5)
			return;

		float px = Math.min(Math.max(map.getX() + deltaX, maxWidth), 0);
		float py = Math.min(Math.max(map.getY() + deltaY, maxHeight), 0);
		map.setPosition(px, py);
		preX = x;
		preY = y;

	}

	@Override
	public void onTouchUp(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();

		int rect = 2;
		if (Math.abs(x - preX) < rect && Math.abs(y - preY) < rect) {

			if (player != null) {
				int playerX = (int) player.getX();
				int playerY = (int) player.getY();
				Point startNodePoint = new Point(playerX, playerY);
				Util.world2screen(map, startNodePoint);
				//动态更新当前坦克可点击的范围
				Rect playerCurrentRect = new Rect(startNodePoint.x, startNodePoint.y, (int) (startNodePoint.x + player.getWidth()), (int) (startNodePoint.y + player.getHeight()));

				// 如果点击的是坦克判断当前坦克是否被选中，如果选中才进行移动
				if (playerCurrentRect.contains((int) preX, (int) preY)) {
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
				Point endNodePoint = new Point((int) preX, (int) preY);
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
				this.computeShortestPath(startNodePoint,endNodePoint);

			}
		}
	}
	

	/**
	 * 计算坦克与点击坐标间的最短路径
	 * @param startNodePoint 坦克在世界地图中坐标点
	 * @param endNodePoint 用户点击在世界地图中的坐标 
	 */
	public void computeShortestPath(Point startNodePoint,Point endNodePoint){
		int startNode = Util.worldPoint2Node(map, startNodePoint);
		int endNode = Util.worldPoint2Node(map, endNodePoint);
		List pathList = pathSolver.computeShortestPath(gameMap01Vector, startNode, endNode);
		player.setPathList(pathList);
	}
	
	
}

