package ConfigXML;

import java.util.HashSet;
import java.util.Set;

/**
 * ��Ӧxml�ļ��е�
 * <servlet-mapping>
		<servlet-name>login</servlet-name>
		<url-pattern>/login</url-pattern>
		<url-pattern>/g</url-pattern>
	</servlet-mapping>
	
 * @author BinGCU
 *
 */
public class ServletMapping {
	private String name;					//�ͻ�������ģʽ�Ĵ�����
	private Set<String> patterns;		//�ͻ�������ģʽ��ÿ��ģʽ��Ӧ�Ų�ͬServletEntity�е�ClassName
	
	public ServletMapping() {
		patterns = new HashSet<String>();
	}
	
	public void addPattern(String pattern) {
		this.patterns.add(pattern);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<String> getPatterns() {
		return patterns;
	}

	
}
