package cn.doitoo.game.framework.context;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class GameContext {
  
	private static Map<String,Object> map = new HashMap<String,Object>();
    
	public  Object get(String key){
		return map.get(key);
	}
	
	protected  void set(String key,Object value){
		map.put(key, value);
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
