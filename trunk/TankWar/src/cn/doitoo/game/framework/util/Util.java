package cn.doitoo.game.framework.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


/**
 * 常用工具类
 * @author Oliver O
 *
 */
public class Util {

	public static Bitmap getBitMapById(Context context,int id){
		return  BitmapFactory.decodeStream(context.getResources().openRawResource(id));
	}
}
