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
				//��̬���µ�ǰ̹�˿ɵ���ķ�Χ
				Rect playerCurrentRect = new Rect(startNodePoint.x, startNodePoint.y, (int) (startNodePoint.x + player.getWidth()), (int) (startNodePoint.y + player.getHeight()));

				// ����������̹���жϵ�ǰ̹���Ƿ�ѡ�У����ѡ�вŽ����ƶ�
				if (playerCurrentRect.contains((int) preX, (int) preY)) {
					//���ѡ����Ч����
					SelectCircle circle = (SelectCircle) player.getAnimation("circle0");
					if (player.isSelected()) {
						if (circle != null) {
							player.deleteAnimation("circle0");
						}
						player.setSelected(false);
					} else {
						if (circle == null)
							circle = new SelectCircle(playerX, playerY);
						circle.setMoving(true);//����Ϊ���ƶ�������̹����������ƶ���
						player.addAnimation("circle0", circle);
						player.setSelected(true);
					}
				} else {
					if (!player.isSelected())
						return;
				}
				Point endNodePoint = new Point((int) preX, (int) preY);
				Util.screen2world(map, endNodePoint);
				
				//�����Ч����
				ClickCircle clickCircle = (ClickCircle) player.getAnimation("clickCircle");
				if (clickCircle == null)
					clickCircle = new ClickCircle(endNodePoint.x, endNodePoint.y);
				else
					clickCircle.setPosition(endNodePoint.x, endNodePoint.y);
				player.addAnimation("clickCircle", clickCircle);
				
				//̹��������Ҫת������������
				Util.screen2world(map, startNodePoint);
				this.computeShortestPath(startNodePoint,endNodePoint);

			}
		}
	}
	

	/**
	 * ����̹���������������·��
	 * @param startNodePoint ̹���������ͼ�������
	 * @param endNodePoint �û�����������ͼ�е����� 
	 */
	public void computeShortestPath(Point startNodePoint,Point endNodePoint){
		int startNode = Util.worldPoint2Node(map, startNodePoint);
		int endNode = Util.worldPoint2Node(map, endNodePoint);
		List pathList = pathSolver.computeShortestPath(gameMap01Vector, startNode, endNode);
		player.setPathList(pathList);
	}
	
	
}

