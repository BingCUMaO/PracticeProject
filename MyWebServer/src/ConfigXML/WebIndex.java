package ConfigXML;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * WebIndex:	索引类
 * 用来管理从XML文件中所读取的Servlet信息
 * Servlet的具体实现不在此处，
 */
public class WebIndex {
	private ArrayList<ServletEntity> servEntities;
	private ArrayList<ServletMapping> servMappings;
	//K:	servlet-name
	//V:	servlet-class
	private Map<String, String> servEntitiesMap;
	//K:	url-pattern
	//V:	servlet-name
	private Map<String, String> servMappingsMap;

	//构造器
	public WebIndex(ArrayList<ServletEntity> servEntities, ArrayList<ServletMapping> servMappings) {
		this.servEntities = servEntities;
		this.servMappings = servMappings;
		this.servEntitiesMap = new HashMap<String, String>();
		this.servMappingsMap = new HashMap<String, String>();
		
		appendToMap();
	}
	
	//将解析得到的servEntities和servEntities分别映射到Map上，方便通过<K,V>索引
	private void appendToMap() {
		for(ServletEntity se:servEntities) {
			servEntitiesMap.put(se.getName(), se.get_class());
		}
		for(ServletMapping sm:servMappings) {
			for(String pattern:sm.getPatterns()) {
				servMappingsMap.put(pattern,sm.getName());
			}
		}
	}
	
	//根据<url-pattern></url-pattern>获取<servlet-class></servlet-class>对应的Class名
	public String get_class(String pattern) {
			return servEntitiesMap.get(servMappingsMap.get(pattern));
	}
}
