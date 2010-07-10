package cn.doitoo.game.tankwar.task;

import android.graphics.Canvas;
import android.util.Log;
import cn.doitoo.game.framework.task.GameDrawTask;
import cn.doitoo.game.tankwar.role.Bullet;

import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: ����
 * Date: 2010-7-7
 * Time: 16:56:25
 */
public class DrawOtherInfoTask implements GameDrawTask {
    public void draw(Canvas c) {
        //ɾ���ӵ�
//        deleteBullet();
        
        //�����ӵ�
//        for (Bullet bullet : Bullet.bullets) {
//            Log.d("bulletVisabled:", bullet.isVisabled() + "");
//            if (bullet.isVisabled()) {
//                bullet.paint(c);
//            }
//        }
    }

    private void deleteBullet() {
        Iterator<Bullet> bulletIt = Bullet.bullets.iterator();
        while (bulletIt.hasNext()) {
            Bullet bullet = bulletIt.next();
            if (!bullet.isVisabled()) {
                bulletIt.remove();
            }
        }
    }
}


