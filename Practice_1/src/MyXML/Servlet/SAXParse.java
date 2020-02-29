package MyXML.Servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXParse {
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		//1、获取解析工厂
		SAXParserFactory factory = SAXParserFactory.newInstance();
		//2、从解析工厂获取解析器
		SAXParser parser = factory.newSAXParser();

		//4、加载文档Document 注册处理器
		WebHandler handler = new WebHandler();
		//5、解析
		parser.parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("MyXML/Servlet/web.xml"), handler);
		//6、获取内容
		WebIndex wi = new WebIndex(handler.getServEntities(),handler.getServMappings());
		String className = wi.get_class("/login");
		System.out.println(className);
		
		//7、通过反射获取类
		Class<?> c = Class.forName(className);
		LoginServlet ls = (LoginServlet) c.getConstructor().newInstance();
		ls.service();
	}
}

//3、编写处理器
class WebHandler extends DefaultHandler{
	private ArrayList<ServletEntity> servEntities;
	private ArrayList<ServletMapping> servMappings;
	private ServletEntity servletEntiry;
	private ServletMapping  servMapping;
	private String tag;
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

	public ArrayList<ServletMapping> getServMappings() {
		return servMappings;
	}

	public ArrayList<ServletEntity> getServEntities() {
		return servEntities;
	}


	
	
}



