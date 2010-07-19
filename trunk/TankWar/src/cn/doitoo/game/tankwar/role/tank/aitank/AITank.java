package cn.doitoo.game.tankwar.role.tank.aitank;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import cn.doitoo.game.framework.context.G;
import cn.doitoo.game.framework.role.MovableRole;
import cn.doitoo.game.framework.util.CoordinateUtil;
import cn.doitoo.game.framework.util.Util;
import cn.doitoo.game.tankwar.role.building.Pagoda;
import cn.doitoo.game.tankwar.role.bullet.Bullet;
import cn.doitoo.game.tankwar.role.bullet.YellowBullet;
import cn.doitoo.game.tankwar.role.tank.HeroTank;
import cn.doitoo.game.tankwar.role.tank.Tank;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA. User: 阳葵 Date: 2010-7-5 Time: 21:52:38
 */
public abstract class AITank extends Tank {
    public static List<AITank> AITanks = new Vector<AITank>();
    // 所属的子弹
    private List<Bullet> bullets = new ArrayList<Bullet>();
    // 攻击对象
    private MovableRole attackRole;
    // 攻击范围
    private int range = 150;

    private Rect attackRect;

    public AITank(int x, int y) {
        super(x, y);
        AITanks.add(this);
        this.setDirection(move_direct.DOWN);
        attackRect = computeAttackRect();
        this.setSpeed(5);
//         Paint paint = new Paint();
//         paint.setColor(Color.GREEN);
//         this.setPaint(paint);
    }

    @Override
    public void paint(Canvas c) {
        super.paint(c);
        this.attack();
        drawBullets(c);
//        c.drawRect(attackRect, this.getPaint());
    }
    // 画子弹

    private void drawBullets(Canvas c) {
        if (bullets.isEmpty()) {
            return;
        }
        Iterator<Bullet> bulletIt = bullets.iterator();
        while (bulletIt.hasNext()) {
            Bullet bullet = bulletIt.next();
            if (bullet.isVisabled())
                bullet.paint(c);
            else if (!attackRect.contains(bullet.getX(), bullet.getY()) || bullet.getAttackRole() == null || !bullet.getAttackRole().isVisabled()
                    || !bullet.isVisabled())
                bulletIt.remove();
        }
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

            // 判断是否需要切换到下一个节点
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
            Point endPoint = this.getEndPoint();
            if (x == endPoint.x && y == endPoint.y) {
                int hiddenTankCount = G.getInt("hiddenTankCount");
                hiddenTankCount++;
                G.set("hiddenTankCount", hiddenTankCount, true);
                this.setVisabled(false);
            }
        } else {
            pathList = null;
        }
    }

    private void attack() {
        if (Pagoda.pagodas.isEmpty() && HeroTank.HeroTanks.isEmpty()) {
            return;
        }
        attackRect = computeAttackRect();
        this.move();
        if (attackRole != null && !attackRole.isVisabled()) {
            attackRole = null;
        }

        isAttackeding();

        if (attackRole != null && this.isAttack()) {
            if (!attackRect.contains(attackRole.getX(), attackRole.getY())) {
                this.setPathList(Util.computeShortestPath(new Point(this.getX(), this.getY()), new Point(attackRole.getX(), attackRole.getY())));
                this.setHasChangePathList(true);
            } else {
                this.stay();
            }
        }
        //如果未被攻击，且原有路线被更改，则重新规划移动路径
        if (!this.isAttack() && this.isHasChangePathList()) {
            this.setPathList(Util.computeShortestPath(new Point(this.getX(), this.getY()), this.getEndPoint()));
        }

        for (Pagoda pagoda : Pagoda.pagodas) {
            if (attackRect.contains(pagoda.getX(), pagoda.getY())) {
                addAttacked(pagoda);
                break;
            }
        }
        for (HeroTank heroTank : HeroTank.HeroTanks) {
            int x = heroTank.getX();
            int y = heroTank.getY();
            if (attackRect.contains(x, y)) {
                addAttacked(heroTank);
                break;
            }
        }
    }

    /**
     * 判断是否被攻击
     */
    private void isAttackeding() {
        if (!this.isAttack() || this.getAttacker() == null) {
            return;
        }
        MovableRole attacker = this.getAttacker();
        if (attackRect.contains(attacker.getX(), attacker.getY())) {
            addAttacked(attacker);
        }
    }

    /**
     * 添加被攻击对象
     */
    private void addAttacked(MovableRole attacked) {
        Point centerPoint = this.getCenterPoint();
        Bullet bullet = new YellowBullet(centerPoint.x, centerPoint.y);
        bullet.setAttackRole(attacked);
        bullet.setPower(this.getPower());
        bullet.setAttacker(this);
        bullets.add(bullet);
        attackRole = attacked;
    }

    /**
     * 计算攻击范围(坦克运行方向前面的一个矩形)
     *
     * @return 攻击范围
     */
    private Rect computeAttackRect() {
        int left = 0;
        int top = 0;
        int x = this.getX();
        int y = this.getY();
        int w = this.getWidth();
        int h = this.getHeight();
        MovableRole.move_direct direction = this.getDirection();
        if (direction.equals(MovableRole.move_direct.DOWN)) {
            left = x - (range / 2 - w / 2);
            top = y + h;
        } else if (direction.equals(MovableRole.move_direct.LEFT)) {
            left = x - range;
            top = y - (range / 2 - h / 2);
        } else if (direction.equals(MovableRole.move_direct.RIGHT)) {
            left = x + w;
            top = y - (range / 2 - h / 2);
        } else if (direction.equals(MovableRole.move_direct.UP)) {
            left = x - (range / 2 - w / 2);
            top = y - range;
        }
        int right = left + range;
        int bottom = top + range;
        return new Rect(left, top, right, bottom);
    }


}
