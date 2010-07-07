package cn.doitoo.game.tankwar.role.tank.aitank;

import android.graphics.Point;
import cn.doitoo.game.framework.role.MovableRole;
import cn.doitoo.game.framework.util.CoordinateUtil;
import cn.doitoo.game.tankwar.role.tank.Tank;

/**
 * Created by IntelliJ IDEA.
 * User: ����
 * Date: 2010-7-5
 * Time: 21:52:38
 */
public abstract class AITank extends Tank {
    /**
     * ��ʼ��Ӣ��̹��
     *
     * @param x ��ʼ��X����
     * @param y ��ʼ��Y����
     */
    public AITank(int x, int y) {
        super(x, y);
    }

    @Override
    public void move() {
        if (pathList != null && !pathList.isEmpty() && pathListIndex < pathList.size()) {
            int node = (Integer) pathList.get(pathListIndex);
            Point nextNode = CoordinateUtil.node2WorldPoint(node);
            int x = this.getX();
            int y = this.getY();
            int speed = this.getSpeed();
            distanceX = nextNode.x - x;
            distanceY = nextNode.y - y;
            int absDistanceX = Math.abs(distanceX);
            int absDistanceY = Math.abs(distanceY);
            int max = Math.max(absDistanceX, absDistanceY);
            move_direct direct = this.getDirection();
            if (max == absDistanceX && distanceX > 0) {
                direct = MovableRole.move_direct.RIGHT;
                x += speed;
            } else if (max == absDistanceX && distanceX < 0) {
                direct = MovableRole.move_direct.LEFT;
                x -= speed;
            } else if (max == absDistanceY && distanceY > 0) {
                direct = MovableRole.move_direct.DOWN;
                y += speed;
            } else if (max == absDistanceY && distanceY < 0) {
                direct = MovableRole.move_direct.UP;
                y -= speed;
            }

            // �ж��Ƿ���Ҫ�л�����һ���ڵ�
            if ((x > nextNode.x && direct.equals(MovableRole.move_direct.RIGHT)) || (y > nextNode.y && direct.equals(MovableRole.move_direct.DOWN))) {
                x = nextNode.x;
                y = nextNode.y;
                pathListIndex++;
            }
            if (x < nextNode.x && direct.equals(MovableRole.move_direct.LEFT) || (y < nextNode.y && direct.equals(MovableRole.move_direct.UP))) {
                x = nextNode.x;
                y = nextNode.y;
                pathListIndex++;
            }

            this.setDirection(direct);
            this.setPosition(x, y);
        } else {
            pathListIndex = 0;
        }
    }
}
