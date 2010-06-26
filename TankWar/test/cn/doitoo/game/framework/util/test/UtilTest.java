package cn.doitoo.game.framework.util.test;

import cn.doitoo.game.framework.arithmetic.Floyd;
import cn.doitoo.game.framework.util.Util;
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
        int[][] vector = Util.gameMap01Vector(DrawMapTask.tank_map1,DrawMapTask.passValue);
        System.out.println("i-length:"+vector.length);
        System.out.println("j-length:"+vector[0].length);
    }
    @Test
    public static void testD(String[] args) {
		int gameMap[][] =Util.gameMap01Vector(DrawMapTask.tank_map1, 1);
		int [][] adjVect = Floyd.gameMap2adjVect(gameMap,gameMap[0].length,gameMap.length);
		for (int i = 0; i < adjVect.length; i++) {
			System.out.println();
		    for(int j=0;j<adjVect.length;j++){
		    	System.out.print(adjVect[i][j]+" ");
		    }
		}
		
	}
}
