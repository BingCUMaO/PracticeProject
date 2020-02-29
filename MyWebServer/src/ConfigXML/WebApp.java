package ConfigXML;

import Servlet.Servlet;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


/**
 * 这个类通过读取XML文件后知晓要获取的Servlet类
 * 然后使用反射机制
 * 通过传参url，来灵活地将不同功能的Web Servlet模块返回给调用者
 */
public class WebApp {
	private  static WebHandler handler;     //处理器

	static {
        try {
            //1、获取解析工厂
            SAXParserFactory factory = SAXParserFactory.newInstance();
            //2、从解析工厂获取解析器
            SAXParser parser = factory.newSAXParser();

            //3、编写处理器Handler（第三步被细化于WebHandler类中）

            //4、加载文档Document 注册处理器
            handler = new WebHandler();
            //5、解析
            parser.parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("ConfigXML/web.xml"), handler);

        } catch (Exception e) {
            System.out.println("Fail To Parse XML File");
        }
    }


    public static Servlet getServletByUrl(String url) {
        //6、获取内容
        //通过处理器handler，将从XML配置文件中所读取的信息交给WebIndex管理
        WebIndex indexTable = new WebIndex(handler.getServEntities(), handler.getServMappings());
        String className = indexTable.get_class("/"+url);
        System.out.println(className);

        //7、通过反射获取类
		Class<?> c = null;
        Servlet app = null;
		try {
			c = Class.forName(className);
			 app = (Servlet) c.getConstructor().newInstance();
		} catch (Exception e) {
			System.out.println("Fail To Get Servlet!");
		}

		return app;
    }
}



