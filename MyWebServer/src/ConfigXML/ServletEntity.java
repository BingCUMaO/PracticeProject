package ConfigXML;

/**
 * 对应xml文件中的
 * <servlet>
		<servlet-name>login</servlet-name>
		<servlet-class>MyXML.LoginServlet</servlet-class>
	</servlet>
	
 * @author BinGCU
 *
 */
public class ServletEntity {
	private String name;		//客户端请求模式的代号名
	private String _class;		//用来存储Servlet具体实现类的Class Name/Class Path
	
	public ServletEntity() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String get_class() {
		return _class;
	}

	public void set_class(String _class) {
		this._class = _class;
	}
}
