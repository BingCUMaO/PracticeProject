package ConfigXML;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * WebIndex:	������
 * ���������XML�ļ�������ȡ��Servlet��Ϣ
 * Servlet�ľ���ʵ�ֲ��ڴ˴���
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

	//������
	public WebIndex(ArrayList<ServletEntity> servEntities, ArrayList<ServletMapping> servMappings) {
		this.servEntities = servEntities;
		this.servMappings = servMappings;
		this.servEntitiesMap = new HashMap<String, String>();
		this.servMappingsMap = new HashMap<String, String>();
		
		appendToMap();
	}
	
	//�������õ���servEntities��servEntities�ֱ�ӳ�䵽Map�ϣ�����ͨ��<K,V>����
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
	
	//����<url-pattern></url-pattern>��ȡ<servlet-class></servlet-class>��Ӧ��Class��
	public String get_class(String pattern) {
			return servEntitiesMap.get(servMappingsMap.get(pattern));
	}
}
