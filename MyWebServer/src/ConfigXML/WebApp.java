package ConfigXML;

import Servlet.Servlet;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


/**
 * �����ͨ����ȡXML�ļ���֪��Ҫ��ȡ��Servlet��
 * Ȼ��ʹ�÷������
 * ͨ������url�������ؽ���ͬ���ܵ�Web Servletģ�鷵�ظ�������
 */
public class WebApp {
	private  static WebHandler handler;     //������

	static {
        try {
            //1����ȡ��������
            SAXParserFactory factory = SAXParserFactory.newInstance();
            //2���ӽ���������ȡ������
            SAXParser parser = factory.newSAXParser();

            //3����д������Handler����������ϸ����WebHandler���У�

            //4�������ĵ�Document ע�ᴦ����
            handler = new WebHandler();
            //5������
            parser.parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("ConfigXML/web.xml"), handler);

        } catch (Exception e) {
            System.out.println("Fail To Parse XML File");
        }
    }


    public static Servlet getServletByUrl(String url) {
        //6����ȡ����
        //ͨ��������handler������XML�����ļ�������ȡ����Ϣ����WebIndex����
        WebIndex indexTable = new WebIndex(handler.getServEntities(), handler.getServMappings());
        String className = indexTable.get_class("/"+url);
        System.out.println(className);

        //7��ͨ�������ȡ��
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



