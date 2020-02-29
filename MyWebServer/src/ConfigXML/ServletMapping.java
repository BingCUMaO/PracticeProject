package ConfigXML;

import java.util.HashSet;
import java.util.Set;

/**
 * 对应xml文件中的
 * <servlet-mapping>
		<servlet-name>login</servlet-name>
		<url-pattern>/login</url-pattern>
		<url-pattern>/g</url-pattern>
	</servlet-mapping>
	
 * @author BinGCU
 *
 */
public class ServletMapping {
	private String name;					//客户端请求模式的代号名
	private Set<String> patterns;		//客户端请求模式。每个模式对应着不同ServletEntity中的ClassName
	
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
