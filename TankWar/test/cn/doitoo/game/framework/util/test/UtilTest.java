package cn.doitoo.game.framework.util.test;

import android.graphics.Point;
import cn.doitoo.game.framework.arithmetic.Floyd;
import cn.doitoo.game.framework.exception.ViewException;
import cn.doitoo.game.framework.util.MatrixIndexUtil;
import cn.doitoo.game.framework.util.Util;
import cn.doitoo.game.tankwar.task.DrawMapTask;
import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: 阳葵
 * Date: 2010-6-26
 * Time: 10:17:33
 */
public class UtilTest {
    @Test
    public void testGameMap01Vector() throws Exception {
        int[][] vector = Util.gameMap01Vector(DrawMapTask.tank_map1, DrawMapTask.passValue);
        System.out.println("i-length:" + vector.length);
        System.out.println("j-length:" + vector[0].length);
    }

    @Test
    public void testWorldPoint2Node() {
        int x = 144;
        int y = 0;

        int w = 48;
        int h = 48;
        int i = (144 % w == 0) ? 144 / w : 144 / w + 1;
        int j = (y % h == 0) ? y / h : y / h + 1;
        System.out.println("i:" + i);
        System.out.println("j:" + j);
    }

    @Test
    public void testGetPoint() {
        int[][] background2_map1 = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0},
                {0, 5, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0,
                        0, 0, 5, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 4, 0, 0, 4, 0, 0, 4, 0, 0, 0,
                        0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 2, 0, 0, 0,
                        0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0}
        };

        for (int row = 0; row < background2_map1.length; row++) {
            for (int cols = 0; cols < background2_map1[row].length; cols++) {
                int index = background2_map1[row][cols];
                int x = cols * 48;
                int y = row * 48;
                int node = MatrixIndexUtil.convertPoint2Node(cols, row, cols);
                if (index == 2) {
                    System.out.println("炮塔(2): " + x + "," + y + " " + row + ":" + cols + " node:" + node);
                } else if (index == 5) {
                    System.out.println("坦克开始位置(5): " + x + "," + y + " " + row + ":" + cols + " node:" + node);
                } else if (index == 4) {
                    System.out.println("坦克结束位置(4): " + x + "," + y + " " + row + ":" + cols + " node:" + node);
                }
            }
        }

    }


}
