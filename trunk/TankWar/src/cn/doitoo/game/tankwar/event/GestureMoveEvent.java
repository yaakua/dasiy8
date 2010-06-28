package cn.doitoo.game.tankwar.event;

import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import cn.doitoo.game.framework.arithmetic.PathSolver;
import cn.doitoo.game.framework.context.G;
import cn.doitoo.game.framework.event.ITouchEventHandler;
import cn.doitoo.game.framework.map.DoitooMap;
import cn.doitoo.game.framework.math.LinearSolver;
import cn.doitoo.game.framework.util.Util;
import cn.doitoo.game.tankwar.role.tank.player.PlayerHeroTank;

import java.util.List;

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
				Rect playerCurrentRect = new Rect(playerX, playerY, (int) (playerX + player.getWidth()), (int) (playerY + player.getHeight()));

				// 如果点击的是坦克判断当前坦克是否被选中，如果选中才进行移动
				if (playerCurrentRect.contains((int) preX, (int) preY)) {
					if (player.isSelected()) {
						player.setSelected(false);
					} else {
						player.setSelected(true);
					}
				} else {
					if (!player.isSelected())
						return;
				}

				Point startNodePoint = new Point(playerX, playerY);
				Util.screen2world(map, startNodePoint);
				int startNode = Util.worldPoint2Node(map, startNodePoint);

				Point endNodePoint = new Point((int) preX, (int) preY);
				Util.screen2world(map, endNodePoint);
				int endNode = Util.worldPoint2Node(map, endNodePoint);

				List pathList = pathSolver.computeShortestPath(gameMap01Vector, startNode, endNode);
				player.setPathList(pathList);

			}
		}

	}

}
