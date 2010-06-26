package cn.doitoo.game.framework.util;

import cn.doitoo.game.tankwar.task.DrawMapTask;
import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: Ñô¿û
 * Date: 2010-6-26
 * Time: 10:17:33
 */
public class UtilTest {
    @Test
    public void testGameMap01Vector() throws Exception {
        int[][] vector = Util.gameMap01Vector(DrawMapTask.tank_map1,DrawMapTask.tank_map_bg);
        System.out.println("i-length:"+vector.length);
        System.out.println("j-length:"+vector[0].length);
    }
}
