package cn.doitoo.game.framework.context;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import cn.doitoo.game.framework.exception.ViewException;

import android.content.Context;
import android.view.SurfaceHolder;

/**
 * 
 * @author Oliver O
 *
 */
public  class GameContext {
	protected static SurfaceHolder HOLDER;
	protected static Context CONTEXT;
	
	public GameContext() {
		if(HOLDER==null ||CONTEXT==null){
			throw new ViewException("GameContext.HOLDER or GameContext.CONTEXT is null");
		}
	}

	private static Map<String,Object> map = new HashMap<String,Object>();
	
	public  static Object get(String key){
		return map.get(key);
	}
	
	public static  void set(String key,Object value){
		if(!map.containsKey(key))
		map.put(key, value);
	}
	public static  void set(String key,Object value,boolean modifiable){
		if(modifiable&&!map.containsKey(key))map.put(key, value);
		else if(modifiable&&map.containsKey(key))throw new RuntimeException("Access deny: "+key+" is not modifiable!");
		else set(key, value);
	}
	
	public  String listKeys(){
		Set<String> keyset = map.keySet();
		StringBuilder sb = new StringBuilder();
		for (String str : keyset) {
			sb.append(str+"_");
		}
		return sb.toString();
	}

	public static void init(SurfaceHolder holder,Context context){
		GameContext.HOLDER =holder;
		GameContext.CONTEXT = context;
	}
}
