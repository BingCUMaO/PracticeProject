package ConfigXML;

/**
 * ��Ӧxml�ļ��е�
 * <servlet>
		<servlet-name>login</servlet-name>
		<servlet-class>MyXML.LoginServlet</servlet-class>
	</servlet>
	
 * @author BinGCU
 *
 */
public class ServletEntity {
	private String name;		//�ͻ�������ģʽ�Ĵ�����
	private String _class;		//�����洢Servlet����ʵ�����Class Name/Class Path
	
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
