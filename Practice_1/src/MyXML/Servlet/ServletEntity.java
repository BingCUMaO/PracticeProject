package MyXML.Servlet;

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
	private String name;
	private String _class;
	
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
