package cn.doitoo.game.framework.context;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author Oliver O
 *
 */
public  class GameContext {
  
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
  
  
}
