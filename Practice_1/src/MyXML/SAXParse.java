package MyXML;

import java.awt.List;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXParse {
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		//SAX解析
		//1、获取解析工厂
		SAXParserFactory factory = SAXParserFactory.newInstance();
		//2、从解析工厂获取解析器
		SAXParser parser = factory.newSAXParser();

		//4、加载文档Document 注册处理器
		PersonHandler handler = new PersonHandler();
		//5、解析
		parser.parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("MyXML/person.xml"), handler);
		//6、获取数据
		ArrayList<Person> persons = handler.getPersons();
		System.out.println("size:\t"+persons.size());

		for(Person ps:persons) {
			System.out.println(ps.getName()+"-->"+ps.getAge());
		}
	}
}

//3、编写处理器
class PersonHandler extends DefaultHandler{
	private ArrayList<Person> persons;
	private Person person;
	private String tag;	//储存操作的标签名
	
	@Override
	public void startDocument() throws SAXException {
		System.out.println("---------Start---------");
		
		this.persons = new ArrayList<Person>();
	}
	
	public ArrayList<Person> getPersons() {
		return persons;
	}

	//qName:	标签名/元素名
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if(null!=qName) {
				tag  = qName;
			if(tag.equals("person")) {
				person = new Person();
			}
		}
	}
	

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		String context  = new String(ch, start, length).trim();

		if(null!=tag) {
			if(tag.equals("name")) {
				if(context.length()>0) {
					person.setName(context);
				}
			}else if (tag.equals("age")) {
				if(context.length()>0) {
					person.setAge(Integer.valueOf(context));
				}
			}
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		tag = qName;	//①
		if(tag!=null) {
			if(tag.equals("person")) {
				persons.add(person);
			}
		}
		tag = null;			//②
		//这里置为null是为了省略</XXX>后面的空格符
		//但endElement中需要通过tag将对象add到list中
		//因此将add过程的代码裹在①和②中
	}
	
	@Override
	public void endDocument() throws SAXException {
		System.out.println("---------End---------");
	}
	
}
