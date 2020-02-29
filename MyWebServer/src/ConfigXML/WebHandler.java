package ConfigXML;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

/**
 * 3����д������Handler
 */
class WebHandler extends DefaultHandler {
	private ArrayList<ServletEntity> servEntities;			//������������Servlet����ʵ�����Class Name/Class Path
	private ArrayList<ServletMapping> servMappings;	//������������Servlet������ģʽ
	private ServletEntity servletEntiry;
	private ServletMapping  servMapping;
	private String tag;
	//���ڶ�ȡname
	// ��ֹ�����<δ����String name����ServletEntity����ServletMapping>ʱ����ɵ�����
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
		tag = qName;	//��
		if(null!=tag) {
			if(tag.equals("servlet")) {
				servEntities.add(servletEntiry);
			}else if(tag.equals("servlet-mapping")){
				servMappings.add(servMapping);
			}
		}
		tag = null;		//��
		//������Ϊnull��Ϊ��ʡ��</XXX>����Ŀո��
		//��endElement����Ҫͨ��tag������add��list��
		//��˽�add���̵Ĵ�����ڢٺ͢���
	}

	@Override
	public void endDocument() throws SAXException {
		System.out.println("------End------");
	}

	//��ȡ��������Servlet����ģʽ�Ĺ���
	public ArrayList<ServletMapping> getServMappings() {
		return servMappings;
	}

	//��ȡ��������Servlet����ʵ����·���Ĺ���
	public ArrayList<ServletEntity> getServEntities() {
		return servEntities;
	}




}
