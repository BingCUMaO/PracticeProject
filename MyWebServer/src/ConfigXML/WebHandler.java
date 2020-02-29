package ConfigXML;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

/**
 * 3、编写处理器Handler
 */
class WebHandler extends DefaultHandler {
	private ArrayList<ServletEntity> servEntities;			//用来管理所有Servlet具体实现类的Class Name/Class Path
	private ArrayList<ServletMapping> servMappings;	//用来管理所有Servlet的请求模式
	private ServletEntity servletEntiry;
	private ServletMapping  servMapping;
	private String tag;
	//用于读取name
	// 防止造成因<未声明String name是在ServletEntity还是ServletMapping>时所造成的歧义
	private boolean areMapping;

	public WebHandler() {
	}

	@Override
	public void startDocument() throws SAXException {
		System.out.println("------Start------");

		servEntities = new ArrayList<ServletEntity>();
		servMappings = new ArrayList<ServletMapping>();
		areMapping = false;
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if(null!=qName) {
			tag = qName;

			if(tag.equals("servlet")) {
				servletEntiry = new ServletEntity();
				areMapping = false;
			}else if(tag.equals("servlet-mapping")){
				servMapping = new ServletMapping();
				areMapping = true;
			}
		}
	}


	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		String context  = new String(ch,start,length);
		if(null!=tag) {
			if(tag.equals("servlet-name")&&!areMapping) {
				if(context.length()>0) {
					servletEntiry.setName(context);
				}
			}else if(tag.equals("servlet-class")) {
				if(context.length()>0) {
					servletEntiry.set_class(context);
				}
			}else if(tag.equals("servlet-name")&&areMapping) {
				if(context.length()>0) {
					servMapping.setName(context);
				}
			}else if(tag.equals("url-pattern")) {
				if(context.length()>0) {
					servMapping.addPattern(context);
				}
			}
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		tag = qName;	//①
		if(null!=tag) {
			if(tag.equals("servlet")) {
				servEntities.add(servletEntiry);
			}else if(tag.equals("servlet-mapping")){
				servMappings.add(servMapping);
			}
		}
		tag = null;		//②
		//这里置为null是为了省略</XXX>后面的空格符
		//但endElement中需要通过tag将对象add到list中
		//因此将add过程的代码裹在①和②中
	}

	@Override
	public void endDocument() throws SAXException {
		System.out.println("------End------");
	}

	//获取管理所有Servlet请求模式的工具
	public ArrayList<ServletMapping> getServMappings() {
		return servMappings;
	}

	//获取管理所有Servlet具体实现类路径的工具
	public ArrayList<ServletEntity> getServEntities() {
		return servEntities;
	}




}
